package src.mua;

import java.util.HashMap;
import java.util.Map;
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
	
	// 全局变量空间
	public static Map<String, Variable> map = new HashMap<String, Variable>();
	
	public static void main(String[] args) {		
		while (scan.hasNext()) {
			Interpreter.interpret(scan, map);
		}
	}
}