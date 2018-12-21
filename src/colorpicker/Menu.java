package colorpicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Menu {
    JMenuBar jMenuBar = new JMenuBar();
    JMenu jMenuFile = new JMenu("Файл");
    JMenu jMenuThemes = new JMenu("Темы");
    JMenu jMenuAbout = new JMenu("Справка");
    JFrame frame;


    Menu(JPanel menuPanel, JFrame frame){
        this.frame = frame;
        createMenu();
        menuPanel.add(jMenuBar);
    }

    void createMenu(){
        createMenuItem();
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuAbout);
    }

    void createMenuItem(){
        UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo look : info){
            JMenuItem themeItem = new JMenuItem();
            themeItem.setText(look.getName());
            themeItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        UIManager.setLookAndFeel(look.getClassName());
                        SwingUtilities.updateComponentTreeUI(frame);
                        File file = new File("look.inf");
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                        bufferedWriter.write(look.getClassName());
                        bufferedWriter.close();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedLookAndFeelException e1) {
                        e1.printStackTrace();
                    } catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            });
            jMenuThemes.add(themeItem);
        }

        JMenuItem about = new JMenuItem("О программе");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutFrame aboutFrame = new AboutFrame();
                aboutFrame.createAboutFrame();
            }
        });
        JMenuItem closeItem = new JMenuItem();
        closeItem.setText("Выход");
        closeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        jMenuAbout.add(about);
        jMenuFile.add(jMenuThemes);
        jMenuFile.add(closeItem);
    }
}
