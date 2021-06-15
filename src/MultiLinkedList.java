
public class MultiLinkedList {
	static LineNode head; // static'e çevir
	static int LineNum;

	MultiLinkedList() {
		LineNum = 0;
	}

	public void addLine() {
		LineNum++;
		LineNode temp;
		if (head == null) {
			temp = new LineNode(LineNum);
			head = temp;
		} else {
			temp = head;
			while (temp.getDown() != null)
				temp = temp.getDown();
			LineNode newnode = new LineNode(LineNum);
			temp.setDown(newnode);
		}
	}

	public void addChar(int Line, char Word) {
		if (head == null) {
		} else {
			LineNode temp = head;
			while (temp != null) {
				if (Line > Editor.mll.sizeLine()) {
					for (int i = 0; i < Line - Editor.mll.sizeLine(); i++) {
						Editor.mll.addLine();
					}
				}
				if (Line == temp.getLineNum()) {
					CharNode temp2 = temp.getRight();
					if (temp2 == null) {
						temp2 = new CharNode(Word);
						temp.setRight(temp2);
					} else {
						while (temp2.getNext() != null)
							temp2 = temp2.getNext();
						CharNode newnode = new CharNode(Word);
						temp2.setNext(newnode);
					}
				}

				temp = temp.getDown();
			}
		}
	}

	public int sizeLine() {
		int count = 0;
		if (head == null)
			System.out.println("linked list is empty");
		else {
			LineNode temp = head;
			while (temp != null) {
				count++;
				temp = temp.getDown();
			}
		}
		return count;
	}

	public int sizeChar() {
		int count = 0;
		if (head == null) {
		} else {
			LineNode temp = head;
			while (temp != null) {
				CharNode temp2 = temp.getRight();
				while (temp2 != null) {
					count++;
					temp2 = temp2.getNext();
				}
				temp = temp.getDown();
			}
		}
		return count;
	}

	public int sizeChar_line(int initialLineNum) {
		int count = 0;
		if (head == null) {
		} else {
			LineNode temp = head;
			do {
				temp.getDown();
			} while (temp.getLineNum() < initialLineNum);
			CharNode temp2 = null;
			do {
				temp2 = temp.getRight();
				while (temp2 != null) {
					count++;
					temp2 = temp2.getNext();
				}
			} while (temp2 != null);
		}
		return count;
	}

	 public void display(int x, int y, int keypr) {
	        if (keypr == 1) {
	            if (head == null) {}
//	                System.out.println("linked list is empty");
	            else {
	                LineNode temp = head;
	                while (temp != null) {
	                    CharNode temp2 = temp.getRight();
	                    while (temp2 != null) {
	                       EnigmaConsole.cnt.setCursorPosition(x, y);
	                        System.out.print(temp2.getChar());
	                        temp2 = temp2.getNext();
	                    }
	                    temp = temp.getDown();
	                    // System.out.print("\n");
	                }
	            }
	        } else {
//	            EnigmaConsole.cnt.setCursorPosition(x, y);
	            // System.out.print("x");
	        }
	 }
	
//	public void display(int x, int y, int keypr, int currentLine) {
//		if (keypr == 1) {
//			if (head == null) {
//			} else {
//				LineNode temp = null;
//				CharNode temp2 = null;
//				if (Editor.mll.sizeLine() <= 20) {
//					temp = head;
//					for (int i = 0; i < Editor.mll.sizeLine(); i++) {
//						temp2 = temp.getRight();
//						while (temp2 != null) {
//							EnigmaConsole.cnt.setCursorPosition(x, y);
//							System.out.print(temp2.getChar());
//							temp2 = temp2.getNext();
//						}
//						temp = temp.getDown();
//					}
//				} else {
//					temp = head;
//					do {
//						temp.getDown();
//					} while (temp.getLineNum() < currentLine);
//
//					for (int i = 0; i < 20; i++) {
//						temp2 = temp.getRight();
//						while (temp2 != null) {
//							EnigmaConsole.cnt.setCursorPosition(x, y);
//							System.out.print(temp2.getChar());
//							temp2 = temp2.getNext();
//						}
//						temp = temp.getDown();
//					}
//				}
//			}
//		} else {
//		}
//
//	}

	public static String[] mllToString() {
		String[] arr = new String[21];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = "";
		}
		int index = 1;
		LineNode temp = head;
		while (temp != null) {
			CharNode temp2 = temp.getRight();
			while (temp2 != null) {
				arr[index] = arr[index].concat(Character.toString(temp2.getChar()));
				temp2 = temp2.getNext();
			}
			temp = temp.getDown();
			index++;
			// System.out.print("\n");
		}
		return arr;
	}
	
///////////////////// daha hazır değil, burası düzenlenecek //////////////////////////
//	public int addToNode(int initialLineNum, int index, Object item) {
//		int indexCounter = 0;
//
//		if (head == null) {
//		} else {
//			LineNode temp = head;
//
//			do {
//				temp.getDown();
//			} while (temp.getLineNum() < initialLineNum);
//
//		}
//		return indexCounter;
//	}
///////////////////// daha hazır değil, burası düzenlenecek //////////////////////////
	
	public void delete(int row, int column)
	{
		LineNode temporaryLine,temporaryLine2;
		CharNode temporaryCharacter;
		CharNode previousCharacter;
		CharNode temp2;
		
		if (head != null)
		{			
			temporaryLine = head;
			temporaryCharacter = null;
			previousCharacter = null;
			temp2=temporaryLine.getRight();		
				//column 0 ise demekki en baştaki karakter oluyor
			if(column == 0){ // x=2 , previousChar = satırın en sağı olcak
				// previousCharacter = temporaryLine.getRight();
				
				while(temp2.getNext() != null){
					previousCharacter = temp2;
					temp2=temp2.getNext();
				}
				
				// temporaryCharacter = temporaryLine.getDown().getRight();
				
				while(temporaryLine.getLineNum() < row)
			{
				temporaryLine = temporaryLine.getDown();
			}
				temporaryLine.setRight(null);
				return;
			}

			while(temporaryLine.getLineNum() < row)
			{
				temporaryLine = temporaryLine.getDown();
			}
			
			temporaryCharacter = temporaryLine.getRight();
			
			if (temporaryCharacter != null)
			{ 
				
				for (int i = 0; i < column; i++) 
				{
					previousCharacter = temporaryCharacter;
					temporaryCharacter = temporaryCharacter.getNext();
				}
				
			}
			if(previousCharacter != null)
				previousCharacter.setNext(temporaryCharacter.getNext());
		}
	}

	// public void deleteLastChar(int LineNum) // not done yet!
	// {
	// 	if (head == null)
	// 		System.out.println("linked list is empty");
	// 	else {
	// 		LineNode line = head;
	// 		line.setLineNum(LineNum); // needs a care
	// 		CharNode previous = null;

	// 		while (line != null) {
	// 			if (LineNum == line.getLineNum()) {
	// 				CharNode character = line.getRight();
	// 				if (character == null) {
	// 				} else {
	// 					while (character.getNext() != null) {
	// 						previous = character;
	// 						character = character.getNext();
	// 					}
	// 					if (previous != null)
	// 						previous.setNext(character.getNext());
	// 					else {
	// 					}
	// 				}
	// 				break;
	// 			}
	// 		}
	// 	}
	// }
	public static CharNode searchByIndex(int row, int column)
	{
		LineNode temporaryLine;
		CharNode temporaryChar;
		
		temporaryLine = head;
		
		while(temporaryLine.getLineNum() < row)
		{
			temporaryLine = temporaryLine.getDown();
		}
		
		temporaryChar = temporaryLine.getRight();
		
		for (int i = 0; i < column; i++) 
		{
			temporaryChar = temporaryChar.getNext();
		}
		return temporaryChar;
	}
}