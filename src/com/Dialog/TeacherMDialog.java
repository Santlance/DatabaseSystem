package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.Class;
import com.Instance.Teacher;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TeacherMDialog extends BaseDialog {
    private JFrame containerFrame;
    private JTable teacherMessTable;
    private JScrollPane teacherMessageTable;
    private JPanel modifyTeacherPanel;
    private JButton insertTeacherButton;
    private JButton updateTeacherButton;
    private JButton deleteTeacherButton;
    private JLabel message;

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y - 160, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH + 150);
        containerFrame.setTitle("教师管理");

        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));

        ArrayList<Teacher> teachers = sqlServer.managerSelectTeacher();
        int csize = teachers.size();
        Object[][] ctable = new Object[csize][4];

        for (int i = 0; i < csize; i++) {
            Teacher teacher = teachers.get(i);
            ctable[i][0] = teacher.getTeacherNo();
            ctable[i][1] = teacher.getTeacherName();
            ctable[i][2] = teacher.getTeacherResDir();
            ctable[i][3] = teacher.getTeacherCollegeNo();
        }


        message = new JLabel();
        message.setForeground(Color.red);
        message.setText("登录失败");

        Object[] cname = {"教师编号", "教师名", "研究领域", "教师系编号"};
        teacherMessTable = new JTable(ctable, cname);
        teacherMessageTable = new JScrollPane(teacherMessTable);

        insertTeacherButton = new JButton("增加");
        updateTeacherButton = new JButton("更改");
        deleteTeacherButton = new JButton("删除");
        modifyTeacherPanel = new JPanel();

        containerFrame.add(teacherMessageTable);

        modifyTeacherPanel.add(insertTeacherButton);
        modifyTeacherPanel.add(updateTeacherButton);
        modifyTeacherPanel.add(deleteTeacherButton);
        containerFrame.add(modifyTeacherPanel);

        containerFrame.add(message);
        containerFrame.add(new JPanel());
        teacherMessageTable.setBounds(0, 0, 412, 20 * 30);
        modifyTeacherPanel.setBounds(0, 620, 412, 40);
        message.setBounds(20, 660, 412, 40);
        containerFrame.setVisible(true);
        message.setVisible(false);
        containerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addListener() {
        deleteTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = teacherMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择教师");
                } else {
                    int crow = crows[0];
                    String teacherNo = (String) teacherMessTable.getValueAt(crow, 0);
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.managerDeleteTeacher(teacherNo);
                    if (result) {
                        setMessage("删除教师成功");
                        containerFrame.dispose();
                        TeacherMDialog teacherMDialog = new TeacherMDialog();
                    } else {
                        setMessage("删除失败");
                    }
                }
            }
        });
        insertTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                inputFrame.setTitle("增加教师");
                JLabel inputTeacherNo = new JLabel("  教师编号");
                JTextField inputNo = new JTextField(20);
                JLabel inputTeacherName = new JLabel("     教师名");
                JTextField inputName = new JTextField(20);
                JLabel inputTeacherResDir = new JLabel("  研究领域");
                JTextField inputResDir = new JTextField(20);
                JLabel inputTeacherColNo = new JLabel("教师系编号");
                JTextField inputColNo = new JTextField(20);
                JButton confirmButton = new JButton("确定");
                JButton cancelButton = new JButton("取消");
                JLabel mmessage = new JLabel();
                mmessage.setForeground(Color.red);
                mmessage.setText("登录失败");
                mmessage.setVisible(false);
                JPanel nopanel = new JPanel();
                nopanel.add(inputTeacherNo);
                nopanel.add(inputNo);
                JPanel namepanel = new JPanel();
                namepanel.add(inputTeacherName);
                namepanel.add(inputName);
                JPanel pronamepanel = new JPanel();
                pronamepanel.add(inputTeacherResDir);
                pronamepanel.add(inputResDir);
                JPanel colnopanel = new JPanel();
                colnopanel.add(inputTeacherColNo);
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
                        String resdir = inputResDir.getText();
                        String colno = inputColNo.getText();
                        if (no.equals("") || name.equals("") || resdir.equals("") || colno.equals("")) {
                            mmessage.setText("请输入完整信息");
                            mmessage.setVisible(true);
                        } else {
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerInsertTeacher(new Teacher(name, no, resdir, colno));
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                TeacherMDialog teacherMDialog = new TeacherMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("增加教师失败");
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
        updateTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = teacherMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择教师");
                } else {
                    int crow = crows[0];
                    String teacherNo = (String) teacherMessTable.getValueAt(crow, 0);
                    JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                    inputFrame.setTitle("更改教师");
                    JLabel inputTeacherName = new JLabel("     教师名");
                    JTextField inputName = new JTextField(20);
                    JLabel inputTeacherResDir = new JLabel("  研究领域");
                    JTextField inputResDir = new JTextField(20);
                    JLabel inputTeacherColNo = new JLabel("教师系编号");
                    JTextField inputColNo = new JTextField(20);
                    JButton confirmButton = new JButton("确定");
                    JButton cancelButton = new JButton("取消");
                    JLabel mmessage = new JLabel();
                    mmessage.setForeground(Color.red);
                    mmessage.setText("登录失败");
                    mmessage.setVisible(false);
                    JPanel namepanel = new JPanel();
                    namepanel.add(inputTeacherName);
                    namepanel.add(inputName);
                    JPanel pronamepanel = new JPanel();
                    pronamepanel.add(inputTeacherResDir);
                    pronamepanel.add(inputResDir);
                    JPanel colnopanel = new JPanel();
                    colnopanel.add(inputTeacherColNo);
                    colnopanel.add(inputColNo);
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
                            String resdir = inputResDir.getText();
                            String colno = inputColNo.getText();

                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerUpdateTeacher(new Teacher(name, teacherNo, resdir, colno));
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                TeacherMDialog teacherMDialog = new TeacherMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("更改教师失败");
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
