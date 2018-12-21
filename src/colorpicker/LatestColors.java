package colorpicker;

import javax.swing.*;
import java.io.*;

public class LatestColors {
    LatestColors(String color) {

        long countLine = 0;
        //Create file latestColors.txt
        File file = new File("latestColors.txt");
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            //Get count lines from file latestColors.txt
            countLine = reader.lines().count();
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Произошла ошибка", JOptionPane.ERROR_MESSAGE, null);
        }
        try {
            if (countLine < 24) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                bufferedWriter.append(color);
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
            else {
                File tempFile = new File("temp.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile, true));
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String[] array = new String[24];
                int i = 0;
                for (Object s : reader.lines().toArray()) {
                    array[i] = s.toString();
                    i++;
                }
                for (int j = 1; j < array.length; j++) {
                    bufferedWriter.append(array[j]);
                    bufferedWriter.newLine();
                }
                bufferedWriter.append(color);
                bufferedWriter.newLine();

                //Close BufferedReader and BufferedWriter
                reader.close();
                bufferedWriter.close();

                file.delete();
                tempFile.renameTo(file);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Произошла ошибка", JOptionPane.ERROR_MESSAGE, null);
        }
    }
}
