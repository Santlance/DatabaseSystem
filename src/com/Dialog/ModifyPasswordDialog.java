package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.User;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ModifyPasswordDialog extends BaseDialog {
    private JFrame containerFrame;
    private JPanel userOPasswordPanel;
    private JPanel userNPasswordPanel;
    private JPanel modifyPanel;
    private JLabel userOPasswordLabel;
    private JLabel userNPasswordLabel;
    private JPasswordField userOPasswordField;
    private JPasswordField userNPasswordField;
    private JButton modifyButton;
    private JButton cancelButton;

    private JLabel message;

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y, ConstantUtils.LOGIN_WIDTH, ConstantUtils.LOGIN_HEIGH - 200);
        //containerFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        containerFrame.setTitle("修改密码");
        userNPasswordPanel = new JPanel();
        userOPasswordPanel = new JPanel();
        modifyPanel = new JPanel();

        userOPasswordLabel = new JLabel("旧密码");
        userNPasswordLabel = new JLabel("新密码");

        userNPasswordField = new JPasswordField(20);
        userOPasswordField = new JPasswordField(20);//密码输入框

        modifyButton = new JButton("修改");
        cancelButton = new JButton("取消");

        message = new JLabel();
        message.setForeground(Color.red);
        message.setText("修改失败");

        userOPasswordPanel.add(userOPasswordLabel);
        userOPasswordPanel.add(userOPasswordField);


        userNPasswordPanel.add(userNPasswordLabel);
        userNPasswordPanel.add(userNPasswordField);

        modifyPanel.add(modifyButton);
        modifyPanel.add(cancelButton);

        containerFrame.add(userOPasswordPanel);
        containerFrame.add(userNPasswordPanel);
        containerFrame.add(modifyPanel);
        containerFrame.add(message);
        containerFrame.add(new JPanel());

        userOPasswordPanel.setBounds(20, 120, 370, 40);
        userNPasswordPanel.setBounds(20, 160, 370, 40);
        modifyPanel.setBounds(20, 200, 370, 40);
        message.setBounds(20, 240, 370, 40);

        message.setVisible(false);
        containerFrame.setVisible(true);
        containerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addListener() {
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (String.valueOf(userOPasswordField.getPassword()).equals("")) {
                    System.out.println("旧密码为空");
                    setMessage("旧密码为空");
                }
                if (String.valueOf(userNPasswordField.getPassword()).equals("")) {
                    System.out.println("新密码为空");
                    setMessage("新密码为空");
                }
                if (!String.valueOf(userNPasswordField.getPassword()).equals("")
                        && !String.valueOf(userOPasswordField.getPassword()).equals("")) {
                    String userName = Main.user.getUserName();
                    String userOPassword = String.valueOf(userOPasswordField.getPassword());
                    String userNPassword = String.valueOf(userNPasswordField.getPassword());
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.managerUpdatePassword(Main.user.getUserName(), userOPassword, userNPassword);
                    if (result) {
                        System.out.println("密码修改成功");
                        setMessage("密码修改成功");
                    } else {
                        System.out.println("密码修改失败，请检查旧密码是否正确");
                        setMessage("密码修改失败，请检查旧密码是否正确");
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
