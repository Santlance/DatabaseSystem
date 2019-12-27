package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.Class;
import com.Instance.College;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

public class CollegeMDialog extends BaseDialog {
    private JFrame containerFrame;
    private JTable collegeMessTable;
    private JScrollPane collegeMessageTable;
    private JTable collegeTeacherMessTable;
    private JScrollPane collegeTeacherMessageTable;
    private JPanel modifyCollegePanel;
    private JButton insertCollegeButton;
    private JButton updateCollegeButton;
    private JButton deleteCollegeButton;
    private JPanel modifyCollegeTeacherPanel;
    private JButton insertCollegeTeacherButton;
    private JButton updateCollegeTeacherButton;
    private JButton deleteCollegeTeacherButton;
    private JLabel message;

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y - 160, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH + 150);
        containerFrame.setTitle("系管理");

        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));

        ArrayList<College> colleges = sqlServer.managerSelectCollege();
        int csize = colleges.size();
        Object[][] ctable = new Object[csize][2];

        for (int i = 0; i < csize; i++) {
            College college = colleges.get(i);
            ctable[i][0] = college.getCollegeNo();
            ctable[i][1] = college.getCollegeName();
        }

        ArrayList<Pair<String, String>> collegeTeachers = sqlServer.managerSelectCollegeManager();
        int ctsize = collegeTeachers.size();
        Object[][] cttable = new Object[ctsize][2];
        for (int i = 0; i < ctsize; i++) {
            Pair<String, String> collegeTeacher = collegeTeachers.get(i);
            cttable[i][0] = collegeTeacher.getKey();
            cttable[i][1] = collegeTeacher.getValue();
        }


        message = new JLabel();
        message.setForeground(Color.red);
        message.setText("登录失败");

        Object[] cname = {"系编号", "系名"};
        collegeMessTable = new JTable(ctable, cname);
        collegeMessageTable = new JScrollPane(collegeMessTable);
        Object[] ctname = {"系编号", "系主任教师编号"};
        collegeTeacherMessTable = new JTable(cttable, ctname);
        collegeTeacherMessageTable = new JScrollPane(collegeTeacherMessTable);

        insertCollegeButton = new JButton("增加系");
        updateCollegeButton = new JButton("更改系");
        deleteCollegeButton = new JButton("删除系");
        modifyCollegePanel = new JPanel();

        insertCollegeTeacherButton = new JButton("增加系主任");
        updateCollegeTeacherButton = new JButton("更改系主任");
        deleteCollegeTeacherButton = new JButton("删除系主任");
        modifyCollegeTeacherPanel = new JPanel();

        containerFrame.add(collegeMessageTable);

        modifyCollegePanel.add(insertCollegeButton);
        modifyCollegePanel.add(updateCollegeButton);
        modifyCollegePanel.add(deleteCollegeButton);
        containerFrame.add(modifyCollegePanel);

        containerFrame.add(collegeTeacherMessageTable);

        modifyCollegeTeacherPanel.add(insertCollegeTeacherButton);
        modifyCollegeTeacherPanel.add(updateCollegeTeacherButton);
        modifyCollegeTeacherPanel.add(deleteCollegeTeacherButton);
        containerFrame.add(modifyCollegeTeacherPanel);

        containerFrame.add(message);
        containerFrame.add(new JPanel());
        collegeMessageTable.setBounds(0, 0, 412, 20 * 12);
        modifyCollegePanel.setBounds(0, 260, 412, 40);
        collegeTeacherMessageTable.setBounds(0, 320, 412, 20 * 12);
        modifyCollegeTeacherPanel.setBounds(0, 580, 412, 40);
        message.setBounds(20, 660, 412, 40);
        containerFrame.setVisible(true);
        message.setVisible(false);
        containerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addListener() {
        deleteCollegeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = collegeMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择系");
                } else {
                    int crow = crows[0];
                    String collegeNo = (String) collegeMessTable.getValueAt(crow, 0);
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.managerDeleteCollege(collegeNo);
                    if (result) {
                        setMessage("删除系成功");
                        containerFrame.dispose();
                        CollegeMDialog collegeMDialog = new CollegeMDialog();
                    } else {
                        setMessage("删除失败");
                    }
                }
            }
        });
        insertCollegeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                inputFrame.setTitle("增加系");
                JLabel inputClassNo = new JLabel("系编号");
                JTextField inputNo = new JTextField(20);
                JLabel inputClassName = new JLabel("  系名");
                JTextField inputName = new JTextField(20);
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
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(confirmButton);
                buttonPanel.add(cancelButton);
                inputFrame.add(nopanel);
                inputFrame.add(namepanel);
                inputFrame.add(buttonPanel);
                inputFrame.add(mmessage);
                inputFrame.add(new JPanel());

                nopanel.setBounds(15, 0, 420, 40);
                namepanel.setBounds(15, 40, 420, 40);
                buttonPanel.setBounds(15, 160, 420, 40);
                mmessage.setBounds(15, 200, 420, 40);
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String no = inputNo.getText();
                        String name = inputName.getText();
                        if (no.equals("") || name.equals("")) {
                            mmessage.setText("请输入完整信息");
                            mmessage.setVisible(true);
                        } else {
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerInsertCollege(new College(name, no));
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                CollegeMDialog collegeMDialog = new CollegeMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("增加系失败");
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
        updateCollegeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = collegeMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择系");
                } else {
                    int crow = crows[0];
                    String collegeNo = (String) collegeMessTable.getValueAt(crow, 0);
                    JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                    inputFrame.setTitle("更改系");
                    JLabel inputClassName = new JLabel("系名");
                    JTextField inputName = new JTextField(20);
                    JButton confirmButton = new JButton("确定");
                    JButton cancelButton = new JButton("取消");
                    JLabel mmessage = new JLabel();
                    mmessage.setForeground(Color.red);
                    mmessage.setText("登录失败");
                    mmessage.setVisible(false);
                    JPanel namepanel = new JPanel();
                    namepanel.add(inputClassName);
                    namepanel.add(inputName);
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(confirmButton);
                    buttonPanel.add(cancelButton);
                    inputFrame.add(namepanel);
                    inputFrame.add(buttonPanel);
                    inputFrame.add(mmessage);
                    inputFrame.add(new JPanel());

                    namepanel.setBounds(15, 40, 420, 40);
                    buttonPanel.setBounds(15, 160, 420, 40);
                    mmessage.setBounds(15, 200, 420, 40);
                    confirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String name = inputName.getText();
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerUpdateCollege(new College(name, collegeNo));
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                CollegeMDialog collegeMDialog = new CollegeMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("更改系失败");
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

        insertCollegeTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                inputFrame.setTitle("增加系主任");
                JLabel inputCollegeNo = new JLabel("        系编号");
                JTextField inputNo = new JTextField(20);
                JLabel inputTeacherNo = new JLabel("系主任教师编号");
                JTextField inputTno = new JTextField(20);
                JButton confirmButton = new JButton("确定");
                JButton cancelButton = new JButton("取消");
                JLabel mmessage = new JLabel();
                mmessage.setForeground(Color.red);
                mmessage.setText("登录失败");
                mmessage.setVisible(false);
                JPanel nopanel = new JPanel();
                nopanel.add(inputCollegeNo);
                nopanel.add(inputNo);
                JPanel namepanel = new JPanel();
                namepanel.add(inputTeacherNo);
                namepanel.add(inputTno);
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(confirmButton);
                buttonPanel.add(cancelButton);
                inputFrame.add(nopanel);
                inputFrame.add(namepanel);
                inputFrame.add(buttonPanel);
                inputFrame.add(mmessage);
                inputFrame.add(new JPanel());

                nopanel.setBounds(15, 0, 420, 40);
                namepanel.setBounds(15, 40, 420, 40);
                buttonPanel.setBounds(15, 160, 420, 40);
                mmessage.setBounds(15, 200, 420, 40);
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String no = inputNo.getText();
                        String name = inputTno.getText();
                        if (no.equals("") || name.equals("")) {
                            mmessage.setText("请输入完整信息");
                            mmessage.setVisible(true);
                        } else {
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            String csql = "select * from College where collegeNo = " + sqlServer.modifyStr(no);
                            String tsql = "select * from Teacher where teacherNo = " + sqlServer.modifyStr(name);
                            ResultSet cset = sqlServer.executeQuery(csql);
                            ResultSet tset = sqlServer.executeQuery(tsql);

                            try {
                                //System.err.println(cset.getFetchSize() + " " + tset.getFetchSize());
                                if (!(cset.next() && tset.next())) {
                                    inputFrame.dispose();
                                    setMessage("系或教师不存在");
                                } else {
                                    boolean result = sqlServer.managerInsertCollegeManager(no, name);
                                    if (result) {
                                        inputFrame.dispose();
                                        containerFrame.dispose();
                                        CollegeMDialog collegeMDialog = new CollegeMDialog();
                                    } else {
                                        inputFrame.dispose();
                                        setMessage("增加系-系主任失败");
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
        updateCollegeTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = collegeTeacherMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择系-系主任");
                } else {
                    int crow = crows[0];
                    String collegeNo = (String) collegeMessTable.getValueAt(crow, 0);
                    JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                    inputFrame.setTitle("更改系主任");
                    JLabel inputTeacherNo = new JLabel("系主任教师编号");
                    JTextField inputTno = new JTextField(20);
                    JButton confirmButton = new JButton("确定");
                    JButton cancelButton = new JButton("取消");
                    JLabel mmessage = new JLabel();
                    mmessage.setForeground(Color.red);
                    mmessage.setText("登录失败");
                    mmessage.setVisible(false);
                    JPanel namepanel = new JPanel();
                    namepanel.add(inputTeacherNo);
                    namepanel.add(inputTno);
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(confirmButton);
                    buttonPanel.add(cancelButton);
                    inputFrame.add(namepanel);
                    inputFrame.add(buttonPanel);
                    inputFrame.add(mmessage);
                    inputFrame.add(new JPanel());

                    namepanel.setBounds(15, 40, 420, 40);
                    buttonPanel.setBounds(15, 160, 420, 40);
                    mmessage.setBounds(15, 200, 420, 40);
                    confirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String Tno = inputTno.getText();
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerUpdateCollegeManager(collegeNo, Tno);
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                CollegeMDialog collegeMDialog = new CollegeMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("更改系-系主任失败");
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
        deleteCollegeTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = collegeTeacherMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择系-系主任");
                } else {
                    int crow = crows[0];
                    String collegeNo = (String) collegeTeacherMessTable.getValueAt(crow, 0);
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.managerDeleteCollegeManager(collegeNo);
                    if (result) {
                        setMessage("删除系-系主任成功");
                        containerFrame.dispose();
                        CollegeMDialog collegeMDialog = new CollegeMDialog();
                    } else {
                        setMessage("删除失败");
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
