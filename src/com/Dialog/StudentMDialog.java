package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.Class;
import com.Instance.Student;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentMDialog extends BaseDialog {
    private JFrame containerFrame;
    private JTable studentMessTable;
    private JScrollPane studentMessageTable;
    private JPanel modifyStudentPanel;
    private JButton insertStudentButton;
    private JButton updateStudentButton;
    private JButton deleteStudentButton;
    private JLabel message;

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y - 160, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH + 150);
        containerFrame.setTitle("学生管理");

        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
        ArrayList<Student> students = sqlServer.managerSelectStudent();
        int ssize = students.size();

        Object[][] table = new Object[ssize][8];
        for (int i = 0; i < ssize; i++) {
            Student student = students.get(i);
            table[i][0] = student.getStudentNo();
            table[i][1] = student.getStudentName();
            table[i][2] = student.getStudentAge();
            table[i][3] = student.getStudentSex();
            table[i][4] = student.getStudentNation();
            table[i][5] = student.getStudentBirthday();
            table[i][6] = student.getStudentInsNo();
            table[i][7] = student.getStudentClassNo();
        }

        Object[] name = {"学号", "姓名", "年龄", "性别", "民族", "出生日期", "指导员学号", "班级编号"};

        studentMessTable = new JTable(table, name);
        studentMessTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        studentMessTable.getColumnModel().getColumn(6).setPreferredWidth(120);
        studentMessTable.getColumnModel().getColumn(7).setPreferredWidth(120);


        message = new JLabel();
        message.setForeground(Color.red);
        message.setText("登录失败");

        studentMessageTable = new JScrollPane(studentMessTable);

        insertStudentButton = new JButton("增加");
        updateStudentButton = new JButton("更改");
        deleteStudentButton = new JButton("删除");
        modifyStudentPanel = new JPanel();

        containerFrame.add(studentMessageTable);

        modifyStudentPanel.add(insertStudentButton);
        modifyStudentPanel.add(updateStudentButton);
        modifyStudentPanel.add(deleteStudentButton);
        containerFrame.add(modifyStudentPanel);

        containerFrame.add(message);
        containerFrame.add(new JPanel());
        studentMessageTable.setBounds(0, 0, 412, 20 * 30);
        modifyStudentPanel.setBounds(0, 620, 412, 40);
        message.setBounds(20, 660, 412, 40);
        containerFrame.setVisible(true);
        message.setVisible(false);
        containerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addListener() {
        deleteStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = studentMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择学生");
                } else {
                    int crow = crows[0];
                    String studentNo = (String) studentMessTable.getValueAt(crow, 0);
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.managerDeleteStudent(studentNo);
                    if (result) {
                        setMessage("删除学生成功");
                        containerFrame.dispose();
                        StudentMDialog studentMDialog = new StudentMDialog();
                    } else {
                        setMessage("删除失败");
                    }
                }
            }
        });
        insertStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                inputFrame.setTitle("增加学生");
                JLabel inputStudentNo = new JLabel("      学号");
                JTextField inputNo = new JTextField(20);
                JLabel inputStudentName = new JLabel("      姓名");
                JTextField inputName = new JTextField(20);
                JLabel inputStudentAge = new JLabel("      年龄");
                JTextField inputAge = new JTextField(20);
                JLabel inputStudentSex = new JLabel("      性别");
                JTextField inputSex = new JTextField(20);
                JLabel inputStudentNation = new JLabel("      民族");
                JTextField inputNation = new JTextField(20);
                JLabel inputStudentBirthday = new JLabel("  出生日期");
                JTextField inputBirthday = new JTextField(20);
                JLabel inputStudentInsNo = new JLabel("指导员学号");
                JTextField inputInsNo = new JTextField(20);
                JLabel inputStudentClassNo = new JLabel("  班级编号");
                JTextField inputClassNo = new JTextField(20);

                JButton confirmButton = new JButton("确定");
                JButton cancelButton = new JButton("取消");
                JLabel mmessage = new JLabel();
                mmessage.setForeground(Color.red);
                mmessage.setText("登录失败");
                mmessage.setVisible(false);

                JPanel nopanel = new JPanel();
                nopanel.add(inputStudentNo);
                nopanel.add(inputNo);

                JPanel namepanel = new JPanel();
                namepanel.add(inputStudentName);
                namepanel.add(inputName);

                JPanel pronamepanel = new JPanel();
                pronamepanel.add(inputStudentAge);
                pronamepanel.add(inputAge);

                JPanel colnopanel = new JPanel();
                colnopanel.add(inputStudentSex);
                colnopanel.add(inputSex);

                JPanel nationPanel = new JPanel();
                nationPanel.add(inputStudentNation);
                nationPanel.add(inputNation);

                JPanel birthdayPanel = new JPanel();
                birthdayPanel.add(inputStudentBirthday);
                birthdayPanel.add(inputBirthday);

                JPanel insnoPanel = new JPanel();
                insnoPanel.add(inputStudentInsNo);
                insnoPanel.add(inputInsNo);

                JPanel classnoPanel = new JPanel();
                classnoPanel.add(inputStudentClassNo);
                classnoPanel.add(inputClassNo);


                JPanel buttonPanel = new JPanel();
                buttonPanel.add(confirmButton);
                buttonPanel.add(cancelButton);

                inputFrame.add(nopanel);
                inputFrame.add(namepanel);
                inputFrame.add(pronamepanel);
                inputFrame.add(colnopanel);
                inputFrame.add(nationPanel);
                inputFrame.add(birthdayPanel);
                inputFrame.add(insnoPanel);
                inputFrame.add(classnoPanel);
                inputFrame.add(buttonPanel);
                inputFrame.add(mmessage);
                inputFrame.add(new JPanel());

                nopanel.setBounds(15, 0, 420, 40);
                namepanel.setBounds(15, 40, 420, 40);
                pronamepanel.setBounds(15, 80, 420, 40);
                colnopanel.setBounds(15, 120, 420, 40);
                nationPanel.setBounds(15, 160, 420, 40);
                birthdayPanel.setBounds(15, 200, 420, 40);
                insnoPanel.setBounds(15, 240, 420, 40);
                classnoPanel.setBounds(15, 280, 420, 40);
                buttonPanel.setBounds(15, 320, 420, 40);
                mmessage.setBounds(15, 360, 420, 40);

                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String no = inputNo.getText();
                        String name = inputName.getText();
                        String age = inputAge.getText();
                        String sex = inputSex.getText();
                        String nation = inputNation.getText();
                        String birthday = inputBirthday.getText();
                        String insno = inputInsNo.getText();
                        String classno = inputClassNo.getText();
                        if (no.equals("") || name.equals("") || age.equals("") || sex.equals("")
                                || nation.equals("") || birthday.equals("") || insno.equals("") || classno.equals("")) {
                            mmessage.setText("请输入完整信息");
                            mmessage.setVisible(true);
                        } else {
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerInsertStudent(new Student(name, no, age, sex, nation,
                                    birthday, insno, classno));
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                StudentMDialog studentMDialog = new StudentMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("增加学生失败");
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
        updateStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = studentMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择学生");
                } else {
                    JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                    inputFrame.setTitle("更新学生");
                    JLabel inputStudentName = new JLabel("      姓名");
                    JTextField inputName = new JTextField(20);
                    JLabel inputStudentAge = new JLabel("      年龄");
                    JTextField inputAge = new JTextField(20);
                    JLabel inputStudentSex = new JLabel("      性别");
                    JTextField inputSex = new JTextField(20);
                    JLabel inputStudentNation = new JLabel("      民族");
                    JTextField inputNation = new JTextField(20);
                    JLabel inputStudentBirthday = new JLabel("  出生日期");
                    JTextField inputBirthday = new JTextField(20);
                    JLabel inputStudentInsNo = new JLabel("指导员学号");
                    JTextField inputInsNo = new JTextField(20);
                    JLabel inputStudentClassNo = new JLabel("  班级编号");
                    JTextField inputClassNo = new JTextField(20);

                    JButton confirmButton = new JButton("确定");
                    JButton cancelButton = new JButton("取消");
                    JLabel mmessage = new JLabel();
                    mmessage.setForeground(Color.red);
                    mmessage.setText("登录失败");
                    mmessage.setVisible(false);
                    JPanel namepanel = new JPanel();
                    namepanel.add(inputStudentName);
                    namepanel.add(inputName);
                    JPanel pronamepanel = new JPanel();
                    pronamepanel.add(inputStudentAge);
                    pronamepanel.add(inputAge);
                    JPanel colnopanel = new JPanel();
                    colnopanel.add(inputStudentSex);
                    colnopanel.add(inputSex);

                    JPanel nationPanel = new JPanel();
                    nationPanel.add(inputStudentNation);
                    nationPanel.add(inputNation);

                    JPanel birthdayPanel = new JPanel();
                    birthdayPanel.add(inputStudentBirthday);
                    birthdayPanel.add(inputBirthday);

                    JPanel insnoPanel = new JPanel();
                    insnoPanel.add(inputStudentInsNo);
                    insnoPanel.add(inputInsNo);

                    JPanel classnoPanel = new JPanel();
                    classnoPanel.add(inputStudentClassNo);
                    classnoPanel.add(inputClassNo);


                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(confirmButton);
                    buttonPanel.add(cancelButton);
                    inputFrame.add(namepanel);
                    inputFrame.add(pronamepanel);
                    inputFrame.add(colnopanel);
                    inputFrame.add(nationPanel);
                    inputFrame.add(birthdayPanel);
                    inputFrame.add(insnoPanel);
                    inputFrame.add(classnoPanel);
                    inputFrame.add(buttonPanel);
                    inputFrame.add(mmessage);
                    inputFrame.add(new JPanel());

                    namepanel.setBounds(15, 40, 420, 40);
                    pronamepanel.setBounds(15, 80, 420, 40);
                    colnopanel.setBounds(15, 120, 420, 40);
                    nationPanel.setBounds(15, 160, 420, 40);
                    birthdayPanel.setBounds(15, 200, 420, 40);
                    insnoPanel.setBounds(15, 240, 420, 40);
                    classnoPanel.setBounds(15, 280, 420, 40);
                    buttonPanel.setBounds(15, 320, 420, 40);
                    mmessage.setBounds(15, 360, 420, 40);
                    confirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int crow = crows[0];
                            String studentNo = (String) studentMessTable.getValueAt(crow, 0);
                            String name = inputName.getText();
                            String age = inputAge.getText();
                            String sex = inputSex.getText();
                            String nation = inputNation.getText();
                            String birthday = inputBirthday.getText();
                            String insno = inputInsNo.getText();
                            String classno = inputClassNo.getText();

                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerUpdateStudent(new Student(name, studentNo, age, sex, nation,
                                    birthday, insno, classno));
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                StudentMDialog studentMDialog = new StudentMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("更改学生失败");
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
