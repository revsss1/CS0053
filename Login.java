import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Login implements ActionListener {
    JFrame frame;

    JLabel usernameLabel = new JLabel("User Name: ");
    JLabel passwordLabel = new JLabel("password: ");
    JLabel stayLoginLabel = new JLabel("Stay Login");
    JLabel loginTitle = new JLabel("LOGIN", JLabel.CENTER);

    JTextField usernameTF = new JTextField(15);
    JPasswordField passwordTF = new JPasswordField(15);
    JTextField[] textFields = { usernameTF, passwordTF };

    JButton loginButton = new JButton("Login");
    JButton clearButton = new JButton("Clear");
    JButton exitButton = new JButton("Exit");
    JButton[] buttons = { loginButton, clearButton, exitButton };

    JCheckBox stayLogin = new JCheckBox();

    public Login() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setResizable(false);
        ;
        frame.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 2;
        frame.add(loginTitle, grid);

        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;
        frame.add(usernameLabel, grid);

        grid.gridx = 1;
        frame.add(usernameTF, grid);

        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 2;
        frame.add(loginTitle, grid);

        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 1;
        frame.add(passwordLabel, grid);

        grid.gridx = 1;
        frame.add(passwordTF, grid);

        JPanel stayLogiPanel = new JPanel();
        stayLogiPanel.add(stayLogin);
        stayLogiPanel.add(stayLoginLabel);
        grid.gridx = 0;
        grid.gridy = 3;
        grid.gridwidth = 1;
        frame.add(stayLogiPanel, grid);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        grid.gridx = 0;
        grid.gridy = 4;
        grid.gridwidth = 7;
        frame.add(buttonPanel, grid);

        loginButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {

            String username = textFields[0].getText().trim();
            String password = textFields[1].getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter username/password");
            } else {
                if (!isValidUserName(username)) {
                    JOptionPane.showMessageDialog(frame, "Invalid Username");
                } else if (!isValidPassword(password)) {
                    JOptionPane.showMessageDialog(frame, "Invalid Password");
                } else {
                    new HomePage(username);
                    frame.dispose();
                }
            }
        } else if (e.getSource() == clearButton) {
            usernameTF.setText("");
            passwordTF.setText("");
        } else if (e.getSource() == exitButton) {
            frame.dispose();
        }
    }

    public static boolean isValidUserName(String username) {
        boolean hasUpperCase = false;
        boolean hasTransitioned = false;

        for (int i = 0; i < username.length(); i++) {
            char currentChar = username.charAt(i);

            if (Character.isUpperCase(currentChar)) {
                hasUpperCase = true;
                if (hasTransitioned) {
                    return false;
                }
            } else if (Character.isLowerCase(currentChar)) {
                hasTransitioned = true;
            }
        }

        return hasUpperCase;
    }

    public static boolean isValidPassword(String password) {
        int n = password.length();
        boolean hasLower = false, hasUpper = false,
                hasDigit = false, specialChar = false;
        Set<Character> set = new HashSet<Character>(
                Arrays.asList('!', '@', '#', '$', '%', '^', '&',
                        '*', '(', ')', '-', '+'));

        for (char i : password.toCharArray()) {
            if (Character.isLowerCase(i))
                hasLower = true;
            if (Character.isUpperCase(i))
                hasUpper = true;
            if (Character.isDigit(i))
                hasDigit = true;
            if (set.contains(i))
                specialChar = true;
        }

        if (hasDigit && hasLower && hasUpper && specialChar && (n >= 8)) {
            return true;
        } else
            return false;
    }

    public static void main(String[] args) {
        new Login();
    }
}

class HomePage implements ActionListener {
    JFrame homeFrame = new JFrame("Home Page");

    HomePage(String Username) {

        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setSize(350, 250);
        homeFrame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcome = new JLabel("Welcome " + Username + "!", JLabel.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        welcome.setForeground(Color.BLACK);
        panel.add(welcome, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(this);
        panel.add(logoutButton, BorderLayout.SOUTH);

        homeFrame.add(panel);

        homeFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JButton) {
            JButton source = (JButton) e.getSource();
            if (source.getText().equals("Logout")) {
                new Login();
                homeFrame.dispose();
            }
        }
    }
}
