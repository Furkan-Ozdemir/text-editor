
public class CharNode {
    private char Char;
    private CharNode next;

    public CharNode(char dataToAdd) {
        Char = dataToAdd;
        next = null;
    }

    public char getChar() {
        return Char;
    }

    public void setChar(char data) {
        this.Char = data;
    }

    public CharNode getNext() {
        return next;
    }

    public void setNext(CharNode next) {
        this.next = next;
    }
}