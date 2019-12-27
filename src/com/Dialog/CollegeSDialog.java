package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;
import org.javatuples.Quartet;

import javax.swing.*;
import java.util.ArrayList;

public class CollegeSDialog extends BaseDialog {
    private JFrame containerFrame;
    private JScrollPane classMessageTable;



    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y, ConstantUtils.LOGIN_WIDTH, ConstantUtils.LOGIN_HEIGH - 200);
        //containerFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        containerFrame.setTitle("个人系信息");
        JTable classMessTable = new JTable();

        Object[][] table = new Object[1][4];

        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
        ArrayList<Quartet<String,String,String,String>> college = sqlServer.studentSelectCollege(Main.user.getUserName());
        Quartet<String,String,String,String> collegeMess = college.get(0);
        table[0][0] = collegeMess.getValue0();
        table[0][1] = collegeMess.getValue1();
        table[0][2] = collegeMess.getValue2();
        table[0][3] = collegeMess.getValue3();

        Object[] name = {"系编号","系名","系主任教师编号","系主任教师名"};

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
