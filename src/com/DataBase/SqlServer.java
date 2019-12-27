package com.DataBase;

import com.Instance.*;
import com.Instance.Class;
import javafx.util.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Sextet;

import java.sql.*;
import java.util.ArrayList;


public class SqlServer {
    private Connection connection = null;

    private String SQL_SELECT = "select sth from table ";

    public SqlServer(SqlUser sqlUser) {
        this.connection = sqlUser.getConnection();
    }

    public String modifyStr(String str) {
        return "'" + str + "'";
    }

    public boolean executeUpdateProcedure(String sql) {
        System.out.println(sql);
        try {
            Statement stm = connection.createStatement();
            stm.execute(sql);
            int result = stm.getUpdateCount();
            if (result > 0) {
                System.out.println("SqlServer : SQL EXEC 执行成功");
                return true;
            } else {
                stm.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SqlServer : SQL EXEC 失败" + e.toString());
            return false;
        }
    }

    public ResultSet executeQueryProcedure(String sql) {
        System.out.println(sql);
        try {
            Statement stm = connection.createStatement();
            stm.execute(sql);
            ResultSet result = stm.getResultSet();
            if (result != null) {
                System.out.println("SqlServer : SQL EXEC 执行成功");
                return result;
            } else {
                stm.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SqlServer : SQL EXEC 失败" + e.toString());
            return null;
        }
    }


    private boolean executeUpdate(String sql) {
        System.out.println(sql);
        try {
            Statement stm = connection.createStatement();
            int result = stm.executeUpdate(sql);
            if (result > 0) {
                System.out.println("SqlServer : SQL Update 执行成功");
                return true;
            } else {
                stm.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SqlServer : SQL Update 失败" + e.toString());
            return false;
        }
    }

    public ResultSet executeQuery(String sql) {
        System.out.println(sql);
        try {
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            System.out.println("SqlServer : SQL Query 成功");
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SqlServer : SQL Query 失败" + e.toString());
            return null;
        }
    }

    public boolean checkUser(String userName, String userPassword) {
        String sql = "select dbo.checkUser( " + modifyStr(userName) + " , " + modifyStr(userPassword) + " )";
        ResultSet resultSet = executeQuery(sql);
        try {
            resultSet.next();
            int result = resultSet.getInt(0);
            return result == 1? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SqlServer : SQL 检验用户 失败" + e.toString());
            return false;
        }
    }

    /******************************/
    /**
     * 超级管理员
     **/

    public boolean rootInsertUser(User user) {
        String sql = "exec dbo.superAddUser " + modifyStr(user.getUserName()) + "," + modifyStr(user.getUserPassword())
                + "," + String.valueOf(user.getUserType());
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入用户成功");
            return true;
        } else {
            System.out.println("表中已有该用户信息，插入用户失败");
            return false;
        }
    }

    public boolean rootDeleteUser(String userName) {
        String sql = "exec dbo.superDeleteUser " + modifyStr(userName);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除用户成功");
            return true;
        } else {
            System.out.println("表中无该用户信息，删除用户失败");
            return false;
        }
    }

    public boolean rootInsertManager(Manager manager) {
        String sql = "exec dbo.superAddManager " + modifyStr(manager.getManagerName()) + "," + modifyStr(manager.getManagerNo())
                + "," + modifyStr(manager.getManagerPhone());
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入管理员成功");
            User user = new User(manager.getManagerNo(), manager.getManagerNo(), 1);
            rootInsertUser(user);
            return true;
        } else {
            System.out.println("表中已有该管理员信息，插入管理员失败");
            return false;
        }
    }

    public boolean rootDeleteManager(String managerNo) {
        String sql = "exec dbo.superDeleteManager " + modifyStr(managerNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除管理员成功");
            rootDeleteUser(managerNo);
            return true;
        } else {
            System.out.println("表中无该管理员信息，删除管理员失败");
            return false;
        }
    }

    public boolean rootUpdateUser(User user) {
        String sql = SQL_SELECT.replace("sth", "*");
        sql = sql.replace("table", "Users");
        sql = sql + "where userName = " + modifyStr(user.getUserName());
        ResultSet resultSet = executeQuery(sql);
        if (resultSet == null) {
            System.out.println("表中无该用户信息，更新用户信息失败");
            return false;
        } else {
            try {
                resultSet.next();
                String nUpassword = resultSet.getString("userPassword");
                int nUtype = resultSet.getInt("userType");
                if (!user.getUserPassword().equals("")) {
                    nUpassword = user.getUserPassword();
                }
                if (user.getUserType() != -1) {
                    nUtype = user.getUserType();
                }
                String exesql = "exec dbo.superUpdateUser " + modifyStr(user.getUserName()) + "," + modifyStr(nUpassword)
                        + "," + String.valueOf(nUtype);
                boolean result = executeUpdateProcedure(exesql);
                if (result) {
                    System.out.println("更新用户信息成功");
                    return true;
                } else {
                    System.out.println("表中无该用户信息，更新用户信息失败");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean rootUpdateManager(Manager manager) {
        String sql = SQL_SELECT.replace("sth", "*");
        sql = sql.replace("table", "Manager");
        sql = sql + "where managerNo = " + modifyStr(manager.getManagerNo());
        ResultSet resultSet = executeQuery(sql);
        if (resultSet == null) {
            System.out.println("表中无该管理员信息，更新管理员信息失败");
            return false;
        } else {
            try {
                resultSet.next();
                String nMname = resultSet.getString("managerName");
                String nMphone = resultSet.getString("managerPhone");
                if (!manager.getManagerName().equals("")) {
                    nMname = manager.getManagerName();
                }
                if (!manager.getManagerPhone().equals("")) {
                    nMphone = manager.getManagerPhone();
                }
                String exesql = "exec dbo.superUpdateManager " + modifyStr(nMname) + "," + modifyStr(manager.getManagerNo())
                        + "," + modifyStr(nMphone);
                boolean result = executeUpdateProcedure(exesql);
                if (result) {
                    System.out.println("更新管理员信息成功");
                    return true;
                } else {
                    System.out.println("表中无该管理员信息，更新管理员信息失败");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public ArrayList<User> rootSelectUser() {
        String sql = "exec dbo.superSelectUsers ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<User> userList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询用户表失败");
        } else {
            try {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getString("userName"),
                            resultSet.getString("userPassword"),
                            resultSet.getInt("userType")
                    );
                    userList.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询用户表失败");
            }
        }
        return userList;
    }

    public ArrayList<Manager> rootSelectManager() {
        String sql = "exec dbo.superSelectManager ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Manager> managerList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询管理员表失败");
        } else {
            try {
                while (resultSet.next()) {
                    Manager manager = new Manager(
                            resultSet.getString("managerName"),
                            resultSet.getString("managerNo"),
                            resultSet.getString("managerPhone")
                    );
                    managerList.add(manager);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询管理员表失败");
            }
        }
        return managerList;
    }

    /******************************/
    /**
     * 管理员
     **/
    public boolean managerInsertCollege(College college) {
        String sql = "exec dbo.addCollege " + modifyStr(college.getCollegeNo()) + "," + modifyStr(college.getCollegeName());
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入系成功");
            return true;
        } else {
            System.out.println("表中已有该系信息，插入系失败");
            return false;
        }
    }

    public boolean managerDeleteCollege(String collegeNo) {
        String sql = "exec dbo.deleteCollege " + modifyStr(collegeNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除系成功");
            managerDeleteCollegeManager(collegeNo);
            String dsql = "delete from CCTable where collegeNo = " + modifyStr(collegeNo);
            executeUpdate(dsql);
            return true;
        } else {
            System.out.println("表中无该系信息，删除系失败");
            return false;
        }
    }

    public boolean managerUpdateCollege(College college) {
        String sql = SQL_SELECT.replace("sth", "*");
        sql = sql.replace("table", "College");
        sql = sql + "where collegeNo = " + modifyStr(college.getCollegeNo());
        ResultSet resultSet = executeQuery(sql);
        if (resultSet == null) {
            System.out.println("表中无该系信息，更新系信息失败");
            return false;
        } else {
            try {
                resultSet.next();
                String nCname = resultSet.getString("collegeName");
                if (!college.getCollegeName().equals("")) {
                    nCname = college.getCollegeName();
                }
                String exesql = "exec dbo.updateCollege " + modifyStr(college.getCollegeNo()) + "," + modifyStr(nCname);
                boolean result = executeUpdateProcedure(exesql);
                if (result) {
                    System.out.println("更新系信息成功");
                    return true;
                } else {
                    System.out.println("表中无该系信息，更新系信息失败");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean managerInsertClass(Class classObj) {
        String sql = "exec dbo.addClass " + modifyStr(classObj.getClassNo()) + "," + modifyStr(classObj.getClassName())
                + "," + modifyStr(classObj.getClassProName()) + "," + modifyStr(classObj.getClassCollegeNo());
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入班级成功");
            return true;
        } else {
            System.out.println("表中已有该班级信息，插入班级失败");
            return false;
        }
    }

    public boolean managerDeleteClass(String classNo) {
        String sql = "exec dbo.deleteClass " + modifyStr(classNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除班级成功");
            String dsql = "delete from CSTable where classNo = " + modifyStr(classNo);
            executeUpdate(dsql);
            return true;
        } else {
            System.out.println("表中无该班级信息，删除班级失败");
            return false;
        }
    }

    public boolean managerUpdateClass(Class classObj) {
        String sql = SQL_SELECT.replace("sth", "*");
        sql = sql.replace("table", "Class");
        sql = sql + "where classNo = " + modifyStr(classObj.getClassNo());
        ResultSet resultSet = executeQuery(sql);
        if (resultSet == null) {
            System.out.println("表中无该班级信息，更新班级信息失败");
            return false;
        } else {
            try {
                resultSet.next();
                String nCname = resultSet.getString("className");
                String nCproname = resultSet.getString("classProName");
                String nCcolno = resultSet.getString("classColNo");
                if (!classObj.getClassName().equals("")) {
                    nCname = classObj.getClassName();
                }
                if (!classObj.getClassProName().equals("")) {
                    nCproname = classObj.getClassProName();
                }
                if (!classObj.getClassCollegeNo().equals("")) {
                    nCcolno = classObj.getClassCollegeNo();
                }
                String exesql = "exec dbo.updateClass " + modifyStr(classObj.getClassNo()) + "," + modifyStr(nCname)
                        + "," + modifyStr(nCproname) + "," + modifyStr(nCcolno);
                boolean result = executeUpdateProcedure(exesql);
                if (result) {
                    System.out.println("更新班级信息成功");
                    return true;
                } else {
                    System.out.println("表中无该班级信息，更新班级信息失败");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean managerInsertTeacher(Teacher teacher) {
        String sql = "exec dbo.addTeacher " + modifyStr(teacher.getTeacherNo()) + "," + modifyStr(teacher.getTeacherName())
                + "," + modifyStr(teacher.getTeacherResDir()) + "," + modifyStr(teacher.getTeacherCollegeNo());
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入教师成功");
            return true;
        } else {
            System.out.println("表中已有该教师信息，插入教师失败");
            return false;
        }
    }

    public boolean managerDeleteTeacher(String teacherNo) {
        String sql = "exec dbo.deleteTeacher " + modifyStr(teacherNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除教师成功");
            String dsql = "delete from TCTable where teacherNo = " + modifyStr(teacherNo);
            executeUpdate(dsql);
            return true;
        } else {
            System.out.println("表中无该教师信息，删除教师失败");
            return false;
        }
    }

    public boolean managerUpdateTeacher(Teacher teacher) {
        String sql = SQL_SELECT.replace("sth", "*");
        sql = sql.replace("table", "Teacher");
        sql = sql + "where teacherNo = " + modifyStr(teacher.getTeacherNo());
        ResultSet resultSet = executeQuery(sql);
        if (resultSet == null) {
            System.out.println("表中无该教师信息，更新教师信息失败");
            return false;
        } else {
            try {
                resultSet.next();
                String nTname = resultSet.getString("teacherName");
                String nTresdir = resultSet.getString("teacherResDir");
                String nTcolno = resultSet.getString("teacherColNo");
                if (!teacher.getTeacherName().equals("")) {
                    nTname = teacher.getTeacherName();
                }
                if (!teacher.getTeacherResDir().equals("")) {
                    nTresdir = teacher.getTeacherResDir();
                }
                if (!teacher.getTeacherCollegeNo().equals("")) {
                    nTcolno = teacher.getTeacherCollegeNo();
                }
                String exesql = "exec dbo.updateTeacher " + modifyStr(teacher.getTeacherNo()) + "," + modifyStr(nTname)
                        + "," + modifyStr(nTresdir) + "," + modifyStr(nTcolno);
                boolean result = executeUpdateProcedure(exesql);
                if (result) {
                    System.out.println("更新教师信息成功");
                    return true;
                } else {
                    System.out.println("表中无该教师信息，更新教师信息失败");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean managerInsertStudent(Student student) {
        String sql = "exec dbo.addStudent " + modifyStr(student.getStudentNo()) + "," + modifyStr(student.getStudentName())
                + "," + modifyStr(student.getStudentAge()) + "," + modifyStr(student.getStudentSex())
                + "," + modifyStr(student.getStudentNation()) + "," + modifyStr(student.getStudentBirthday())
                + "," + modifyStr(student.getStudentInsNo()) + "," + modifyStr(student.getStudentClassNo());
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入学生成功");
            rootInsertUser(new User(student.getStudentNo(), student.getStudentNo(), 2));
            return true;
        } else {
            System.out.println("表中已有该学生信息，插入学生失败");
            return false;
        }
    }

    public boolean managerDeleteStudent(String studentNo) {
        String sql = "exec dbo.deleteStudent " + modifyStr(studentNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除学生成功");
            rootDeleteUser(studentNo);
            String dsql = "delete from SCTSTable where studentNo = " + modifyStr(studentNo);
            executeUpdate(dsql);
            return true;
        } else {
            System.out.println("表中无该学生信息，删除学生失败");
            return false;
        }
    }

    public boolean managerUpdateStudent(Student student) {
        String sql = SQL_SELECT.replace("sth", "*");
        sql = sql.replace("table", "Student");
        sql = sql + "where studentNo = " + modifyStr(student.getStudentNo());
        ResultSet resultSet = executeQuery(sql);
        if (resultSet == null) {
            System.out.println("表中无该学生信息，更新学生信息失败");
            return false;
        } else {
            try {
                resultSet.next();
                String nSname = resultSet.getString("studentName");
                String nSage = resultSet.getString("studentAge");
                String nSsex = resultSet.getString("studentSex");
                String nSnation = resultSet.getString("studentNation");
                String nSbirthday = resultSet.getString("studentBirthday");
                String nSinsno = resultSet.getString("studentInsNo");
                String nSclassno = resultSet.getString("studentClassNo");
                if (!student.getStudentName().equals("")) {
                    nSname = student.getStudentName();
                }
                if (!student.getStudentAge().equals("")) {
                    nSage = student.getStudentAge();
                }
                if (!student.getStudentSex().equals("")) {
                    nSsex = student.getStudentSex();
                }
                if (!student.getStudentNation().equals("")) {
                    nSnation = student.getStudentNation();
                }
                if (!student.getStudentBirthday().equals("")) {
                    nSbirthday = student.getStudentBirthday();
                }
                if (!student.getStudentInsNo().equals("")) {
                    nSinsno = student.getStudentInsNo();
                }
                if (!student.getStudentClassNo().equals("")) {
                    nSclassno = student.getStudentClassNo();
                }
                String exesql = "exec dbo.updateStudent " + modifyStr(student.getStudentNo()) + "," + modifyStr(nSname)
                        + "," + modifyStr(nSage) + "," + modifyStr(nSsex) + "," + modifyStr(nSnation) + "," + modifyStr(nSbirthday)
                        + "," + modifyStr(nSinsno) + "," + modifyStr(nSclassno);
                boolean result = executeUpdateProcedure(exesql);
                if (result) {
                    System.out.println("更新学生信息成功");
                    return true;
                } else {
                    System.out.println("表中无该学生信息，更新学生信息失败");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean managerInsertCourse(Course course) {
        String sql = "exec dbo.addCourse " + modifyStr(course.getCourseNo()) + "," + modifyStr(course.getCourseName())
                + "," + String.valueOf(course.getCourseCredict()) + "," + String.valueOf(course.getCoursePeriod());
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入课程成功");
            return true;
        } else {
            System.out.println("表中已有该课程信息，插入课程失败");
            return false;
        }
    }

    public boolean managerDeleteCourse(String courseNo) {
        String sql = "exec dbo.deleteCourse " + modifyStr(courseNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除课程成功");
            String dsql1 = "delete from CCTable where courseNo = " + modifyStr(courseNo);
            executeUpdate(dsql1);
            String dsql2 = "delete from TCTable where courseNo = " + modifyStr(courseNo);
            executeUpdate(dsql2);
            String dsql3 = "delete from SCTSTable where courseNo = " + modifyStr(courseNo);
            executeUpdate(dsql3);
            return true;
        } else {
            System.out.println("表中无该课程信息，删除课程失败");
            return false;
        }
    }

    public boolean managerUpdateCourse(Course course) {
        String sql = SQL_SELECT.replace("sth", "*");
        sql = sql.replace("table", "Course");
        sql = sql + "where courseNo = " + modifyStr(course.getCourseNo());
        ResultSet resultSet = executeQuery(sql);
        if (resultSet == null) {
            System.out.println("表中无该班级信息，更新班级信息失败");
            return false;
        } else {
            try {
                resultSet.next();
                String nCname = resultSet.getString("courseName");
                int nCcredict = resultSet.getInt("courseCredict");
                int nCperiod = resultSet.getInt("coursePeriod");
                if (!course.getCourseName().equals("")) {
                    nCname = course.getCourseName();
                }
                if (course.getCourseCredict() != -1) {
                    nCcredict = course.getCourseCredict();
                }
                if (course.getCoursePeriod() != -1) {
                    nCperiod = course.getCoursePeriod();
                }

                String exesql = "exec dbo.updateCourse " + modifyStr(course.getCourseNo()) + "," + modifyStr(nCname)
                        + "," + String.valueOf(nCcredict) + "," + String.valueOf(nCperiod);
                boolean result = executeUpdateProcedure(exesql);
                if (result) {
                    System.out.println("更新课程信息成功");
                    return true;
                } else {
                    System.out.println("表中无该课程信息，更新课程信息失败");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean managerInsertCollegeManager(String collegeNo, String teacherNo) {
        String sql = "exec dbo.addCollegeManager " + modifyStr(collegeNo) + "," + modifyStr(teacherNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入系-系主任成功");
            return true;
        } else {
            System.out.println("表中已有该系-系主任信息，插入系-系主任失败");
            return false;
        }
    }

    public boolean managerDeleteCollegeManager(String collegeNo) {
        String sql = "exec dbo.deleteCollegeManager " + modifyStr(collegeNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除系-系主任成功");
            return true;
        } else {
            System.out.println("表中无该系-系主任信息，删除系-系主任失败");
            return false;
        }
    }

    public boolean managerUpdateCollegeManager(String collegeNo, String teacherNo) {
        String sql = SQL_SELECT.replace("sth", "*");
        sql = sql.replace("table", "CTTable");
        sql = sql + "where collegeNo = " + modifyStr(collegeNo);
        ResultSet resultSet = executeQuery(sql);
        if (resultSet == null) {
            System.out.println("表中无该系-系主任信息，更新系-系主任信息失败");
            return false;
        } else {
            try {
                resultSet.next();
                String nTno = resultSet.getString("teacherNo");
                if (!teacherNo.equals("")) {
                    nTno = teacherNo;
                }

                String exesql = "exec dbo.updateCollegeManager " + modifyStr(collegeNo) + "," + modifyStr(nTno);
                boolean result = executeUpdateProcedure(exesql);
                if (result) {
                    System.out.println("更新系-系主任信息成功");
                    return true;
                } else {
                    System.out.println("表中无该系-系主任信息，更新系-系主任信息失败");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean managerInsertCollegeCourse(String collegeNo, String courseNo) {
        String sql = "exec dbo.addCollegeCourse " + modifyStr(collegeNo) + "," + modifyStr(courseNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入系-课程成功");
            return true;
        } else {
            System.out.println("表中已有该系-课程信息，插入系-课程失败");
            return false;
        }
    }

    public boolean managerDeleteCollegeCourse(String collegeNo, String courseNo) {
        String sql = "exec dbo.deleteCollegeCourse " + modifyStr(collegeNo) + "," + modifyStr(courseNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除系-课程成功");
            return true;
        } else {
            System.out.println("表中无该系-课程信息，删除系-课程失败");
            return false;
        }
    }

    public boolean managerInsertClassMonitor(String classNo, String studentNo) {
        String sql = "exec dbo.addClassMonitor " + modifyStr(classNo) + "," + modifyStr(studentNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入班级-班长成功");
            return true;
        } else {
            System.out.println("表中已有该班级-班长信息，插入班级-班长失败");
            return false;
        }
    }

    public boolean managerDeleteClassMonitor(String classNo, String studentNo) {
        String sql = "exec dbo.deleteClassMonitor " + modifyStr(classNo) + "," + modifyStr(studentNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除班级-班长成功");
            return true;
        } else {
            System.out.println("表中无该系信息，删除班级-班长失败");
            return false;
        }
    }

    public boolean managerUpdateClassMonitor(String classNo, String studentNo) {
        String sql = SQL_SELECT.replace("sth", "*");
        sql = sql.replace("table", "CSTable");
        sql = sql + "where classNo = " + modifyStr(classNo);
        ResultSet resultSet = executeQuery(sql);
        if (resultSet == null) {
            System.out.println("表中无该班级-班长信息，更新班级-班长信息失败");
            return false;
        } else {
            try {
                resultSet.next();
                String nSno = resultSet.getString("studentNo");
                if (!studentNo.equals("")) {
                    nSno = studentNo;
                }
                String exesql = "exec dbo.updateClassMonitor " + modifyStr(classNo) + "," + modifyStr(nSno);
                boolean result = executeUpdateProcedure(exesql);
                if (result) {
                    System.out.println("更新班级-班长信息成功");
                    return true;
                } else {
                    System.out.println("表中无该班级-班长信息，更新班级-班长信息失败");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean managerInsertTeacherCourse(String teacherNo, String courseNo) {
        String sql = "exec dbo.addTeacherCourse " + modifyStr(teacherNo) + "," + modifyStr(courseNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入教师-课程成功");
            return true;
        } else {
            System.out.println("表中已有该教师-课程信息，插入教师-课程失败");
            return false;
        }
    }

    public boolean managerDeleteTeacherCourse(String teacherNo, String courseNo) {
        String sql = "exec dbo.deleteTeacherCourse " + modifyStr(teacherNo) + "," + modifyStr(courseNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除教师-课程成功");
            return true;
        } else {
            System.out.println("表中无该教师-课程信息，删除教师-课程失败");
            return false;
        }
    }

    public boolean managerInsertStuTeaCouSco(String studentNo, String courseNo, String teacherNo, int score) {
        String sql = "exec dbo.addStuTeaCouSco " + modifyStr(studentNo) + "," + modifyStr(courseNo)
                + "," + modifyStr(teacherNo) + "," + String.valueOf(score);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("插入学生-课程-教师-成绩成功");
            return true;
        } else {
            System.out.println("表中已有该学生-课程-教师-成绩信息，插入学生-课程-教师-成绩失败");
            return false;
        }
    }

    public boolean managerDeleteStuCouTeaSco(String studentNo, String courseNo) {
        String sql = "exec dbo.deleteStuCouTeaSco " + modifyStr(studentNo) + "," + modifyStr(courseNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("删除学生-课程-教师-成绩成功");
            return true;
        } else {
            System.out.println("表中无该学生-课程-教师-成绩信息，删除学生-课程-教师-成绩失败");
            return false;
        }
    }

    public boolean managerUpdateStuCouTeaSco(String studentNo, String courseNo, String teacherNo, int score) {
        String sql = SQL_SELECT.replace("sth", "*");
        sql = sql.replace("table", "SCTSTable");
        sql = sql + "where studentNo = " + modifyStr(studentNo) + " and " + "courseNo = " + modifyStr(courseNo);
        ResultSet resultSet = executeQuery(sql);
        if (resultSet == null) {
            System.out.println("表中无该学生-课程-教师-成绩信息，更新学生-课程-教师-成绩信息失败");
            return false;
        } else {
            try {
                resultSet.next();
                String nTno = resultSet.getString("teacherNo");
                int nScore = resultSet.getInt("score");
                if (!teacherNo.equals("")) {
                    nTno = teacherNo;
                }
                if (score != -1) {
                    nScore = score;
                }

                String exesql = "exec dbo.updateStuCouTeaSco " + modifyStr(studentNo) + "," + modifyStr(courseNo) + ","
                        + modifyStr(nTno) + "," + String.valueOf(nScore);
                boolean result = executeUpdateProcedure(exesql);
                if (result) {
                    System.out.println("更新学生-课程-教师-成绩信息成功");
                    return true;
                } else {
                    System.out.println("表中无该学生-课程-教师-成绩信息，更新学生-课程-教师-成绩信息失败");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean managerUpdatePassword(String userName, String userOldPassword, String userNewPassword) {
        String exesql = "exec dbo.updatePassword " + modifyStr(userName) + "," + modifyStr(userOldPassword) + ","
                + modifyStr(userNewPassword);
        boolean result = executeUpdateProcedure(exesql);
        if (result) {
            System.out.println("更新密码成功");
            return true;
        } else {
            System.out.println("表中无该用户信息或旧密码不正确，更新密码失败");
            return false;
        }
    }

    public ArrayList<College> managerSelectCollege() {
        String sql = "exec dbo.selectCollege ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<College> collegeList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询系表失败");
        } else {
            try {
                while (resultSet.next()) {
                    College college = new College(
                            resultSet.getString("collegeName"),
                            resultSet.getString("collegeNo")
                    );
                    collegeList.add(college);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询系表失败");
            }
        }
        return collegeList;
    }

    public ArrayList<Class> managerSelectClass() {
        String sql = "exec dbo.selectClass ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Class> classList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询班级表失败");
        } else {
            try {
                while (resultSet.next()) {
                    Class classObj = new Class(
                            resultSet.getString("className"),
                            resultSet.getString("classNo"),
                            resultSet.getString("classProName"),
                            resultSet.getString("classColNo")
                    );
                    classList.add(classObj);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询班级表失败");
            }
        }
        return classList;
    }

    public ArrayList<Teacher> managerSelectTeacher() {
        String sql = "exec dbo.selectTeacher ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Teacher> teacherList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询教师表失败");
        } else {
            try {
                while (resultSet.next()) {
                    Teacher teacher = new Teacher(
                            resultSet.getString("teacherName"),
                            resultSet.getString("teacherNo"),
                            resultSet.getString("teacherResDir"),
                            resultSet.getString("teacherColNo")
                    );
                    teacherList.add(teacher);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询教师表失败");
            }
        }
        return teacherList;
    }

    public ArrayList<Student> managerSelectStudent() {
        String sql = "exec dbo.selectStudent ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Student> studentList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询学生表失败");
        } else {
            try {
                while (resultSet.next()) {
                    Student student = new Student(
                            resultSet.getString("studentName"),
                            resultSet.getString("studentNo"),
                            resultSet.getString("studentAge"),
                            resultSet.getString("studentSex"),
                            resultSet.getString("studentNation"),
                            resultSet.getString("studentBirthday"),
                            resultSet.getString("studentInsNo"),
                            resultSet.getString("studentClassNo")
                    );
                    studentList.add(student);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询学生表失败");
            }
        }
        return studentList;
    }

    public ArrayList<Course> managerSelectCourse() {
        String sql = "exec dbo.selectCourse ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Course> courseList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询课程表失败");
        } else {
            try {
                while (resultSet.next()) {
                    Course course = new Course(
                            resultSet.getString("courseName"),
                            resultSet.getString("courseNo"),
                            resultSet.getInt("courseCredict"),
                            resultSet.getInt("coursePeriod")
                    );
                    courseList.add(course);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询课程表失败");
            }
        }
        return courseList;
    }

    public ArrayList<Pair<String, String>> managerSelectCollegeManager() {
        String sql = "exec dbo.selectCollegeManager ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Pair<String, String>> collegeManagerList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询系-系主任表失败");
        } else {
            try {
                while (resultSet.next()) {
                    Pair<String, String> collegeManager = new Pair<>(
                            resultSet.getString("collegeNo"),
                            resultSet.getString("teacherNo")
                    );
                    collegeManagerList.add(collegeManager);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询系-系主任表失败");
            }
        }
        return collegeManagerList;
    }

    public ArrayList<Pair<String, String>> managerSelectCollegeCourse() {
        String sql = "exec dbo.selectCollegeCourse ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Pair<String, String>> collegeCourseList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询系-课程表失败");
        } else {
            try {
                while (resultSet.next()) {
                    Pair<String, String> collegeCourse = new Pair<>(
                            resultSet.getString("collegeNo"),
                            resultSet.getString("courseNo")
                    );
                    collegeCourseList.add(collegeCourse);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询系-课程表失败");
            }
        }
        return collegeCourseList;
    }

    public ArrayList<Pair<String, String>> managerSelectClassMonitor() {
        String sql = "exec dbo.selectClassMonitor ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Pair<String, String>> classMonitorList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询班级-班长表失败");
        } else {
            try {
                while (resultSet.next()) {
                    Pair<String, String> classMonitor = new Pair<>(
                            resultSet.getString("classNo"),
                            resultSet.getString("studentNo")
                    );
                    classMonitorList.add(classMonitor);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询班级-班长表失败");
            }
        }
        return classMonitorList;
    }

    public ArrayList<Pair<String, String>> managerSelectTeacherCourse() {
        String sql = "exec dbo.selectTeacherCourse ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Pair<String, String>> teacherCourseList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询教师-课程表失败");
        } else {
            try {
                while (resultSet.next()) {
                    Pair<String, String> teacherCourse = new Pair<>(
                            resultSet.getString("teacherNo"),
                            resultSet.getString("courseNo")
                    );
                    teacherCourseList.add(teacherCourse);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询教师-课程表失败");
            }
        }
        return teacherCourseList;
    }

    public ArrayList<Quartet<String, String, String, Integer>> managerSelectStuCouTeaSco() {
        String sql = "exec dbo.selectStuCouTeaSco ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Quartet<String, String, String, Integer>> stucouteascoList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询学生-课程-教师-成绩失败");
        } else {
            try {
                while (resultSet.next()) {
                    Quartet<String, String, String, Integer> stucouteasco = new Quartet<>(
                            resultSet.getString("studentNo"),
                            resultSet.getString("courseNo"),
                            resultSet.getString("teacherNo"),
                            resultSet.getInt("score")
                    );
                    stucouteascoList.add(stucouteasco);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询学生-课程-教师-成绩表失败");
            }
        }
        return stucouteascoList;
    }


    /******************************/

    /**
     * 学生
     **/
    public boolean studentInsertSCTSTable(String studentNo, String courseNo, String teacherNo) {
        String sql = "exec dbo.studentAddSCTTable " + modifyStr(studentNo) + "," + modifyStr(courseNo)
                + "," + modifyStr(teacherNo) + "," + String.valueOf(0);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("选课成功");
            return true;
        } else {
            System.out.println("表中已有该选课信息，选课失败");
            return false;
        }
    }

    public boolean studentDeleteSCTSTable(String studentNo, String courseNo) {
        String sql = "exec dbo.studentDeleteSCTTable " + modifyStr(studentNo) + "," + modifyStr(courseNo);
        boolean result = executeUpdateProcedure(sql);
        if (result) {
            System.out.println("退课成功");
            return true;
        } else {
            System.out.println("表中无该选课信息，退课失败");
            return false;
        }
    }

    public boolean studentUpdatePassword(String userName, String userOldPassword, String userNewPassword) {
        String exesql = "exec dbo.updatePassword " + modifyStr(userName) + "," + modifyStr(userOldPassword) + ","
                + modifyStr(userNewPassword);
        boolean result = executeUpdateProcedure(exesql);
        if (result) {
            System.out.println("更新密码成功");
            return true;
        } else {
            System.out.println("表中无该用户信息或旧密码不正确，更新密码失败");
            return false;
        }
    }

    public ArrayList<Quartet<String, String, String, String>> studentSelectCollege(String studentNo) {
        String sql = "exec dbo.studentSelectCollege " + modifyStr(studentNo);
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Quartet<String, String, String, String>> studentCollegeList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询学生所在系信息失败");
        } else {
            try {
                while (resultSet.next()) {
                    Quartet<String, String, String, String> studentCollege = new Quartet<>(
                            resultSet.getString("collegeNo"),
                            resultSet.getString("collegeName"),
                            resultSet.getString("teacherNo"),
                            resultSet.getString("teacherName")
                    );
                    studentCollegeList.add(studentCollege);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询学生所在系信息失败");
            }
        }
        return studentCollegeList;
    }

    public ArrayList<Sextet<String, String, String, String, String, String>> studentSelectClass(String studentNo) {
        String sql = "exec dbo.studentSelectClass " + modifyStr(studentNo);
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Sextet<String, String, String, String, String, String>> studentClassList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询学生所在班级信息失败");
        } else {
            try {
                while (resultSet.next()) {
                    Sextet<String, String, String, String, String, String> studentCollege = new Sextet<>(
                            resultSet.getString("classNo"),
                            resultSet.getString("className"),
                            resultSet.getString("classProName"),
                            resultSet.getString("classColNo"),
                            resultSet.getString("studentNo"),
                            resultSet.getString("studentName")
                    );
                    studentClassList.add(studentCollege);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询学生所在班级信息失败");
            }
        }
        return studentClassList;
    }

    public ArrayList<Quintet<String, String, String, Integer, Integer>> studentSelectCourse(String studentNo) {
        String sql = "exec dbo.studentSelectCourse " + modifyStr(studentNo);
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Quintet<String, String, String, Integer, Integer>> studentCourseList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询学生选课信息失败");
        } else {
            try {
                while (resultSet.next()) {
                    Quintet<String, String, String, Integer, Integer> studentCourse = new Quintet<>(
                            resultSet.getString("courseNo"),
                            resultSet.getString("teacherNo"),
                            resultSet.getString("courseName"),
                            resultSet.getInt("courseCredict"),
                            resultSet.getInt("coursePeriod")
                    );
                    studentCourseList.add(studentCourse);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询学生选课信息失败");
            }
        }
        return studentCourseList;
    }

    public ArrayList<Course> studentSelectCourse() {
        String sql = "exec dbo.selectCourse ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Course> courseList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询课程表失败");
        } else {
            try {
                while (resultSet.next()) {
                    Course course = new Course(
                            resultSet.getString("courseName"),
                            resultSet.getString("courseNo"),
                            resultSet.getInt("courseCredict"),
                            resultSet.getInt("coursePeriod")
                    );
                    courseList.add(course);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询课程表失败");
            }
        }
        return courseList;
    }

    public ArrayList<Teacher> studentSelectTeacher() {
        String sql = "exec dbo.selectTeacher ";
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Teacher> teacherList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询教师表失败");
        } else {
            try {
                while (resultSet.next()) {
                    Teacher teacher = new Teacher(
                            resultSet.getString("teacherName"),
                            resultSet.getString("teacherNo"),
                            resultSet.getString("teacherResDir"),
                            resultSet.getString("teacherColNo")
                    );
                    teacherList.add(teacher);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询教师表失败");
            }
        }
        return teacherList;
    }

    public Student studentSelectMessage(String studentNo) {
        String sql = "exec dbo.studentSelectMessage " + modifyStr(studentNo);
        ResultSet resultSet = executeQueryProcedure(sql);
        Student student = null;
        if (resultSet == null) {
            System.out.println("查询学生个人信息失败");
        } else {
            try {
                resultSet.next();
                student = new Student(
                        resultSet.getString("studentName"),
                        resultSet.getString("studentNo"),
                        resultSet.getString("studentAge"),
                        resultSet.getString("studentSex"),
                        resultSet.getString("studentNation"),
                        resultSet.getString("studentBirthday"),
                        resultSet.getString("studentInsNo"),
                        resultSet.getString("studentClassNo")
                );

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询学生个人信息失败");
            }
        }
        return student;
    }

    public ArrayList<Quartet<String, String, String, Integer>> studentSelectScore(String studentNo) {
        String sql = "exec dbo.studentSelectScore " + modifyStr(studentNo);
        ResultSet resultSet = executeQueryProcedure(sql);
        ArrayList<Quartet<String, String, String, Integer>> studentScoreList = new ArrayList<>();
        if (resultSet == null) {
            System.out.println("查询学生个人成绩失败");
        } else {
            try {
                while (resultSet.next()) {
                    Quartet<String, String, String, Integer> studentScore = new Quartet<>(
                            resultSet.getString("studentNo"),
                            resultSet.getString("courseNo"),
                            resultSet.getString("teacherNo"),
                            resultSet.getInt("score")
                    );
                    studentScoreList.add(studentScore);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("查询学生个人成绩失败");
            }
        }
        return studentScoreList;
    }
}

