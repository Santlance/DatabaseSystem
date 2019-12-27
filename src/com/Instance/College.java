package com.Instance;

public class College {
    private String collegeName;
    private String collegeNo;

    public College(String cname, String cno) {
        this.collegeName = cname;
        this.collegeNo = cno;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public String getCollegeNo() {
        return collegeNo;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public void setCollegeNo(String collegeNo) {
        this.collegeNo = collegeNo;
    }

}
