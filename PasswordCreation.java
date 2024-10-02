import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PasswordCreation implements ActionListener{
    JFrame frame;

    JLabel userNameLabel = new JLabel("Username:");
    JLabel passwordLabel = new JLabel("Password:");

    JTextField userNameTF = new JTextField(15);
    JTextField passwordTF = new JTextField(15);

    JButton saveButton = new JButton("Save");
    JButton resetButton = new JButton("Reset");
    JButton exitButton = new JButton("Exit");
    JButton[] buttonsArr = {saveButton, resetButton, exitButton};

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

        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 1;
        frame.add(userNameLabel,grid);
        grid.gridx = 1;
        frame.add(userNameTF,grid);

        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;
        frame.add(passwordLabel,grid);
        grid.gridx = 1;
        frame.add(passwordTF,grid);

        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 7;
        frame.add(buttonsPanel,grid);
        
        for(JButton buttons : buttonsArr){
            buttons.addActionListener(this);
        }

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            
        } else if (e.getSource() == resetButton) {
            userNameTF.setText("");
            passwordTF.setText("");
        } else {
            frame.dispose();
        }
    }

    private boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    private boolean isValidPassword(String password) {
        assert password.length() >= 8 : "Password must be at least 8 characters.";
        
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> "!@#$%^&*()_+[]{}|;':\",.<>?/`~".indexOf(ch) >= 0);
        
        assert hasLower : "Password must contain at least one lowercase letter.";
        assert hasUpper : "Password must contain at least one uppercase letter.";
        assert hasDigit : "Password must contain at least one digit.";
        assert hasSpecial : "Password must contain at least one special character.";
        
        return hasLower && hasUpper && hasDigit && hasSpecial;
    }
    
    
    public static void main(String[] args) {
        new PasswordCreation();
    }
}
