import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.event.*;

public class ContentManagement extends JFrame implements ActionListener {
    private JComboBox<String> fontCB, fontSizeCB, colorCB;
    private JCheckBox boldCheckBox, italicCheckBox;
    private Button[] funcBtn = new Button[3]; 

    public ContentManagement() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setTitle("Content Management");
        setResizable(false);

        JPanel panel = new Panel();
        panel.setLayout(null); 

        Font defaultFont = new Font("Arial", Font.PLAIN, 14);
        Font h1Font = new Font("Arial", Font.BOLD, 36);

        int y = 40;

        JLabel h1 = new JLabel("Font Settings");
        h1.setFont(h1Font);
        h1.setBounds(85, y, 400, 40); 
        panel.add(h1);

        y += 60; 

        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setFont(defaultFont);
        sizeLabel.setBounds(30, y, 100, 30);
        panel.add(sizeLabel);

        String[] fontSizes = {"8", "10", "11", "12", "14", "16", "18", "21", "24", "28", "32", "36", "42", "48", "56", "64", "72", "96" };
        fontSizeCB = new ComboBox<>(fontSizes);
        fontSizeCB.setBounds(120, y, 230, 30);
        panel.add(fontSizeCB);

        y += 40; 

        JLabel styleLabel = new JLabel("Style:");
        styleLabel.setFont(defaultFont);
        styleLabel.setBounds(30, y, 100, 30);
        panel.add(styleLabel);

        String[] fonts = {"Arial", "Comic Sans MS", "Courier New", "Georgia", "Impact", "Tahoma", "Times New Roman", "Veranda"};
        fontCB = new ComboBox<>(fonts);
        fontCB.setBounds(120, y, 230, 30);
        panel.add(fontCB);

        y += 40;

        JLabel faceLabel = new JLabel("Face:");
        faceLabel.setFont(defaultFont);
        faceLabel.setBounds(30, y, 100, 30);  
        panel.add(faceLabel);

        JLabel boldLabel = new JLabel("Bold");
        boldLabel.setFont(defaultFont);
        boldLabel.setBounds(150, y, 100, 30);  
        panel.add(boldLabel);

        boldCheckBox = new CheckBox(); 
        boldCheckBox.setBounds(185, y, 100, 30);  
        panel.add(boldCheckBox);

        JLabel italicLabel = new JLabel("Italic");
        italicLabel.setFont(defaultFont);
        italicLabel.setBounds(255, y, 100, 30);  
        panel.add(italicLabel);

        italicCheckBox = new CheckBox(); 
        italicCheckBox.setBounds(290, y, 100, 30);
        panel.add(italicCheckBox);

        y += 40;

        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setFont(defaultFont);
        colorLabel.setBounds(30, y, 100, 30);
        panel.add(colorLabel);

        String[] colors = {
                "Black", "White", "Red", "Green", "Blue", "Yellow", "Orange", "Pink"};
        colorCB = new ComboBox<>(colors);
        colorCB.setBounds(120, y, 230, 30);
        panel.add(colorCB);

        y += 70; 

        String[] buttonHeaders = {"APPLY", "RESET", "CLOSE"};

        int xPosition = 27;
        
        for (int i = 0; i < buttonHeaders.length; i++) {
            funcBtn[i] = new Button(buttonHeaders[i]);
            funcBtn[i].setBounds(xPosition, y, 90, 30);
            funcBtn[i].setFont(defaultFont);
            funcBtn[i].setForeground(Color.white);;
            funcBtn[i].setBackground(new Color(0xAEC6CF));
            funcBtn[i].addActionListener(this);

            final int index = i;
            funcBtn[i].addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    funcBtn[index].setBackground(new Color(0xAEC6CF).darker());
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    funcBtn[index].setBackground(new Color(0xAEC6CF));
                }
            });

            panel.add(funcBtn[i]);
            xPosition += 120;
        }

        add(panel);
        setVisible(true);
    }

    private void showMessage(String message, String title, int messageType) {
        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
    
        // Retrieve selected font, size, and color; show error if any are null
        String selectedFont = (String) fontCB.getSelectedItem();
        String selectedSizeStr = (String) fontSizeCB.getSelectedItem();
        String selectedColor = (String) colorCB.getSelectedItem();
        
        if (selectedFont == null || selectedSizeStr == null || selectedColor == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Selection",
                    "Selection Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
    
        int selectedFontSize = Integer.parseInt(selectedSizeStr);
        
        Color color = Color.BLACK;
        switch (selectedColor) {
            case "White": color = Color.WHITE; break;
            case "Red": color = Color.RED; break;
            case "Green": color = Color.GREEN; break;
            case "Blue": color = Color.BLUE; break;
            case "Yellow": color = Color.YELLOW; break;
            case "Orange": color = Color.ORANGE; break;
            case "Pink": color = Color.PINK; break;
        }
    
        
        int style = Font.PLAIN;
        if (boldCheckBox.isSelected()) style |= Font.BOLD;
        if (italicCheckBox.isSelected()) style |= Font.ITALIC;
    
        Font displayFont = new Font(selectedFont, style, selectedFontSize);
    
        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        label.setFont(displayFont);
        label.setForeground(color);
        panel.add(label);
    
        Button btn = new Button(messageType == JOptionPane.INFORMATION_MESSAGE ? "EXIT" : "OK");
        btn.setBackground(messageType == JOptionPane.INFORMATION_MESSAGE ? new Color(229, 217, 242) : new Color(244, 193, 193));
        btn.setFont(defaultFont);
    
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
    
        btn.addActionListener(e -> ((JDialog) SwingUtilities.getWindowAncestor(panel)).dispose());
    
        Object[] options = { btn };
    
        JOptionPane.showOptionDialog(
                this,
                panel,
                title,
                JOptionPane.DEFAULT_OPTION,
                messageType,
                null,
                options,
                options[0]
        );
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == funcBtn[0]) {
            showMessage("HELLO WORLD", "Output", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == funcBtn[1]) {
            fontCB.setSelectedItem(null);
            fontSizeCB.setSelectedItem(null);
            colorCB.setSelectedItem(null);
            boldCheckBox.setSelected(false);
            italicCheckBox.setSelected(false);
        } else if (e.getSource() == funcBtn[2]) {
            dispose();
        }
    }
    
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContentManagement frame = new ContentManagement();
            frame.setVisible(true);
        });
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
        // Leave track transparent
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
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(6, Integer.MAX_VALUE)); // Thinner vertical scrollbar
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 6)); // Thinner horizontal scrollbar
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
        return new Insets(5, 10, 5, 10);
    }
}

class ComboBoxUI extends BasicComboBoxUI {
    private int cornerRadius = 15;

    @Override
    protected ComboPopup createPopup() {
        ComboPopup popup = super.createPopup();

        JList<?> list = popup.getList();
        JScrollPane scrollPane = new JScrollPane(list);
        ScrollBar.applyMinimalScrollBar(scrollPane);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Border for dropdown
        scrollPane.setBackground(Color.WHITE); // Set dropdown background to white
        scrollPane.setOpaque(true);
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding for dropdown

        // Rounded corners for dropdown
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
                g2d.setColor(new Color(0xAEC6CF));
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Rounded rectangle
                g2d.dispose();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension preferred = super.getPreferredSize();
            preferred.height = label.getPreferredSize().height + 5; // Add padding to fit rounded background
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

        // Draw shadow
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 20, 20);

        // Draw button background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 20, 20);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No custom border; override to prevent default painting
    }
}

class CheckBox extends JCheckBox {
    private final Color checkBoxColor = new Color(0xAEC6CF);
    private final Color borderColor = new Color(180, 180, 180);
    private final Color hoverColor = new Color(0xAEC6CF);
    private boolean hovered = false;

    public CheckBox() {
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));  

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = 20;  
        int x = 2;
        int y = (getHeight() - size) / 2; // Center the checkbox vertically within the component

        // Draw border (circle shape)
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, size, size);  // Draw circle border

        // Draw background based on hover state
        g2.setColor(hovered ? hoverColor : getBackground());
        g2.fillOval(x + 1, y + 1, size - 2, size - 2);  // Fill the circle background

        // Draw the check if selected
        if (isSelected()) {
            g2.setColor(checkBoxColor);
            int padding = 4;
            g2.fillOval(x + padding, y + padding, size - padding * 2, size - padding * 2);  // Fill the inner circle when checked
        }

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(26, 26); // Adjusted size for a circular checkbox
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
        int offsetY = (int) (h * -0.50);

        Color color3 = new Color(255, 255, 255);
        Color color2 = new Color(0xdfeef3);
        Color color1 = new Color(0xc9e1e6);

        RadialGradientPaint radialGradientPaint = new RadialGradientPaint(
                new Point2D.Float(offsetX, offsetY), radius,
                new float[] { 0f, 0.6f, 1f },
                new Color[] { color1, color2, color3 });

        g2d.setPaint(radialGradientPaint);
        g2d.fillRect(0, 0, w, h);
    }
}