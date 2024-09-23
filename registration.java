import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The registration class creates a GUI for a user registration form.
 * It includes text fields, combo boxes, radio buttons, and buttons to capture and display user input.
 * The form captures the user's name, course, year level, gender, email, and contact number.
 */

public class registration implements ActionListener {
    JFrame frame;

    // Create Labels
    JLabel nameLabel = new JLabel("Name: ");
    JLabel courseLabel = new JLabel("Course: ");
    JLabel yearLabel = new JLabel("Year Level: ");
    JLabel genderLabel = new JLabel("Gender: ");
    JLabel addressLabel = new JLabel("Address: ");
    JLabel emailLabel = new JLabel("Email: ");
    JLabel contactNumLabel = new JLabel("Contact Number: ");
    JLabel title = new JLabel("Registration Form", JLabel.CENTER);

    // Create Buttons
    JButton registerButton = new JButton("Register");
    JButton clearButton = new JButton("Clear");
    JButton exitButton = new JButton("Exit");

    // Create text fields and put it in an array for easy traversal
    JTextField nameTF = new JTextField(15);
    JTextField emailTF = new JTextField(15);
    JTextField contactNumberTF = new JTextField(15);
    JTextField[] textFields = { nameTF, emailTF, contactNumberTF };

    // Create combo boxes and put it in an array for easy traversal
    JComboBox<String> courseComboBox = new JComboBox<>(new String[] { "BSCSSE", "BSCSDS", "BSCEAI" });
    JComboBox<Integer> yearComboBox = new JComboBox<>(new Integer[] { 1, 2, 3, 4 });
    @SuppressWarnings("rawtypes")
    JComboBox[] comboBoxs = { courseComboBox, yearComboBox };

    // create radio buttons and group it
    JRadioButton maleButton = new JRadioButton("Male");
    JRadioButton femaleButton = new JRadioButton("Female");
    ButtonGroup genderGroup = new ButtonGroup();

    registration() {
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        // initialized frame
        frame = new JFrame("Registration Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        // application layout
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 2;
        frame.add(title, grid);

        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;
        frame.add(nameLabel, grid);

        grid.gridx = 1;
        frame.add(nameTF, grid);

        grid.gridx = 0;
        grid.gridy = 2;
        frame.add(courseLabel, grid);

        grid.gridx = 1;
        frame.add(courseComboBox, grid);

        grid.gridx = 0;
        grid.gridy = 3;
        frame.add(yearLabel, grid);

        grid.gridx = 1;
        frame.add(yearComboBox, grid);

        grid.gridx = 0;
        grid.gridy = 4;
        frame.add(genderLabel, grid);

        grid.gridx = 1;
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        frame.add(genderPanel, grid);

        grid.gridx = 0;
        grid.gridy = 5;
        frame.add(emailLabel, grid);

        grid.gridx = 1;
        frame.add(emailTF, grid);

        grid.gridx = 0;
        grid.gridy = 6;
        frame.add(contactNumLabel, grid);

        grid.gridx = 1;
        frame.add(contactNumberTF, grid);

        grid.gridx = 0;
        grid.gridy = 7;
        grid.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, grid);

        registerButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        frame.setVisible(true);
    }

        /**
     * Method to handle button clicks for register, clear, and exit buttons.
     * @param e ActionEvent representing the button click event.
     */
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            // check information is complete
            if (isFieldEmpty(textFields) || isGenderSelected(genderGroup)) {
                // display an error message if infomation is incomplete
                JOptionPane.showMessageDialog(frame, "Please fill out all required fields!");
            } else {
                // if complete, retrieve all information and diplay a preview
                String gender = " ";
                String name = nameTF.getText();
                String course = (String) courseComboBox.getSelectedItem();
                int yearlevel = (int) yearComboBox.getSelectedItem();
                if (maleButton.isSelected()) {
                    gender = "Male";
                } else if (femaleButton.isSelected()) {
                    gender = "Female";
                }
                String email = emailTF.getText();
                String contactNum = contactNumberTF.getText();
                JOptionPane.showMessageDialog(frame,
                        "Name: " + name + "\nCourse: " + course + "\nYear Level: " + yearlevel +
                                "\nGender: " + gender + "\nEmail: " + email + "\nContact Number: " + contactNum);
            }
        } else if (e.getSource() == clearButton) {
            nameTF.setText("");
            emailTF.setText("");
            contactNumberTF.setText("");
            genderGroup.clearSelection();
            courseComboBox.setSelectedIndex(0);
            yearComboBox.setSelectedIndex(0);
        } else if (e.getSource() == exitButton) {
            frame.dispose();
        }
    }

    // function to check if text fields are empty
    public boolean isFieldEmpty(JTextField[] textFields) {
        for (int i = 0; i < textFields.length; i++) {
            if (textFields[i].getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // check if the user choose a gender
    public boolean isGenderSelected(ButtonGroup genderGroup) {
        return genderGroup.getSelection() == null;
    }

    public static void main(String[] args) {
        new registration();
    }
}
