package com.Instance;

public class Class {
    private String className;
    private String classNo;
    private String classProName;
    private String classCollegeNo;

    public Class(String cname, String cno, String cproname, String ccollegeno) {
        this.classCollegeNo = ccollegeno;
        this.className = cname;
        this.classProName = cproname;
        this.classNo = cno;
    }

    public String getClassCollegeNo() {
        return classCollegeNo;
    }

    public String getClassName() {
        return className;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getClassProName() {
        return classProName;
    }

    public void setClassCollegeNo(String classCollegeNo) {
        this.classCollegeNo = classCollegeNo;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public void setClassProName(String classProName) {
        this.classProName = classProName;
    }
}
