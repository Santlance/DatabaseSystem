package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.Class;
import com.Instance.Course;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CourseMDialog extends BaseDialog {
    private JFrame containerFrame;
    private JTable courseMessTable;
    private JScrollPane courseMessageTable;
    private JPanel modifyCoursePanel;
    private JButton insertCourseButton;
    private JButton updateCourseButton;
    private JButton deleteCourseButton;

    private JLabel message;

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y - 160, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH + 150);
        containerFrame.setTitle("课程管理");

        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));

        ArrayList<Course> courses = sqlServer.managerSelectCourse();
        int csize = courses.size();
        Object[][] ctable = new Object[csize][4];

        for (int i = 0; i < csize; i++) {
            Course course = courses.get(i);
            ctable[i][0] = course.getCourseNo();
            ctable[i][1] = course.getCourseName();
            ctable[i][2] = course.getCourseCredict();
            ctable[i][3] = course.getCoursePeriod();
        }


        message = new JLabel();
        message.setForeground(Color.red);
        message.setText("登录失败");

        Object[] cname = {"课程编号", "课程名", "学分", "学时"};
        courseMessTable = new JTable(ctable, cname);
        courseMessageTable = new JScrollPane(courseMessTable);

        insertCourseButton = new JButton("增加");
        updateCourseButton = new JButton("更改");
        deleteCourseButton = new JButton("删除");
        modifyCoursePanel = new JPanel();

        containerFrame.add(courseMessageTable);

        modifyCoursePanel.add(insertCourseButton);
        modifyCoursePanel.add(updateCourseButton);
        modifyCoursePanel.add(deleteCourseButton);
        containerFrame.add(modifyCoursePanel);

        containerFrame.add(message);
        containerFrame.add(new JPanel());
        courseMessageTable.setBounds(0, 0, 412, 20 * 30);
        modifyCoursePanel.setBounds(0, 620, 412, 40);
        message.setBounds(20, 660, 412, 40);
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
                    String courseNo = (String) courseMessTable.getValueAt(crow, 0);
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.managerDeleteCourse(courseNo);
                    if (result) {
                        setMessage("删除班级成功");
                        containerFrame.dispose();
                        CourseMDialog courseMDialog = new CourseMDialog();
                    } else {
                        setMessage("删除失败");
                    }
                }
            }
        });
        insertCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                inputFrame.setTitle("增加课程");
                JLabel inputCourseNo = new JLabel("课程编号");
                JTextField inputNo = new JTextField(20);
                JLabel inputCourseName = new JLabel("  课程名");
                JTextField inputName = new JTextField(20);
                JLabel inputCourseCredict = new JLabel("    学分");
                JTextField inputCredict = new JTextField(20);
                JLabel inputCoursePeriod = new JLabel("    学时");
                JTextField inputColNo = new JTextField(20);
                JButton confirmButton = new JButton("确定");
                JButton cancelButton = new JButton("取消");
                JLabel mmessage = new JLabel();
                mmessage.setForeground(Color.red);
                mmessage.setText("登录失败");
                mmessage.setVisible(false);
                JPanel nopanel = new JPanel();
                nopanel.add(inputCourseNo);
                nopanel.add(inputNo);
                JPanel namepanel = new JPanel();
                namepanel.add(inputCourseName);
                namepanel.add(inputName);
                JPanel pronamepanel = new JPanel();
                pronamepanel.add(inputCourseCredict);
                pronamepanel.add(inputCredict);
                JPanel colnopanel = new JPanel();
                colnopanel.add(inputCoursePeriod);
                colnopanel.add(inputColNo);
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(confirmButton);
                buttonPanel.add(cancelButton);
                inputFrame.add(nopanel);
                inputFrame.add(namepanel);
                inputFrame.add(pronamepanel);
                inputFrame.add(colnopanel);
                inputFrame.add(buttonPanel);
                inputFrame.add(mmessage);
                inputFrame.add(new JPanel());

                nopanel.setBounds(15, 0, 420, 40);
                namepanel.setBounds(15, 40, 420, 40);
                pronamepanel.setBounds(15, 80, 420, 40);
                colnopanel.setBounds(15, 120, 420, 40);
                buttonPanel.setBounds(15, 160, 420, 40);
                mmessage.setBounds(15, 200, 420, 40);
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String no = inputNo.getText();
                        String name = inputName.getText();
                        String credict = inputCredict.getText();
                        String period = inputColNo.getText();
                        if (no.equals("") || name.equals("") || credict.equals("") || period.equals("")) {
                            mmessage.setText("请输入完整信息");
                            mmessage.setVisible(true);
                        } else {
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerInsertCourse(new Course(name, no, Integer.parseInt(credict), Integer.parseInt(period)));
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                CourseMDialog courseMDialog = new CourseMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("增加课程失败");
                            }
                        }
                    }
                });

                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        inputFrame.dispose();
                    }
                });


                inputFrame.setVisible(true);
                inputFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            }
        });
        updateCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = courseMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择课程");
                } else {
                    int crow = crows[0];
                    String courseNo = (String) courseMessTable.getValueAt(crow, 0);
                    JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                    inputFrame.setTitle("更改课程");
                    JLabel inputCourseName = new JLabel("课程名");
                    JTextField inputName = new JTextField(20);
                    JLabel inputCredict = new JLabel("  学分");
                    JTextField inputcredict = new JTextField(20);
                    JLabel inputperiod = new JLabel("  学时");
                    JTextField inputper = new JTextField(20);
                    JButton confirmButton = new JButton("确定");
                    JButton cancelButton = new JButton("取消");
                    JLabel mmessage = new JLabel();
                    mmessage.setForeground(Color.red);
                    mmessage.setText("登录失败");
                    mmessage.setVisible(false);
                    JPanel namepanel = new JPanel();
                    namepanel.add(inputCourseName);
                    namepanel.add(inputName);
                    JPanel pronamepanel = new JPanel();
                    pronamepanel.add(inputCredict);
                    pronamepanel.add(inputcredict);
                    JPanel colnopanel = new JPanel();
                    colnopanel.add(inputperiod);
                    colnopanel.add(inputper);
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(confirmButton);
                    buttonPanel.add(cancelButton);
                    inputFrame.add(namepanel);
                    inputFrame.add(pronamepanel);
                    inputFrame.add(colnopanel);
                    inputFrame.add(buttonPanel);
                    inputFrame.add(mmessage);
                    inputFrame.add(new JPanel());

                    namepanel.setBounds(15, 40, 420, 40);
                    pronamepanel.setBounds(15, 80, 420, 40);
                    colnopanel.setBounds(15, 120, 420, 40);
                    buttonPanel.setBounds(15, 160, 420, 40);
                    mmessage.setBounds(15, 200, 420, 40);
                    confirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String name = inputName.getText();
                            String credict = inputcredict.getText();
                            String period = inputper.getText();

                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerUpdateCourse(new Course(name, courseNo, credict.equals("") ? -1 : Integer.parseInt(credict), period.equals("") ? -1 : Integer.parseInt(period)));
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                CourseMDialog courseMDialog = new CourseMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("更改课程失败");
                            }
                        }
                    });

                    cancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            inputFrame.dispose();
                        }
                    });


                    inputFrame.setVisible(true);
                    inputFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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
