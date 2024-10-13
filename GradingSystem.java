import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.stream.Stream;

class ScrollBar extends javax.swing.plaf.basic.BasicScrollBarUI {

    @Override
    protected void configureScrollBarColors() {
        thumbColor = new Color(0xF7D486);
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

    @Override
    protected Dimension getMinimumThumbSize() {
        return new Dimension(2, 10);
    }

    private JButton createInvisibleButton() {
        JButton Button = new JButton();
        Button.setPreferredSize(new Dimension(0, 0));
        Button.setMinimumSize(new Dimension(0, 0));
        Button.setMaximumSize(new Dimension(0, 0));
        return Button;
    }

    public static void applyMinimalScrollBar(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().setUI(new ScrollBar());
        scrollPane.getHorizontalScrollBar().setUI(new ScrollBar());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, Integer.MAX_VALUE)); // Make vertical
                                                                                                 // scrollbar narrow
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 8)); // Make horizontal
                                                                                                   // scrollbar narrow
        scrollPane.getVerticalScrollBar().setOpaque(false);
        scrollPane.getHorizontalScrollBar().setOpaque(false);
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
        float radius = Math.max(w, h) / 2f;

        int offsetX = (int) (w * 0.99);
        int offsetY = (int) (h * 0.45);

        Color color3 = new Color(255, 255, 255);
        Color color2 = new Color(0xF7D486);
        Color color1 = new Color(0xF27A7D);

        RadialGradientPaint radialGradientPaint = new RadialGradientPaint(
                new Point2D.Float(offsetX, offsetY), radius,
                new float[] { 0f, 0.6f, 1f },
                new Color[] { color1, color2, color3 });

        g2d.setPaint(radialGradientPaint);
        g2d.fillRect(0, 0, w, h);
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
        return new Insets(5, 10, 5, 10);
    }
}

public class GradingSystem extends JFrame implements ActionListener {
    private JTextField[] longQuiz = new TextField[3];
    private JTextField[] classPart = new TextField[4];
    private JTextField[] labAct = new TextField[4];
    private TextField mtrmExam, finalExam, TEavg;
    private JLabel LQavg, CPavg, LAavg;
    private Button[] funcBtn = new Button[3];

    public GradingSystem() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(510, 900);
        setLocationRelativeTo(null);
        setTitle("Grading System");
        setResizable(false);

        JPanel bgPanel = new Panel();
        bgPanel.setLayout(null);
        bgPanel.setPreferredSize(new Dimension(490, 1150));

        JScrollPane scrollPane = new JScrollPane(bgPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new ScrollBar());
        verticalScrollBar.setPreferredSize(new Dimension(7, Integer.MAX_VALUE));

        add(scrollPane);

        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font h1Font = new Font("Segoe UI", Font.BOLD, 36);
        Font h2Font = new Font("Segoe UI", Font.BOLD, 18);
        Font h3Font = new Font("Segoe UI", Font.BOLD, 16);
        Font h4Font = new Font("Segoe UI", Font.BOLD, 14);

        int y = 10;

        JLabel h1 = new JLabel("Grading System");
        h1.setBounds(120, y += 10, 300, 80);
        h1.setFont(h1Font);
        bgPanel.add(h1);

        JLabel h2 = new JLabel("LECTURE (100%)");
        h2.setBounds(30, y += 80, 300, 40);
        h2.setFont(h2Font);
        bgPanel.add(h2);

        JLabel h3 = new JLabel("Final");
        h3.setBounds(30, y += 30, 300, 40);
        h3.setFont(h3Font);
        bgPanel.add(h3);

        String[] percentHeaders = {
                "Class Standing (60%):",
                "Midterms (15%):",
                "Finals (25%)"
        };

        for (int i = 0; i < percentHeaders.length; i++) {
            JLabel percentLabel = new JLabel(percentHeaders[i]);
            percentLabel.setBounds(50, y += 35, 250, 30);
            percentLabel.setFont(h4Font);
            percentLabel.setForeground(new Color(0x7C9D96));
            bgPanel.add(percentLabel);

            // Class Standing Section
            if (percentHeaders[i].equals("Class Standing (60%):")) {
                String[] CSheaders = {
                        "Long Quizzes (30%):",
                        "Teacher Evaluation (5%):",
                        "Class Participation (15%):",
                        "Lab Activities (50%)"
                };

                for (int j = 0; j < CSheaders.length; j++) {
                    JLabel CSlabels = new JLabel(CSheaders[j]);
                    CSlabels.setBounds(70, y += 35, 250, 30);
                    CSlabels.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    CSlabels.setForeground(new Color(0x51829B));
                    bgPanel.add(CSlabels);

                    // Long Quizzes section
                    if (CSheaders[j].equals("Long Quizzes (30%):")) {
                        String[] LQlabels = { "Long Quiz 1:", "Long Quiz 2:", "Long Quiz 3:" };
                        for (int k = 0; k < LQlabels.length; k++) {
                            JLabel lqLabel = new JLabel(LQlabels[k]);

                            lqLabel.setBounds(90, y += 35, 80, 30);
                            lqLabel.setFont(defaultFont);
                            bgPanel.add(lqLabel);

                            longQuiz[k] = new TextField();
                            longQuiz[k].setBounds(265, y, 180, 30);
                            longQuiz[k].setHorizontalAlignment(JTextField.CENTER);
                            bgPanel.add(longQuiz[k]);
                        }

                        JLabel LQavgLabel = new JLabel("Long Quiz Average:");
                        LQavgLabel.setBounds(90, y += 35, 120, 30);
                        LQavgLabel.setFont(defaultFont);
                        bgPanel.add(LQavgLabel);

                        LQavg = new JLabel();
                        LQavg.setBounds(265, y, 180, 30);
                        LQavg.setHorizontalAlignment(JTextField.CENTER);
                        LQavg.setForeground(Color.RED);
                        bgPanel.add(LQavg);

                    }

                    // Class Participation Section
                    else if (CSheaders[j].equals("Class Participation (15%):")) {
                        String[] CPlabels = { "Seatwork:", "Assignment:", "Short Quiz:", "Recitation:" };
                        for (int k = 0; k < CPlabels.length; k++) {
                            JLabel cpLabel = new JLabel(CPlabels[k]);

                            cpLabel.setBounds(90, y += 35, 80, 30);
                            cpLabel.setFont(defaultFont);
                            bgPanel.add(cpLabel);

                            classPart[k] = new TextField();
                            classPart[k].setBounds(265, y, 180, 30);
                            classPart[k].setHorizontalAlignment(JTextField.CENTER);
                            bgPanel.add(classPart[k]);
                        }

                        JLabel cpAvgLabel = new JLabel("Class Participation Average:");
                        cpAvgLabel.setBounds(90, y += 35, 180, 30);
                        cpAvgLabel.setFont(defaultFont);
                        bgPanel.add(cpAvgLabel);

                        CPavg = new JLabel();
                        CPavg.setBounds(265, y, 180, 30);
                        CPavg.setHorizontalAlignment(JTextField.CENTER);
                        CPavg.setForeground(Color.RED);
                        bgPanel.add(CPavg);
                    }

                    // Lab Activities section
                    else if (CSheaders[j].equals("Lab Activities (50%)")) {
                        String[] LAlabels = { "Lab Exercises (10%):", "MP (20%):", "Practical (20%)",
                                "Projects (50%):" };
                        for (int k = 0; k < LAlabels.length; k++) {
                            JLabel laLabel = new JLabel(LAlabels[k]);

                            laLabel.setBounds(90, y += 35, 130, 30);
                            laLabel.setFont(defaultFont);
                            bgPanel.add(laLabel);

                            labAct[k] = new TextField();
                            labAct[k].setBounds(265, y, 180, 30);
                            labAct[k].setHorizontalAlignment(JTextField.CENTER);
                            bgPanel.add(labAct[k]);
                        }

                        JLabel laAvgLabel = new JLabel("Lab Activities Average:");
                        laAvgLabel.setBounds(90, y += 35, 240, 30);
                        laAvgLabel.setFont(defaultFont);
                        bgPanel.add(laAvgLabel);

                        LAavg = new JLabel();
                        LAavg.setBounds(265, y, 180, 30);
                        LAavg.setHorizontalAlignment(JTextField.CENTER);
                        LAavg.setForeground(Color.RED);
                        bgPanel.add(LAavg);

                    } else {
                        TEavg = new TextField();
                        TEavg.setBounds(265, y, 180, 30);
                        TEavg.setHorizontalAlignment(JTextField.CENTER);
                        bgPanel.add(TEavg);

                    }

                }

                // Midterms Section
            } else if (percentHeaders[i].equals("Midterms (15%):")) {
                mtrmExam = new TextField();
                mtrmExam.setBounds(265, y, 180, 30);
                mtrmExam.setHorizontalAlignment(JTextField.CENTER);
                bgPanel.add(mtrmExam);

                // Finals Section
            } else {
                finalExam = new TextField();
                finalExam.setBounds(265, y, 180, 30);
                finalExam.setHorizontalAlignment(JTextField.CENTER);
                bgPanel.add(finalExam);
            }
        }

        String[] buttonheaders = { "COMPUTE", "CLEAR", "EXIT" };
        Color[] buttonColors = {
                new Color(210, 224, 251),
                new Color(254, 249, 217),
                new Color(222, 229, 212)
        };

        y += 40;
        int xPosition = 27;
        for (int i = 0; i < buttonheaders.length; i++) {
            funcBtn[i] = new Button(buttonheaders[i]);
            funcBtn[i].setBounds(xPosition, y + 20, 100, 30);
            funcBtn[i].setFont(defaultFont);
            funcBtn[i].setBackground(buttonColors[i]);
            funcBtn[i].addActionListener(this);

            final int index = i;
            funcBtn[i].addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    funcBtn[index].setBackground(buttonColors[index].brighter());
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    funcBtn[index].setBackground(buttonColors[index]);
                }
            });

            bgPanel.add(funcBtn[i]);
            xPosition += 159;
        }

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

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == funcBtn[0]) {
                float[] finalGradeComponent = {
                        computeClassStanding(),
                        Float.parseFloat(mtrmExam.getText()),
                        Float.parseFloat(finalExam.getText())
                };
                double[] componentPercentage = { 0.6, 0.15, 0.25 };
                float rawGrade = 0;
                double finalGrade;

                for (int i = 0; i < finalGradeComponent.length; i++) {
                    rawGrade += finalGradeComponent[i] * componentPercentage[i];
                }

                if (rawGrade >= 97 && rawGrade <= 100) {
                    finalGrade = 4.0;
                } else if (rawGrade >= 93 && rawGrade <= 96) {
                    finalGrade = 3.5;
                } else if (rawGrade >= 89 && rawGrade <= 92) {
                    finalGrade = 3.0;
                } else if (rawGrade >= 85 && rawGrade <= 88) {
                    finalGrade = 2.5;
                } else if (rawGrade >= 80 && rawGrade <= 84) {
                    finalGrade = 2.0;
                } else if (rawGrade >= 75 && rawGrade <= 79) {
                    finalGrade = 1.5;
                } else if (rawGrade >= 70 && rawGrade <= 74) {
                    finalGrade = 1.0;
                } else {
                    finalGrade = 0.5;
                }

                LQavg.setText(Float.toString(getAverageLongQuiz()));
                CPavg.setText(Float.toString(getAverageClassPart()));
                LAavg.setText(Float.toString(getAverageLabAct()));

                showMessage("Your final grade is: " + finalGrade, "Final Grade", JOptionPane.INFORMATION_MESSAGE);

            } else if (e.getSource() == funcBtn[1]) {
                LQavg.setText("");
                CPavg.setText("");
                LAavg.setText("");
                JTextField[] allFields = Stream.of(longQuiz, classPart, labAct)
                        .flatMap(Arrays::stream)
                        .toArray(JTextField[]::new);
                for (JTextField jTextField : allFields) {
                    jTextField.setText("");
                }
                TEavg.setText("");
                mtrmExam.setText("");
                finalExam.setText("");

            } else if (e.getSource() == funcBtn[2]) {
                System.exit(0);
            }
        } catch (NumberFormatException ex) {
            showMessage("Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            showMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (AssertionError ex) {
            showMessage(ex.getMessage(), "Assertion Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    float getAverageLongQuiz() {
        float total = 0;

        for (JTextField longquiz : longQuiz) {
            try {
                total += Float.parseFloat(longquiz.getText());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number in one of the fields");
            }
        }

        return total / 3;
    }

    float getAverageClassPart() {
        float total = 0;
        for (JTextField classpart : classPart) {
            try {
                total += Float.parseFloat(classpart.getText());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number in one of the fields: ");
            }
        }
        return total / 4;
    }

    float getAverageLabAct() {
        float total = 0;

        try {
            total += Float.parseFloat(labAct[0].getText()) * 0.10;
            total += Float.parseFloat(labAct[1].getText()) * 0.20;
            total += Float.parseFloat(labAct[2].getText()) * 0.20;
            total += Float.parseFloat(labAct[3].getText()) * 0.50;

        } catch (NumberFormatException e) {
            System.out.println("Invalid input in one of the labAct fields");
        }
        return total;
    }

    float computeClassStanding() {

        float classStanding = 0;
        float[] classStandingScores = { getAverageLongQuiz(), Float.parseFloat(TEavg.getText()), getAverageClassPart(),
                getAverageLabAct() };
        double[] percentage = { 0.3, 0.05, 0.15, 0.50 };

        for (int i = 0; i < classStandingScores.length; i++) {
            classStanding += classStandingScores[i] * percentage[i];
        }

        return classStanding;
    }

    public static void main(String[] args) {
        new GradingSystem();
    }
}
