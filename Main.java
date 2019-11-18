import java.util.Scanner;

enum Type { NUMBER, WORD, BOOLEAN, LIST }

class Variable {
	String content;
	Type type;
	
	public Variable(String content, Type type) {
		this.content = content;
		this.type = type;
	}
}

public class Main {

	public static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		while (scan.hasNext()) {
			Interpreter.interpret(scan);
		}
	}
}