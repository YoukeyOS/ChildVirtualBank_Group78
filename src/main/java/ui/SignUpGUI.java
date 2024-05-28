package ui;

import control.userControl;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;


public class SignUpGUI extends JFrame {

    private MainGUI mainGUI;

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public SignUpGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;

        // 设置窗口标题
        setTitle("Sign Up");

        // 设置窗口大小
        setSize(400, 300);

        // 创建提示信息标签
        JLabel promptLabel = new JLabel("Please create your new account with details");

        // 创建用户名输入框
        JTextField usernameTextField = new JTextField();

        // 创建密码输入框
        JPasswordField passwordField = new JPasswordField();

        // 创建确认密码输入框
        JPasswordField passwordAgainField = new JPasswordField();

        //创建邮件输入框
        JTextField emailTextField = new JTextField();

        // 创建“Are you kid/parent?”选择列表
        String[] options = {"Kid", "Parent"};
        JComboBox<String> kidOrParentComboBox = new JComboBox<>(options);

        String[] accountOptions = {"current", "saving"};
        JComboBox<String> accountTypeComboBox = new JComboBox<>(accountOptions);

        // 创建注册按钮
        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(e -> {
            // 注册按钮事件处理
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            String passwordAgain = new String(passwordAgainField.getPassword());
            String kidOrParent = (String) kidOrParentComboBox.getSelectedItem();
            String accountType = (String) accountTypeComboBox.getSelectedItem();
            String email = emailTextField.getText();

            if (!password.equals(passwordAgain)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!");
                return;
            }
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format!");
                return;
            }
            userControl u=new userControl();
            boolean registerFlag;
            try {
                registerFlag=u.register(username,password,kidOrParent,accountType,email);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            // 验证用户名和密码


            // 注册成功则跳转到主界面
            if (registerFlag) {
            // 关闭注册窗口
            setVisible(false);

            // 显示主界面
            mainGUI.setVisible(true);
        } else {
            // 显示错误信息
            JOptionPane.showMessageDialog(this, "Invalid username or password!");
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

    // 创建一个中间面板并添加输入框和选择列表
    JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(6, 2));
        centerPanel.add(new JLabel("Username:"));
        centerPanel.add(usernameTextField);
        centerPanel.add(new JLabel("Password:"));
        centerPanel.add(passwordField);
        centerPanel.add(new JLabel("Password again:"));
        centerPanel.add(passwordAgainField);
        centerPanel.add(new JLabel("email"));
        centerPanel.add(emailTextField);
        centerPanel.add(new JLabel("Are you kid/parent?:"));
        centerPanel.add(kidOrParentComboBox);
        centerPanel.add(new JLabel("The account is current or saving?:"));
        centerPanel.add(accountTypeComboBox);

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

