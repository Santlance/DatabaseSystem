package com.Activity;

import com.Base.BaseActivity;
import com.DataBase.SqlUser;
import com.Dialog.*;
import com.Instance.Student;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentActivity extends BaseActivity {
    private JFrame containerFrame;
    private JButton collegeMButton;
    private JButton classMButton;
    private JButton courseMButton;
    private JButton teacherMButton;
    private JButton studentMButton;
    private JButton scoreMButton;
    private JButton selectCourseMButton;
    private JButton modifyPasswordMButton;

    private JLabel message;

    @Override
    public SqlUser initSqlUser() {
        return SqlUser.newSqlUser(SqlUser.USER_TYPE);
    }

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X, ConstantUtils.LOGIN_Y, ConstantUtils.LOGIN_WIDTH, ConstantUtils.LOGIN_HEIGH - 200);
        containerFrame.setTitle("欢迎你，学生——" + Main.user.getUserName());
        containerFrame.setLayout(new GridLayout(4, 2));
        collegeMButton = new JButton("个人系信息");
        classMButton = new JButton("个人班级信息");
        courseMButton = new JButton("个人课表信息");
        teacherMButton = new JButton("教师信息");
        studentMButton = new JButton("个人信息");
        scoreMButton = new JButton("个人成绩信息");
        selectCourseMButton = new JButton("选课管理");
        modifyPasswordMButton = new JButton("修改密码");

        containerFrame.add(collegeMButton);
        containerFrame.add(classMButton);
        containerFrame.add(courseMButton);
        containerFrame.add(teacherMButton);
        containerFrame.add(studentMButton);
        containerFrame.add(scoreMButton);
        containerFrame.add(selectCourseMButton);
        containerFrame.add(modifyPasswordMButton);
        containerFrame.setVisible(true);
    }

    @Override
    public void addListener() {
        modifyPasswordMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("学生修改密码");
                ModifyPasswordDialog modifyPasswordDialog = new ModifyPasswordDialog();
            }
        });
        classMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("学生查询个人班级信息");
                ClassSDialog classSDialog = new ClassSDialog();
            }
        });
        collegeMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("学生查询个人系信息");
                CollegeSDialog collegeSDialog = new CollegeSDialog();
            }
        });
        courseMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("学生查询个人课表信息");
                CourseSDialog courseSDialog = new CourseSDialog();
            }
        });
        scoreMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("学生查询个人成绩信息");
                ScoreSDialog scoreSDialog = new ScoreSDialog();
            }
        });
        studentMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("学生查询个人信息");
                StudentSDialog studentSDialog = new StudentSDialog();
            }
        });
        teacherMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("学生查询教师信息");
                TeacherSDialog teacherSDialog = new TeacherSDialog();
            }
        });
        selectCourseMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("学生选课管理");
                SelectCourseSDialog selectCourseSDialog = new SelectCourseSDialog();
            }
        });
    }

    @Override
    public void loadData() {

    }
}
