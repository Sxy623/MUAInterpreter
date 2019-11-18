import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Interpreter {
	
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
	public static String getList(String str, Scanner scan) {
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
			return new Variable(getList(p, scan), Type.LIST);
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
			return Utility.addOp(p1, p2);
		case "sub":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return Utility.subOp(p1, p2);
		case "mul":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return Utility.mulOp(p1, p2);
		case "div":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return Utility.divOp(p1, p2);
		// 比较运算
		case "eq":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return Utility.eqOp(p1, p2);
		case "gt":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return Utility.gtOp(p1, p2);
		case "lt":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return Utility.ltOp(p1, p2);
		// 逻辑运算
		case "and":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return Utility.andOp(p1, p2);
		case "or":
			p1 = nextParameter(scan);
			p2 = nextParameter(scan);
			return Utility.orOp(p1, p2);
		case "not":
			p1 = nextParameter(scan);
			return Utility.notOp(p1);
		case "readlist":
			p = scan.nextLine();  // 行末回车
			p = scan.nextLine();
			return new Variable(p, Type.LIST);
		// 判断类型
		case "isnumber":
			p1 = nextParameter(scan);
			return Utility.isNumber(p1);
		case "isword":
			p1 = nextParameter(scan);
			return Utility.isWord(p1);
		case "islist":
			p1 = nextParameter(scan);
			return Utility.isList(p1);
		case "isbool":
			p1 = nextParameter(scan);
			return Utility.isBool(p1);
		case "isempty":
			p1 = nextParameter(scan);
			return Utility.isEmpty(p1);
		// 数值计算
		case "random":
			p1 = nextParameter(scan);
			return Utility.random(p1);
		case "sqrt":
			p1 = nextParameter(scan);
			return Utility.sqrt(p1);
		case "int":
			p1 = nextParameter(scan);
			return Utility.intOp(p1);
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
				for (int i = 0; i < num - Utility.eps; i++) {
					tempScanner = new Scanner(p1.content);
					interpret(tempScanner);
				}
				break;
			}
		}
	}
}