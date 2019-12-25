package src.mua;

public class Utility {
	
	public static double eps = 1e-5;
	
	public static int count(String str, char c) {
		int count = 0;
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) == c)
				count++;
		return count;
	}
	
	// 算术运算
	// 返回值: Number 类型的 Variable
	public static Variable addOp(Variable a, Variable b) {
		Double result = Double.valueOf(a.content) + Double.valueOf(b.content);
		return new Variable(result.toString(), Type.NUMBER);
	}
	public static Variable subOp(Variable a, Variable b) {
		Double result = Double.valueOf(a.content) - Double.valueOf(b.content);
		return new Variable(result.toString(), Type.NUMBER);
	}
	public static Variable mulOp(Variable a, Variable b) {
		Double result = Double.valueOf(a.content) * Double.valueOf(b.content);
		return new Variable(result.toString(), Type.NUMBER);
	}
	public static Variable divOp(Variable a, Variable b) {
		Double result = Double.valueOf(a.content) / Double.valueOf(b.content);
		return new Variable(result.toString(), Type.NUMBER);
	}
	public static Variable modOp(Variable a, Variable b) {
		long num1 = Math.round(Double.valueOf(a.content));
		long num2 = Math.round(Double.valueOf(b.content));
		Double result = Double.valueOf(num1 % num2);
		return new Variable(result.toString(), Type.NUMBER);
	}
	
	// 比较运算
	// 返回值: Boolean 类型的 Variable
	public static Variable eqOp(Variable a, Variable b) {
		if (a.type == Type.NUMBER || b.type == Type.NUMBER) {
			if (a.type == Type.LIST || a.type == Type.BOOLEAN) {
				return new Variable("false", Type.BOOLEAN);
			}
			if (b.type == Type.LIST || b.type == Type.BOOLEAN) {
				return new Variable("false", Type.BOOLEAN);
			}
			if (Math.abs(Double.valueOf(a.content) - Double.valueOf(b.content)) < eps) {
				return new Variable("true", Type.BOOLEAN);
			}
			else {
				return new Variable("false", Type.BOOLEAN);
			}
		}
		else {
			if (a.content.equals(b.content)) {
				return new Variable("true", Type.BOOLEAN);
			}
			else {
				return new Variable("false", Type.BOOLEAN);
			}
		}
	}
	public static Variable gtOp(Variable a, Variable b) {
		if (Double.valueOf(a.content) > Double.valueOf(b.content)) {
			return new Variable("true", Type.BOOLEAN);
		}
		else {
			return new Variable("false", Type.BOOLEAN);
		}
	}
	public static Variable ltOp(Variable a, Variable b) {
		if (Double.valueOf(a.content) < Double.valueOf(b.content)) {
			return new Variable("true", Type.BOOLEAN);
		}
		else {
			return new Variable("false", Type.BOOLEAN);
		}
	}
	
	// 逻辑运算
	// 返回值: Boolean 类型的 Variable
	public static Variable andOp(Variable a, Variable b) {
		Boolean result = Boolean.valueOf(a.content) && Boolean.valueOf(b.content);
		return new Variable(result.toString(), Type.BOOLEAN);
	}
	public static Variable orOp(Variable a, Variable b) {
		Boolean result = Boolean.valueOf(a.content) || Boolean.valueOf(b.content);
		return new Variable(result.toString(), Type.BOOLEAN);
	}
	public static Variable notOp(Variable a) {
		Boolean result = !Boolean.valueOf(a.content);
		return new Variable(result.toString(), Type.BOOLEAN);
	}
	
	// 判断类型
	// 返回值: Boolean 类型的 Variable
	public static Variable isNumber(Variable a) {
		Boolean result = (a.type == Type.NUMBER);
		return new Variable(result.toString(), Type.BOOLEAN);
	}
	public static Variable isWord(Variable a) {
		Boolean result = (a.type == Type.WORD);
		return new Variable(result.toString(), Type.BOOLEAN);
	}
	public static Variable isList(Variable a) {
		Boolean result = (a.type == Type.LIST);
		return new Variable(result.toString(), Type.BOOLEAN);
	}
	public static Variable isBool(Variable a) {
		Boolean result = (a.type == Type.BOOLEAN);
		return new Variable(result.toString(), Type.BOOLEAN);
	}
	public static Variable isEmpty(Variable a) {
		Boolean result = a.content.isEmpty();
		return new Variable(result.toString(), Type.BOOLEAN);
	}
	
	// 字表处理
	public static Variable wordOp(Variable a, Variable b) {
		return new Variable(a.content + b.content, Type.WORD);
	}
	public static Variable sentenceOp(Variable a, Variable b) {
		return new Variable(a.content + " " + b.content, Type.LIST);
	}
	public static Variable listOp(Variable a, Variable b) {
		String s1, s2;
		s1 = a.content;
		s2 = b.content;
		if (a.type == Type.LIST) {
			s1 = "[" + s1 + "]";
		}
		if (b.type == Type.LIST) {
			s2 = "[" + s2 + "]";
		}
		return new Variable(s1 + " " + s2, Type.LIST);
	}
	public static Variable joinOp(Variable a, Variable b) {
		String s1, s2;
		s1 = a.content;
		s2 = b.content;
		if (b.type == Type.LIST) {
			s2 = "[" + s2 + "]";
		}
		return new Variable(s1 + " " + s2, Type.LIST);
	}
	public static Variable firstOp(Variable a) {
		if (a.type == Type.WORD || a.type == Type.BOOLEAN || a.type == Type.NUMBER)
			return new Variable(String.valueOf(a.content.charAt(0)), Type.WORD);
		else if (a.content.charAt(0) != '[') {
			if (a.content.contains(" ")) {
				int firstBlank = a.content.indexOf(' ');
				return new Variable(a.content.substring(0, firstBlank), Type.WORD);
			}
			else return new Variable(a.content, Type.WORD);
		}
		else {
			int p = 1;
			int depth = 1;
			while (depth != 0) {
				if (a.content.charAt(p) == '[') depth++;
				if (a.content.charAt(p) == ']') depth--;
				p++;
			}
			return new Variable(a.content.substring(1, p-1), Type.LIST);
		}
	}
	public static Variable lastOp(Variable a) {
		int length = a.content.length();
		if (a.type == Type.WORD || a.type == Type.BOOLEAN || a.type == Type.NUMBER)
			return new Variable(String.valueOf(a.content.charAt(length - 1)), Type.WORD);
		else if (a.content.charAt(length - 1) != ']') {
			if (a.content.contains(" ")) {
				int lastBlank = a.content.lastIndexOf(' ');
				return new Variable(a.content.substring(lastBlank + 1), Type.WORD);
			}
			else return new Variable(a.content, Type.WORD);
		}
		else {
			int p = length - 2;
			int depth = 1;
			while (depth != 0) {
				if (a.content.charAt(p) == ']') depth++;
				if (a.content.charAt(p) == '[') depth--;
				p--;
			}
			return new Variable(a.content.substring(p + 2, length - 1), Type.LIST);
		}
	}
	public static Variable butfirstOp(Variable a) {
		if (a.type == Type.WORD || a.type == Type.BOOLEAN || a.type == Type.NUMBER)
			return new Variable(a.content.substring(1), Type.WORD);
		else if (a.content.charAt(0) != '[') {
			if (a.content.contains(" ")) {
				int firstBlank = a.content.indexOf(' ');
				return new Variable(a.content.substring(firstBlank + 1), Type.LIST);
			}
			else return new Variable("", Type.LIST);
		}
		else {
			int p = 0;
			int depth = 0;
			while (a.content.charAt(p) != ' ' || depth != 0) {
				if (a.content.charAt(p) == '[') depth++;
				if (a.content.charAt(p) == ']') depth--;
				p++;
			}
			return new Variable(a.content.substring(p + 1), Type.LIST);
		}
	}
	public static Variable butlastOp(Variable a) {		
		int length = a.content.length();
		if (a.type == Type.WORD || a.type == Type.BOOLEAN || a.type == Type.NUMBER)
			return new Variable(a.content.substring(0, length - 1), Type.WORD);
		else if (a.content.charAt(length - 1) != ']') {
			if (a.content.contains(" ")) {
				int lastBlank = a.content.lastIndexOf(' ');
				return new Variable(a.content.substring(0, lastBlank), Type.LIST);
			}
			else return new Variable("", Type.LIST);
		}
		else {
			int p = length - 1;
			int depth = 0;
			while (a.content.charAt(p) != ' ' || depth != 0) {
				if (a.content.charAt(p) == ']') depth++;
				if (a.content.charAt(p) == '[') depth--;
				p--;
			}
			return new Variable(a.content.substring(0, p), Type.LIST);
		}
	}
	
	// 数值计算
	// 返回值: Number 类型的 Variable
	public static Variable random(Variable a) {
		Double result = Math.random() * Double.valueOf(a.content);
		return new Variable(result.toString(), Type.NUMBER);
	}
	public static Variable sqrt(Variable a) {
		Double result = Math.sqrt(Double.valueOf(a.content));
		return new Variable(result.toString(), Type.NUMBER);
	}
	public static Variable intOp(Variable a) {
		Double result = Math.floor(Double.valueOf(a.content));
		return new Variable(result.toString(), Type.NUMBER);
	}
}
