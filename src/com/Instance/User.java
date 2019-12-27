package com.Instance;

public class User {
    private String userName;
    private String userPassword;
    private int userType;

    /**
     * 0 means administer 1 means manager 2 means student and teacher
     **/

    public User(String uname, String upassword, int utype) {
        this.userName = uname;
        this.userPassword = upassword;
        this.userType = utype;
    }

    public int getUserType() {
        return userType;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
