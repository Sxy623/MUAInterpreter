package src.mua;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
	
	public static final int MAX_LENGTH = 100;
	
	public static boolean isOp(char c) {
		return c == '+' ||  c == '-' ||  c == '*' ||  c == '/' ||  c == '%' ||  c == '(' || c == ')';
	}
	
	public static int priority(String s) {
		switch (s) {
		case "(":
			return 0;
		case "+":
		case "-":
			return 1;
		case "*":
		case "/":
		case "%":
			return 2;
		default:
			return 3;
		}
	}
	
	public static double process(double num1, double num2, String op) {
		switch (op) {
		case "+":
			return num1 + num2;
		case "-":
			return num1 - num2;
		case "*":
			return num1 * num2;
		case "/":
			return num1 / num2;
		case "%":
			return Math.round(num1) % Math.round(num2);
		default:
			break;
		}
		return 0;
	}

	public static double calcExpression(String s, Map<String, Variable> map) {
//		System.out.println("Input: " + s);
		String[] ops = new String[MAX_LENGTH];
		double[] nums = new double[MAX_LENGTH];
		int opsTop = 0, numsTop = 0;
		
		int numberLiteral = 0;
		String variableName = "";
		String functionName = "";
		boolean numberFlag = false;
		boolean variableFlag = false;
		boolean functionFlag = false;
		
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
//			System.out.println("Processing: " + ch);
//			for (int k = 0; k < opsTop; k++)
//				System.out.print(ops[k] + " ");
//			System.out.println();
//			for (int k = 0; k < numsTop; k++)
//				System.out.print(nums[k] + " ");
//			System.out.println();
			// 字面量
			if (Character.isDigit(ch)) {
				numberLiteral = numberLiteral * 10 + (int)(ch) - '0';
				numberFlag = true;
				continue;
			}
			// 变量
			if (ch == ':') {
				variableFlag = true;
				variableName = "";
				continue;
			}
			if (variableFlag && ch != ' ' && !isOp(ch)) {
				variableName += ch;
				continue;
			}
			// 函数
			if (!isOp(ch) && ch != ' ') {
				if (functionFlag == false) {
					functionName = "";
				}
				functionName += ch;
				functionFlag = true;
				continue;
			}
			// 字面量、变量、函数结束
			if (numberFlag) {
				nums[numsTop++] = numberLiteral;
				numberLiteral = 0;
				numberFlag = false;
			}
			if (variableFlag) {
				nums[numsTop++] = Double.parseDouble(map.get(variableName).content);
				variableFlag = false;
			}
			if (functionFlag) {
				ops[opsTop++] = functionName;
				functionFlag = false;
			}
			// 空白位
			if (Character.isWhitespace(ch)) {
				continue;
			}
			// 括号
			if (ch == '(') {
				ops[opsTop++] = String.valueOf(ch);
				continue;
			}
			if (ch == ')') {
				String op = ops[--opsTop];
				while (!op.equals("(")) {
					double num2 = nums[--numsTop];
					double num1 = nums[--numsTop];
					nums[numsTop++] = process(num1, num2, op);
					op = ops[--opsTop];
				}
				continue;
			}
			// 运算符
			while (opsTop != 0 && priority(String.valueOf(ch)) <= priority(ops[opsTop - 1])) {
				String op = ops[--opsTop];
				// 四则运算
				if (priority(op) <= 2) {
					double num2 = nums[--numsTop];
					double num1 = nums[--numsTop];
					nums[numsTop++] = process(num1, num2, op);
				}
				// 函数
				else {
					Variable f = map.get(functionName);
					String str;
					if (f != null) str = f.content;
					else str = Main.map.get(functionName).content;
					Scanner tempScanner = new Scanner(str);
					// 局部变量空间
					Map<String, Variable> localMap = new HashMap<String, Variable>();
					Variable p1 = Interpreter.nextParameter(tempScanner, map);
					Variable p2 = Interpreter.nextParameter(tempScanner, map);
					String noLeadingBlank = p1.content.replaceAll("^ +", "");
					int paraNum;
					if (noLeadingBlank.isEmpty())
						paraNum = 0;
					else {
						String[] parameters = noLeadingBlank.split(" ");
						paraNum = parameters.length;
						Variable x;
						// 参数绑定
						for (int j = paraNum - 1; j >= 0; j--) {
							x = new Variable(String.valueOf(nums[--numsTop]), Type.NUMBER);
							localMap.put(parameters[j], x);
						}
					}
					tempScanner = new Scanner(p2.content);
					Interpreter.interpret(tempScanner, localMap);
					nums[numsTop++] = Double.parseDouble(localMap.get("__output").content);
				}
			}
			ops[opsTop++] = String.valueOf(ch);
		}
		if (numberFlag) {
			nums[numsTop++] = numberLiteral;
			numberLiteral = 0;
			numberFlag = false;
		}
		if (variableFlag) {
			nums[numsTop++] = Double.parseDouble(map.get(variableName).content);
			variableFlag = false;
		}
		
		while (opsTop != 0) {
			String op = ops[--opsTop];
			// 四则运算
			if (priority(op) <= 2) {
				double num2 = nums[--numsTop];
				double num1 = nums[--numsTop];
				nums[numsTop++] = process(num1, num2, op);
			}
			// 函数
			else {
				Variable f = map.get(functionName);
				String str;
				if (f != null) str = f.content;
				else str = Main.map.get(functionName).content;
				Scanner tempScanner = new Scanner(str);
				// 局部变量空间
				Map<String, Variable> localMap = new HashMap<String, Variable>();
				Variable p1 = Interpreter.nextParameter(tempScanner, map);
				Variable p2 = Interpreter.nextParameter(tempScanner, map);
				String noLeadingBlank = p1.content.replaceAll("^ +", "");
				int paraNum;
				if (noLeadingBlank.isEmpty())
					paraNum = 0;
				else {
					String[] parameters = noLeadingBlank.split(" ");
					paraNum = parameters.length;
					Variable x;
					// 参数绑定
					for (int j = paraNum - 1; j >= 0; j--) {
						x = new Variable(String.valueOf(nums[--numsTop]), Type.NUMBER);
						localMap.put(parameters[j], x);
					}
				}
				tempScanner = new Scanner(p2.content);
				Interpreter.interpret(tempScanner, localMap);
				nums[numsTop++] = Double.parseDouble(localMap.get("__output").content);
			}
		}
		return nums[0];
	}
}
