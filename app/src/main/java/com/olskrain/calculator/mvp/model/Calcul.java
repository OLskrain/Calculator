package com.olskrain.calculator.mvp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

/**
 * Created by Andrey Ievlev on 19,Март,2019
 */

public class Calcul {
    private boolean isArithmeticError = false;
    private String expression;

    public String ExpressionToRPN(String expr) { //переводим выражение в обратную польскую натацию
        this.expression = expr;
        StringBuilder current = new StringBuilder();
        Stack<String> stackOperation = new Stack<>();

        // подготавливаем строку
        String expression = expr.replace("(-", "(0-");
        if (expression.length() > 0) {
            if (expression.charAt(0) == '-' || expression.charAt(0) == '+') {
                expression = "0" + expression;
            }
        }

        int priority;
        for (int i = 0; i < expression.length(); i++) {
            priority = getPriority(Character.toString(expression.charAt(i)));

            if (priority == 0) {
                current.append(expression.charAt(i));
            }
            if (priority == 1) {
                stackOperation.push(Character.toString(expression.charAt(i)));
            }
            if (priority > 1) {
                current.append(' ');
                while (!stackOperation.empty()) {
                    if (getPriority(stackOperation.peek()) >= priority) {
                        current.append(stackOperation.pop());
                    } else break;
                }
                stackOperation.push(Character.toString(expression.charAt(i)));
            }
            if (priority == -1) {
                current.append(' ');
                while (!stackOperation.empty()) {
                    if (getPriority(stackOperation.peek()) != 1) {
                        current.append(stackOperation.pop());
                    } else break;
                }
                if (!stackOperation.empty()) {
                    stackOperation.pop();
                }
            }
        }

        while (!stackOperation.empty()) {
            current.append(stackOperation.pop());
        }
        return current.toString();
    }

    public String RPNToAnswer(String rpn) throws ArithmeticException { //из обратной польской натации вычисляем ответ
        StringBuilder operand = new StringBuilder();
        Stack<Double> stackRPN = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(Character.toString(rpn.charAt(i))) == 0) {
                while (rpn.charAt(i) != ' ' && getPriority(Character.toString(rpn.charAt(i))) == 0) {
                    operand.append(rpn.charAt(i++));
                    if (i == rpn.length()) {
                        break;
                    }
                }
                stackRPN.push(Double.parseDouble(operand.toString()));
                operand = new StringBuilder();
            }

            if (getPriority(Character.toString(rpn.charAt(i))) > 1) {
                double a = stackRPN.pop(), b = stackRPN.pop();
                if (rpn.charAt(i) == '+') {
                    stackRPN.push(b + a);
                }
                if (rpn.charAt(i) == '-') {
                    stackRPN.push(b - a);
                }
                if (rpn.charAt(i) == '*') {
                    stackRPN.push(b * a);
                }
                if (rpn.charAt(i) == '/') {
                    stackRPN.push(b / a);
                }
            }
        }

        String answer;
        if (!stackRPN.empty() && !isArithmeticError) {
            BigDecimal bd = new BigDecimal(stackRPN.pop()).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros();
            //TODO: разобраться с нулем
            answer = "" + bd.toPlainString();
            answer.replace("0.00000", "0");
        } else {
            answer = expression;
        }
        return answer;
    }

    private int getPriority(String token) { //определяем приоритет каждого символа в выражении
        switch (token) {
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 2;
            case "(":
                return 1;
            case ")":
                return -1;
            default:
                return 0;
        }
    }
}
