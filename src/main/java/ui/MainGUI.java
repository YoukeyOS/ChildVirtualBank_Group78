package ui;

import control.BalanceIncreaseTask;
import control.accountControl;


import javax.swing.*;
import java.awt.*;
import java.util.Timer;

public class MainGUI extends JFrame {
    private SignInGUI signInGUI;
    private SignUpGUI signUpGUI;
    private accountControl accountControl = new accountControl();

    public MainGUI() {
        // 设置窗口标题
        setTitle("Welcome to child virtual bank!");
        // 设置窗口大小
        setSize(400, 300);

        // 创建欢迎信息标签
        JLabel welcomeLabel = new JLabel("Welcome to child virtual bank!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 加载图片并添加到JLabel
        ImageIcon imageIcon = new ImageIcon("src/storage/pictures/mainGUI.jpg");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(150, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 设置图片居中

        // 创建登录按钮
        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(e -> {
            // 登录按钮事件处理
            signInGUI = new SignInGUI(this);
            setVisible(false);
        });

        // 创建注册按钮
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            // 注册按钮事件处理
            signUpGUI = new SignUpGUI(this);
            setVisible(false);
        });

        // 创建面板并添加组件
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(signInButton);
        buttonPanel.add(signUpButton);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(welcomeLabel);
        panel.add(Box.createHorizontalGlue());
        panel.add(imageLabel);
        panel.add(Box.createHorizontalGlue());
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        // 将面板添加到窗口中
        add(panel);

        java.util.List<String> usernames = null;
        usernames =accountControl.getSavingAccountUsernames();

        accountControl accountControl = new accountControl();

        for (String username : usernames) {
            java.util.Timer timer = new Timer();
            timer.schedule(new BalanceIncreaseTask(accountControl, username), 0, 1000);
        }


        // 设置窗口可见
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}

