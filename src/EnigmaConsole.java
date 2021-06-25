
import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import enigma.console.TextAttributes;
import java.awt.Color;

public class EnigmaConsole {
    public static enigma.console.Console cn = Enigma.getConsole("CENG Editor", 100, 34, 16, 1); // col,row,fontsize,fonttype
    public static enigma.console.TextWindow cnt = cn.getTextWindow();
    public static TextAttributes att0 = new TextAttributes(Color.white, Color.black); // foreground, background color
    public static TextAttributes att1 = new TextAttributes(Color.black, Color.white);
    public TextMouseListener tmlis;
    public KeyListener klis;
    static int cursorx = 1, cursory = 2, lastCursorX = 0, selectionX, selectionY, endX, endY, lastCursorPosition = 0;
    static int initialLineNumber = 1;
    static int currentLine = 1, index;
    static String selectedString = "", cutString = "", copiedString = "", stringToFind = "", foundString = "",
            stringToReplace = "";

    // ------ Standard variables for keyboard and mouse 2 --------------------------
    public int mousepr; // mouse pressed?
    public int mousex, mousey; // mouse text coords.
    public int keypr; // key pressed?
    public int rkey; // key (for press/release)
    public int rkeymod; // key modifiers
    public int capslock = 0; // 0:off 1:on
    // -----------------------------------------------------------------------------

    EnigmaConsole() throws Exception { // --- Contructor

        Editor editor = new Editor();
        // ------ Standard code for keyboard and mouse 2 -------- Do not change -----
        tmlis = new TextMouseListener() {
            public void mouseClicked(TextMouseEvent arg0) {
            }

            public void mousePressed(TextMouseEvent arg0) {
                if (mousepr == 0) {
                    mousepr = 1;
                    mousex = arg0.getX();
                    mousey = arg0.getY();
                }
            }

            public void mouseReleased(TextMouseEvent arg0) {
            }
        };
        cn.getTextWindow().addTextMouseListener(tmlis);

        klis = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (keypr == 0) {
                    keypr = 1;
                    rkey = e.getKeyCode();
                    rkeymod = e.getModifiersEx();
                    if (rkey == KeyEvent.VK_CAPS_LOCK) {
                        if (capslock == 0)
                            capslock = 1;
                        else
                            capslock = 0;
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        };
        cn.getTextWindow().addKeyListener(klis);
        // --------------------------------------------------------------------------

        int curtype;
        curtype = cnt.getCursorType(); // default:2 (invisible) 0-1:visible
        cnt.setCursorType(0);
        cn.setTextAttributes(att0);

        /*
         * // console clear (white background) cn.setTextAttributes(att1); for(int x=0;
         * x<100*30-1; x++) { System.out.print(" "); }
         */
        // --- main game loop ---
        Editor.mll.addLine(); // ilk satır

        while (true) {
            // cnt.setCursorPosition(2,2);

            if (keypr == 1) { // if keyboard button pressed

                if (rkey == KeyEvent.VK_F1) {
                    selectionX = cursorx;
                    selectionY = cursory;
                    cn.setTextAttributes(att1);
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_F2) {
                    endX = cursorx;
                    endY = cursory;

                    cnt.setCursorPosition(selectionX, selectionY);

                    for (int i = selectionX; i < endX + 1; i++) {
                        selectedString = selectedString.concat(
                                Character.toString(MultiLinkedList.searchByIndex(initialLineNumber, i - 2).getChar()));
                        System.out.print(MultiLinkedList.searchByIndex(initialLineNumber, i - 2).getChar());
                    }
                    cn.setTextAttributes(att0);
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_F3) { // cut
                    for (int i = 0; i < selectedString.length(); i++) {
                        Editor.mll.delete(endY - 1, endX - 2);
                        cnt.setCursorPosition(cursorx, cursory);
                        System.out.print(" ");
                        endX--;
                        cursorx--;
                    }
                    cutString = selectedString;
                    selectedString = "";
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_F4) { // copy
                    copiedString = selectedString;
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_F5) {// paste
                    if (cutString.equals("")) {
                        // copylenmişi yapıştır
                        for (int i = 0; i < copiedString.length(); i++) {
                            Editor.mll.addChar(initialLineNumber, copiedString.charAt(i));
                            Editor.mll.display(++cursorx, cursory, 1); // yazdırıp sağa ilerliyor.
                        }
                        copiedString = "";
                    } else {
                        // cutlanmışı yapıştır
                        for (int i = 0; i < cutString.length(); i++) {
                            Editor.mll.addChar(initialLineNumber, cutString.charAt(i));
                            Editor.mll.display(++cursorx, cursory, 1);
                        }
                        cutString = "";
                    }
                    keypr = 0;

                }
                if (rkey == KeyEvent.VK_F6) { // find
                    // mll to stringi çağır eşit olan var mı diye bak
                    //

                    MultiLinkedList.find();

                    // cursory--;
                    // cursorx++;
                    // sc.close();
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_F7) {// replace
                    // iki kelimenin de aynı uzunlukta olması gerekiyor.
                    Scanner sc = new Scanner(System.in);
                    cnt.setCursorPosition(30, 30);
                    System.out.print("Enter string:");
                    stringToReplace = sc.nextLine();

                    for (int i = 0; i < selectedString.length(); i++) {

                        Editor.mll.delete(cursory - 1, cursorx - 2); // kelimeyi silecek sondan başlayarak
                        cnt.setCursorPosition(cursorx, cursory);
                        System.out.print(" ");
                        cursorx--;
                    }
                    for (int i = 0; i < selectedString.length(); i++) {
                        Editor.mll.addChar(initialLineNumber, stringToReplace.charAt(i));
                        cursorx++;
                        Editor.mll.display(cursorx, cursory, 1);

                    }
                    // cursorx++;
                    keypr = 0;
                    sc.close();

                }
                if (rkey == KeyEvent.VK_F8) {// next'
                    MultiLinkedList.next();
                    keypr = 0;

                }
                if (rkey == KeyEvent.VK_F9) {
                    keypr = 0;

                }
                if (rkey == KeyEvent.VK_F10) {
                    keypr = 0;

                }
                if (rkey == KeyEvent.VK_F11) {
                    Editor.fileReader("SavedFile.txt");
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_F12) {
                    Editor.fileWriter();
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_PAGE_UP) {
                    currentLine--;
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_HOME) {
                    cursorx = 2;
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_END) {
                    cursorx = Editor.mll.sizeChar_line(initialLineNumber) + 2;
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_PAGE_DOWN) {
                    currentLine++;
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_BACK_SPACE) {
                    // cursorı 2 kere sola çekiyor.
                    if (cursory >= 2) {
                        if (cursorx >= 2) {
                            // int cursorxHolder = cursorx;
                            // int cursoryHolder = cursory;
                            // Editor.mll.deleteLastChar(initialLineNumber);
                            Editor.mll.delete(cursory - 1, cursorx - 2);
                            cnt.setCursorPosition(cursorx, cursory);
                            System.out.print(" ");
                            // cursorx = 2;
                            // cursory = 2;
                            // Editor.mll.display(cursorx, cursory, keypr);
                            // cursorx = cursorxHolder;
                            // cursory = cursoryHolder;

                            // cnt.setCursorPosition(cursorx, cursory);
                            // System.out.print(" ");
                            cursorx--;
                        } else {
                            cursorx = lastCursorX;
                            cursory--;
                            initialLineNumber--;
                            // int cursorxHolder = cursorx;
                            // int cursoryHolder = cursory;
                            // Editor.mll.deleteLastChar(initialLineNumber);
                            Editor.mll.delete(cursory - 1, cursorx - 2);
                            cnt.setCursorPosition(cursorx, cursory);
                            System.out.print(" ");
                            // cursorx = 2;
                            // cursory = 2;
                            // Editor.mll.display(cursorx, cursory, keypr);
                            // cnt.setCursorPosition(cursorx, cursory);
                            // cursorx = cursorxHolder;
                            // cursory = cursoryHolder;

                            // cnt.setCursorPosition(cursorx, cursory);
                            // System.out.print(" ");
                        }
                    } else if (cursorx == 2 && cursory == 2) {
                    }
                    keypr = 0;
                }
                if (rkey == KeyEvent.VK_SPACE) {
                    Editor.mll.addChar(initialLineNumber, ' ');
                    cursorx++;
                }
                if (rkey == KeyEvent.VK_ENTER) {
                    lastCursorX = cursorx; // enter'a bastığındaki cursorx'i tutuyor. Silerken kullanıcaz
                    cursorx = 1;
                    cursory++;
                    // Editor.mll.addChar(initialLineNumber, '\n');
                    Editor.mll.addLine();
                    initialLineNumber++;
                    keypr = 0; // üstteki satırı tekrar yazdırmaması için
                }
                if (rkey == KeyEvent.VK_LEFT) {
                    if (cursorx >= 3)
                        cursorx--;
                    keypr = 0;
                    cnt.setCursorPosition(cursorx, cursory);
                }
                if (rkey == KeyEvent.VK_RIGHT) {
                    if (cursorx < 61)
                        cursorx++;
                    keypr = 0;

                    cnt.setCursorPosition(cursorx, cursory);
                }
                if (rkey == KeyEvent.VK_UP) {
                    initialLineNumber--;
                    if (cursory >= 2)
                        cursory--;
                    keypr = 0;

                    cnt.setCursorPosition(cursorx, cursory);
                }
                if (rkey == KeyEvent.VK_DOWN) {
                    initialLineNumber++;
                    if (cursory < 22)
                        cursory++;
                    keypr = 0;

                    cnt.setCursorPosition(cursorx, cursory);
                }

                if (rkey == 20) { // buraya kabul edilmeyen tuşlar eklenecek. başka bir yol bulunana kadar
                    keypr = 0;
                }
                if (cursorx > 61) { // satır sonuna ulaştığı için bir alt satıra geçiriyor.
                    Editor.mll.addLine();
                    initialLineNumber++;
                    cursory++;
                    cursorx = 1;
                }

                char rckey = (char) rkey;

                // left right up down
                if (rckey == '%' || rckey == '\'' || rckey == '&' || rckey == '(') { // test without using VK (Virtual
                                                                                     // Keycode)
                    // cnt.setCursorPosition(px, py);
                    // System.out.print('P');
                } else {

                    // cnt.setCursorPosition(cursorx, cursory);

                    if (rckey >= '0' && rckey <= '9') {
                        // System.out.print(rckey);
                        Editor.mll.addChar(initialLineNumber, rckey);
                        cursorx++;

                        // cnt.setCursorPosition(cursorx,cursory);
                        // Editor.mll.display();
                    }
                    if (rckey >= 'A' && rckey <= 'Z') {
                        if (((rkeymod & KeyEvent.SHIFT_DOWN_MASK) > 0) || capslock == 1) {
                            // System.out.print(rckey);
                            Editor.mll.addChar(initialLineNumber, rckey);
                            cursorx++;

                            // cnt.setCursorPosition(cursorx,cursory);
                            // Editor.mll.display();
                        } else {
                            // System.out.print((char)(rckey+32));
                            Editor.mll.addChar(initialLineNumber, (char) (rckey + 32));
                            cursorx++;
                            // cnt.setCursorPosition(cursorx,cursory);
                            // Editor.mll.display();
                        }
                    }
                    if ((rkeymod & KeyEvent.SHIFT_DOWN_MASK) == 0) {
                        if (rckey == '.' || rckey == ',' || rckey == '-') {
                            // System.out.print(rckey);
                            Editor.mll.addChar(initialLineNumber, rckey);
                            cursorx++;

                            // cnt.setCursorPosition(cursorx,cursory);
                            // Editor.mll.display();
                        }
                    } else {
                        if (rckey == '.') {
                            // System.out.print(':');
                            Editor.mll.addChar(initialLineNumber, ':');

                            cursorx++;
                            // cnt.setCursorPosition(cursorx,cursory);
                            // Editor.mll.display();
                        }
                        if (rckey == ',') {
                            // System.out.print(';');
                            Editor.mll.addChar(initialLineNumber, ';');

                            cursorx++;
                            // cnt.setCursorPosition(cursorx,cursory);
                            // Editor.mll.display();
                        }

                    }

                }

                // cnt.setCursorPosition(15, 15);
                // System.out.print(Editor.mll.sizeChar());
                // System.out.print("cursorx" + cursorx + "cursory" + cursory);
                Editor.mll.display(cursorx, cursory, keypr);
                keypr = 0; // last action
                cnt.setCursorPosition(cursorx, cursory);

            }

            Thread.sleep(30);
        } // end of game loop

    }
}
