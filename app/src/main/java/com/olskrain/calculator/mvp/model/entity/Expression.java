package com.olskrain.calculator.mvp.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import timber.log.Timber;

/**
 * Created by Andrey Ievlev on 19,Март,2019
 */
public class Expression {

    private List<String> list = new ArrayList<>();
    private boolean isError;
    private boolean isErrorTokens;
    private boolean isMathOperator;


    public boolean isError() {
        return isError;
    }

    public List<String> getList() {
        return list;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void addTokenToExpression(String token) {
        list.add(token);
    }

    public void deleteTokenFromExpression() {
        if (!list.isEmpty()) {
            list.remove(list.size() - 1);
        }
    }

    public void clearExpression() {
        list.clear();
    }

    public StringBuilder showExpression() {
        StringBuilder expression = new StringBuilder();
        checkError();
        for (int i = 0; i < list.size(); i++) {
            expression.append(list.get(i));
        }
        isErrorTokens = false;
        isMathOperator = false;
        return expression;
    }

    private void checkError() {
        checkErrorTokens();
        if (checkErrorStartOrEnd() || checkErrorBrackets() || isErrorTokens || !isMathOperator) {
            //if (checkErrorBrackets()){
            // if (isErrorTokens){
            isError = true;
        } else {
            isError = false;
        }
    }

    private void checkErrorTokens() { //Проверка на две и более бинарных фунции или точки рядом
        for (int i = 0; i < list.size(); i++) {
            //Todo: заменить на поток
            String current = list.get(i);

            if (current.equals("*") || current.equals("/") || current.equals("+") || current.equals("-")) {
                isMathOperator = true;
            }

            if (current.equals("*") || current.equals("/") || current.equals("+") || current.equals("-") || current.equals(".")) {
                if (list.size() > 1 && i < list.size() - 1) {
                    String next = list.get(i + 1);
                    if (next.equals("*") || next.equals("/") || next.equals("+") || next.equals("-") || next.equals(".") || next.equals(")")) {
                        isErrorTokens = true;
                    }
                }
            }

            if (current.equals(".")) {
                if (list.size() > 1 && i < list.size() - 1) {
                    String next = list.get(i + 1);
                    if (next.equals("(")) {
                        isErrorTokens = true;
                    }
                }
            }

            if (current.equals("0") || current.equals("1") || current.equals("2") || current.equals("3") || current.equals("4") || current.equals("5") ||
                    current.equals("6") || current.equals("7") || current.equals("8") || current.equals("9")) {
                if (list.size() > 1 && i < list.size() - 1) {
                    String next = list.get(i + 1);
                    if (next.equals("(")) {
                        isErrorTokens = true;
                    }
                }

                if (list.size() > 1 && i > 0) {
                    String previous = list.get(i - 1);
                    if (previous.equals(")")) {
                        isErrorTokens = true;
                    }
                }
            }
        }
    }

    private boolean checkErrorStartOrEnd() {  //проверка на наличие бинарной функции в начале или конце строки
        if (list.size() > 0) {
            String finalValue = list.get(list.size() - 1);
            String initial = list.get(0);
            if (initial.equals("*") || initial.equals("/") || initial.equals(".") ||
                    finalValue.equals("*") || finalValue.equals("/") || finalValue.equals("+") || finalValue.equals("-") || finalValue.equals(".")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkErrorBrackets() { //проверка на кол-во и расположение скобок
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < list.size(); i++) { //Todo: заменить на поток
            String current = list.get(i);

            if (current.equals("(")) {
                stack.push(current);
            } else if (current.equals(")")) {
                if (stack.isEmpty()) {
                    return true;
                }

                String c = stack.pop();

                if (!(c.equals("(") && current.equals(")"))) {
                    return true;
                }
            }
        }

        if (!stack.isEmpty()) {
            return true;
        }
        return false;
    }
}
