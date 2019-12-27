package com.Instance;

public class Teacher {
    private String teacherName;
    private String teacherNo;
    private String teacherResDir;
    private String teacherCollegeNo;

    public Teacher(String tname, String tno, String teacherresdir,String tcollegeno) {
        this.teacherName = tname;
        this.teacherNo = tno;
        this.teacherResDir = teacherresdir;
        this.teacherCollegeNo = tcollegeno;
    }

    public String getTeacherCollegeNo() {
        return teacherCollegeNo;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherResDir() {
        return teacherResDir;
    }

    public void setTeacherCollegeNo(String teacherCollegeNo) {
        this.teacherCollegeNo = teacherCollegeNo;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }

    public void setTeacherResDir(String teacherResDir) {
        this.teacherResDir = teacherResDir;
    }
}
