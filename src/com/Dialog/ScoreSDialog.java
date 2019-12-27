package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import javax.swing.*;
import java.util.ArrayList;

public class ScoreSDialog extends BaseDialog {
    private JFrame containerFrame;
    private JScrollPane courseMessageTable;



    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y, ConstantUtils.LOGIN_WIDTH, ConstantUtils.LOGIN_HEIGH - 200);
        //containerFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        containerFrame.setTitle("个人成绩信息");
        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
        ArrayList<Quartet<String, String,String,Integer>> courseScores = sqlServer.studentSelectScore(Main.user.getUserName());
        int csize = courseScores.size();
        JTable courseMessTable = new JTable();
        Object[][] table = new Object[csize][4];

        for(int i = 0; i < csize; i ++){
            Quartet<String, String,String,Integer> courseScore = courseScores.get(i);
            table[i][0] = courseScore.getValue0();
            table[i][1] = courseScore.getValue1();
            table[i][2] = courseScore.getValue2();
            table[i][3] = courseScore.getValue3();
        }

        Object[] name = {"学号","课程编号","任课教师编号","成绩"};

        courseMessTable = new JTable(table, name);
        courseMessageTable = new JScrollPane(courseMessTable);
        containerFrame.add(courseMessageTable);
        containerFrame.add(new JPanel());
        courseMessageTable.setBounds(0,0,412,20 * 15);
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
