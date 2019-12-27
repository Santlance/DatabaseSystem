package com.Instance;

public class Student {
    private String studentName;
    private String studentNo;
    private String studentAge;
    private String studentSex;
    private String studentNation;
    private String studentBirthday;
    private String studentInsNo;
    private String studentClassNo;

    public Student(String sname, String sno, String sage, String ssex, String snation, String sbirthday,
                   String studentinsno, String sclassno) {
        this.studentName = sname;
        this.studentNo = sno;
        this.studentAge = sage;
        this.studentSex = ssex;
        this.studentNation = snation;
        this.studentBirthday = sbirthday;
        this.studentClassNo = sclassno;
        this.studentInsNo = studentinsno;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public String getStudentSex() {
        return studentSex;
    }

    public String getStudentAge() {
        return studentAge;
    }

    public String getStudentNation() {
        return studentNation;
    }

    public String getStudentBirthday() {
        return studentBirthday;
    }

    public String getStudentClassNo() {
        return studentClassNo;
    }

    public String getStudentInsNo() {
        return studentInsNo;
    }

    public void setStudentName(String sname) {
        this.studentName = sname;
    }

    public void setStudentNo(String sno) {
        this.studentNo = sno;
    }

    public void setStudentAge(String sage) {
        this.studentAge = sage;
    }

    public void setStudentSex(String ssex) {
        this.studentSex = ssex;
    }

    public void setStudentBirthday(String sbirthday) {
        this.studentBirthday = sbirthday;
    }

    public void setStudentClassNo(String sclassno) {
        this.studentClassNo = sclassno;
    }

    public void setStudentInsNo(String studentInsNo) {
        this.studentInsNo = studentInsNo;
    }
}
