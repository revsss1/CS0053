import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.time.DateTimeException;
import java.time.LocalDateTime;


public class Announcement extends JFrame {

    private JComboBox<String> typeCB, priorityCB, monthCB, dayCB, yearCB;
    private JTextField titleField;
    private JTextArea contentArea;
    private JButton[] funcBtn = new JButton[3];

    public Announcement() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Announcement");
        setSize(485, 650);
        setResizable(false);
        setLayout(null);

        JPanel panel = new announcePanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, getWidth(), getHeight());

        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font h1Font = new Font("Segoe UI", Font.BOLD, 30);

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("megaphone.png");
        imageLabel.setIcon(imageIcon);
        imageLabel.setBounds(210, 0, getWidth(), 130);
        panel.add(imageLabel);

        JLabel titleLabel = new JLabel("Announcement", SwingConstants.CENTER);
        titleLabel.setBounds(0, 120, getWidth(), 40);
        titleLabel.setForeground(new Color(0x7D0A0A));
        titleLabel.setFont(h1Font);
        panel.add(titleLabel);

        createInputFields(panel, defaultFont);
        createButtons(panel, defaultFont);
        add(panel);
        setVisible(true);
    }

    private void createInputFields(JPanel panel, Font defaultFont) {
        String[] labels = {"Type:", "Schedule:", "Priority:", "Title:", "Content:"};
        int yPosition = 190;

        for (String label : labels) {
            JLabel lbl = new JLabel(label);
            lbl.setBounds(30, yPosition, 100, 30);
            lbl.setFont(defaultFont);  
            panel.add(lbl);

            switch (label) {
                case "Type:" -> {
                    typeCB = new ComboBox<>(new String[]{"Event", "Reminder", "Alert"});
                    typeCB.setBounds(140, yPosition, 300, 30);
                    typeCB.setFont(defaultFont); 
                    panel.add(typeCB);
                }
                case "Schedule:" -> {
                    String[] months = {"January", "February", "March", "April", "May", "June",
                            "July", "August", "September", "October", "November", "December"};
                    String[] days = new String[31];
                    for (int i = 0; i < 31; i++) days[i] = String.valueOf(i + 1);
                    String[] years = new String[7];
                    for (int i = 0; i < 7; i++) years[i] = String.valueOf(2024 + i);

                    monthCB = new ComboBox<>(months);
                    dayCB = new ComboBox<>(days);
                    yearCB = new ComboBox<>(years);

                    monthCB.setBounds(140, yPosition, 120, 30);
                    monthCB.setFont(defaultFont);  
                    panel.add(monthCB);

                    dayCB.setBounds(270, yPosition, 70, 30);
                    dayCB.setFont(defaultFont); 
                    panel.add(dayCB);

                    yearCB.setBounds(350, yPosition, 90, 30);
                    yearCB.setFont(defaultFont); 
                    panel.add(yearCB);
                }
                case "Priority:" -> {
                    priorityCB = new ComboBox<>(new String[]{"High", "Medium", "Low"});
                    priorityCB.setBounds(140, yPosition, 300, 30);
                    priorityCB.setFont(defaultFont);  
                    priorityCB.setMaximumRowCount(3); 
                    panel.add(priorityCB);
                }
                case "Title:" -> {
                    titleField = new TextField();
                    titleField.setBounds(140, yPosition, 300, 30);
                    titleField.setFont(defaultFont);  
                    panel.add(titleField);
                }
                case "Content:" -> {
                    contentArea = new TextArea();
                    contentArea.setFont(defaultFont); 
                    
                    JScrollPane contentScrollPane = new JScrollPane(contentArea);
                    contentScrollPane.setBounds(140, yPosition, 300, 150);
                    contentScrollPane.setBorder(BorderFactory.createEmptyBorder());
                    ScrollBar.applyMinimalScrollBar(contentScrollPane);
    
                    panel.add(contentScrollPane);
                    yPosition += 120;
                }
            }

            yPosition += 40;
        }
    }

    private void createButtons(JPanel panel, Font defaultFont) {
        String[] buttonColors = {"#6DA4AA", "#E9C46A", "#D04848"};
        String[] buttonHeaders = {"POST", "CLEAR", "CLOSE"};
        int buttonSpacing = 20;
    
        for (int i = 0; i < buttonHeaders.length; i++) {
            funcBtn[i] = new Button(buttonHeaders[i]);
            funcBtn[i].setFont(defaultFont);
            funcBtn[i].setBackground(Color.decode(buttonColors[i]));
            funcBtn[i].setForeground(Color.WHITE);
            funcBtn[i].setFocusPainted(false);
            funcBtn[i].setBounds(70 + (i * (100 + buttonSpacing)), 530, 100, 35);
            panel.add(funcBtn[i]);
    
            funcBtn[i].addActionListener(e -> {
                switch (e.getActionCommand()) {
                    case "POST":
                        postAction();
                        break;
                    case "CLEAR":
                        clearFields();
                        break;
                    case "CLOSE":
                        System.exit(0);
                        break;
                }
            });
    
            final int index = i;  
            funcBtn[i].addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    funcBtn[index].setBackground(Color.decode(buttonColors[index]).darker());
                }
    
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    funcBtn[index].setBackground(Color.decode(buttonColors[index]));
                }
            });
        }
    }

    private boolean isValidDate() {
        // Ensure all combo boxes are selected
        String selectedDay = (String) dayCB.getSelectedItem();
        String selectedMonth = (String) monthCB.getSelectedItem();
        String selectedYear = (String) yearCB.getSelectedItem();
    
        // Check if any of the combo boxes are unselected or empty
        if (selectedDay == null || selectedDay.isEmpty() ||
            selectedMonth == null || selectedMonth.isEmpty() ||
            selectedYear == null || selectedYear.isEmpty()) {
            return false; // One or more combo boxes are not selected
        }
    
        // Convert month index to integer (ComboBox values are zero-indexed)
        int month = monthCB.getSelectedIndex();
        int year = Integer.parseInt(selectedYear);
    
        // Determine the number of days in the month
        int daysInMonth = switch (month) {
            case 0, 2, 4, 6, 7, 9, 11 -> 31;  
            case 3, 5, 8, 10 -> 30;  
            case 1 -> (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;  
            default -> 31;  
        };
    
        int selectedDayInt = Integer.parseInt(selectedDay);
    
        // Validate the selected date
        if ((month == 1 && selectedDayInt > 29) || 
            (month == 1 && selectedDayInt == 29 && !(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))) || 
            (month == 3 || month == 5 || month == 8 || month == 10) && selectedDayInt > 30 || // April, June, September, November can't have more than 30 days
            (selectedDayInt > daysInMonth)) { // If selected day is invalid for the month
            return false; 
        }
    
        return true; // Valid date
    }
    
    private void postAction() {

        // Ensure the type is selected
        String selectedType = (String) typeCB.getSelectedItem();
        if (selectedType == null || selectedType.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a valid type for the announcement.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Validate the date
        if (!isValidDate()) {
            JOptionPane.showMessageDialog(null, "Invalid date selected. Ensure that the day, month, and year are correctly chosen.", "Date Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Get and trim title and content
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();
    
        // Check if title and content are filled
        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Both title and content fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Get date values from combo boxes
        String selectedDay = (String) dayCB.getSelectedItem();
        String selectedYear = (String) yearCB.getSelectedItem();
        int monthIndex = monthCB.getSelectedIndex();
    
        // Ensure day, year, and month have valid selections
        if (selectedDay == null || selectedDay.isEmpty() || selectedYear == null || selectedYear.isEmpty() || monthIndex < 0) {
            JOptionPane.showMessageDialog(null, "Please select a valid day, month, and year for the date.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Parse inputs after validation
        int day = Integer.parseInt(selectedDay);
        int year = Integer.parseInt(selectedYear);
        int month = monthIndex + 1;
    
        // Validation: Check for valid date
        try {
            LocalDateTime scheduleTime = LocalDateTime.of(year, month, day, 0, 0);
            scheduleAnnouncement(scheduleTime, title, content);
            JOptionPane.showMessageDialog(null, "Announcement has been scheduled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (DateTimeException e) {
            JOptionPane.showMessageDialog(null, "The selected date is invalid. Please check the day, month, and year values.", "Date Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void clearFields() {
        titleField.setText("");
        contentArea.setText("");
        priorityCB.setSelectedIndex(-1);
        typeCB.setSelectedIndex(-1);
        monthCB.setSelectedIndex(-1);
        dayCB.setSelectedIndex(-1);
        yearCB.setSelectedIndex(-1);
        priorityCB.setSelectedIndex(-1);
    }
    

    private void scheduleAnnouncement(LocalDateTime scheduleTime, String title, String content) {
        long delay = java.time.Duration.between(LocalDateTime.now(), scheduleTime).toMillis();
    
        if (delay <= 0) {
            showFormattedMessage(title, content);
            return;
        }
    
        // Create a new thread to handle the delayed announcement
        Thread announcementThread = new Thread(() -> {
            try {
                // Sleep for the delay period
                Thread.sleep(delay);
    
                // After delay, show the announcement
                SwingUtilities.invokeLater(() -> {
                    showFormattedMessage(title, content);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    
        // Start the thread
        announcementThread.start();
    }
    
    
    private void showFormattedMessage(String title, String content) {
        String type = (String) typeCB.getSelectedItem();
        String priority = (String) priorityCB.getSelectedItem();
    
        String priorityColor;
        switch (priority) {
            case "High":
                priorityColor = "#D04848"; 
                break;
            case "Medium":
                priorityColor = "#E9C46A"; 
                break;
            case "Low":
                priorityColor = "#6DA4AA"; 
                break;
            default:
                priorityColor = "#808080"; 
                break;
        }

        String formattedContent = content.replace("\n", "<br>");

        String message = "<html><body style='font-family: Segoe UI; font-size: 12px; color: #333333;'>"
                + "<p style='color: #808080;'>Announcement Type: " + type + "</p>"  
                + "<p style='color: " + priorityColor + ";'>Priority: " + priority + "</p>"  
                + "<h1>" + title + "</h1>" 
                + "<p>" + formattedContent + "</p>"   
                + "</body></html>";
        
        showMessage(message, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }
    

    
    
    private void showMessage(String message, String title, int messageType) {
        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
    
        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        label.setFont(defaultFont);
        label.setText("<html>" + message + "</html>");  
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
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Announcement::new);
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

        // Draw shadow
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

class announcePanel extends JPanel {
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

        Color color3 = new Color(0xEEEEEE);
        Color color2 = new Color(0xF8F4EC);
        Color color1 = new Color(0xEAD196);

        RadialGradientPaint radialGradientPaint = new RadialGradientPaint(
                new Point2D.Float(offsetX, offsetY), radius,
                new float[] { 0f, 0.6f, 1f },
                new Color[] { color1, color2, color3 });

        g2d.setPaint(radialGradientPaint);
        g2d.fillRect(0, 0, w, h);
    }
}

class TextField extends JTextField {
    private int cornerRadius = 15;

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

class TextArea extends JTextArea {
    private int cornerRadius = 15;

    public TextArea() {
        super();
        setOpaque(false); 
        setWrapStyleWord(true); 
        setLineWrap(true); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 

        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        graphics.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        super.paintComponent(graphics); 
        graphics.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 

        graphics.setColor(new Color(200, 200, 200)); 
        graphics.setStroke(new BasicStroke(2)); 

        graphics.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, cornerRadius, cornerRadius);
        graphics.dispose();
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 15, 5, 10); 
    }
}

class ScrollBar extends javax.swing.plaf.basic.BasicScrollBarUI {
    
    @Override
    protected void configureScrollBarColors() {
        thumbColor = Color.GRAY;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createInvisibleButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createInvisibleButton();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(thumbColor);
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
        g2.dispose();
    }

    private JButton createInvisibleButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    public static void applyMinimalScrollBar(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().setUI(new ScrollBar());
        scrollPane.getHorizontalScrollBar().setUI(new ScrollBar());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(6, Integer.MAX_VALUE)); 
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 6)); 
        scrollPane.getVerticalScrollBar().setOpaque(false);
        scrollPane.getHorizontalScrollBar().setOpaque(false);
    }
}

class ComboBox<E> extends JComboBox<E> {
    private int cornerRadius = 15;

    public ComboBox(E[] items) {
        super();
        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setBorder(new EmptyBorder(5, 5, 5, 10));
        setUI(new CBUI());
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
        return new Insets(5, 10, 5, 10);
    }
}

class CBUI extends BasicComboBoxUI {

    @Override
    protected ComboPopup createPopup() {
        ComboPopup popup = super.createPopup();
    
        JList<?> list = popup.getList();
        list.setVisibleRowCount(3); 
        list.setFixedCellHeight(30); 
    
        JScrollPane scrollPane = new JScrollPane(list);
        ScrollBar.applyMinimalScrollBar(scrollPane);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollPane.setBackground(Color.WHITE); 
        scrollPane.setOpaque(true);
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5)); 
    
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
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setBorder(new EmptyBorder(2, 5, 2, 5));

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
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15); 
                g2d.dispose();
            }
        }

        @Override
        public Insets getInsets() {
            return new Insets(0, 0, 0, 0); 
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension preferred = super.getPreferredSize();
            preferred.height = label.getPreferredSize().height; 
            return preferred;
        }
    }
}
