package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.Course;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SelectCourseSDialog extends BaseDialog {
    private JFrame containerFrame;
    private JTable courseMessTable;
    private JTable teacherCourseMessTable;
    private JScrollPane courseMessageTable;
    private JScrollPane teacherCourseMessageTable;
    private JPanel selectCoursePanel;
    private JButton selectCourseButton;
    private JLabel message;

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y - 160, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH + 150);
        //containerFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        containerFrame.setTitle("选课管理");

        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));

        ArrayList<Course> courses = sqlServer.studentSelectCourse();
        ArrayList<Pair<String, String>> teacherCourses = sqlServer.managerSelectTeacherCourse();
        int csize = courses.size();
        int tcsize = teacherCourses.size();
        Object[][] ctable = new Object[csize][4];
        Object[][] tctable = new Object[tcsize][2];

        for (int i = 0; i < csize; i++) {
            Course course = courses.get(i);
            ctable[i][0] = course.getCourseNo();
            ctable[i][1] = course.getCourseName();
            ctable[i][2] = course.getCourseCredict();
            ctable[i][3] = course.getCoursePeriod();
        }

        for (int i = 0; i < tcsize; i++) {
            Pair<String, String> teacherCourse = teacherCourses.get(i);
            tctable[i][0] = teacherCourse.getKey();
            tctable[i][1] = teacherCourse.getValue();
        }

        message = new JLabel();
        message.setForeground(Color.red);
        message.setText("登录失败");

        Object[] cname = {"课程编号", "课程名", "学分", "学时"};
        Object[] tcname = {"教师编号", "课程编号"};
        courseMessTable = new JTable(ctable, cname);
        teacherCourseMessTable = new JTable(tctable, tcname);
        courseMessageTable = new JScrollPane(courseMessTable);
        teacherCourseMessageTable = new JScrollPane(teacherCourseMessTable);

        selectCourseButton = new JButton("选课");
        selectCoursePanel = new JPanel();

        containerFrame.add(courseMessageTable);
        containerFrame.add(teacherCourseMessageTable);

        selectCoursePanel.add(selectCourseButton);
        containerFrame.add(selectCoursePanel);

        containerFrame.add(message);
        containerFrame.add(new JPanel());
        courseMessageTable.setBounds(0, 0, 412, 20 * 15);
        teacherCourseMessageTable.setBounds(0, 310, 412, 20 * 15);
        selectCoursePanel.setBounds(0, 620, 412, 40);
        message.setBounds(20, 660, 412, 40);
        containerFrame.setVisible(true);
        message.setVisible(false);
        containerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addListener() {
        selectCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = courseMessTable.getSelectedRows();
                int[] tcrows = teacherCourseMessTable.getSelectedRows();
                if (crows.length > 1 || tcrows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0 || tcrows.length == 0) {
                    setMessage("请选择课程或教师");
                } else {
                    int crow = crows[0];
                    int tcrow = tcrows[0];
                    String course = (String) courseMessTable.getValueAt(crow, 0);
                    String teacher = (String) teacherCourseMessTable.getValueAt(tcrow, 0);
                    String tcourse = (String) teacherCourseMessTable.getValueAt(tcrow, 1);
                    if (!course.equals(tcourse)) {
                        setMessage("所选课程与教师授课不符");
                    } else {
                        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                        boolean result = sqlServer.studentInsertSCTSTable(Main.user.getUserName(), course, teacher);
                        if (result) {
                            setMessage("选课成功");
                        } else {
                            setMessage("选课失败");
                        }
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
