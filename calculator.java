import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class calculator implements ActionListener {

    JFrame frame;
    JTextField textField;
    JButton[] numbers = new JButton[10];
    JButton[] methods = new JButton[5];
    JButton add, sub, mul, div, equ;
    JPanel panel, panel2;

    Color methodColor = new Color(77, 148, 255);
    Color backgroundColor = new Color(245, 245, 245);

    Font textFieldFont = new Font("Monospaced", Font.BOLD, 30);
    Font buttonFont = new Font("Monospaced", Font.BOLD, 30);
    double firstNum, secondNum, result;
    char operation;

    calculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 625);
        frame.setLayout(null);

        frame.getContentPane().setBackground(backgroundColor);

        textField = new JTextField();
        textField.setBounds(65, 25, 300, 100);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);
        textField.setFont(textFieldFont);

        add = new JButton("+");
        sub = new JButton("-");
        mul = new JButton("*");
        div = new JButton("/");
        equ = new JButton("=");

        methods[0] = add;
        methods[1] = sub;
        methods[2] = mul;
        methods[3] = div;
        methods[4] = equ;

        for (int i = 0; i < 5; i++) {
            methods[i].addActionListener(this);
            methods[i].setFont(buttonFont);
            methods[i].setFocusable(false);
            methods[i].setBackground(methodColor);
        }

        equ.setBounds(65, 540, 300, 50);

        for (int i = 0; i < 10; i++) {
            numbers[i] = new JButton(String.valueOf(i));
            numbers[i].addActionListener(this);
            numbers[i].setFont(buttonFont);
            numbers[i].setFocusable(false);
            numbers[i].setBackground(Color.WHITE);
        }

        panel = new JPanel();
        panel.setBounds(65, 150, 300, 300);
        panel.setLayout(new GridLayout(3, 4, 10, 10));
        panel.setBackground(backgroundColor); // Set the same color as frame

        panel.add(numbers[1]);
        panel.add(numbers[2]);
        panel.add(numbers[3]);
        panel.add(add);
        panel.add(numbers[4]);
        panel.add(numbers[5]);
        panel.add(numbers[6]);
        panel.add(sub);
        panel.add(numbers[7]);
        panel.add(numbers[8]);
        panel.add(numbers[9]);
        panel.add(mul);

        panel2 = new JPanel();
        panel2.setBounds(65, 460, 300, 70);
        panel2.setLayout(new GridLayout(1, 4, 10, 10));
        panel2.setBackground(backgroundColor); // Set the same color as frame

        panel2.add(numbers[0]);
        panel2.add(div);

        frame.add(panel);
        frame.add(panel2);
        frame.add(textField);
        frame.add(equ);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numbers[i]) {
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }

        if (e.getSource() == add) {
            firstNum = Double.parseDouble(textField.getText());
            operation = '+';
            textField.setText("");
        }

        if (e.getSource() == sub) {
            firstNum = Double.parseDouble(textField.getText());
            operation = '-';
            textField.setText("");
        }

        if (e.getSource() == mul) {
            firstNum = Double.parseDouble(textField.getText());
            operation = '*';
            textField.setText("");
        }

        if (e.getSource() == div) {
            firstNum = Double.parseDouble(textField.getText());
            operation = '/';
            textField.setText("");
        }

        if (e.getSource() == equ) {
            secondNum = Double.parseDouble(textField.getText());

            switch (operation) {
                case '+':
                    result = firstNum + secondNum;
                    break;
                case '-':
                    result = firstNum - secondNum;
                    break;
                case '*':
                    result = firstNum * secondNum;
                    break;
                case '/':
                    result = firstNum / secondNum;
                    break;
            }

            textField.setText(String.valueOf(result));
            firstNum = result; 
        }
    }

    public static void main(String[] args) {
        calculator calculator = new calculator();
    }
}
