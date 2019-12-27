package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;
import org.javatuples.Sextet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ClassSDialog extends BaseDialog {
    private JFrame containerFrame;
    private JScrollPane classMessageTable;



    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y, ConstantUtils.LOGIN_WIDTH, ConstantUtils.LOGIN_HEIGH - 200);
        //containerFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        containerFrame.setTitle("个人班级信息");
        JTable classMessTable = new JTable();

        Object[][] table = new Object[1][6];

        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
        ArrayList<Sextet<String,String,String,String,String,String>> classes = sqlServer.studentSelectClass(Main.user.getUserName());
        Sextet<String,String,String,String,String,String> classMess = classes.get(0);
        table[0][0] = classMess.getValue0();
        table[0][1] = classMess.getValue1();
        table[0][2] = classMess.getValue2();
        table[0][3] = classMess.getValue3();
        table[0][4] = classMess.getValue4();
        table[0][5] = classMess.getValue5();

        Object[] name = {"班级编号","班级名","班级专业名","班级系编号","班长学号","班长姓名"};

        classMessTable = new JTable(table, name);
        classMessageTable = new JScrollPane(classMessTable);
        containerFrame.add(classMessageTable);
        containerFrame.add(new JPanel());
        classMessageTable.setBounds(0,0,412,40);
        containerFrame.setVisible(true);
        containerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addListener() {

    }

    @Override
    public SqlUser initSqlUser() {
        return SqlUser.newSqlUser(Main.user.getUserType());
    }

    @Override
    public void loadData() {

    }


}
