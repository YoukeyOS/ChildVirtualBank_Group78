package ui;

import control.taskControl;
import control.transactionControl;
import control.accountControl;
import model.Task;
import model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public class ViewTaskGUI extends JFrame {
    private static final String TASK_DIR = "src/storage/Task";

    private ChildHomepageGUI childHomepageGUI;

    private String username;
    private JTable table;

    public ViewTaskGUI(ChildHomepageGUI childHomepageGUI, String username) throws IOException {
        super("Task Status");

        this.childHomepageGUI = childHomepageGUI;
        this.username = username;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Your task status", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        List<Task> tasks = taskControl.getTasksByUsername(username);
        String[] columnNames = {"TaskID", "TaskDescription", "TaskStatus", "RewardAmount", ""};

        //创建动态表
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (Task task : tasks) {
            Object[] row = new Object[5];
            row[0] = task.getTaskID();
            row[1] = task.getTaskDescription();
            row[2] = task.getTaskStatus();
            row[3] = task.getRewardAmount();
            row[4] = "Finish";
            tableModel.addRow(row);
        }

        //创建表格
        table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only the "Finish" column is editable
            }
        };
        table.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 4) {
                int row = e.getFirstRow();
                String taskID = (String) table.getModel().getValueAt(row, 0);
                String taskDescription = (String) table.getModel().getValueAt(row, 1);
                String taskStatus = (String) table.getModel().getValueAt(row, 2);
                double rewardAmount = (Double) table.getModel().getValueAt(row, 3);

                Task task = new Task();
                task.setTaskID(taskID);
                task.setTaskDescription(taskDescription);
                task.setTaskStatus(taskStatus);
                task.setRewardAmount(rewardAmount);

                try {
                    taskControl.finishTask(task, username);
                    accountControl.reward(username, rewardAmount);

                    refresh();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        table.getColumn("").setCellRenderer(new ButtonRenderer());
        table.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox(), username, this));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            setVisible(false);
            this.childHomepageGUI.updateBalance(username);
            this.childHomepageGUI.setVisible(true);
        });
        add(goBackButton, BorderLayout.SOUTH);

        pack();
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Button renderer and editor
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private String username;
        private ViewTaskGUI viewTaskGUI;

        public ButtonEditor(JCheckBox checkBox, String username, ViewTaskGUI viewTaskGUI) {
            super(checkBox);
            this.username = username;
            this.viewTaskGUI = viewTaskGUI;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    isPushed = true;
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Finish the task
                try {
                    int selectedRow = table.getSelectedRow();
                    String taskID = (String) table.getModel().getValueAt(selectedRow, 0);
                    String taskDescription = (String) table.getModel().getValueAt(selectedRow, 1);
                    String taskStatus = (String) table.getModel().getValueAt(selectedRow, 2);
                    double rewardAmount = (Double) table.getModel().getValueAt(selectedRow, 3);

                    Task task = new Task();
                    task.setTaskID(taskID);
                    task.setTaskDescription(taskDescription);
                    task.setTaskStatus(taskStatus);
                    task.setRewardAmount(rewardAmount);

                    taskControl.finishTask(task, username);
                    viewTaskGUI.refresh();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    public void refresh() throws IOException {
        List<Task> tasks = taskControl.getTasksByUsername(username);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        for (Task task : tasks) {
            Object[] row = new Object[5];
            row[0] = task.getTaskID();
            row[1] = task.getTaskDescription();
            row[2] = task.getTaskStatus();
            row[3] = task.getRewardAmount();
            row[4] = "Finish";
            tableModel.addRow(row);
        }
    }
}