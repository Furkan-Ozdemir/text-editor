import java.util.Arrays;

import enigma.core.Enigma;

public class MultiLinkedList {
	static LineNode head; // static'e çevir
	static int LineNum, positionIndex, nextIndex = 49;

	static int cursorPositions[] = new int[50];

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
			if (head == null) {
			}
			// System.out.println("linked list is empty");
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
			// EnigmaConsole.cnt.setCursorPosition(x, y);
			// System.out.print("x");
		}
	}

	// public void display(int x, int y, int keypr, int currentLine) {
	// if (keypr == 1) {
	// if (head == null) {
	// } else {
	// LineNode temp = null;
	// CharNode temp2 = null;
	// if (Editor.mll.sizeLine() <= 20) {
	// temp = head;
	// for (int i = 0; i < Editor.mll.sizeLine(); i++) {
	// temp2 = temp.getRight();
	// while (temp2 != null) {
	// EnigmaConsole.cnt.setCursorPosition(x, y);
	// System.out.print(temp2.getChar());
	// temp2 = temp2.getNext();
	// }
	// temp = temp.getDown();
	// }
	// } else {
	// temp = head;
	// do {
	// temp.getDown();
	// } while (temp.getLineNum() < currentLine);
	//
	// for (int i = 0; i < 20; i++) {
	// temp2 = temp.getRight();
	// while (temp2 != null) {
	// EnigmaConsole.cnt.setCursorPosition(x, y);
	// System.out.print(temp2.getChar());
	// temp2 = temp2.getNext();
	// }
	// temp = temp.getDown();
	// }
	// }
	// }
	// } else {
	// }
	//
	// }

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

	///////////////////// daha hazır değil, burası düzenlenecek
	///////////////////// //////////////////////////
	// public int addToNode(int initialLineNum, int index, Object item) {
	// int indexCounter = 0;
	//
	// if (head == null) {
	// } else {
	// LineNode temp = head;
	//
	// do {
	// temp.getDown();
	// } while (temp.getLineNum() < initialLineNum);
	//
	// }
	// return indexCounter;
	// }
	///////////////////// daha hazır değil, burası düzenlenecek
	///////////////////// //////////////////////////

	public void delete(int row, int column) {
		LineNode temporaryLine, temporaryLine2;
		CharNode temporaryCharacter;
		CharNode previousCharacter;
		CharNode temp2;

		if (head != null) {
			temporaryLine = head;
			temporaryCharacter = null;
			previousCharacter = null;
			temp2 = temporaryLine.getRight();
			// column 0 ise demekki en baştaki karakter oluyor
			if (column == 0) { // x=2 , previousChar = satırın en sağı olcak
				// previousCharacter = temporaryLine.getRight();

				while (temp2.getNext() != null) {
					previousCharacter = temp2;
					temp2 = temp2.getNext();
				}

				// temporaryCharacter = temporaryLine.getDown().getRight();

				while (temporaryLine.getLineNum() < row) {
					temporaryLine = temporaryLine.getDown();
				}
				temporaryLine.setRight(null);
				return;
			}

			while (temporaryLine.getLineNum() < row) {
				temporaryLine = temporaryLine.getDown();
			}

			temporaryCharacter = temporaryLine.getRight();

			if (temporaryCharacter != null) {

				for (int i = 0; i < column; i++) {
					previousCharacter = temporaryCharacter;
					temporaryCharacter = temporaryCharacter.getNext();
				}

			}
			if (previousCharacter != null)
				previousCharacter.setNext(temporaryCharacter.getNext());
		}
	}

	// public void deleteLastChar(int LineNum) // not done yet!
	// {
	// if (head == null)
	// System.out.println("linked list is empty");
	// else {
	// LineNode line = head;
	// line.setLineNum(LineNum); // needs a care
	// CharNode previous = null;

	// while (line != null) {
	// if (LineNum == line.getLineNum()) {
	// CharNode character = line.getRight();
	// if (character == null) {
	// } else {
	// while (character.getNext() != null) {
	// previous = character;
	// character = character.getNext();
	// }
	// if (previous != null)
	// previous.setNext(character.getNext());
	// else {
	// }
	// }
	// break;
	// }
	// }
	// }
	// }
	public static CharNode searchByIndex(int row, int column) {
		LineNode temporaryLine;
		CharNode temporaryChar;

		temporaryLine = head;

		while (temporaryLine.getLineNum() < row) {
			temporaryLine = temporaryLine.getDown();
		}

		temporaryChar = temporaryLine.getRight();

		for (int i = 0; i < column; i++) {
			temporaryChar = temporaryChar.getNext();
		}
		return temporaryChar;
	}

	public static void find() {
		String strArray[] = mllToString();
		int index = 0;
		for (int i = 0; i < strArray.length; i++) {
			if (strArray[i].contains(EnigmaConsole.selectedString)) {
				// gidip highlight etsin
				while (index != -1) {

					index = strArray[i].indexOf(EnigmaConsole.selectedString, index);
					cursorPositions[positionIndex++] = index + 2; // bulunan kelimelerin cursor konumunu atıyorum ki
																	// nextte kullanabileyim
																	// doğru cursor pozisyonu index+1 konumunda
					if (index != -1) {

						index++;

						// int temp = index+2-2;
						EnigmaConsole.cn.setTextAttributes(EnigmaConsole.att1);
						for (int j = 0; j < EnigmaConsole.selectedString.length(); j++) {
							EnigmaConsole.cnt.setCursorPosition(index + 1 + j, EnigmaConsole.cursory); // cnt.setCursorPosition(index+2+j,cursory-1);
							System.out.print(MultiLinkedList.searchByIndex(EnigmaConsole.cursory - 1, index + 1 - 2 + j)
									.getChar());
							// EnigmaConsole.foundString = EnigmaConsole.foundString
							// .concat(Character.toString(MultiLinkedList
							// .searchByIndex(EnigmaConsole.cursory - 1, index + 1 - 2 + j).getChar()));
						}
						EnigmaConsole.cn.setTextAttributes(EnigmaConsole.att0);
						// System.out.print("X");
					}
				}
				break;
			}
		}
		// while (cursorPositions[nextIndex] != 0) {
		// nextIndex++;
		// }
		// nextIndex--;
	}

	public static void next() {
		/*
		 * eğer print i yorumdan çıkarırsan çalıştığını anlıyorsun her bulduğu yere
		 * yazdırarak ilerliyor bastıkça ama printi yoruma alınca çalıştığı belli
		 * olmuyor çünkü enigmaconsolda loop un en sonundaki setcursorposition cursorı
		 * en son yazılan yere getiriyor. next e bastıkça replace edilebilir. replace
		 * edilecek stringi tutarız f7 ye , burdan var olan karakteri silip replace
		 * edilecek karakteri addChar yaparız
		 */
		// if (cursorPositions[nextIndex] != 0) { // sıfırsa oraya eleman atılmamış yani

		// EnigmaConsole.cnt.setCursorPosition(cursorPositions[nextIndex],
		// EnigmaConsole.cursory);
		// // System.out.print("x");
		// Editor.mll.delete(EnigmaConsole.cursory - 1, cursorPositions[nextIndex] - 2);
		// EnigmaConsole.cnt.setCursorPosition(cursorPositions[nextIndex],
		// EnigmaConsole.cursory);
		// System.out.print(EnigmaConsole.stringToReplace);

		// for (int i = 0; i < EnigmaConsole.stringToReplace.length(); i++) {
		// Editor.mll.addChar(EnigmaConsole.initialLineNumber,
		// EnigmaConsole.stringToReplace.charAt(i));
		// }

		// nextIndex++;
		// } else
		// return;

		int temp = 0;
		for (int i = 0; i < cursorPositions.length; i++) { // indexleri küçükten
			// büyüğe sortluyor delete'in düzgün
			// çalışması için
			for (int j = i + 1; j < cursorPositions.length; j++) {
				if (cursorPositions[i] > cursorPositions[j]) {
					temp = cursorPositions[i];
					cursorPositions[i] = cursorPositions[j];
					cursorPositions[j] = temp;
				}
			}
		}
		for (int i = 0; i < EnigmaConsole.stringToReplace.length(); i++) {

			EnigmaConsole.cnt.setCursorPosition(cursorPositions[nextIndex] - i, EnigmaConsole.cursory);
			Editor.mll.delete(EnigmaConsole.cursory - 1, cursorPositions[nextIndex] - 2 - i);
		}

		EnigmaConsole.cnt.setCursorPosition(cursorPositions[nextIndex], EnigmaConsole.cursory);
		System.out.print(EnigmaConsole.stringToReplace);

		for (int i = 0; i < EnigmaConsole.stringToReplace.length(); i++) {
			Editor.mll.addChar(EnigmaConsole.initialLineNumber, EnigmaConsole.stringToReplace.charAt(i));
		}
		nextIndex--;

	}

	public static int countOccurrences(String str, String word) {
		// split the string by spaces in a
		String a[] = str.split(" ");

		// search for pattern in a
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			// if match found increase count
			if (word.equals(a[i]))
				count++;
		}

		return count;
	}
}