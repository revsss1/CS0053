import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PasswordCreation implements ActionListener {
    JFrame frame;

    JLabel newPasswordLabel = new JLabel("New Password:");
    JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
    JLabel showPasswordLabel = new JLabel("Show Password");

    JPasswordField newPasswordTF = new JPasswordField(15);
    JPasswordField confirmPasswordTF = new JPasswordField(15);

    JCheckBox showPassword = new JCheckBox();

    JButton saveButton = new JButton("Save");
    JButton resetButton = new JButton("Reset");
    JButton exitButton = new JButton("Exit");
    JButton[] buttonsArr = { saveButton, resetButton, exitButton };

    public PasswordCreation() {
        frame = new JFrame("Password Creation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);
        frame.setLayout(new GridBagLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(saveButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(exitButton);

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        // Add components to the frame
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 1;
        frame.add(newPasswordLabel, grid);
        grid.gridx = 1;
        frame.add(newPasswordTF, grid);

        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;
        frame.add(confirmPasswordLabel, grid);
        grid.gridx = 1;
        frame.add(confirmPasswordTF, grid);

        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 1;
        frame.add(showPasswordLabel, grid);
        grid.gridx = 1;
        frame.add(showPassword, grid);

        grid.gridx = 0;
        grid.gridy = 3;
        grid.gridwidth = 7;
        frame.add(buttonsPanel, grid);

        // Add action listeners for buttons
        for (JButton buttons : buttonsArr) {
            buttons.addActionListener(this);
        }

        // Add action listener for show password checkbox
        showPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPassword.isSelected()) {
                    // Show password
                    newPasswordTF.setEchoChar((char) 0);
                    confirmPasswordTF.setEchoChar((char) 0);
                } else {
                    // Hide password
                    newPasswordTF.setEchoChar('\u2022'); // Default bullet character
                    confirmPasswordTF.setEchoChar('\u2022');
                }
            }
        });

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            // Get passwords from fields as char[] for security reasons
            char[] newPasswordChars = newPasswordTF.getPassword();
            char[] confirmPasswordChars = confirmPasswordTF.getPassword();

            String newPassword = new String(newPasswordChars);
            String confirmPassword = new String(confirmPasswordChars);

            try {
                if (!newPassword.equals(confirmPassword)) {
                    throw new Exception("Passwords do not match.");
                }

                // Validate the password
                if (isValidPassword(newPassword)) {
                    // Proceed with saving the password
                    JOptionPane.showMessageDialog(frame, "Password Saved!");
                } else {
                    throw new Exception("Password is invalid.");
                }
            } catch (Exception ex) {
                // Handle password mismatch or validation failure
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == resetButton) {
            newPasswordTF.setText("");
            confirmPasswordTF.setText("");
        } else {
            frame.dispose();
        }
    }

    private boolean isValidPassword(String password) {
        try {
            if (password.length() < 8) {
                throw new AssertionError("Password must be at least 8 characters.");
            }

            boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
            boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
            boolean hasDigit = password.chars().anyMatch(Character::isDigit);
            boolean hasSpecial = password.chars().anyMatch(ch -> "!@#$%^&*()_+[]{}|;':\",.<>?/`~".indexOf(ch) >= 0);

            if (!hasLower) {
                throw new AssertionError("Password must contain at least one lowercase letter.");
            }
            if (!hasUpper) {
                throw new AssertionError("Password must contain at least one uppercase letter.");
            }
            if (!hasDigit) {
                throw new AssertionError("Password must contain at least one digit.");
            }
            if (!hasSpecial) {
                throw new AssertionError("Password must contain at least one special character.");
            }

            return true;

        } catch (AssertionError ae) {
            JOptionPane.showMessageDialog(frame, ae.getMessage(), "Invalid Password", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public static void main(String[] args) {
        new PasswordCreation();
    }
}
