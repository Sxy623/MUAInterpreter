package src.mua;

public class Calculator {
	
	public static final int MAX_LENGTH = 100;
	
	public static int priority(char c) {
		switch (c) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
		case '%':
			return 2;
		case '(':
			return 0;
		default:
			break;
		}
		return 0;
	}
	
	public static double process(double num1, double num2, char op) {
		switch (op) {
		case '+':
			return num1 + num2;
		case '-':
			return num1 - num2;
		case '*':
			return num1 * num2;
		case '/':
			return num1 / num2;
		case '%':
			return Math.round(num1) % Math.round(num2);
		default:
			break;
		}
		return 0;
	}

	public static double calcExpression(String s) {
		char[] ops = new char[MAX_LENGTH];
		double[] nums = new double[MAX_LENGTH];
		int opsTop = 0, numsTop = 0;
		
		int temp = 0;
		boolean flag = false;
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			// 跳过空白位
			if (Character.isWhitespace(ch)) {
				continue;
			}
			// 数字位
			if (Character.isDigit(ch)) {
				temp = temp * 10 + (int)(ch) - '0';
				flag = true;
			}
			// 运算符
			else {
				if (flag) {
					nums[numsTop++] = temp;
					temp = 0;
					flag = false;
				}
				if (ch == '(') {
					ops[opsTop++] = ch;
				}
				else if (ch == ')') {
					char op = ops[--opsTop];
					while (op != '(') {
						double num1 = nums[--numsTop];
						double num2 = nums[--numsTop];
						nums[numsTop++] = process(num1, num2, op);
						op = ops[--opsTop];
					}
				}
				// 符号栈为空
				else if (opsTop == 0) {
					ops[opsTop++] = ch;
				}
				// 遇到了优先级更低的运算符
				else if (priority(ch) <= priority(ops[opsTop - 1])) {
					char op = ops[--opsTop];
					double num1 = nums[--numsTop];
					double num2 = nums[--numsTop];
					nums[numsTop++] = process(num1, num2, op);
					ops[opsTop++] = ch;
				}
				// 遇到了优先级更高的运算符
				else {
					ops[opsTop++] = ch;
				}
			}
		}
		if (flag) {
			nums[numsTop++] = temp;
			temp = 0;
			flag = false;
		}
		while (opsTop != 0) {
			char op = ops[--opsTop];
			double num1 = nums[--numsTop];
			double num2 = nums[--numsTop];
			nums[numsTop++] = process(num1, num2, op);
		}
		return nums[0];
	}
}
