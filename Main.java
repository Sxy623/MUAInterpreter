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
	
	// 全局变量空间
	public static Map<String, Variable> map = new HashMap<String, Variable>();

	// 判断是否为 Word 字面量
	public static boolean isWordLiteral(String str) {
		return str.charAt(0) == '\"';
	}
	// 从 Word 字面量中得到 Word 变量的 content
	public static String getWord(String str) {
		return str.substring(1);
	}
	
	// 判断是否为 Number 字面量
	public static boolean isNumberLiteral(String str) {
		return str.charAt(0) == '-' || (str.charAt(0) >= '0' && str.charAt(0) <= '9');
	}
	// 从 Number 字面量中得到 Number 变量的 content
	public static String getNumber(String str) {
		return Double.valueOf(str).toString();
	}
	
	// 判断是否为 Boolean 字面量
	public static boolean isBooleanLiteral(String str) {
		return str.equals("true") || str.equals("false");
	}
	// 从 Boolean 字面量中得到 Boolean 变量的 content
	public static String getBoolean(String str) {
		return str;
	}
	
	// 判断是否为 List 字面量
	public static boolean isListLiteral(String str) {
		return str.charAt(0) == '[';
	}
	// 读到 List 字面量结束，返回 List 变量的 content
	public static String getList(String str) {
		if (str.contains("]")) {
			int index = str.lastIndexOf("]");
			return str.substring(1, index);
		}
		
		String tempStr = str.substring(1);
		String p = scan.next();
		while(!p.contains("]")) {
			tempStr += " " + p;
			p = scan.next();
		}
		int index = p.lastIndexOf("]");
		tempStr += " " + p.substring(0, index);
		return tempStr;
	}
	
	// 判断是否以冒号开头
	public static boolean hasColon(String str) {
		return str.charAt(0) == ':';
	}
	// 对冒号进行递归解析，取出对应变量
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
	
	// 判断类型
	// 返回值: Boolean类型的Variable
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
	
	public static Variable nextParameter(Scanner scan) {
		String p = scan.next();
		// 解析 Word 字面量
		if (isWordLiteral(p)) {
			return new Variable(getWord(p), Type.WORD);
		}
		// 解析 Number 字面量
		else if (isNumberLiteral(p)) {
			return new Variable(getNumber(p), Type.NUMBER);
		}
		// 解析 Boolean 字面量
		else if (isBooleanLiteral(p)) {
			return new Variable(getBoolean(p), Type.BOOLEAN);
		}
		// 解析 List 字面量
		else if (isListLiteral(p)) {
			return new Variable(getList(p), Type.LIST);
		}
		// 解析冒号
		else if (hasColon(p)) {
			return parseColon(p);
		}
		
		Variable p1, p2;
		
		switch(p) {
		case "thing":
			p1 = nextParameter(scan);
			Variable variable = map.get(p1.content);
			return variable;
		case "read":
			p = scan.next();
			return new Variable(p, Type.WORD);
		case "isname":
			p1 = nextParameter(scan);
			if (map.get(p1.content) == null) {
				return new Variable("false", Type.BOOLEAN);
			}
			else {
				return new Variable("true", Type.BOOLEAN);
			}
		// 算术运算
		case "add":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return addOp(p1, p2);
		case "sub":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return subOp(p1, p2);
		case "mul":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return mulOp(p1, p2);
		case "div":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return divOp(p1, p2);
		// 比较运算
		case "eq":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return eqOp(p1, p2);
		case "gt":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return gtOp(p1, p2);
		case "lt":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return ltOp(p1, p2);
		// 逻辑运算
		case "and":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return andOp(p1, p2);
		case "or":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return orOp(p1, p2);
		case "not":
			p1 = nextParameter(scan);
			return notOp(p1);
		case "readlist":
			p = scan.nextLine();  // 行末回车
			p = scan.nextLine();
			return new Variable(p, Type.LIST);
		// 判断类型
		case "isnumber":
			p1 = nextParameter(scan);
			return isNumber(p1);
		case "isword":
			p1 = nextParameter(scan);
			return isWord(p1);
		case "islist":
			p1 = nextParameter(scan);
			return isList(p1);
		case "isbool":
			p1 = nextParameter(scan);
			return isBool(p1);
		case "isempty":
			p1 = nextParameter(scan);
			return isEmpty(p1);
		}
		return null;
	}
	
	public static void interpret(Scanner scan) {
		Scanner tempScanner;
		while (scan.hasNext()) {
			String instruction = scan.next();
			Variable p1, p2;
			switch(instruction) {
			case "make":
				p1 = nextParameter(scan);
				p2 = nextParameter(scan);
				map.put(p1.content, p2);
				break;
			case "print":
				p1 = nextParameter(scan);
				System.out.println(p1.content);
				break;
			case "erase":
				p1 = nextParameter(scan);
				map.remove(p1.content);
				break;
			case "run":
				p1 = nextParameter(scan);
				tempScanner = new Scanner(p1.content);
				interpret(tempScanner);
				break;
			case "repeat":
				p1 = nextParameter(scan);
				p2 = nextParameter(scan);
				double num = Double.valueOf(p2.content);
				for (int i = 0; i < num - eps; i++) {
					tempScanner = new Scanner(p1.content);
					interpret(tempScanner);
				}
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		while (scan.hasNext()) {
			interpret(scan);
		}
	}
}