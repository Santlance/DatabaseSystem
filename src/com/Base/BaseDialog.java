package com.Base;


import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Utils.ConstantUtils;

import javax.swing.*;
import java.awt.*;

public abstract class BaseDialog {
    protected JFrame myFrame;
    protected JLabel bgLabel;
    protected Font textFont;
    protected Font titleFont;
    protected SqlServer sqlServer;
    private JFrame dialogFrame;

    public BaseDialog() {
        sqlServer = new SqlServer(initSqlUser());
        dialogFrame = new JFrame();
        dialogFrame.setSize(400, 400);
        dialogFrame.setLocationRelativeTo(null);
        dialogFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        textFont = new Font("宋体", Font.PLAIN, 16);
        titleFont = new Font("宋体", Font.PLAIN, 20);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                myFrame = new JFrame();
                myFrame.setLayout(null);
                myFrame.setBounds(ConstantUtils.LOGIN_X + 50, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH - 100,
                        ConstantUtils.LOGIN_HEIGH - 200);
                initView();
                addListener();
            }
        });
    }

    public void initFrame(String bgPath) {
        myFrame.setVisible(true);
    }

    //界面初始化
    public abstract void initView();
    //事件初始化
    public abstract void addListener();
    //SQL对象初始化
    public abstract SqlUser initSqlUser();

    public SqlServer getSqlServer() {
        return sqlServer;
    }
    //信息弹窗封装
    public void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(dialogFrame, message, "消息提示", JOptionPane.INFORMATION_MESSAGE);
    }
    //数据初始化
    public abstract void loadData();
}
