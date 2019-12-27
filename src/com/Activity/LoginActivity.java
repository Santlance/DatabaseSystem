package com.Activity;

import com.Base.BaseActivity;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.User;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;
import com.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends BaseActivity {
    private JFrame containerFrame;
    private JPanel userNamePanel;
    private JPanel userPasswordPanel;
    private JPanel loginPanel;
    private JLabel userNameLabel;
    private JLabel userPasswordLabel;
    private JTextField userNameField;
    private JPasswordField userPasswordField;
    private JButton loginButton;
    private JButton cancelButton;

    private JLabel message;

    @Override
    public SqlUser initSqlUser() {
        return SqlUser.newSqlUser(SqlUser.USER_TYPE);
    }

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X, ConstantUtils.LOGIN_Y, ConstantUtils.LOGIN_WIDTH, ConstantUtils.LOGIN_HEIGH - 200);
        userNamePanel = new JPanel();
        userPasswordPanel = new JPanel();
        loginPanel = new JPanel();

        userNameLabel = new JLabel("用户名");
        userPasswordLabel = new JLabel("    密码");

        userNameField = new JTextField(20);
        userPasswordField = new JPasswordField(20);//密码输入框

        loginButton = new JButton("登录");
        cancelButton = new JButton("取消");

        message = new JLabel();
        message.setForeground(Color.red);
        message.setText("登录失败");

        userNamePanel.add(userNameLabel);
        userNamePanel.add(userNameField);

        userPasswordPanel.add(userPasswordLabel);
        userPasswordPanel.add(userPasswordField);

        loginPanel.add(loginButton);
        loginPanel.add(cancelButton);

        containerFrame.add(userNamePanel);
        containerFrame.add(userPasswordPanel);
        containerFrame.add(loginPanel);
        containerFrame.add(message);
        JPanel jPanel = new JPanel();
        containerFrame.add(jPanel);


        userNamePanel.setBounds(20, 120, 370, 40);
        userPasswordPanel.setBounds(20, 160, 370, 40);
        loginPanel.setBounds(20, 200, 370, 40);
        message.setBounds(20, 240, 370, 40);

        message.setVisible(false);

        containerFrame.setVisible(true);
    }

    @Override
    public void addListener() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userNameField.getText().equals("")) {
                    System.out.println("用户名为空");
                    setMessage("用户名为空");
                } else {
                    String userName = userNameField.getText();
                    String userPassword = String.valueOf(userPasswordField.getPassword());
                    SqlServer sqlServer = new SqlServer(getSqlUser());
                    String sql = "select * from Users where userName = " + sqlServer.modifyStr(userName)
                            + " and userPassword = " + sqlServer.modifyStr(userPassword);
                    ResultSet resultSet = sqlServer.executeQuery(sql);

                    try {
                        resultSet.next();
                        User user = new User(
                                resultSet.getString("userName"),
                                resultSet.getString("userPassword"),
                                resultSet.getInt("userType")
                        );
                        Main.user = user;
                        setMessage("登录成功");
                        openActivity(user);
                    } catch (SQLException ee) {
                        ee.printStackTrace();
                        System.out.println("用户查询失败" + ee.toString());
                        setMessage("用户名不存在或者密码错误");
                    }

                }
            }
        });
        message.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                message.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        cancelButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                containerFrame.dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    public void loadData() {

    }

    public void setMessage(String s) {
        message.setText(s);
        message.setVisible(true);
    }

    public void openActivity(User user) {
        if (user.getUserType() == 0) {
            System.out.println("超级管理员登录");
        } else if (user.getUserType() == 1) {
            System.out.println("管理员登录");
            ManagerActivity managerActivity = new ManagerActivity();
            containerFrame.dispose();
        } else {
            System.out.println("学生登录");
            StudentActivity studentActivity = new StudentActivity();
            containerFrame.dispose();
        }
    }


}
