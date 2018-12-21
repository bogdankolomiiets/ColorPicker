package colorpicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AboutFrame extends JFrame {
    void createAboutFrame(){
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-350, Toolkit.getDefaultToolkit().getScreenSize().height/2-250);
        super.setSize(700, 350);
        super.setResizable(false);
        super.setLayout(new GridLayout());
        super.setUndecorated(true);
        initComponents();
        super.setVisible(true);
    }

    private void initComponents(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#005a8e"));
        panel.setLayout(null);
        panel.setVisible(true);

        JLabel closeLabel = new JLabel();
        closeLabel.setText("X");
        closeLabel.setFont(new Font("Arial", Font.BOLD, 25));
        closeLabel.setVisible(true);
        JPanel panelForCloseButton= new JPanel();
        panelForCloseButton.setBackground(Color.decode("#d60411"));
        panelForCloseButton.setLayout(new GridBagLayout());
        panelForCloseButton.add(closeLabel);
        panelForCloseButton.setBounds(660, 0, 40, 27);
        panelForCloseButton.setVisible(true);
        panelForCloseButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton()==1){
                    AboutFrame.super.setVisible(false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        JTextArea aboutArea = new JTextArea();
        aboutArea.setText("Автор: Богдан Коломиец\n\n" +
                "Email: bogdan.kolomiiets@gmail.com\n\n" +
                "Это моя вторая программа на языке программирования java. " +
                "Если она Вам нравиться - пользуйтесь на здоровье. ))");
        aboutArea.setBackground(Color.decode("#005a8e"));
        aboutArea.setForeground(Color.decode("#e17800"));
        aboutArea.setLineWrap(true);
        aboutArea.setWrapStyleWord(true);
        aboutArea.setEditable(false);
        aboutArea.setFont(new Font("Arial", Font.BOLD, 30));
        aboutArea.setBounds(50, 20, 600, 250);

        JTextField textField = new JTextField();
        textField.setText("2018 год");
        textField.setBackground(Color.decode("#005a8e"));
        textField.setForeground(Color.decode("#e17800"));
        textField.setEditable(false);
        textField.setBorder(null);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.BOLD, 30));
        textField.setBounds(50, 300, 600, 40);


        panel.add(panelForCloseButton);
        panel.add(aboutArea);
        panel.add(textField);
        super.add(panel);

    }
}
