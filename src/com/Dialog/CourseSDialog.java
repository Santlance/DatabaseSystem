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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CourseSDialog extends BaseDialog {
    private JFrame containerFrame;
    private JTable courseMessTable;
    private JScrollPane courseMessageTable;
    private JLabel message;
    private JButton deleteCourseButton;
    private JPanel deleteCoursePanel;


    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y, ConstantUtils.LOGIN_WIDTH, ConstantUtils.LOGIN_HEIGH - 200);
        //containerFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        containerFrame.setTitle("个人课表信息");
        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
        ArrayList<Quintet<String, String, String, Integer, Integer>> courses = sqlServer.studentSelectCourse(Main.user.getUserName());
        int csize = courses.size();
        Object[][] table = new Object[csize][5];

        for (int i = 0; i < csize; i++) {
            Quintet<String, String, String, Integer, Integer> course = courses.get(i);
            table[i][0] = course.getValue0();
            table[i][1] = course.getValue1();
            table[i][2] = course.getValue2();
            table[i][3] = course.getValue3();
            table[i][4] = course.getValue4();
        }

        Object[] name = {"课程编号", "任课教师编号", "课程名", "学分", "学时"};

        message = new JLabel();
        message.setForeground(Color.red);
        message.setText("登录失败");

        deleteCourseButton = new JButton("退课");
        deleteCoursePanel = new JPanel();


        courseMessTable = new JTable(table, name);
        courseMessageTable = new JScrollPane(courseMessTable);

        deleteCoursePanel.add(deleteCourseButton);
        containerFrame.add(courseMessageTable);

        containerFrame.add(deleteCoursePanel);
        containerFrame.add(message);

        containerFrame.add(new JPanel());
        courseMessageTable.setBounds(0, 0, 412, 20 * 15);
        deleteCoursePanel.setBounds(0, 320, 412, 40);
        message.setBounds(20, 350, 412, 40);
        containerFrame.setVisible(true);
        message.setVisible(false);
        containerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addListener() {
        deleteCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = courseMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择课程");
                } else {
                    int crow = crows[0];
                    String course = (String) courseMessTable.getValueAt(crow, 0);
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.studentDeleteSCTSTable(Main.user.getUserName(), course);
                    if (result) {
                        setMessage("退课成功");
                        containerFrame.dispose();
                        CourseSDialog courseSDialog = new CourseSDialog();
                    } else {
                        setMessage("退课失败");
                    }
                }
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
