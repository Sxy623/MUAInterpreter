package src.mua;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

enum Type { NUMBER, WORD, BOOLEAN, LIST }

class Variable {
	String content;
	Type type;
	
	public Variable(String content, Type type) {
		this.content = content.trim();
		this.type = type;
	}
}

public class Main {

	public static Scanner scan = new Scanner(System.in);
	
	// 全局变量空间
	public static Map<String, Variable> map = new HashMap<String, Variable>();
	
	public static void main(String[] args) {
		map.put("pi", new Variable("3.14159", Type.NUMBER));
		while (scan.hasNext()) {
			try {
				Interpreter.interpret(scan, map);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
