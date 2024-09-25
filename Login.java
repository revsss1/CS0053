import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Login implements ActionListener {
    JFrame frame;
    
    JLabel usernameLabel = new JLabel("User Name: ");
    JLabel passwordLabel = new JLabel("password: ");
    JLabel loginTitle = new JLabel("LOGIN", JLabel.CENTER);
    
    JTextField usernameTF = new JTextField(15);
    JTextField passwordTF = new JTextField(15);

    JButton loginButton = new JButton("Login");
    JButton clearButton = new JButton("Clear");
    JButton exitButton = new JButton("Exit");

    public Login() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
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
        
        grid.gridx = 0;
        grid.gridy = 3;
        grid.gridwidth = 7;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, grid);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            
        }
         else if (e.getSource() == clearButton) {
            usernameTF.setText("");
            passwordTF.setText("");
        } else if (e.getSource() == exitButton) {
            frame.dispose();
        }
    }
    public static void main(String[] args) {
        new Login();
    }
}
