import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.ArrayList;

public class AccountManagement extends JFrame implements ActionListener {
    private ComboBox<String> courseCB, levelCB;
    private JButton[] funcBtn = new Button[2];
    private JButton[] arrowBtn = new Button[2];
    private JTextField[] textFields = new TextField[7];
    private List<Student> students;
    private int currentIndex = 0; 

    public AccountManagement() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Account Management");
        setSize(500, 750);
        setResizable(false);
        setLayout(null);

        JPanel panel = new Panel();
        panel.setLayout(null);
        panel.setBounds(0, 0, getWidth(), getHeight());

        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font h1Font = new Font("Segoe UI", Font.BOLD, 30);

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("tamaraw.png");
        imageLabel.setIcon(imageIcon);
        imageLabel.setBounds(175, 40, getWidth(), 130);
        panel.add(imageLabel);

        JLabel titleLabel = new JLabel("Account Management", SwingConstants.CENTER);
        titleLabel.setBounds(0, 190, getWidth(), 40);
        titleLabel.setForeground(new Color(0xF5F7F8));
        titleLabel.setFont(h1Font);
        panel.add(titleLabel);

        String[] labels = {"Student ID:", "Name:", "Course:", "Level:", "Address:", "Email:", "Contact Number:"};
        int yPosition = 250;

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setBounds(30, yPosition, 200, 30);
            label.setFont(defaultFont);
            panel.add(label);

            if (labels[i].equals("Course:")) {
                String[] courses = {"BSCS-SE", "BSCS-DS", "BSCS-AI", "BSIT-C", "BSIT-WMA", "BSIT-AG", "BSIT-BA", "BMMA", "BSCE", "BSCPE", "BSEE", "BSEPE", "BSME"};
                courseCB = new ComboBox<>(courses);
                courseCB.setBounds(120, yPosition, 335, 30);
                courseCB.setFont(defaultFont);
                panel.add(courseCB);
            } else if (labels[i].equals("Level:")) {
                String[] levels = {"1", "2", "3", "4", "5"};
                levelCB = new ComboBox<>(levels);
                levelCB.setBounds(120, yPosition, 125, 30);
                levelCB.setFont(defaultFont);
                panel.add(levelCB);
            } else {
                textFields[i] = new TextField();
                textFields[i].setBounds(120, yPosition, 335, 30);
                textFields[i].setFont(defaultFont);
                if (labels[i].equals("Contact Number:")) {
                    textFields[i].setBounds(170, yPosition, 285, 30);
                }
                panel.add(textFields[i]);
            }
            yPosition += 40;
        }

        String[] funcBtnLabels = {"UPDATE", "EXIT"};
        String[] arrowBtnLabels = {"<<", ">>"};
        int xPosition = 70;

        for (int i = 0; i < arrowBtnLabels.length; i++) {
            arrowBtn[i] = new Button(arrowBtnLabels[i]);
            arrowBtn[i].setBounds(xPosition, yPosition + 40, 160, 35);
            arrowBtn[i].setFont(defaultFont);
            arrowBtn[i].setForeground(new Color(0xF5F7F8));
            Color defaultArrowColor = new Color(0X16423C);
            arrowBtn[i].setBackground(defaultArrowColor);
            arrowBtn[i].addActionListener(this);
            panel.add(arrowBtn[i]);
            xPosition += 180;
        }

        xPosition = 140;
        yPosition += 50;

        for (int i = 0; i < funcBtnLabels.length; i++) {
            funcBtn[i] = new Button(funcBtnLabels[i]);
            funcBtn[i].setBounds(xPosition, yPosition + 40, 90, 35);
            funcBtn[i].setFont(defaultFont);
            Color defaultFuncColor = new Color(0xC4DAD2);
            funcBtn[i].setBackground(defaultFuncColor);
            funcBtn[i].addActionListener(this);
            panel.add(funcBtn[i]);
            xPosition += 110;
        }

        students = new ArrayList<>();
        students.add(new Student("202210759", "Karl Alexis Revilla", "BSCS-SE", "3", "Quezon City", "karlrevilla@gmail.com", "1234567890"));
        students.add(new Student("202211324", "Alessandra Gayle Cilot", "BSCS-SE", "3", "Pasig City", "sandracilot@gmail.com", "0987654321"));
        students.add(new Student("202210907", "Mica Tambalong", "BSCS-SE", "3", "Malabon City", "micatambalong@gmail.com", "1122334455"));

        displayStudentData(currentIndex);  

        add(panel);
        setVisible(true);
    }

    private void showMessage(String message, String title, int messageType) {
        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        label.setFont(defaultFont);
        panel.add(label);

        Button btn;

        if (messageType == JOptionPane.INFORMATION_MESSAGE) {
            btn = new Button("EXIT");
            btn.setBackground(new Color(229, 217, 242));
            btn.setFont(defaultFont);
        } else {
            btn = new Button("OK");
            btn.setBackground(new Color(244, 193, 193));
            btn.setFont(defaultFont);
        }

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(btn.getBackground().brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(messageType == JOptionPane.INFORMATION_MESSAGE ? new Color(229, 217, 242)
                        : new Color(244, 193, 193));
            }
        });

        btn.addActionListener(e -> {
            ((JDialog) SwingUtilities.getWindowAncestor(panel)).dispose();
        });

        Object[] options = { btn };

        JOptionPane.showOptionDialog(
                this,
                panel,
                title,
                JOptionPane.DEFAULT_OPTION,
                messageType,
                null,
                options,
                options[0]);
    }

    private void displayStudentData(int index) {
        if (index == students.size()) {
            textFields[0].setText("");
            textFields[1].setText("");
            courseCB.setSelectedIndex(-1);
            levelCB.setSelectedIndex(-1);
            textFields[4].setText("");
            textFields[5].setText("");
            textFields[6].setText("");
            
            
    
        } else {
            Student student = students.get(index);
            textFields[0].setText(student.getStudentId());
            textFields[1].setText(student.getName());
            if (courseCB != null) {
                courseCB.setSelectedItem(student.getCourse());  
            }
            if (levelCB != null) {
                levelCB.setSelectedItem(student.getLevel());  
            }
            textFields[4].setText(student.getAddress());
            textFields[5].setText(student.getEmail());
            textFields[6].setText(student.getContact());
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
    
        if (command.equals("UPDATE")) {
            String studentId = textFields[0].getText().trim();
            String name = textFields[1].getText().trim();
            String course = (String) courseCB.getSelectedItem();
            String level = (String) levelCB.getSelectedItem();
            String address = textFields[4].getText().trim();
            String email = textFields[5].getText().trim();
            String contact = textFields[6].getText().trim();
    
            if (!studentId.isEmpty() && !name.isEmpty() && course != null && level != null &&
                !address.isEmpty() && !email.isEmpty() && !contact.isEmpty()) {
    
                boolean isDuplicate = false;
                for (int i = 0; i < students.size(); i++) {
                    if (students.get(i).getStudentId().equals(studentId) && i != currentIndex) {
                        isDuplicate = true;
                        break;
                    }
                }
    
                if (isDuplicate) {
                    showMessage("A student with this ID already exists!", "Duplicate Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (currentIndex == students.size()) {
                        students.add(new Student(studentId, name, course, level, address, email, contact));
                        showMessage("New student added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        students.set(currentIndex, new Student(studentId, name, course, level, address, email, contact));
                        showMessage("Student details updated!", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                showMessage("Please fill out all fields correctly!", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        } else if (command.equals("<<")) {
            if (currentIndex > 0) {
                currentIndex--;
                displayStudentData(currentIndex);
            } else {
                showMessage("You are at the first record.", "Navigation", JOptionPane.WARNING_MESSAGE);
            }
        } else if (command.equals(">>")) {
            if (currentIndex < students.size()) {
                currentIndex++;
            } else {
                students.add(new Student("", "", null, null, "", "", ""));
                currentIndex++;
            }
            displayStudentData(currentIndex);
        }
    
    

        if(command.equals("EXIT")){
            dispose();
        }
    }

    public static void main(String[] args) {
        new AccountManagement();
    }

    static class Student {
        private String studentId, name, course, level, address, email, contact;

        public Student(String studentId, String name, String course, String level, String address, String email, String contact) {
            this.studentId = studentId;
            this.name = name;
            this.course = course;
            this.level = level;
            this.address = address;
            this.email = email;
            this.contact = contact;
        }

        public String getStudentId() { return studentId; }
        public String getName() { return name; }
        public String getCourse() { return course; }
        public String getLevel() { return level; }
        public String getAddress() { return address; }
        public String getEmail() { return email; }
        public String getContact() { return contact; }
    }
}

class ComboBox<E> extends JComboBox<E> {
    private int cornerRadius = 15;

    public ComboBox(E[] items) {
        super();
        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setBorder(new EmptyBorder(5, 10, 5, 10));
        setUI(new ComboBoxUI());
        setRenderer(new LCR());
        setFocusable(false);

        DefaultComboBoxModel<E> model = new DefaultComboBoxModel<>();
        for (E item : items) {
            model.addElement(item);
        }
        setModel(model);

        setSelectedIndex(-1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(graphics);
        graphics.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(new Color(200, 200, 200));
        graphics.setStroke(new BasicStroke(2));
        graphics.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        graphics.dispose();
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 5, 5, 10);
    }
}

class ComboBoxUI extends BasicComboBoxUI {

    @Override
    protected ComboPopup createPopup() {
        ComboPopup popup = super.createPopup();

        JList<?> list = popup.getList();
        JScrollPane scrollPane = new JScrollPane(list);
        ScrollBar.applyMinimalScrollBar(scrollPane);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollPane.setBackground(Color.WHITE); 
        scrollPane.setOpaque(true);
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 

        JComponent popupComponent = (JComponent) popup;
        popupComponent.setLayout(new BorderLayout());
        popupComponent.add(scrollPane, BorderLayout.CENTER);
        popupComponent.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        return popup;
    }

    @Override
    protected JButton createArrowButton() {
        JButton arrowButton = new JButton() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D graphics = (Graphics2D) g.create();
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                graphics.setColor(Color.GRAY);
                int[] xPoints = {getWidth() / 2 - 5, getWidth() / 2 + 5, getWidth() / 2};
                int[] yPoints = {getHeight() / 2 - 2, getHeight() / 2 - 2, getHeight() / 2 + 4};
                graphics.fillPolygon(xPoints, yPoints, 3);

                graphics.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(20, 20);
            }
        };

        arrowButton.setOpaque(false);
        arrowButton.setContentAreaFilled(false);
        arrowButton.setBorderPainted(false);
        arrowButton.setFocusPainted(false);

        return arrowButton;
    }

    @Override
    public void installUI(JComponent comboBox) {
        super.installUI(comboBox);
        comboBox.setLayout(new BorderLayout());
        comboBox.add(arrowButton, BorderLayout.EAST);
    }
}

class LCR extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setBorder(new EmptyBorder(5, 10, 5, 10));

        if (isSelected) {
            label.setOpaque(false); 
            label.setForeground(Color.WHITE);
        } else {
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);
        }

        return new RoundedPanel(label, isSelected);
    }

    private static class RoundedPanel extends JPanel {
        private final JLabel label;
        private final boolean isSelected;

        public RoundedPanel(JLabel label, boolean isSelected) {
            this.label = label;
            this.isSelected = isSelected;
            setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
            setOpaque(false); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (isSelected) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0xE8B86D));
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); 
                g2d.dispose();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension preferred = super.getPreferredSize();
            preferred.height = label.getPreferredSize().height + 5; 
            return preferred;
        }
    }
}

class Button extends JButton {
    private int shadowSize = 2;

    public Button(String label) {
        super(label);
        setFocusable(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 20, 20);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 20, 20);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
}

class TextField extends JTextField {
    private final int cornerRadius = 15;

    public TextField() {
        super();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(new Color(200, 200, 200));
        graphics.setStroke(new BasicStroke(2));
        graphics.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 15, 5, 10);
    }
}

class Panel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();
        float radius = Math.max(w, h) /  1;

        int offsetX = (int) (w * 0.50);
        int offsetY = (int) (h * -0.10);

        Color color3 = new Color(0xE9EFEC);
        Color color2 = new Color(0xC4DAD2);
        Color color1 = new Color(0x16423C);

        RadialGradientPaint radialGradientPaint = new RadialGradientPaint(
                new Point2D.Float(offsetX, offsetY), radius,
                new float[] { 0f, 0.6f, 1f },
                new Color[] { color1, color2, color3 });

        g2d.setPaint(radialGradientPaint);
        g2d.fillRect(0, 0, w, h);
    }
}
