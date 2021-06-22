

import enigma.console.TextAttributes;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Editor {
    private static int x = 0, y = 0;
    public static MultiLinkedList mll = new MultiLinkedList();

    Editor() throws IOException {
        Editor.printBorder();
        Editor.printInfo();
    }

    public static void printBorder() {
        EnigmaConsole.cn.setTextAttributes(new TextAttributes(Color.LIGHT_GRAY, Color.LIGHT_GRAY));
        for (int i = 0; i < 64; i++) {
            EnigmaConsole.cn.getTextWindow().setCursorPosition(x, y);
            System.out.print("-");
            x++;
        }
        x--;
        y++;
        for (int i = 0; i < 23; i++) {
            EnigmaConsole.cn.getTextWindow().setCursorPosition(x, y);
            System.out.println("|");
            y++;
        }
        x = 0;
        y = 1;
        for (int i = 0; i < 23; i++) {
            EnigmaConsole.cn.getTextWindow().setCursorPosition(x, y);
            System.out.println("|");
            y++;
        }
        y--;
        for (int i = 0; i < 64; i++) {
            EnigmaConsole.cn.getTextWindow().setCursorPosition(x, y);
            System.out.println("-");
            x++;
        }
        EnigmaConsole.cn.setTextAttributes(new TextAttributes(Color.WHITE, Color.BLACK));
        x = 0;
        y = 0;
    }

    public static void printInfo() {
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 0);
        System.out.println("F1:  Selection start");
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 1);
        System.out.println("F2:  Selection end");
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 2);
        System.out.println("F3:  Cut");
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 3);
        System.out.println("F4:  Copy");
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 4);
        System.out.println("F5:  Paste");
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 5);
        System.out.println("F6:  Find");
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 6);
        System.out.println("F7:  Replace");
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 7);
        System.out.println("F8:  Next");
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 8);
        System.out.println("F9:  Align Left");
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 9);
        System.out.println("F10: Justify");
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 10);
        System.out.println("F11: Load");
        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 11);
        System.out.println("F12: Save");

        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 14);
        System.out.println("Mode: ");

        EnigmaConsole.cn.getTextWindow().setCursorPosition(66, 16);
        System.out.println("Alignment: ");
    }

    public static void fileReader(String fileName) throws IOException {
        BufferedReader inputStream = null;
        int tempCursorX = 1, tempLineNumber = EnigmaConsole.initialLineNumber;
        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            String line;
            x = 2;
            y = 2;

            while ((line = inputStream.readLine()) != null) {
                EnigmaConsole.cn.getTextWindow().setCursorPosition(x, y);
                System.out.println(line);

                for (int i = 0; i < line.length(); i++) { // tüm satırı karakter karakter mll ye ekliyor
                    Editor.mll.addChar(EnigmaConsole.initialLineNumber, line.charAt(i));
                    if (tempLineNumber != EnigmaConsole.initialLineNumber) { // enter'a basarak alt satıra geçmişse
                        tempCursorX = 1;
                    }
                    if (tempCursorX < 61) {
                        tempCursorX += i;
                    } else {// alt satıra geçmiş ama yazıcak yer kalmadı
                        tempCursorX = 1;
                    }
                }
                y++;
                Editor.mll.addLine();
                EnigmaConsole.initialLineNumber++; // alt satıra geçiyor.
            }
        } catch (FileNotFoundException e) {
            System.out.println("File '" + fileName + "' not found");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            EnigmaConsole.cursory = EnigmaConsole.initialLineNumber;
            EnigmaConsole.cursorx = tempCursorX + 1;
            EnigmaConsole.cnt.setCursorPosition(EnigmaConsole.cursorx, EnigmaConsole.cursory);
            // System.out.print("XX");
        }
    }

    public static void fileWriter() {
        try {
          
            FileWriter fileWritter = new FileWriter("SavedFile.txt");
            BufferedWriter bw = new BufferedWriter(fileWritter);
            String[] arr = MultiLinkedList.mllToString();
            for (int i = 1; i < arr.length; i++) {
                if(!arr[i].equals(""))
                    bw.write(arr[i]+"\n");
                
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearConsole() {
        // console clear (white background)
        EnigmaConsole.cn.setTextAttributes(EnigmaConsole.att1);
        for (int x = 0; x < 100 * 30 - 1; x++) {
            System.out.print(" ");
        }
    }
}