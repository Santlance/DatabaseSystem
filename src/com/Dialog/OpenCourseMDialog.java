package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.Course;
import com.Instance.Teacher;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OpenCourseMDialog extends BaseDialog {
    private JFrame containerFrame;
    private JTable CollegeCourseMessTable;
    private JScrollPane CollegeCourseMessageTable;
    private JPanel modifyCollegeCoursePanel;
    private JButton insertCollegeCourseButton;
    private JButton deleteCollegeCourseButton;


    private JTable TeacherCourseMessTable;
    private JScrollPane TeacherCourseMessageTable;
    private JPanel modifyTeacherCoursePanel;
    private JButton insertTeacherCourseButton;
    private JButton deleteTeacherCourseButton;


    private JLabel message;

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y - 160, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH + 150);
        containerFrame.setTitle("开课管理");

        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));

        ArrayList<Pair<String, String>> collegeCourses = sqlServer.managerSelectCollegeCourse();
        int ccsize = collegeCourses.size();
        Object[][] cctable = new Object[ccsize][2];

        for (int i = 0; i < ccsize; i++) {
            Pair<String, String> collegeCourse = collegeCourses.get(i);
            cctable[i][0] = collegeCourse.getKey();
            cctable[i][1] = collegeCourse.getValue();
        }

        ArrayList<Pair<String, String>> teacherCourses = sqlServer.managerSelectTeacherCourse();
        int tcsize = teacherCourses.size();
        Object[][] tctable = new Object[tcsize][2];

        for (int i = 0; i < tcsize; i++) {
            Pair<String, String> teacherCourse = teacherCourses.get(i);
            tctable[i][0] = teacherCourse.getKey();
            tctable[i][1] = teacherCourse.getValue();
        }


        message = new JLabel();
        message.setForeground(Color.red);
        message.setText("登录失败");

        Object[] cname = {"系编号", "课程编号"};
        CollegeCourseMessTable = new JTable(cctable, cname);
        CollegeCourseMessageTable = new JScrollPane(CollegeCourseMessTable);

        insertCollegeCourseButton = new JButton("增加系-课程");
        deleteCollegeCourseButton = new JButton("删除系-课程");
        modifyCollegeCoursePanel = new JPanel();


        Object[] tcname = {"教师编号", "课程编号"};
        TeacherCourseMessTable = new JTable(tctable, tcname);
        TeacherCourseMessageTable = new JScrollPane(TeacherCourseMessTable);

        insertTeacherCourseButton = new JButton("增加教师-课程");
        deleteTeacherCourseButton = new JButton("删除教师-课程");
        modifyTeacherCoursePanel = new JPanel();


        containerFrame.add(CollegeCourseMessageTable);

        modifyCollegeCoursePanel.add(insertCollegeCourseButton);
        modifyCollegeCoursePanel.add(deleteCollegeCourseButton);
        containerFrame.add(modifyCollegeCoursePanel);


        containerFrame.add(TeacherCourseMessageTable);

        modifyTeacherCoursePanel.add(insertTeacherCourseButton);
        modifyTeacherCoursePanel.add(deleteTeacherCourseButton);
        containerFrame.add(modifyTeacherCoursePanel);

        containerFrame.add(message);
        containerFrame.add(new JPanel());
        CollegeCourseMessageTable.setBounds(0, 0, 412, 20 * 12);
        modifyCollegeCoursePanel.setBounds(0, 260, 412, 40);
        TeacherCourseMessageTable.setBounds(0, 320, 412, 20 * 12);
        modifyTeacherCoursePanel.setBounds(0, 580, 412, 40);
        message.setBounds(20, 660, 412, 40);
        containerFrame.setVisible(true);
        message.setVisible(false);
        containerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addListener() {
        deleteCollegeCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = CollegeCourseMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择系-课程");
                } else {
                    int crow = crows[0];
                    String collegeNo = (String) CollegeCourseMessTable.getValueAt(crow, 0);
                    String courseNo = (String) CollegeCourseMessTable.getValueAt(crow, 1);
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.managerDeleteCollegeCourse(collegeNo, courseNo);
                    if (result) {
                        setMessage("删除系-课程成功");
                        containerFrame.dispose();
                        OpenCourseMDialog openCourseMDialog = new OpenCourseMDialog();
                    } else {
                        setMessage("删除失败");
                    }
                }
            }
        });
        insertCollegeCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                inputFrame.setTitle("增加系-课程");
                JLabel inputCollegeNo = new JLabel("  系编号");
                JTextField inputNo = new JTextField(20);
                JLabel inputCourseNo = new JLabel("课程编号");
                JTextField inputCno = new JTextField(20);
                JButton confirmButton = new JButton("确定");
                JButton cancelButton = new JButton("取消");
                JLabel mmessage = new JLabel();
                mmessage.setForeground(Color.red);
                mmessage.setText("登录失败");
                mmessage.setVisible(false);
                JPanel nopanel = new JPanel();
                nopanel.add(inputCollegeNo);
                nopanel.add(inputNo);
                JPanel namepanel = new JPanel();
                namepanel.add(inputCourseNo);
                namepanel.add(inputCno);
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(confirmButton);
                buttonPanel.add(cancelButton);
                inputFrame.add(nopanel);
                inputFrame.add(namepanel);
                inputFrame.add(buttonPanel);
                inputFrame.add(mmessage);
                inputFrame.add(new JPanel());

                nopanel.setBounds(15, 0, 420, 40);
                namepanel.setBounds(15, 40, 420, 40);
                buttonPanel.setBounds(15, 80, 420, 40);
                mmessage.setBounds(15, 120, 420, 40);
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String no = inputNo.getText();
                        String name = inputCno.getText();
                        if (no.equals("") || name.equals("")) {
                            mmessage.setText("请输入完整信息");
                            mmessage.setVisible(true);
                        } else {
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            String colsql = "select * from College where collegeNo = " + sqlServer.modifyStr(no);
                            String cousql = "select * from Course where courseNo = " + sqlServer.modifyStr(name);
                            ResultSet colset = sqlServer.executeQuery(colsql);
                            ResultSet couset = sqlServer.executeQuery(cousql);

                            try {
                                if (!(colset.next() && couset.next())) {
                                    inputFrame.dispose();
                                    setMessage("系或课程不存在");
                                } else {
                                    boolean result = sqlServer.managerInsertCollegeCourse(no, name);
                                    if (result) {
                                        inputFrame.dispose();
                                        containerFrame.dispose();
                                        OpenCourseMDialog openCourseMDialog = new OpenCourseMDialog();
                                    } else {
                                        inputFrame.dispose();
                                        setMessage("增加系-课程失败");
                                    }
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });

                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        inputFrame.dispose();
                    }
                });


                inputFrame.setVisible(true);
                inputFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            }
        });

        deleteTeacherCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = TeacherCourseMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择教师-课程");
                } else {
                    int crow = crows[0];
                    String teacherNo = (String) TeacherCourseMessTable.getValueAt(crow, 0);
                    String courseNo = (String) TeacherCourseMessTable.getValueAt(crow, 1);
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.managerDeleteTeacherCourse(teacherNo, courseNo);
                    if (result) {
                        setMessage("删除教师-课程成功");
                        containerFrame.dispose();
                        OpenCourseMDialog openCourseMDialog = new OpenCourseMDialog();
                    } else {
                        setMessage("删除失败");
                    }
                }
            }
        });
        insertTeacherCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                inputFrame.setTitle("增加教师-课程");
                JLabel inputCourseNo = new JLabel("教师编号");
                JTextField inputNo = new JTextField(20);
                JLabel inputCourseName = new JLabel("课程编号");
                JTextField inputName = new JTextField(20);
                JButton confirmButton = new JButton("确定");
                JButton cancelButton = new JButton("取消");
                JLabel mmessage = new JLabel();
                mmessage.setForeground(Color.red);
                mmessage.setText("登录失败");
                mmessage.setVisible(false);
                JPanel nopanel = new JPanel();
                nopanel.add(inputCourseNo);
                nopanel.add(inputNo);
                JPanel namepanel = new JPanel();
                namepanel.add(inputCourseName);
                namepanel.add(inputName);
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(confirmButton);
                buttonPanel.add(cancelButton);
                inputFrame.add(nopanel);
                inputFrame.add(namepanel);
                inputFrame.add(buttonPanel);
                inputFrame.add(mmessage);
                inputFrame.add(new JPanel());

                nopanel.setBounds(15, 0, 420, 40);
                namepanel.setBounds(15, 40, 420, 40);
                buttonPanel.setBounds(15, 80, 420, 40);
                mmessage.setBounds(15, 120, 420, 40);
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String no = inputNo.getText();
                        String name = inputName.getText();
                        if (no.equals("") || name.equals("")) {
                            mmessage.setText("请输入完整信息");
                            mmessage.setVisible(true);
                        } else {
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            String colsql = "select * from Teacher where teacherNo = " + sqlServer.modifyStr(no);
                            String cousql = "select * from Course where courseNo = " + sqlServer.modifyStr(name);
                            ResultSet teaset = sqlServer.executeQuery(colsql);
                            ResultSet couset = sqlServer.executeQuery(cousql);

                            try {
                                if (!(teaset.next() && couset.next())) {
                                    inputFrame.dispose();
                                    setMessage("教师或课程不存在");
                                } else {
                                    boolean result = sqlServer.managerInsertTeacherCourse(no, name);
                                    if (result) {
                                        inputFrame.dispose();
                                        containerFrame.dispose();
                                        OpenCourseMDialog openCourseMDialog = new OpenCourseMDialog();
                                    } else {
                                        inputFrame.dispose();
                                        setMessage("增加教师-课程失败");
                                    }
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });

                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        inputFrame.dispose();
                    }
                });


                inputFrame.setVisible(true);
                inputFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            }
        });
    }

    @Override
    public SqlUser initSqlUser() {
        return SqlUser.newSqlUser(Main.user.getUserType());
    }

    @Override
    public void loadData() {

    }

    public void setMessage(String s) {
        message.setText(s);
        message.setVisible(true);
    }
}
