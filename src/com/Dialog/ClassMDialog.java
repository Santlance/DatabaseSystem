package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.Class;
import com.Instance.Course;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClassMDialog extends BaseDialog {
    private JFrame containerFrame;
    private JTable classMessTable;
    private JScrollPane classMessageTable;
    private JPanel modifyClassPanel;
    private JButton insertClassButton;
    private JButton updateClassButton;
    private JButton deleteClassButton;
    private JTable classMonitorMessTable;
    private JScrollPane classMonitorMessageTable;
    private JPanel modifyClassMonitorPanel;
    private JButton insertClassMonitorButton;
    private JButton updateClassMonitorButton;
    private JButton deleteClassMonitorButton;
    private JLabel message;

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y - 160, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH + 150);
        containerFrame.setTitle("班级管理");

        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));

        ArrayList<Class> classes = sqlServer.managerSelectClass();
        int csize = classes.size();
        Object[][] ctable = new Object[csize][4];

        for (int i = 0; i < csize; i++) {
            Class classObj = classes.get(i);
            ctable[i][0] = classObj.getClassNo();
            ctable[i][1] = classObj.getClassName();
            ctable[i][2] = classObj.getClassProName();
            ctable[i][3] = classObj.getClassCollegeNo();
        }

        ArrayList<Pair<String, String>> classMonitors = sqlServer.managerSelectClassMonitor();
        int cmsize = classMonitors.size();
        Object[][] cmtable = new Object[cmsize][2];

        for (int i = 0; i < cmsize; i++) {
            Pair<String, String> classMonitor = classMonitors.get(i);
            cmtable[i][0] = classMonitor.getKey();
            cmtable[i][1] = classMonitor.getValue();
        }


        message = new JLabel();
        message.setForeground(Color.red);
        message.setText("登录失败");

        Object[] cname = {"班级编号", "班级名", "专业名", "班级系编号"};
        classMessTable = new JTable(ctable, cname);
        classMessageTable = new JScrollPane(classMessTable);

        insertClassButton = new JButton("增加班级");
        updateClassButton = new JButton("更改班级");
        deleteClassButton = new JButton("删除班级");
        modifyClassPanel = new JPanel();

        Object[] cmname = {"班级编号", "班长学号"};
        classMonitorMessTable = new JTable(cmtable, cmname);
        classMonitorMessageTable = new JScrollPane(classMonitorMessTable);

        insertClassMonitorButton = new JButton("增加班长");
        updateClassMonitorButton = new JButton("更改班长");
        deleteClassMonitorButton = new JButton("删除班长");
        modifyClassMonitorPanel = new JPanel();


        containerFrame.add(classMessageTable);

        modifyClassPanel.add(insertClassButton);
        modifyClassPanel.add(updateClassButton);
        modifyClassPanel.add(deleteClassButton);
        containerFrame.add(modifyClassPanel);


        containerFrame.add(classMonitorMessageTable);

        modifyClassMonitorPanel.add(insertClassMonitorButton);
        modifyClassMonitorPanel.add(updateClassMonitorButton);
        modifyClassMonitorPanel.add(deleteClassMonitorButton);
        containerFrame.add(modifyClassMonitorPanel);


        containerFrame.add(message);
        containerFrame.add(new JPanel());
        classMessageTable.setBounds(0, 0, 412, 20 * 12);
        modifyClassPanel.setBounds(0, 260, 412, 40);
        classMonitorMessageTable.setBounds(0, 320, 412, 20 * 12);
        modifyClassMonitorPanel.setBounds(0, 580, 412, 40);

        message.setBounds(20, 660, 412, 40);
        containerFrame.setVisible(true);
        message.setVisible(false);
        containerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addListener() {
        deleteClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = classMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择班级");
                } else {
                    int crow = crows[0];
                    String classNo = (String) classMessTable.getValueAt(crow, 0);
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.managerDeleteClass(classNo);
                    if (result) {
                        setMessage("删除班级成功");
                        containerFrame.dispose();
                        ClassMDialog classMDialog = new ClassMDialog();
                    } else {
                        setMessage("删除失败");
                    }
                }
            }
        });
        insertClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 300);
                inputFrame.setTitle("增加班级");
                JLabel inputClassNo = new JLabel("  班级编号");
                JTextField inputNo = new JTextField(20);
                JLabel inputClassName = new JLabel("     班级名");
                JTextField inputName = new JTextField(20);
                JLabel inputClassProName = new JLabel("     专业名");
                JTextField inputProName = new JTextField(20);
                JLabel inputClassColNo = new JLabel("班级系编号");
                JTextField inputColNo = new JTextField(20);
                JButton confirmButton = new JButton("确定");
                JButton cancelButton = new JButton("取消");
                JLabel mmessage = new JLabel();
                mmessage.setForeground(Color.red);
                mmessage.setText("登录失败");
                mmessage.setVisible(false);
                JPanel nopanel = new JPanel();
                nopanel.add(inputClassNo);
                nopanel.add(inputNo);
                JPanel namepanel = new JPanel();
                namepanel.add(inputClassName);
                namepanel.add(inputName);
                JPanel pronamepanel = new JPanel();
                pronamepanel.add(inputClassProName);
                pronamepanel.add(inputProName);
                JPanel colnopanel = new JPanel();
                colnopanel.add(inputClassColNo);
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
                        String proname = inputProName.getText();
                        String colno = inputColNo.getText();
                        if (no.equals("") || name.equals("") || proname.equals("") || colno.equals("")) {
                            mmessage.setText("请输入完整信息");
                            mmessage.setVisible(true);
                        } else {
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerInsertClass(new Class(name, no, proname, colno));
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                ClassMDialog classMDialog = new ClassMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("增加班级失败");
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
        updateClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = classMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择班级");
                } else {
                    int crow = crows[0];
                    String classNo = (String) classMessTable.getValueAt(crow, 0);
                    JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 300);
                    inputFrame.setTitle("更改班级");
                    JLabel inputClassName = new JLabel("     班级名");
                    JTextField inputName = new JTextField(20);
                    JLabel inputClassProName = new JLabel("     专业名");
                    JTextField inputProName = new JTextField(20);
                    JLabel inputClassColNo = new JLabel("班级系编号");
                    JTextField inputColNo = new JTextField(20);
                    JButton confirmButton = new JButton("确定");
                    JButton cancelButton = new JButton("取消");
                    JLabel mmessage = new JLabel();
                    mmessage.setForeground(Color.red);
                    mmessage.setText("登录失败");
                    mmessage.setVisible(false);
                    JPanel namepanel = new JPanel();
                    namepanel.add(inputClassName);
                    namepanel.add(inputName);
                    JPanel pronamepanel = new JPanel();
                    pronamepanel.add(inputClassProName);
                    pronamepanel.add(inputProName);
                    JPanel colnopanel = new JPanel();
                    colnopanel.add(inputClassColNo);
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
                            String proname = inputProName.getText();
                            String colno = inputColNo.getText();

                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerUpdateClass(new Class(name, classNo, proname, colno));
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                ClassMDialog classMDialog = new ClassMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("更改班级失败");
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

        deleteClassMonitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = classMonitorMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择班级-班长");
                } else {
                    int crow = crows[0];
                    String classNo = (String) classMonitorMessTable.getValueAt(crow, 0);
                    String monitorNo = (String) classMonitorMessTable.getValueAt(crow, 1);
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.managerDeleteClassMonitor(classNo, monitorNo);
                    if (result) {
                        setMessage("删除班级-班长成功");
                        containerFrame.dispose();
                        ClassMDialog classMDialog = new ClassMDialog();
                    } else {
                        setMessage("删除失败");
                    }
                }
            }
        });
        insertClassMonitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 300);
                inputFrame.setTitle("增加班长");
                JLabel inputClassNo = new JLabel("  班级编号");
                JTextField inputNo = new JTextField(20);
                JLabel inputSno = new JLabel("班长学号");
                JTextField inputColNo = new JTextField(20);
                JButton confirmButton = new JButton("确定");
                JButton cancelButton = new JButton("取消");
                JLabel mmessage = new JLabel();
                mmessage.setForeground(Color.red);
                mmessage.setText("登录失败");
                mmessage.setVisible(false);
                JPanel nopanel = new JPanel();
                nopanel.add(inputClassNo);
                nopanel.add(inputNo);
                JPanel colnopanel = new JPanel();
                colnopanel.add(inputSno);
                colnopanel.add(inputColNo);
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(confirmButton);
                buttonPanel.add(cancelButton);
                inputFrame.add(nopanel);
                inputFrame.add(colnopanel);
                inputFrame.add(buttonPanel);
                inputFrame.add(mmessage);
                inputFrame.add(new JPanel());

                nopanel.setBounds(15, 0, 420, 40);
                colnopanel.setBounds(15, 40, 420, 40);
                buttonPanel.setBounds(15, 80, 420, 40);
                mmessage.setBounds(15, 200, 420, 40);
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String no = inputNo.getText();
                        String colno = inputColNo.getText();
                        if (no.equals("") || colno.equals("")) {
                            mmessage.setText("请输入完整信息");
                            mmessage.setVisible(true);
                        } else {
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            String csql = "select * from Class where classNo = " + sqlServer.modifyStr(no);
                            String tsql = "select * from Student where studentNo = " + sqlServer.modifyStr(colno);
                            ResultSet cset = sqlServer.executeQuery(csql);
                            ResultSet tset = sqlServer.executeQuery(tsql);


                            //System.err.println(cset.getFetchSize() + " " + tset.getFetchSize());
                            try {
                                if (!(cset.next() && tset.next())) {
                                    inputFrame.dispose();
                                    setMessage("班级或学生不存在");
                                } else {
                                    boolean result = sqlServer.managerInsertClassMonitor(no, colno);
                                    if (result) {
                                        inputFrame.dispose();
                                        containerFrame.dispose();
                                        ClassMDialog classMDialog = new ClassMDialog();
                                    } else {
                                        inputFrame.dispose();
                                        setMessage("增加班级-班长失败");
                                    }
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
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
        updateClassMonitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = classMonitorMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择班级-班长");
                } else {
                    int crow = crows[0];
                    String classNo = (String) classMonitorMessTable.getValueAt(crow, 0);
                    JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 300);
                    inputFrame.setTitle("更改班长");
                    JLabel inputClassMonitorNo = new JLabel("班长学号");
                    JTextField inputColNo = new JTextField(20);
                    JButton confirmButton = new JButton("确定");
                    JButton cancelButton = new JButton("取消");
                    JLabel mmessage = new JLabel();
                    mmessage.setForeground(Color.red);
                    mmessage.setText("登录失败");
                    mmessage.setVisible(false);
                    JPanel namepanel = new JPanel();
                    JPanel pronamepanel = new JPanel();
                    JPanel colnopanel = new JPanel();
                    colnopanel.add(inputClassMonitorNo);
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
                            String colno = inputColNo.getText();

                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerUpdateClassMonitor(classNo, colno);
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                ClassMDialog classMDialog = new ClassMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("更改班级失败");
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
