package colorpicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ColorFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel optionPanel;
    private JPanel middlePanel;
    private JButton closeButton;
    private JTextField hexTextField;
    private JTextField rgbTextField;
    private JTextField cmykTextField;
    private JLabel hexLabel;
    private JLabel rgbLabel;
    private JLabel cmykLabel;
    private JButton copyHexButton;
    private JButton copyRgbButton;
    private JButton CopyCmykButton;
    private JButton screenButton;
    private JPanel latestColorsPanel;
    private JPanel leftPanel;
    private JTextField finalColor;
    private JPanel emptyPanel;
    private JPanel spectrumPanel;
    private ColorPanel colorPanel;
    private BufferedImage bufferedImage;
    private BufferedReader bufferedReader;
    public static JTextField hiddenTextField = new JTextField();
    public static Color colorForColorPanel;
    public static Color positionColor = Color.BLACK;
    public static boolean editManualRGB;
    public static boolean editManualHEX;
    public static boolean editManualCMYK;
    public static boolean doneImage;
    public static boolean iconified;


    public ColorFrame() {
        setTitle("Color picker");
        //Set icon on frame
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("pipette.png"));
        setIconImage(imageIcon.getImage());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 300, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 200, 0, 0);
        finalColor.setBorder(BorderFactory.createEtchedBorder(1));
        initLatestColor();
        //Create a menu
        Menu menu = new Menu(menuPanel, this);
        initPanels();
        initListeners();
        myDocumentListener();
        add(mainPanel);
        pack();

        //Get LookAndFeel from file look.inf
        try {
            File file = new File("look.inf");
            bufferedReader = new BufferedReader(new FileReader(file));
            UIManager.setLookAndFeel(bufferedReader.readLine());
            bufferedReader.close();
        } catch (ClassNotFoundException e) {
            UIManager.getLookAndFeelDefaults();
            try {
                bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (InstantiationException e) {
            UIManager.getLookAndFeelDefaults();
        } catch (IllegalAccessException e) {
            UIManager.getLookAndFeelDefaults();
        } catch (UnsupportedLookAndFeelException e) {
            UIManager.getLookAndFeelDefaults();
        } catch (IOException e){
            UIManager.getLookAndFeelDefaults();
        }
        SwingUtilities.updateComponentTreeUI(this);

        setVisible(true);
    }

    //Get color when ScreenPixelFrame is disposed
    public ColorFrame(String screenColor){
        colorForColorPanel = Color.decode(screenColor);
        hiddenTextField.setText(screenColor);
        ColorPanel.posX = -5;
        ColorPanel.posY = -5;
        ColorFrame.editManualRGB = false;
        ColorFrame.editManualHEX = false;
        ColorFrame.editManualCMYK = false;
        ColorFrame.doneImage = false;
        //Append latest selected color to text file
        LatestColors latest = new LatestColors(screenColor);
    }

    private void initPanels(){
        SpectrumPanel spectrumPanel = new SpectrumPanel();
        middlePanel.add(spectrumPanel);
        colorForColorPanel = new Color(255,0,0);
        colorPanel = new ColorPanel();
        leftPanel.add(colorPanel, BorderLayout.CENTER);
    }

    private void myDocumentListener(){
        //DocumentListener for hiddenTextField
        hiddenTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setTextToRgbTextField();
                setTextToHexTextField();
                setTextToCMYKTextField();
                finalColor.setBackground(colorForColorPanel);
                colorPanel.repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {}

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        //DocumentListener for hexTextField
        hexTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { getColor(); }
            @Override
            public void removeUpdate(DocumentEvent e) { getColor(); }
            @Override
            public void changedUpdate(DocumentEvent e) { getColor(); }

            private void getColor(){
                String s = hexTextField.getText();
                //if text in hexTextField length is '6' and contains only digits 0-9 or char a-f
                boolean isMatch = s.matches("[0-9, a-f, A-F][0-9, a-f, A-F][0-9, a-f, A-F][0-9, a-f, A-F][0-9, a-f, A-F][0-9, a-f, A-F]");
                if (isMatch && editManualHEX){
                    try {
                        colorForColorPanel = Color.decode("#" + hexTextField.getText());
                        setTextToRgbTextField();
                        setTextToCMYKTextField();
                        finalColor.setBackground(colorForColorPanel);
                        ColorPanel.posX = -5;
                        ColorPanel.posY = -5;
                        doneImage = false;
                        colorPanel.repaint();
                    } catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        });

        //DocumentListener for rgbTextField
        rgbTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                getColor();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                getColor();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                getColor();
            }

            private void getColor(){
                String s = rgbTextField.getText();
                boolean isMatch = s.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
                if (isMatch && editManualRGB) {
                    try {
                        int first = rgbTextField.getText().indexOf(".");
                        int second = rgbTextField.getText().indexOf(".", first + 1);
                        int R = Integer.parseInt(rgbTextField.getText().substring(0, first));
                        int G = Integer.parseInt(rgbTextField.getText().substring(first + 1, second));
                        int B = Integer.parseInt(rgbTextField.getText().substring(second + 1, rgbTextField.getText().length()));
                        if (R >= 0 && R <= 255 && G >= 0 && G <= 255 && B >= 0 && B <= 255) {
                            colorForColorPanel = new Color(R, G, B);
                            setTextToHexTextField();
                            setTextToCMYKTextField();
                            finalColor.setBackground(colorForColorPanel);
                            ColorPanel.posX = -5;
                            ColorPanel.posY = -5;
                            doneImage = false;
                            colorPanel.repaint();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //DocumentListener for cmykTextField
        cmykTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                getColor();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                getColor();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                getColor();
            }

            private void getColor(){
                String s = cmykTextField.getText();
                boolean isMatch = s.matches("\\d{1,3}\\%, \\d{1,3}\\%, \\d{1,3}\\%, \\d{1,3}\\%");
                if (isMatch && editManualCMYK) {
                    try {
                        //Get index for elements
                        int one = s.indexOf("%");
                        int two = s.indexOf("%", one + 1);
                        int three = s.indexOf("%", two + 1);

                        int cyan = Integer.parseInt(s.substring(0, one));
                        int magenta = Integer.parseInt(s.substring(one + 3, two));
                        int yellow = Integer.parseInt(s.substring(two + 3, three));
                        int k = Integer.parseInt(s.substring(three + 3, s.length() - 1));

                        if (cyan >= 0 && cyan <= 100 && magenta >= 0 && magenta <= 100 && yellow >= 0 && yellow <= 100 && k >= 0 && k <= 100) {
                            int r = (int) Math.round(255 * (100.0 - cyan) / 100.0 * (100.0 - k) / 100.0);
                            int g = (int) Math.round(255 * (100.0 - magenta) / 100.0 * (100.0 - k) / 100.0);
                            int b = (int) Math.round(255 * (100.0 - yellow) / 100.0 * (100.0 - k) / 100.0);
                            colorForColorPanel = new Color(r, g, b);
                            setTextToHexTextField();
                            setTextToRgbTextField();
                            finalColor.setBackground(colorForColorPanel);
                            ColorPanel.posX = -5;
                            ColorPanel.posY = -5;
                            doneImage = false;
                            colorPanel.repaint();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    //Set RGB values to the rgbTextField
    private void setTextToRgbTextField(){
        rgbTextField.setText(colorForColorPanel.getRed() + "." + colorForColorPanel.getGreen() + "." + colorForColorPanel.getBlue());
    }

    private void setTextToRgbTextField(Color color){
        rgbTextField.setText(color.getRed() + "." + color.getGreen() + "." + color.getBlue());
    }

    //Convert RGB values to HEX values and set to the hexTextField
    private void setTextToHexTextField(){
        String temp;
        temp = Integer.toHexString(colorForColorPanel.getRed()).equals("0") ? "00" : Integer.toHexString(colorForColorPanel.getRed());
        temp += Integer.toHexString(colorForColorPanel.getGreen()).equals("0") ? "00" : Integer.toHexString(colorForColorPanel.getGreen());
        temp += Integer.toHexString(colorForColorPanel.getBlue()).equals("0")? "00" : Integer.toHexString(colorForColorPanel.getBlue());
        hexTextField.setText(temp);
    }

    private void setTextToHexTextField(Color color){
        String temp;
        temp = Integer.toHexString(color.getRed()).equals("0") ? "00" : Integer.toHexString(color.getRed());
        temp += Integer.toHexString(color.getGreen()).equals("0") ? "00" : Integer.toHexString(color.getGreen());
        temp += Integer.toHexString(color.getBlue()).equals("0")? "00" : Integer.toHexString(color.getBlue());
        hexTextField.setText(temp);
    }

    //Convert RGB values to CMYK values and set to the cmykTextField
    private void setTextToCMYKTextField() {
        int r = colorForColorPanel.getRed();
        int g = colorForColorPanel.getGreen();
        int b = colorForColorPanel.getBlue();
        if (r == 0 && g == 0 && b == 0){
            cmykTextField.setText("0%, 0%, 0%, 100%");
        } else {
            int c = r * 100 / 255;
            int m = g * 100 / 255;
            int y = b * 100 / 255;

            int k = 100 - Math.max(c, Math.max(m, y));

            int cyan = (100 - c - k);
            int magenta = (100 - m - k);
            int yellow = (100 - y - k);

            cmykTextField.setText(cyan + "%, " + magenta + "%, " + yellow + "%, " + k + "%");
        }
    }

    private void setTextToCMYKTextField(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == 0 && g == 0 && b == 0){
            cmykTextField.setText("0%, 0%, 0%, 100%");
        } else {
            int c = r * 100 / 255;
            int m = g * 100 / 255;
            int y = b * 100 / 255;

            int k = 100 - Math.max(c, Math.max(m, y));

            int cyan = (100 - c - k);
            int magenta = (100 - m - k);
            int yellow = (100 - y - k);

            cmykTextField.setText(cyan + "%, " + magenta + "%, " + yellow + "%, " + k + "%");
        }
    }

    private void initListeners() {
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        copyHexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(hexTextField.getText()), null);
            }
        });

        copyRgbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(rgbTextField.getText()), null);
            }
        });

        CopyCmykButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(cmykTextField.getText()), null);
            }
        });

        screenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenPixelFrame screenPixelFrame = new ScreenPixelFrame();
            }
        });

        //Listeners for colorPanel
        colorPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setFalse();
                colorPanel.posX = e.getX();
                colorPanel.posY = e.getY();
                colorByPosition();
                colorPanel.repaint();

            }
            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!doneImage) {
                    bufferedImage = new BufferedImage(colorPanel.getWidth(), colorPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                    colorPanel.paint(bufferedImage.getGraphics());
                    /*File file = new File("tempfile.jpg");
                    try {
                        ImageIO.write(bufferedImage, "jpg", file);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }*/
                    doneImage = true;
                }
                colorPanel.requestFocus();
            }

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        colorPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                setFalse();
                if (e.getKeyCode() == 37 && colorPanel.posX > 0) {
                    colorPanel.posX--;
                    colorByPosition();
                    colorPanel.repaint();
                }
                if (e.getKeyCode() == 38 && colorPanel.posY > 0) {
                    colorPanel.posY--;
                    colorByPosition();
                    colorPanel.repaint();
                }
                if (e.getKeyCode() == 39 && colorPanel.posX < colorPanel.getWidth()-1) {
                    colorPanel.posX++;
                    colorByPosition();
                    colorPanel.repaint();
                }
                if (e.getKeyCode() == 40 && colorPanel.posY < colorPanel.getHeight()-1) {
                    colorPanel.posY++;
                    colorByPosition();
                    colorPanel.repaint();
                }
            }
        });

        //Listener for rgbTextField
        rgbTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                editManualHEX = false;
                editManualRGB = true;
                editManualCMYK = false;
            }
        });

        //Listener for hexTextField
        hexTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                editManualHEX = true;
                editManualRGB = false;
                editManualCMYK = false;
            }
        });

        //Listener for cmykTextField
        cmykTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                editManualHEX = false;
                editManualRGB = false;
                editManualCMYK = true;
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                iconified = true;
            }

            @Override
            public void windowDeiconified(WindowEvent e) {}
        });
    }

    public void setFalse(){
        editManualHEX = false;
        editManualRGB = false;
        editManualCMYK = false;
    }

    public void colorByPosition(){
        positionColor = new Color(bufferedImage.getRGB(colorPanel.posX, colorPanel.posY));
        finalColor.setBackground(positionColor);
        setTextToHexTextField(positionColor);
        setTextToRgbTextField(positionColor);
        setTextToCMYKTextField(positionColor);
    }

    private void initLatestColor() {
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                try {
                    //Delete previous components inside latestColorsPanel
                    Component[] components = latestColorsPanel.getComponents();
                    for (int i = 0; i < components.length; i++) {
                        latestColorsPanel.remove(components[i]);
                    }
                    latestColorsPanel.revalidate();
                    latestColorsPanel.repaint();

                    File file = new File("latestColors.txt");
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Произошла ошибка", JOptionPane.ERROR_MESSAGE, null);
                        }
                    }
                    BufferedReader reader = new BufferedReader(new FileReader(file));

                    //Add only unique string to ArrayList from file latestColors.txt
                    ArrayList<String> array = new ArrayList<>();
                    for (Object s : reader.lines().toArray()) {
                        if (array.contains(s.toString())) {
                            //Skip
                        } else {
                            array.add(s.toString());
                        }
                    }

                    //For each color in the array create new JTextField
                    for (String s : array) {
                        JTextField colorField = new JTextField();
                        colorField.setEditable(false);
                        colorField.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                ColorPanel.posX = -5;
                                ColorPanel.posY = -5;
                                colorForColorPanel = Color.decode(s);
                                hiddenTextField.setText(s);
                                setFalse();
                                doneImage = false;
                            }
                        });
                        colorField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        colorField.setPreferredSize(new Dimension(20, 20));
                        colorField.setBackground(Color.decode(s));
                        latestColorsPanel.add(colorField);
                    }
                    reader.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Произошла ошибка", JOptionPane.ERROR_MESSAGE, null);
                }
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }
}
