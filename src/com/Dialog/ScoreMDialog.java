package com.Dialog;

import com.Base.BaseDialog;
import com.DataBase.SqlServer;
import com.DataBase.SqlUser;
import com.Instance.Class;
import com.Main;
import com.UI.MyFrame;
import com.Utils.ConstantUtils;
import org.javatuples.Quartet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScoreMDialog extends BaseDialog {
    private JFrame containerFrame;
    private JTable scoreMessTable;
    private JScrollPane scoreMessageTable;
    private JPanel modifyScorePanel;
    private JButton insertScoreButton;
    private JButton updateScoreButton;
    private JButton deleteScoreButton;

    private JButton statisticalScoreButton;

    private JLabel message;

    @Override
    public void initView() {
        containerFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y - 160, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH + 150);
        containerFrame.setTitle("成绩管理");

        SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));

        ArrayList<Quartet<String, String, String, Integer>> scores = sqlServer.managerSelectStuCouTeaSco();
        int csize = scores.size();
        Object[][] ctable = new Object[csize][4];

        for (int i = 0; i < csize; i++) {
            Quartet<String, String, String, Integer> score = scores.get(i);
            ctable[i][0] = score.getValue0();
            ctable[i][1] = score.getValue1();
            ctable[i][2] = score.getValue2();
            ctable[i][3] = score.getValue3();
        }


        message = new JLabel();
        message.setForeground(Color.red);
        message.setText("登录失败");

        Object[] cname = {"学号", "课程编号", "任课教师", "成绩"};
        scoreMessTable = new JTable(ctable, cname);
        scoreMessageTable = new JScrollPane(scoreMessTable);

        insertScoreButton = new JButton("增加");
        updateScoreButton = new JButton("更改");
        deleteScoreButton = new JButton("删除");
        statisticalScoreButton = new JButton("统计平均成绩");
        modifyScorePanel = new JPanel();

        containerFrame.add(scoreMessageTable);

        modifyScorePanel.add(insertScoreButton);
        modifyScorePanel.add(updateScoreButton);
        modifyScorePanel.add(deleteScoreButton);
        modifyScorePanel.add(statisticalScoreButton);
        containerFrame.add(modifyScorePanel);

        containerFrame.add(message);
        containerFrame.add(new JPanel());
        scoreMessageTable.setBounds(0, 0, 412, 20 * 30);
        modifyScorePanel.setBounds(0, 620, 412, 40);
        message.setBounds(20, 660, 412, 40);
        containerFrame.setVisible(true);
        message.setVisible(false);
        containerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addListener() {
        deleteScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = scoreMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择一行");
                } else {
                    int crow = crows[0];
                    String studentNo = (String) scoreMessTable.getValueAt(crow, 0);
                    String courseNo = (String) scoreMessTable.getValueAt(crow, 1);
                    SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                    boolean result = sqlServer.managerDeleteStuCouTeaSco(studentNo, courseNo);
                    if (result) {
                        setMessage("删除成绩成功");
                        containerFrame.dispose();
                        ScoreMDialog scoreMDialog = new ScoreMDialog();
                    } else {
                        setMessage("删除失败");
                    }
                }
            }
        });
        insertScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                inputFrame.setTitle("增加");
                JLabel inputStudentNo = new JLabel("    学号");
                JTextField inputNo = new JTextField(20);
                JLabel inputClassNo = new JLabel("课程编号");
                JTextField inputCNo = new JTextField(20);
                JLabel inputTeacherNo = new JLabel("任课教师");
                JTextField inputTNo = new JTextField(20);
                JLabel inputScore = new JLabel("    成绩");
                JTextField inputscore = new JTextField(20);
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
                namepanel.add(inputClassNo);
                namepanel.add(inputCNo);
                JPanel pronamepanel = new JPanel();
                pronamepanel.add(inputTeacherNo);
                pronamepanel.add(inputTNo);
                JPanel colnopanel = new JPanel();
                colnopanel.add(inputScore);
                colnopanel.add(inputscore);
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
                        String cno = inputCNo.getText();
                        String tno = inputTNo.getText();
                        String score = inputscore.getText();
                        if (no.equals("") || cno.equals("") || tno.equals("") || score.equals("")) {
                            mmessage.setText("请输入完整信息");
                            mmessage.setVisible(true);
                        } else {
                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            String ssql = "select * from Student where studentNo = " + sqlServer.modifyStr(no);
                            String csql = "select * from TCTable where courseNo = " + sqlServer.modifyStr(cno) + " and "
                                    + " teacherNo = " + sqlServer.modifyStr(tno);
                            ResultSet sset = sqlServer.executeQuery(ssql);
                            ResultSet cset = sqlServer.executeQuery(csql);


                            try {
                                //System.err.println(cset.getFetchSize() + " " + tset.getFetchSize());
                                if (!(cset.next() && sset.next())) {
                                    inputFrame.dispose();
                                    setMessage("学生或课程或教师或课程与教师授课不符不存在");
                                } else {
                                    boolean result = sqlServer.managerInsertStuTeaCouSco(no, cno, tno, Integer.parseInt(score));
                                    if (result) {
                                        inputFrame.dispose();
                                        containerFrame.dispose();
                                        ScoreMDialog scoreMDialog = new ScoreMDialog();
                                    } else {
                                        inputFrame.dispose();
                                        setMessage("增加失败");
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
        updateScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] crows = scoreMessTable.getSelectedRows();
                if (crows.length > 1) {
                    setMessage("选择行过多");
                } else if (crows.length == 0) {
                    setMessage("请选择一行");
                } else {
                    int crow = crows[0];
                    String studentNo = (String) scoreMessTable.getValueAt(crow, 0);
                    String courseNo = (String) scoreMessTable.getValueAt(crow, 1);
                    JFrame inputFrame = new MyFrame(ConstantUtils.LOGIN_X + 420, ConstantUtils.LOGIN_Y + 100, ConstantUtils.LOGIN_WIDTH + 20, ConstantUtils.LOGIN_HEIGH - 200);
                    inputFrame.setTitle("更改");
                    JLabel inputTeacherNo = new JLabel("任课教师");
                    JTextField inputTNo = new JTextField(20);
                    JLabel inputScore = new JLabel("    成绩");
                    JTextField inputscore = new JTextField(20);
                    JButton confirmButton = new JButton("确定");
                    JButton cancelButton = new JButton("取消");
                    JLabel mmessage = new JLabel();
                    mmessage.setForeground(Color.red);
                    mmessage.setText("登录失败");
                    mmessage.setVisible(false);
                    JPanel pronamepanel = new JPanel();
                    pronamepanel.add(inputTeacherNo);
                    pronamepanel.add(inputTNo);
                    JPanel colnopanel = new JPanel();
                    colnopanel.add(inputScore);
                    colnopanel.add(inputscore);
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(confirmButton);
                    buttonPanel.add(cancelButton);
                    inputFrame.add(pronamepanel);
                    inputFrame.add(colnopanel);
                    inputFrame.add(buttonPanel);
                    inputFrame.add(mmessage);
                    inputFrame.add(new JPanel());

                    pronamepanel.setBounds(15, 80, 420, 40);
                    colnopanel.setBounds(15, 120, 420, 40);
                    buttonPanel.setBounds(15, 160, 420, 40);
                    mmessage.setBounds(15, 200, 420, 40);
                    confirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String tno = inputTNo.getText();
                            String score = inputscore.getText();

                            SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                            boolean result = sqlServer.managerUpdateStuCouTeaSco(studentNo, courseNo, tno, score.equals("") ? -1 : Integer.parseInt(score));
                            if (result) {
                                inputFrame.dispose();
                                containerFrame.dispose();
                                ScoreMDialog scoreMDialog = new ScoreMDialog();
                            } else {
                                inputFrame.dispose();
                                setMessage("更改失败");
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


        statisticalScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SqlServer sqlServer = new SqlServer(SqlUser.newSqlUser(Main.user.getUserType()));
                String sql = "select courseNo, AVG(score) as score from SCTSTable group by(courseNo)";
                ResultSet resultSet = sqlServer.executeQuery(sql);
                JFrame graphFrame = new JFrame() {
                    @Override
                    public void paint(Graphics g) {
                        try {
                            int width = getWidth();
                            int height = getHeight() - 10;
                            int leftMargin = 20;
                            int topMargin = 50;
                            Graphics2D g2 = (Graphics2D) g;
                            int ruler = height - topMargin - 5;
                            int rulerstep = ruler / 10;
                            g2.setColor(Color.white);
                            g2.fillRect(0, 0, width, height);
                            g2.setColor(Color.lightGray);
                            for (int i = 0; i <= 10; i++) {//绘制灰色横线和百分比
                                g2.drawString(String.valueOf(100 - 10 * i), 10, topMargin + rulerstep * i);//写下百分比
                                g2.drawLine(5, topMargin + rulerstep * i, width, topMargin + rulerstep * i);//绘制灰色横线
                            }
                            g2.setColor(Color.cyan);
                            int step = 0;
                            while (resultSet.next()) {
                                String cno = resultSet.getString("courseNo");
                                int sscore = resultSet.getInt("score");
                                step += 40;
                                int score = (int) (sscore * 1.0 * (height - topMargin) / 100);
                                g2.fillRoundRect(leftMargin + step * 2, height - score, 40, score, 40, 10);
                                g2.drawString(cno + " : " + String.valueOf(sscore), leftMargin + step * 2, height - score - 5);
                            }
                        } catch (SQLException ee) {
                            ee.printStackTrace();
                            System.out.println("报表查询失败");
                        }
                    }
                };
                graphFrame.setTitle("课程平均成绩");
                graphFrame.setBounds(200, 100, 800, 600);
                graphFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                graphFrame.setVisible(true);
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
