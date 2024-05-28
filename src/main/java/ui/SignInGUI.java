package ui;

import control.userControl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SignInGUI extends JFrame {

    private MainGUI mainGUI;
    private ChildHomepageGUI ChildHomepageGUI;
    private ParentHomepageGUI ParentHomepageGUI;
    private  RetrieveGUI retrieveGUI;

    public SignInGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;

        // 设置窗口标题
        setTitle("Sign In");

        // 设置窗口大小
        setSize(400, 300);

        // 创建面板并设置布局管理器
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 创建提示信息标签
        JLabel promptLabel = new JLabel("Please enter your account details");
        promptLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // 创建用户名输入框和提示信息
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(100, 10));
        usernameTextField.setMaximumSize(usernameTextField.getPreferredSize());

        // 创建密码输入框和提示信息
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(100, 10));
        passwordField.setMaximumSize(passwordField.getPreferredSize());

        // 创建按钮
        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(e -> {
            //this.setVisible(false);
            // 登录按钮事件处理
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            // 验证用户名和密码
            userControl u = new userControl();
            boolean loginFlag;
            try {
                loginFlag = u.login(username, password);
                System.out.println("Login flag: " + loginFlag); // 打印登录标志
            } catch (IOException ex) {
                ex.printStackTrace(); // 打印异常堆栈
                throw new RuntimeException(ex);
            }
            // 登录成功则跳转到主界面
            if (loginFlag) {
                String userType;
                try {
                    userType = userControl.getUserTypeByUserName(username);
                    System.out.println("User type: " + userType); // 打印用户类型
                } catch (IOException ex) {
                    ex.printStackTrace(); // 打印异常堆栈
                    throw new RuntimeException(ex);
                }
                setVisible(false);
                mainGUI.setVisible(false);


                if (userType != null && userType.equals("Kid")) {
                    ChildHomepageGUI = new ChildHomepageGUI(username,this.mainGUI);// 显示Kid主界面
                }
                else if (userType != null && userType.equals("Parent")) {
                    ParentHomepageGUI = new ParentHomepageGUI(username,this.mainGUI);// 显示Parent主界面
                }

            } else {
                // 显示错误信息
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        });

        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            // 返回按钮事件处理
            setVisible(false);
            mainGUI.setVisible(true);
        });
        JButton findButton = new JButton("Retrieve Password");
        findButton.addActionListener(e -> {
            // 密码找回按钮事件处理
            setVisible(false);
            retrieveGUI = new RetrieveGUI(this.mainGUI);
        });

        // 添加组件到面板
        panel.add(promptLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 10, 10));
        centerPanel.add(usernameLabel);
        centerPanel.add(usernameTextField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);
        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.add(checkButton);
        southPanel.add(goBackButton);
        southPanel.add(findButton);
        panel.add(southPanel, BorderLayout.SOUTH);

        // 将面板添加到窗口中
        add(panel);

        // 设置窗口可见
        setVisible(true);
    }

}


