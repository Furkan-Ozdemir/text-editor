

public class LineNode 
{
	private int LineNum;
    private LineNode down;
    private CharNode right;

    public LineNode(int dataToAdd)
    {
        LineNum = dataToAdd;
        down = null;
        right = null;
    }

    public int getLineNum() {return LineNum;}
    public void setLineNum(int data) {this.LineNum = data;}
    public LineNode getDown() {return down;}
    public void setDown(LineNode down) {this.down = down;}
    public CharNode getRight() {return right;}
    public void setRight(CharNode right) {this.right = right;}
}