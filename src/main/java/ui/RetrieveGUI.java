package ui;

import control.userControl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class RetrieveGUI extends JFrame {
    private static final String TASK_DIR = "src/storage/Task";
    private MainGUI mainGUI;
    public RetrieveGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setTitle("Retrieve password");
        setSize(400, 300);
        JLabel promptLabel = new JLabel("Please change your account with new password");
        JTextField usernameTextField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField newPasswordAgainField = new JPasswordField();

        //修改确认按钮
        JButton checkButton = new JButton("Change");
        checkButton.addActionListener(e -> {
            // 注册按钮事件处理
            String username = usernameTextField.getText();
            String password = new String(newPasswordField.getPassword());
            String passwordAgain = new String(newPasswordAgainField.getPassword());
            String email = emailField.getText();

            if (!password.equals(passwordAgain)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!");
                return;
            }
            userControl u = new userControl();
            boolean exchangeFlag;
            try {
                exchangeFlag = u.exchange(username, password, email);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            // 找回并修改密码


            // 修改成功则跳转到主界面
            if (exchangeFlag) {
                // 关闭找回窗口
                JOptionPane.showMessageDialog(this, "Successfully change your password");
                setVisible(false);

                // 显示主界面
                mainGUI.setVisible(true);
            } else {
                // 显示错误信息
                JOptionPane.showMessageDialog(this, "Fail");
            }
        });
        // 创建返回按钮
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            // 返回按钮事件处理
            setVisible(false);
            mainGUI.setVisible(true);
        });

        // 创建面板并添加组件
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 添加提示信息标签到顶部
        panel.add(promptLabel, BorderLayout.NORTH);

        // 创建一个中间面板并添加输入框
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2));
        centerPanel.add(new JLabel("Username:"));
        centerPanel.add(usernameTextField);
        centerPanel.add(new JLabel("email"));
        centerPanel.add(emailField);
        centerPanel.add(new JLabel("New Password:"));
        centerPanel.add(newPasswordField);
        centerPanel.add(new JLabel("New Password again:"));
        centerPanel.add(newPasswordAgainField);
        panel.add(centerPanel, BorderLayout.CENTER);
        // 添加按钮到底部
        JPanel southPanel = new JPanel();
        southPanel.add(checkButton);
        southPanel.add(goBackButton);
        panel.add(southPanel, BorderLayout.SOUTH);


        // 将面板添加到窗口中
        add(panel);

        // 设置窗口可见
        setVisible(true);


    }
}