package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.Teacher;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;
import org.javatuples.Quartet;

import javax.swing.*;
import java.util.ArrayList;

public class TeacherSDialog extends BaseDialog {
    private JFrame containerFrame;
    private JScrollPane courseMessageTable;


    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y, ConstantUtils.LOGIN_WIDTH + 50, ConstantUtils.LOGIN_HEIGH - 200);
        //containerFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        containerFrame.setTitle("教师信息");
        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
        ArrayList<Teacher> teachers = sqlServer.studentSelectTeacher();
        int csize = teachers.size();
        JTable courseMessTable = new JTable();
        Object[][] table = new Object[csize][4];

        for (int i = 0; i < csize; i++) {
            Teacher teacher = teachers.get(i);
            table[i][0] = teacher.getTeacherNo();
            table[i][1] = teacher.getTeacherName();
            table[i][2] = teacher.getTeacherResDir();
            table[i][3] = teacher.getTeacherCollegeNo();
        }

        Object[] name = {"教师编号", "教师名", "研究领域", "系编号"};

        courseMessTable = new JTable(table, name);
        courseMessageTable = new JScrollPane(courseMessTable);
        containerFrame.add(courseMessageTable);
        containerFrame.add(new JPanel());
        courseMessageTable.setBounds(0, 0, 412, 20 * 15);
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
