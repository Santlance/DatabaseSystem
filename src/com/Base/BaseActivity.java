package com.Base;

import com.DataBase.SqlServer;
import com.DataBase.SqlUser;

import javax.swing.*;
import java.awt.*;

public abstract class BaseActivity {
    protected Font titleFont;
    protected Font textFont;
    private JFrame dialogFrame;

    /**
     * 每个活动持有一个SQL对象
     */
    private SqlUser sqlUser = null;

    private SqlServer sqlServer = null;

    /**
     * view初始化自动调用
     */
    public BaseActivity() {
        dialogFrame = new JFrame();
        dialogFrame.setSize(400, 400);
        dialogFrame.setLocationRelativeTo(null);
        dialogFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.sqlUser = initSqlUser();
        sqlServer = new SqlServer(sqlUser);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initView();
                addListener();
            }
        });
        // 全局字体初始化
        titleFont = new Font("黑体", Font.PLAIN, 18);
        textFont = new Font("黑体", Font.PLAIN, 16);
    }

    public void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(dialogFrame, message, "消息提示", JOptionPane.INFORMATION_MESSAGE);
    }


    public SqlServer getSqlServer() {
        return sqlServer;
    }

    public abstract SqlUser initSqlUser();

    public abstract void initView();

    protected SqlUser getSqlUser() {
        return sqlUser;
    }

    public abstract void addListener();

    public abstract void loadData();

}
