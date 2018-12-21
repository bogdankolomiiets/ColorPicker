package colorpicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SpectrumPanel extends JPanel {
    ArrayList<Color> arrayColors = new ArrayList<>();
    Color color;
    private int posY = 0;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //arrayColors.add(0, Color.BLACK);
        //arrayColors.add(1, Color.BLACK);

        int x = 0, y = 0;
        //Get colors from red to yellow
        for (int G = 0; G <= 255; G += 6) {
            g.fillRect(x, y, 50, 1);
            color = new Color(255, G, 0);
            g.setColor(color);
            arrayColors.add(y, color);
            y++;
        }
        //Get color from yellow to green
        for (int R = 255; R > 0; R -= 5) {
            g.fillRect(x, y, 50, 1);
            color = new Color(R, 255, 0);
            g.setColor(color);
            arrayColors.add(y, color);
            y++;
        }
        //Get color from green to light blue
        for (int B = 0; B <= 255; B += 6) {
            g.fillRect(x, y, 50, 1);
            color = new Color(0, 255, B);
            g.setColor(color);
            arrayColors.add(y, color);
            y++;
        }
        //Get color from light blue to blue
        for (int G = 255; G > 0; G -= 6) {
            g.fillRect(x, y, 50, 1);
            color = new Color(0, G, 255);
            g.setColor(color);
            arrayColors.add(y, color);
            y++;
        }
        //Get color from blue to violet
        for (int R = 0; R <= 255; R += 5) {
            g.fillRect(x, y, 50, 1);
            color = new Color(R, 0, 255);
            g.setColor(color);
            arrayColors.add(y, color);
            y++;
        }
        //Get color from violet to red
        for (int B = 255; B > 0; B -= 6) {
            g.fillRect(x, y, 50, 1);
            color = new Color(255, 0, B);
            g.setColor(color);
            arrayColors.add(y, color);
            y++;
        }
        g.setColor(Color.BLACK);
        g.fillRect(0, posY, 50, 1);

        setColorForColorPanel();
    }

    public SpectrumPanel() {
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SpectrumPanel.super.requestFocus();
                posY = e.getY();
                setColorForColorPanel();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ColorFrame.iconified = false;
            }
        });

        super.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                SpectrumPanel.super.requestFocus();
                if (e.getWheelRotation() < 0 && posY > 0) {
                    posY--;
                    setColorForColorPanel();
                    repaint();
                }
                if (e.getWheelRotation() > 0 && posY < 275) {
                    posY++;
                    setColorForColorPanel();
                    repaint();
                }
            }
        });
        super.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == 38 && posY > 0) {
                    posY--;
                    setColorForColorPanel();
                    repaint();
                }
                if (e.getKeyCode() == 40 && posY < 275) {
                    posY++;
                    setColorForColorPanel();
                    repaint();
                }
            }
        });
    }

    public void setColorForColorPanel() {
        // boolean iconified does not repaint when the window was minimized
        if (!ColorFrame.iconified) {
            ColorFrame.doneImage = false;
            if (ColorPanel.posX != -5 && ColorPanel.posY != -5) {
                ColorPanel.posX = -5;
                ColorPanel.posY = -5;
                ColorFrame.positionColor = Color.WHITE;
            }
        ColorFrame.colorForColorPanel = arrayColors.get(posY);
            ColorFrame.editManualRGB = false;
            ColorFrame.editManualHEX = false;
            ColorFrame.editManualCMYK = false;
            ColorFrame.hiddenTextField.setText(arrayColors.get(posY).toString());
        }
    }
}
