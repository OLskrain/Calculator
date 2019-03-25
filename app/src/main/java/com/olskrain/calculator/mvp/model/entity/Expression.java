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

    private final String NUMBER = "0123456789";
    private final String OPERATORS = "+-*/";
    private final String SEPARATOR = ".";
    private final String OPEN_BRACKET = "(";
    private final String CLOSE_BRACKET = ")";

    private boolean isNumber(String token) {
        return NUMBER.contains(token);
    }

    private boolean isSeparator(String token) {
        return token.equals(SEPARATOR);
    }

    private boolean isOpenBracket(String token) {
        return token.equals(OPEN_BRACKET);
    }

    private boolean isCloseBracket(String token) {
        return token.equals(CLOSE_BRACKET);
    }

    public boolean isOperator(String token) {
        return OPERATORS.contains(token);
    }

    public boolean getIsError() {
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

    public void changeSing() { //метод смены знака
        for (int i = list.size() - 1; i + 1 > 0; i--) {
            if (list.get(i).equals("+")) {
                list.set(i, "-");
                break;
            } else if (list.get(i).equals("-")) {
                list.set(i, "+");
                break;
            } else if (list.get(i).equals("*") || list.get(i).equals("/")) {
                String string = list.get(i + 1).replace("(-", "-").replace("(","").replace(")","");
                list.set(i + 1, "(" + Integer.parseInt(string) * (-1) + ")");
                break;
            } else if (list.get(i).equals("(")) {
                list.set(i + 1, Integer.toString(Integer.parseInt(list.get(i + 1)) * (-1)));
                break;
            } else if (i == 0){
                list.set(i, Integer.toString(Integer.parseInt(list.get(i)) * (-1)));
            }
        }
    }

    public String showExpression() {
        StringBuilder expression = new StringBuilder();
        checkError();
        for (int i = 0; i < list.size(); i++) {
            expression.append(list.get(i));
        }
        isErrorTokens = false;
        isMathOperator = false;
        return expression.toString();
    }

    private void checkError() {
        checkErrorTokens();
        if (checkErrorStartOrEnd() || checkErrorBrackets() || isErrorTokens || !isMathOperator) {
            isError = true;
        } else isError = false;
    }

    private void checkErrorTokens() { //Проверка на две и более бинарных фунции или точки рядом
        for (int i = 0; i < list.size(); i++) {
            //Todo: заменить на поток
            String current = list.get(i);

            if (isOperator(current)) { //проверка на то, есть ли вообше матет. действия
                isMathOperator = true;
            }

            if (isOperator(current) || isSeparator(current)) { //проверка на конструкции "MOMO", "MO.", "MO)", ".MO", "..", ".)" , где MO- математюоператор
                if (list.size() > 1 && i < list.size() - 1) {
                    String next = list.get(i + 1);
                    if (isOperator(next) || isSeparator(next) || isCloseBracket(next)) {
                        isErrorTokens = true;
                    }
                }
            }

            if (isSeparator(current)) { //проверка на конструкцию ".("
                if (list.size() > 1 && i < list.size() - 1) {
                    String next = list.get(i + 1);
                    if (isOpenBracket(next)) {
                        isErrorTokens = true;
                    }
                }
            }

            if (isNumber(current)) {  //проверка на конструкцию "n(", где n число
                if (list.size() > 1 && i < list.size() - 1) {
                    String next = list.get(i + 1);
                    if (isOpenBracket(next)) {
                        isErrorTokens = true;
                    }
                }

                if (list.size() > 1 && i > 0) { //проверка на конструкцию ")n", где n число
                    String previous = list.get(i - 1);
                    if (isCloseBracket(previous)) {
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
            return initial.equals("*") || initial.equals("/") || isSeparator(initial) ||
                    isOperator(finalValue) || isSeparator(finalValue);
        }
        return false;
    }

    private boolean checkErrorBrackets() { //проверка на кол-во и расположение скобок
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < list.size(); i++) { //Todo: заменить на поток
            String current = list.get(i);

            if (isOpenBracket(current)) {
                stack.push(current);
            } else if (isCloseBracket(current)) {
                if (stack.isEmpty()) {
                    return true;
                }

                String st = stack.pop();
                if (!(isOpenBracket(st) && isCloseBracket(current))) {
                    return true;
                }
            }
        }
        return !stack.isEmpty();
    }
}
