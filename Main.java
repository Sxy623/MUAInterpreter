import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

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

	public static double eps = 1e-5;
	public static Scanner scan = new Scanner(System.in);
	// 变量空间
	public static Map<String, Variable> map = new HashMap<String, Variable>();

	// 判断是否为Word字面量
	public static boolean isWordLiteral(String str) {
		return str.charAt(0) == '\"';
	}
	
	// 从Word字面量中得到Word
	public static String getWord(String str) {
		return str.substring(1);
	}
	
	// 判断是否为Number字面量
	public static boolean isNumLiteral(String str) {
		return str.charAt(0) == '-' || (str.charAt(0) >= '0' && str.charAt(0) <= '9');
	}
	
	// 从Number字面量中得到Number
	public static String getNum(String str) {
		return Double.valueOf(str).toString();
	}
	
	public static boolean hasColon(String str) {
		return str.charAt(0) == ':';
	}
	
	public static Variable parseColon(String str) {
		String id = str.substring(1);
		if (hasColon(id)) {
			Variable variable = parseColon(id);
			id = variable.content;
		}
		return map.get(id);
	}
	
	// 算术运算
	// 返回值: Number类型的Variable
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
	
	// 比较运算
	// 返回值: Boolean类型的Variable
	public static Variable eqOp(Variable a, Variable b) {
		if (a.type == Type.NUMBER || b.type == Type.NUMBER) {
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
	// 返回值: Boolean类型的Variable
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
	
	public static Variable nextParameter() {
		String p = scan.next();
		if (isWordLiteral(p)) {
			return new Variable(getWord(p), Type.WORD);
		}
		else if (isNumLiteral(p)) {
			return new Variable(getNum(p), Type.NUMBER);
		}
		else if (hasColon(p)) {
			return parseColon(p);
		}
		
		Variable p1, p2;
		switch(p) {
		case "thing":
			p1 = nextParameter();
			Variable variable = map.get(p1.content);
			return variable;
		case "read":
			p = scan.next();
			return new Variable(p, Type.WORD);
		case "isname":
			p1 = nextParameter();
			if (map.get(p1.content) == null) {
				return new Variable("false", Type.BOOLEAN);
			}
			else {
				return new Variable("true", Type.BOOLEAN);
			}
		case "add":
			p1 = nextParameter();
			p2 = nextParameter();
			return addOp(p1, p2);
		case "sub":
			p1 = nextParameter();
			p2 = nextParameter();
			return subOp(p1, p2);
		case "mul":
			p1 = nextParameter();
			p2 = nextParameter();
			return mulOp(p1, p2);
		case "div":
			p1 = nextParameter();
			p2 = nextParameter();
			return divOp(p1, p2);
		case "eq":
			p1 = nextParameter();
			p2 = nextParameter();
			return eqOp(p1, p2);
		case "gt":
			p1 = nextParameter();
			p2 = nextParameter();
			return gtOp(p1, p2);
		case "lt":
			p1 = nextParameter();
			p2 = nextParameter();
			return ltOp(p1, p2);
		case "and":
			p1 = nextParameter();
			p2 = nextParameter();
			return andOp(p1, p2);
		case "or":
			p1 = nextParameter();
			p2 = nextParameter();
			return orOp(p1, p2);
		case "not":
			p1 = nextParameter();
			return notOp(p1);
		case "readlist":
			p = scan.nextLine();
			return new Variable(p, Type.LIST);
		}
		return null;
	}
	
	public static void main(String[] args) {
		while (scan.hasNext()) {
			String instruction = scan.next();
			Variable p1, p2;
			switch(instruction) {
			case "make":
				p1 = nextParameter();
				p2 = nextParameter();
				map.put(p1.content, p2);
				break;
			case "print":
				p1 = nextParameter();
				System.out.println(p1.content);
				break;
			case "erase":
				p1 = nextParameter();
				map.remove(p1.content);
				break;
			}
		}
	}
	
}