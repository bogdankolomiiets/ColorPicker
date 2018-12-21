package colorpicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

class ScreenPixelFrame extends JPanel {

    JFrame frame = new JFrame();
    private BufferedImage bufferedImage;
    private Robot robot;
    private Color selectedColor;
    private static int mousePositionX;
    private static int mousePositionY;
    //Create a temp file
    private File file = new File("temp.jpg");

    //The constructor without parameters
    public ScreenPixelFrame() {
        frame.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
        frame.setUndecorated(true);
        getScreenImage();
        myListeners();
        super.setLayout(new BorderLayout());
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        //Draw screen capture inside a JPanel
        g.drawImage(bufferedImage, 0, 0, null);

        int screenSizeWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenSizeHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        Color pixColor = robot.getPixelColor(mousePositionX, mousePositionY);
        g.setColor(pixColor);
        //Set position for the colorField (rectangle)
        if (mousePositionX + 10 < 75 && mousePositionY + 10 > screenSizeHeight - 40) {
            g.fillRect(mousePositionX + 10, mousePositionY - 45, 60, 40);
            colorForDrawRectangle(pixColor, g);
            g.drawRect(mousePositionX + 10, mousePositionY - 45, 60, 40);
        } else if (mousePositionX + 10 > screenSizeWidth - 60 && mousePositionY + 10 < screenSizeHeight - 55) {
            g.fillRect(mousePositionX - 65, mousePositionY + 20, 60, 40);
            colorForDrawRectangle(pixColor, g);
            g.drawRect(mousePositionX - 65, mousePositionY + 20, 60, 40);
        } else if (mousePositionX + 10 > screenSizeWidth - 60 || mousePositionY + 10 > screenSizeHeight - 40) {
            g.fillRect(mousePositionX - 65, screenSizeHeight - 45, 60, 40);
            colorForDrawRectangle(pixColor, g);
            g.drawRect(mousePositionX - 65, screenSizeHeight - 45, 60, 40);
        } else {
            g.fillRect(mousePositionX + 10, mousePositionY + 10, 60, 40);
            colorForDrawRectangle(pixColor, g);
            g.drawRect(mousePositionX + 10, mousePositionY + 10, 60, 40);
        }
    }

    //Rectangle color inside method paintComponent()
    void colorForDrawRectangle(Color color, Graphics g){
        if (color.getRGB() < Color.DARK_GRAY.getRGB()) {
            g.setColor(Color.WHITE);
        } else g.setColor(Color.BLACK);
    }

    //Create listeners
    public void myListeners(){
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Delete the temp file
                file.delete();

                //Get the selected pixel color
                setColor(robot.getPixelColor(e.getX(), e.getY()));

                // Get the colors R, G, B from the selected color and set them to the string temp
                String temp;
                temp = Integer.toHexString(selectedColor.getRed()).equals("0") ? "#00" : "#" + Integer.toHexString(selectedColor.getRed());
                temp += Integer.toHexString(selectedColor.getGreen()).equals("0") ? "00" : Integer.toHexString(selectedColor.getGreen());
                temp += Integer.toHexString(selectedColor.getBlue()).equals("0")? "00" : Integer.toHexString(selectedColor.getBlue());

                //Transfer the pixel color to ColorFrame
                ColorFrame colorFrame = new ColorFrame(temp);
                //Close this frame
                frame.dispose();
            }
        });

        super.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //Get the position of the mouse to draw a rectangle
                mousePositionX = e.getX();
                mousePositionY = e.getY();
                //Repaint the new rectangle in a new position
                repaint();
            }
        });
    }

    //Get screen image
    public void getScreenImage(){
        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try{
            robot = new Robot();
            //set image to bufferedImage
            bufferedImage = robot.createScreenCapture(rectangle);
            //write image to temp file
            ImageIO.write(bufferedImage, "jpg", file);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Произошла ошибка", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    //Set color to field selectedColor
    void setColor(Color color){
        selectedColor = color;
    }
}
