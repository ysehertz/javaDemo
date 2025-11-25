package com.fuchen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowsCalculator extends JFrame {

    // 颜色定义（仿Windows亮色主题）
    private final Color COLOR_BG = Color.WHITE;                    // 窗体背景
    private final Color COLOR_BTN_NUM = new Color(240, 240, 240); // 数字按钮背景
    private final Color COLOR_BTN_OP = new Color(235, 235, 235);  // 运算符按钮背景
    private final Color COLOR_BTN_EQUAL = new Color(76, 194, 206); // 等号按钮颜色 (Windows经典蓝)
    private final Color COLOR_TEXT = Color.BLACK;                 // 文字颜色
    
    // UI组件
    private JLabel displayLabel;
    private JLabel historyLabel; // 显示计算过程，如 "12 + "

    // 计算逻辑变量
    private String currentOperator = "";
    private double firstOperand = 0;
    private boolean isStartNewNumber = true; // 是否开始输入新数字

    public WindowsCalculator() {
        setTitle("计算器");
        setSize(350, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_BG);

        // 1. 顶部显示区域
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(2, 1));
        displayPanel.setBackground(COLOR_BG);
        displayPanel.setBorder(new EmptyBorder(20, 20, 0, 20));

        historyLabel = new JLabel("");
        historyLabel.setForeground(Color.GRAY);
        historyLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        historyLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        displayLabel = new JLabel("0");
        displayLabel.setForeground(COLOR_TEXT);
        displayLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 48));
        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        displayPanel.add(historyLabel);
        displayPanel.add(displayLabel);
        add(displayPanel, BorderLayout.NORTH);

        // 2. 按钮区域
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(COLOR_BG);
        buttonsPanel.setLayout(new GridLayout(6, 4, 2, 2)); 
        buttonsPanel.setBorder(new EmptyBorder(10, 5, 5, 5));

        // 按钮标签数组 (按照截图顺序)
        String[] btnLabels = {
            "%", "CE", "C", "⌫",
            "¹/x", "x²", "²√x", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
        };

        // 字体
        Font fontNum = new Font("Microsoft YaHei", Font.BOLD, 20);
        Font fontOp = new Font("Microsoft YaHei", Font.PLAIN, 18);

        CalculatorAction action = new CalculatorAction();

        for (String label : btnLabels) {
            JButton btn = new JButton(label);
            btn.setFocusPainted(false);
            btn.setBorder(new LineBorder(Color.GRAY, 1)); // 添加边框增强立体感
            btn.setForeground(COLOR_TEXT);
            
            // 设置背景色和字体
            if ("0123456789".contains(label) || "+/-".equals(label) || ".".equals(label)) {
                btn.setBackground(COLOR_BTN_NUM);
                btn.setFont(fontNum);
            } else if ("=".equals(label)) {
                btn.setBackground(COLOR_BTN_EQUAL);
                btn.setForeground(Color.WHITE); // 等号文字通常是白色的
                btn.setFont(fontOp);
            } else {
                btn.setBackground(COLOR_BTN_OP);
                btn.setFont(fontOp);
            }

            // 添加事件监听
            btn.addActionListener(action);
            buttonsPanel.add(btn);
        }

        add(buttonsPanel, BorderLayout.CENTER);
    }

    // 内部类：处理按钮点击事件
    private class CalculatorAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            // 1. 数字输入处理
            if ("0123456789".contains(command)) {
                if (isStartNewNumber) {
                    displayLabel.setText(command);
                    isStartNewNumber = false;
                } else {
                    String currentText = displayLabel.getText();
                    if (currentText.equals("0")) {
                        displayLabel.setText(command);
                    } else {
                        displayLabel.setText(currentText + command);
                    }
                }
                // 调整字体大小以适应长度（简单模拟）
                if (displayLabel.getText().length() > 10) {
                    displayLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 32));
                }
            }
            // 2. 小数点处理
            else if (".".equals(command)) {
                if (isStartNewNumber) {
                    displayLabel.setText("0.");
                    isStartNewNumber = false;
                } else if (!displayLabel.getText().contains(".")) {
                    displayLabel.setText(displayLabel.getText() + ".");
                }
            }
            // 3. 基础运算 (+, -, ×, ÷, %)
            else if ("+-×÷%".contains(command)) {
                if (!currentOperator.isEmpty() && !isStartNewNumber) {
                    calculate();
                }
                
                firstOperand = Double.parseDouble(displayLabel.getText());
                currentOperator = command;
                isStartNewNumber = true;
                
                historyLabel.setText(formatResult(firstOperand) + " " + currentOperator);
            }
            // 4. 等号处理
            else if ("=".equals(command)) {
                if (!currentOperator.isEmpty()) {
                    calculate();
                    currentOperator = "";
                    historyLabel.setText("");
                    isStartNewNumber = true;
                }
            }
            // 5. 功能键处理
            else if ("C".equals(command)) {
                displayLabel.setText("0");
                historyLabel.setText("");
                firstOperand = 0;
                currentOperator = "";
                isStartNewNumber = true;
                resetFont();
            }
            else if ("CE".equals(command)) {
                displayLabel.setText("0");
                isStartNewNumber = true;
            }
            else if ("⌫".equals(command)) { // 退格
                String text = displayLabel.getText();
                if (text.length() > 1) {
                    displayLabel.setText(text.substring(0, text.length() - 1));
                } else {
                    displayLabel.setText("0");
                    isStartNewNumber = true;
                }
            }
            else if ("+/-".equals(command)) {
                double val = Double.parseDouble(displayLabel.getText());
                val = val * -1;
                displayLabel.setText(formatResult(val));
                isStartNewNumber = true;
            }
            // 6. 扩展功能
            else if ("¹/x".equals(command)) {
                double val = Double.parseDouble(displayLabel.getText());
                displayLabel.setText(formatResult(1 / val));
                isStartNewNumber = true;
            }
            else if ("x²".equals(command)) {
                double val = Double.parseDouble(displayLabel.getText());
                displayLabel.setText(formatResult(val * val));
                isStartNewNumber = true;
            }
            else if ("²√x".equals(command)) {
                double val = Double.parseDouble(displayLabel.getText());
                displayLabel.setText(formatResult(Math.sqrt(val)));
                isStartNewNumber = true;
            }
        }
    }

    // 执行计算逻辑
    private void calculate() {
        double secondOperand = Double.parseDouble(displayLabel.getText());
        double result = 0;

        switch (currentOperator) {
            case "+": result = firstOperand + secondOperand; break;
            case "-": result = firstOperand - secondOperand; break;
            case "×": result = firstOperand * secondOperand; break;
            case "÷": 
                if (secondOperand == 0) {
                    displayLabel.setText("除数不能为0");
                    isStartNewNumber = true;
                    return;
                }
                result = firstOperand / secondOperand; 
                break;
            case "%": result = firstOperand % secondOperand; break;
        }
        
        displayLabel.setText(formatResult(result));
        firstOperand = result;
    }

    // 格式化结果：如果是整数去掉小数点，否则保留
    private String formatResult(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.valueOf(d);
    }

    private void resetFont() {
        displayLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 48));
    }

    public static void main(String[] args) {
        // 尝试使用系统UI风格（在Windows上会更好看）
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new WindowsCalculator().setVisible(true);
        });
    }
}