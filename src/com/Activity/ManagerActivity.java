package com.Activity;

import com.Base.BaseActivity;
import com.DataBase.SqlUser;
import com.Dialog.*;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerActivity extends BaseActivity {
    private JFrame containerFrame;
    private JButton collegeMButton;
    private JButton classMButton;
    private JButton courseMButton;
    private JButton teacherMButton;
    private JButton studentMButton;
    private JButton scoreMButton;
    private JButton OpenCourseMButton;
    private JButton modifyPasswordMButton;

    private JLabel message;

    @Override
    public SqlUser initSqlUser() {
        return SqlUser.newSqlUser(SqlUser.MANAGER_TYPE);
    }

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X, ConstantUtils.LOGIN_Y, ConstantUtils.LOGIN_WIDTH, ConstantUtils.LOGIN_HEIGH - 200);
        containerFrame.setTitle("欢迎你，管理员——" + Main.user.getUserName());
        containerFrame.setLayout(new GridLayout(4, 2));
        collegeMButton = new JButton("系管理");
        classMButton = new JButton("班级管理");
        courseMButton = new JButton("课程管理");
        teacherMButton = new JButton("教师管理");
        studentMButton = new JButton("学生管理");
        scoreMButton = new JButton("选课-成绩管理");
        OpenCourseMButton = new JButton("开课管理");
        modifyPasswordMButton = new JButton("修改密码");

        containerFrame.add(collegeMButton);
        containerFrame.add(classMButton);
        containerFrame.add(courseMButton);
        containerFrame.add(teacherMButton);
        containerFrame.add(studentMButton);
        containerFrame.add(scoreMButton);
        containerFrame.add(OpenCourseMButton);
        containerFrame.add(modifyPasswordMButton);
        containerFrame.setVisible(true);
    }

    @Override
    public void addListener() {
        modifyPasswordMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("管理员修改密码");
                ModifyPasswordDialog modifyPasswordDialog = new ModifyPasswordDialog();
            }
        });
        classMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("管理员管理班级信息");
                ClassMDialog classMDialog = new ClassMDialog();
            }
        });
        collegeMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("管理员管理系信息");
                CollegeMDialog collegeMDialog = new CollegeMDialog();
            }
        });
        courseMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("管理员管理课程信息");
                CourseMDialog courseMDialog = new CourseMDialog();
            }
        });
        teacherMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("管理员管理教师信息");
                TeacherMDialog teacherMDialog = new TeacherMDialog();
            }
        });
        studentMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("管理员管理学生信息");
                StudentMDialog studentMDialog = new StudentMDialog();
            }
        });
        scoreMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("管理员管理成绩信息");
                ScoreMDialog scoreMDialog = new ScoreMDialog();
            }
        });
        OpenCourseMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("管理员管理开课信息");
                OpenCourseMDialog openCourseMDialog = new OpenCourseMDialog();
            }
        });

    }

    @Override
    public void loadData() {

    }
}
