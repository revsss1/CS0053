import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class calculator implements ActionListener {

    // initialized everything needed
    JFrame frame;
    JTextField textField;
    JButton[] numbers = new JButton[10];
    JButton[] methods = new JButton[6];
    JButton add, sub, mul, div, equ, del;
    JPanel panel;
    boolean isOperationClicked = false;

    Color methodColor = new Color(77, 148, 255);
    Color backgroundColor = new Color(245, 245, 245);
    Font textFieldFont = new Font("SANS_SERIF", Font.BOLD, 80);
    Font buttonFont = new Font("Monospaced", Font.BOLD, 30);
    double firstNum = 0, secondNum = 0, result = 0;
    char operation = ' ';

    calculator() {
        // create window
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 550);
        frame.setLayout(null);
        frame.getContentPane().setBackground(backgroundColor);

        // create a text field
        textField = new JTextField();
        textField.setBounds(20, 25, 300, 125);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        textField.setFont(textFieldFont);

        // create a panel
        panel = new JPanel();
        panel.setBounds(20, 175, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        panel.setBackground(backgroundColor);

        // customized the number buttons
        for (int i = 0; i < 10; i++) {
            numbers[i] = new JButton(String.valueOf(i));
            numbers[i].addActionListener(this);
            numbers[i].setFont(buttonFont);
            numbers[i].setFocusable(false);
            numbers[i].setBackground(Color.WHITE);
        }

        add = new JButton("+");
        sub = new JButton("-");
        mul = new JButton("*");
        div = new JButton("/");
        equ = new JButton("=");
        del = new JButton("C");

        methods[0] = add;
        methods[1] = sub;
        methods[2] = mul;
        methods[3] = div;
        methods[4] = equ;
        methods[5] = del;

        // customized the fuction buttons
        for (int i = 0; i < methods.length; i++) {
            methods[i].addActionListener(this);
            methods[i].setFont(buttonFont);
            methods[i].setFocusable(false);
            methods[i].setBackground(methodColor);
        }

        // add the buttons to the panel
        panel.add(numbers[1]);
        panel.add(numbers[2]);
        panel.add(numbers[3]);
        panel.add(del);
        panel.add(numbers[4]);
        panel.add(numbers[5]);
        panel.add(numbers[6]);
        panel.add(add);
        panel.add(numbers[7]);
        panel.add(numbers[8]);
        panel.add(numbers[9]);
        panel.add(sub);
        panel.add(numbers[0]);
        panel.add(equ);
        panel.add(mul);
        panel.add(div);

        // make everything visible to the frame
        frame.add(panel);
        frame.add(textField);
        frame.setVisible(true);
    }

    // do certain action when function buttons were clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numbers[i]) {
                if (isOperationClicked) {
                    textField.setText("");
                    isOperationClicked = false;
                }
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }

        if (e.getSource() == add || e.getSource() == sub || e.getSource() == mul || e.getSource() == div) {
            if (!textField.getText().equals("")) {
                secondNum = Double.parseDouble(textField.getText());

                if (operation != ' ') {
                    calculate();
                } else {
                    firstNum = secondNum;
                }

                operation = e.getActionCommand().charAt(0);
                isOperationClicked = true;
                textField.setText(String.valueOf(firstNum));

                if (result == (int) result) {
                    textField.setText(String.valueOf((int) result));
                } else {
                    textField.setText(String.valueOf(result));
                }
            }
        }

        if (e.getSource() == equ) {
            if (!textField.getText().equals("")) {
                secondNum = Double.parseDouble(textField.getText());
                calculate();
                operation = ' ';

                if (result == (int) result) {
                    textField.setText(String.valueOf((int) result));
                } else {
                    textField.setText(String.valueOf(result));
                }
            }
        }

        if (e.getSource() == del) {
            textField.setText("");
            firstNum = 0;
            secondNum = 0;
            result = 0;
            operation = ' ';
            isOperationClicked = false;
        }
    }

    private void calculate() {
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
                if (secondNum != 0) {
                    result = firstNum / secondNum;
                } else {
                    JOptionPane.showMessageDialog(null, "Cannot divide by zero");
                    result = 0;
                }
                break;
        }
        firstNum = result;
    }

    public static void main(String[] args) {
        calculator calculator = new calculator();
    }
}
