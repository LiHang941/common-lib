package xyz.lihang.common.utils;


import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 四则混合运算表达式计算
 * <code>
 *    public static void main(String[] args) {
 *         Calculator cal = new Calculator();
 *         String str = cal.calculate("(a+b*1000000000000000000000000000000)");
 *         System.out.println("运算结果:" + str);
 *    }
 * </code>
 */
public class Calculator {
    /**
     * 运算符枚举
     *
     * @author Jinjichao
     */
    private enum Operator {
        ADD("+", 10), SUBTRACT("-", 10), MULTIPLY("*", 20), DIVIDE("/", 20),
        PARENTHESIS_LEFT("(", 100), PARENTHESIS_RIGHT(")", 100);
        private String operator;
        private int priority;

        Operator(String operator, int priority) {
            this.operator = operator;
            this.priority = priority;
        }
    }

    /**
     * 操作数枚举
     *
     * @author Jinjichao
     */
    private enum Operand {
        ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"),
        SEVEN("7"), EIGHT("8"), NINE("9"), ZERO("0"), POINT(".");
        private String operand;

        Operand(String operand) {
            this.operand = operand;
        }
    }

    /**
     * 获取字符串所对应的运算符枚举
     *
     * @param str
     * @return
     */
    private Operator getOperator(String str) {
        for (Operator op : Operator.values()) {
            if (str.equals(op.operator)) {
                return op;
            }
        }
        return null;
    }

    /**
     * 获取字符串所对应的操作数枚举
     *
     * @param str
     * @return
     */
    private Operand getOperand(String str) {
        for (Operand op : Operand.values()) {
            if (str.equals(op.operand)) {
                return op;
            }
        }
        return null;
    }

    /**
     * 第1步: 将运算表达式字符串分解为运算表达式List
     *
     * @param exp
     * @return
     */
    private List<String> resolveExpr(String exp) {
        List<String> list = new LinkedList<>();
        String temp = "";
        exp = exp.replace(" ", "");
        for (int i = 0; i < exp.length(); i++) {
            String str = exp.substring(i, i + 1);
            Operator op = getOperator(str);
            Operand od = getOperand(str);
            if (op != null) {
                if (!temp.isEmpty()) {
                    list.add(temp);
                    temp = "";
                }
                list.add(str);
            } else if (od != null) {
                temp += str;
            } else {
                throw new RuntimeException("表达式[" + str + "]非法! ");
            }
        }
        if (!temp.isEmpty()) {
            list.add(temp);
        }
        return list;
    }

    /**
     * 第2步: 将运算表达式List转换为逆波兰表达式List
     *
     * @param expList
     * @return
     */
    private List<String> dealExpr(List<String> expList) {
        if (expList == null) {
            return null;
        }

        List<String> list = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        for (String str : expList) {
            Operator op = getOperator(str.substring(0, 1));
            Operand od = getOperand(str.substring(0, 1));
            if (od != null) {
                //操作数直接入队列
                list.add(str);
            } else if (op != null) {
                if (Operator.PARENTHESIS_LEFT.equals(op)) {
                    //左括号入栈
                    stack.push(str);
                } else if (Operator.PARENTHESIS_RIGHT.equals(op)) {
                    //右括号: 循环将栈顶的运算符取出并存入队列，直到取出左括号
                    while (true) {
                        if (stack.empty()) {
                            throw new RuntimeException("缺少左括号! ");
                        } else if (Operator.PARENTHESIS_LEFT.operator.equals(stack.peek())) {
                            stack.pop();
                            break;
                        } else {
                            list.add(stack.pop());
                        }
                    }
                } else {
                    //非括号类运算符
                    if (!stack.empty()) {
                        Operator top_op = getOperator(stack.peek());
                        //当前运算符优先级大于栈顶运算符优先级，或者栈顶为左括号时，当前运算符直接入栈
                        if (op.priority > top_op.priority
                                || Operator.PARENTHESIS_LEFT.equals(top_op)) {
                            stack.push(str);
                        }
                        //否则，将栈顶的运算符取出并存入队列，然后将自己入栈
                        else {
                            list.add(stack.pop());
                            stack.push(str);
                        }
                    } else {
                        stack.push(str);
                    }
                }
            }
        }
        while (!stack.empty()) {
            String str = stack.peek();
            if (Operator.PARENTHESIS_LEFT.operator.equals(str)) {
                throw new RuntimeException("缺少右括号! ");
            } else {
                list.add(stack.pop());
            }
        }
        return list;
    }

    /**
     * 操作数运算
     *
     * @param x
     * @param y
     * @param op
     * @return
     */
    private String operation(String x, String y, Operator op) {
        BigDecimal a;
        BigDecimal b;
        try {
            a = new BigDecimal(x);
            b = new BigDecimal(y);
        } catch (NumberFormatException e) {
            throw new RuntimeException("操作数非法! ");
        }
        try {
            switch (op) {
                case ADD:
                    return String.valueOf(a.add(b));
                case SUBTRACT:
                    return String.valueOf(a.subtract(b));
                case MULTIPLY:
                    return String.valueOf(a.multiply(b));
                case DIVIDE:
                    return String.valueOf(a.divide(b));
                default:
                    throw new RuntimeException("计算失败");
            }
        }catch (ArithmeticException e){
            throw new RuntimeException("计算失败");
        }

    }

    /**
     * 第3步: 逆波兰表达式运算
     *
     * @param exp
     * @return
     */
    public String calculate(String exp) {
        List<String> expList = dealExpr(resolveExpr(exp));
        if (expList == null) {
            return null;
        }
        Stack<String> stack = new Stack<>();
        for (String str : expList) {
            Operator op = getOperator(str.substring(0, 1));
            Operand od = getOperand(str.substring(0, 1));
            if (od != null) {
                stack.push(str);
            } else if (op != null) {
                //目前仅针对二元运算符
                String x = "";
                String y = "";
                if (!stack.empty()) {
                    y = stack.pop();
                }
                if (!stack.empty()) {
                    x = stack.pop();
                }
                if (!x.isEmpty() && !x.isEmpty()) {
                    String result = operation(x, y, op);
                    if (result == null) {
                        return null;
                    }
                    stack.push(result);
                } else {
                    return null;
                }
            }
        }
        return stack.pop();
    }


}