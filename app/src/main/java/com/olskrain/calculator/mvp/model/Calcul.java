package com.olskrain.calculator.mvp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

import timber.log.Timber;

/**
 * Created by Andrey Ievlev on 19,Март,2019
 */

public class Calcul {

    public String ExpressionToRPN(StringBuilder expr) {
        String current = "";
        Stack<Character> stack = new Stack<>();

        int priority;
        for (int i = 0; i < expr.length(); i++) {
            priority = getP(expr.charAt(i));

            if (priority == 0) {
                current += expr.charAt(i);
            }
            if (priority == 1) {
                stack.push(expr.charAt(i));
            }
            if (priority > 1) {
                current += ' ';
                while (!stack.empty()) {
                    if (getP(stack.peek()) >= priority) {
                        current += stack.pop();
                    } else break;
                }
                stack.push(expr.charAt(i));
            }
            if (priority == -1) {
                current += ' ';
                while (!stack.empty()) {
                    if (getP(stack.peek()) != 1){
                        current += stack.pop();
                    }else break;
                }
                if (!stack.empty()){
                    stack.pop();
                }

            }
        }

        while (!stack.empty()) {
            current += stack.pop();
        }
        return current;
    }

    public StringBuilder RPNToAnswer(String rpn) {
        String operand = "";
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getP(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getP(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) {
                        break;
                    }
                }
                stack.push(Double.parseDouble(operand));
                operand = "";
            }
            if (getP(rpn.charAt(i)) > 1) {
                double a = stack.pop(), b = stack.pop();
                if (rpn.charAt(i) == '+') {
                    stack.push(b + a);
                }
                if (rpn.charAt(i) == '-') {
                    stack.push(b - a);
                }
                if (rpn.charAt(i) == '*') {
                    stack.push(b * a);
                }
                if (rpn.charAt(i) == '/') {
                    stack.push(b / a);
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        if(!stack.empty()){
            BigDecimal bd = new BigDecimal(stack.pop()).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros();
            stringBuilder.append(bd);
        } else {
            stringBuilder.append(' ');
        }

        return stringBuilder;
    }

    private int getP(char token) {
        if (token == '*' || token == '/') {
            return 3;
        } else if (token == '+' || token == '-') {
            return 2;
        } else if (token == '(') {
            return 1;
        } else if (token == ')') {
            return -1;
        } else return 0;
    }
}
