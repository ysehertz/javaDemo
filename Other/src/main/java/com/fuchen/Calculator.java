package com.fuchen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 一个模拟基本Windows计算器功能的Java Swing应用。
 * 只实现基本的加、减、乘、除运算。
 *
 * @author Gemini
 */
public class Calculator extends JFrame implements ActionListener {

    // 显示屏
    private JTextField displayField;

    // 按钮
    private JButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private JButton btnAdd, btnSubtract, btnMultiply, btnDivide;
    private JButton btnEquals, btnClear, btnClearEntry, btnBackspace, btnDot, btnPlusMinus;

    // 运算逻辑需要的变量
    private double num1 = 0;
    private double num2 = 0;
    private double result = 0;
    private String operator = "";
    private boolean isOperatorClicked = false; // 标记是否刚按下了操作符

    // 颜色定义 (模仿深色主题)
    private Color bgColor = new Color(32, 32, 32);
    private Color displayBgColor = new Color(45, 45, 45);
    private Color numBtnBgColor = new Color(50, 50, 50);
    private Color opBtnBgColor = new Color(60, 60, 60);
    private Color equalsBtnBgColor = new Color(75, 137, 218); // 蓝色
    private Color fgColor = Color.WHITE;
    private Font displayFont = new Font("Segoe UI", Font.BOLD, 48); // 较大的显示字体
    private Font buttonFont = new Font("Segoe UI", Font.BOLD, 18); // 按钮字体

    public Calculator() {
        // --- 1. 设置窗口 ---
        setTitle("计算器");
        setSize(380, 580); // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false); // 禁止调整大小
        getContentPane().setBackground(bgColor);
        setLocationRelativeTo(null); // 居中显示

        // --- 2. 初始化显示屏 ---
        displayField = new JTextField("0");
        displayField.setEditable(false); // 不可编辑
        displayField.setBackground(bgColor); // 背景色
        displayField.setForeground(fgColor); // 前景色
        displayField.setHorizontalAlignment(JTextField.RIGHT); // 右对齐
        displayField.setFont(displayFont);
        displayField.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // 边距
        add(displayField, BorderLayout.NORTH);

        // --- 3. 初始化按钮面板 ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 2, 2)); // 6行4列, 间距为2
        buttonPanel.setBackground(bgColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        // --- 4. 创建按钮 ---
        // 按钮顺序按照Windows计算器布局
        // 第1行: %, CE, C, Backspace (我们简化, CE, C, Backspace, /)
        btnClearEntry = createButton("CE", opBtnBgColor);
        btnClear = createButton("C", opBtnBgColor);
        btnBackspace = createButton("\u232b", opBtnBgColor); // Backspace 符号
        btnDivide = createButton("/", opBtnBgColor);

        // 第2行: 1/x, x^2, sqrt, / (我们简化, 7, 8, 9, *)
        btn7 = createButton("7", numBtnBgColor);
        btn8 = createButton("8", numBtnBgColor);
        btn9 = createButton("9", numBtnBgColor);
        btnMultiply = createButton("*", opBtnBgColor);

        // 第3行: 7, 8, 9, * (我们简化, 4, 5, 6, -)
        btn4 = createButton("4", numBtnBgColor);
        btn5 = createButton("5", numBtnBgColor);
        btn6 = createButton("6", numBtnBgColor);
        btnSubtract = createButton("-", opBtnBgColor);

        // 第4行: 4, 5, 6, - (我们简化, 1, 2, 3, +)
        btn1 = createButton("1", numBtnBgColor);
        btn2 = createButton("2", numBtnBgColor);
        btn3 = createButton("3", numBtnBgColor);
        btnAdd = createButton("+", opBtnBgColor);

        // 第5行: 1, 2, 3, + (我们简化, +/-, 0, ., =)
        btnPlusMinus = createButton("+/-", numBtnBgColor);
        btn0 = createButton("0", numBtnBgColor);
        btnDot = createButton(".", numBtnBgColor);
        btnEquals = createButton("=", equalsBtnBgColor);

        // --- 5. 添加按钮到面板 (按顺序) ---
        // (为了匹配布局，我调整了简化版的按钮顺序)
        // 简化版布局 (5x4)
        // Row 1: CE, C, Backspace, /
        buttonPanel.add(btnClearEntry);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnBackspace);
        buttonPanel.add(btnDivide);

        // Row 2: 7, 8, 9, *
        buttonPanel.add(btn7);
        buttonPanel.add(btn8);
        buttonPanel.add(btn9);
        buttonPanel.add(btnMultiply);

        // Row 3: 4, 5, 6, -
        buttonPanel.add(btn4);
        buttonPanel.add(btn5);
        buttonPanel.add(btn6);
        buttonPanel.add(btnSubtract);

        // Row 4: 1, 2, 3, +
        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        buttonPanel.add(btn3);
        buttonPanel.add(btnAdd);

        // Row 5: +/-, 0, ., =
        buttonPanel.add(btnPlusMinus);
        buttonPanel.add(btn0);
        buttonPanel.add(btnDot);
        buttonPanel.add(btnEquals);

        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * 辅助方法：创建和设置按钮样式
     */
    private JButton createButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(background);
        button.setForeground(fgColor);
        button.setFocusPainted(false); // 去掉焦点边框
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 按钮内边距
        button.setOpaque(true);
        button.addActionListener(this); // 添加监听器
        return button;
    }

    /**
     * 处理按钮点击事件
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String currentText = displayField.getText();

        try {
            switch (command) {
                // --- 数字按钮 0-9 ---
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    if (currentText.equals("0") || isOperatorClicked) {
                        displayField.setText(command);
                        isOperatorClicked = false;
                    } else {
                        displayField.setText(currentText + command);
                    }
                    break;

                // --- 小数点 ---
                case ".":
                    if (isOperatorClicked) { // 如果刚按了操作符
                        displayField.setText("0.");
                        isOperatorClicked = false;
                    } else if (!currentText.contains(".")) { // 防止多个小数点
                        displayField.setText(currentText + ".");
                    }
                    break;

                // --- 操作符 +, -, *, / ---
                case "+":
                case "-":
                case "*":
                case "/":
                    // 如果不是连续按操作符，先计算之前的结果
                    if (!isOperatorClicked && !operator.isEmpty()) {
                        calculate();
                    }
                    num1 = Double.parseDouble(displayField.getText());
                    operator = command;
                    isOperatorClicked = true;
                    break;

                // --- 等于 ---
                case "=":
                    if (!operator.isEmpty()) {
                        calculate();
                        operator = ""; // 重置操作符
                    }
                    isOperatorClicked = true; // 准备下一次新输入
                    break;

                // --- 清除功能 ---
                case "C": // Clear All
                    num1 = 0;
                    num2 = 0;
                    result = 0;
                    operator = "";
                    displayField.setText("0");
                    break;

                case "CE": // Clear Entry
                    displayField.setText("0");
                    break;

                case "\u232b": // Backspace
                    if (currentText.length() > 1) {
                        displayField.setText(currentText.substring(0, currentText.length() - 1));
                    } else {
                        displayField.setText("0");
                    }
                    break;

                // --- +/- (正负号) ---
                case "+/-":
                    double val = Double.parseDouble(currentText);
                    val = val * -1;
                    displayField.setText(formatResult(val));
                    break;
            }
        } catch (Exception ex) {
            displayField.setText("Error"); // 捕获所有异常，如除零
            num1 = 0;
            num2 = 0;
            operator = "";
        }
    }

    /**
     * 执行计算
     */
    private void calculate() {
        num2 = Double.parseDouble(displayField.getText());
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    displayField.setText("Error: Div by zero");
                    // 重置状态
                    num1 = 0;
                    num2 = 0;
                    operator = "";
                    return;
                }
                result = num1 / num2;
                break;
        }
        displayField.setText(formatResult(result));
        num1 = result; // 允许连续计算
    }

    /**
     * 格式化结果，如果是整数则不显示小数点
     */
    private String formatResult(double val) {
        if (val == (long) val) {
            return String.format("%d", (long) val);
        } else {
            return String.format("%s", val);
        }
    }

    /**
     * Main 方法
     */
    public static void main(String[] args) {
        // 使用 Event Dispatch Thread (EDT) 启动 Swing 应用
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }
}