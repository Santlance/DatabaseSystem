package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.Student;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;
import org.javatuples.Quartet;

import javax.swing.*;
import java.util.ArrayList;

public class StudentSDialog extends BaseDialog {
    private JFrame containerFrame;
    private JScrollPane courseMessageTable;



    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y, ConstantUtils.LOGIN_WIDTH + 60, ConstantUtils.LOGIN_HEIGH - 200);
        //containerFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        containerFrame.setTitle("个人信息");
        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
        Student student = sqlServer.studentSelectMessage(Main.user.getUserName());

        JTable courseMessTable = new JTable();
        Object[][] table = new Object[1][8];

        table[0][0] = student.getStudentNo();
        table[0][1] = student.getStudentName();
        table[0][2] = student.getStudentAge();
        table[0][3] = student.getStudentSex();
        table[0][4] = student.getStudentNation();
        table[0][5] = student.getStudentBirthday();
        table[0][6] = student.getStudentInsNo();
        table[0][7] = student.getStudentClassNo();


        Object[] name = {"学号","姓名","年龄","性别","民族","出生日期","指导员学号","班级编号"};

        courseMessTable = new JTable(table, name);
        courseMessTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        courseMessTable.getColumnModel().getColumn(6).setPreferredWidth(120);
        courseMessTable.getColumnModel().getColumn(7).setPreferredWidth(120);
        courseMessageTable = new JScrollPane(courseMessTable);
        containerFrame.add(courseMessageTable);
        containerFrame.add(new JPanel());
        courseMessageTable.setBounds(0,0,412,40);
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
