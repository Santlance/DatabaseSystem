package com.Instance;

public class Manager {
    private String managerName;
    private String managerNo;
    private String managerPhone;

    public Manager(String managerName, String managerNo, String managerPhone) {
        this.managerName = managerName;
        this.managerNo = managerNo;
        this.managerPhone = managerPhone;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getManagerNo() {
        return managerNo;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setManagerNo(String managerNo) {
        this.managerNo = managerNo;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }
}
