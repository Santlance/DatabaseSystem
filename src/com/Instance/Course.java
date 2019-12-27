package com.Instance;

public class Course {
    private String courseName;
    private String courseNo;
    private int courseCredict;
    private int coursePeriod;

    public Course(String cname, String cno, int ccredict, int cperiod) {
        this.courseName = cname;
        this.courseNo = cno;
        this.courseCredict = ccredict;
        this.coursePeriod = cperiod;
    }

    public int getCourseCredict() {
        return courseCredict;
    }

    public int getCoursePeriod() {
        return coursePeriod;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseCredict(int courseCredict) {
        this.courseCredict = courseCredict;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public void setCoursePeriod(int coursePeriod) {
        this.coursePeriod = coursePeriod;
    }

}

