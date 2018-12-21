package colorpicker;

import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel {
    public static int posX;
    public static int posY;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 0;
        int y = 0;
        int R = ColorFrame.colorForColorPanel.getRed();
        int G = ColorFrame.colorForColorPanel.getGreen();
        int B = ColorFrame.colorForColorPanel.getBlue();
        int r = 255;
        int j = 255;
        int b = 255;
        for (int i = 0; i < 255; i++) {
            g.setColor(new Color(r, j, b));
            g.fillRect(x, y, 300, 1);
            r--;
            j--;
            b--;
            y++;
        }
        for (int i = 0; i < 22; i++) {
            g.setColor(new Color(r, j, b));
            g.fillRect(x, y, 300, 1);
            y++;
        }

        y = 0;
        x = this.getWidth();
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(R, G, B));
            g.fillRect(x, y, 1, this.getHeight());
            x--;
        }
        for (int i = 255; i >= 0; i--) {
            g.setColor(new Color(R, G, B, i));
            g.fillRect(x, y, 1, this.getHeight());
            x--;
            if (R > 0) {
                R--;
            }
            if (G > 0) {
                G--;
            }
            if (B > 0) {
                B--;
            }
        }

        if (ColorFrame.positionColor.getRed() > 150 && ColorFrame.positionColor.getGreen() > 150 && ColorFrame.positionColor.getBlue() > 150) {
            g.setColor(Color.BLACK);
        } else g.setColor(Color.WHITE);
        g.drawOval(posX - 5, posY - 5, 10, 10);
    }
}

