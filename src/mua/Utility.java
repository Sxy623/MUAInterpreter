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
	
	// 比较运算
	// 返回值: Boolean 类型的 Variable
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
