package com.olskrain.calculator.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.olskrain.calculator.Command;
import com.olskrain.calculator.ErrorCode;
import com.olskrain.calculator.mvp.model.Calcul;
import com.olskrain.calculator.mvp.model.entity.Expression;
import com.olskrain.calculator.mvp.view.MainView;

import static com.olskrain.calculator.Command.ADD_TOKEN;
import static com.olskrain.calculator.Command.CHANGE_SIGN;
import static com.olskrain.calculator.Command.ClEAR;
import static com.olskrain.calculator.Command.DELETE;
import static com.olskrain.calculator.Command.GET_RESULT_ERROR;
import static com.olskrain.calculator.ErrorCode.ARITHMETIC_EXCEPTION;
import static com.olskrain.calculator.ErrorCode.ERROR_BRACKETS;
import static com.olskrain.calculator.ErrorCode.ERROR_START_OR_END;
import static com.olskrain.calculator.ErrorCode.ERROR_TOKENS;
import static com.olskrain.calculator.ErrorCode.NO_ERROR;

/**
 * Created by Andrey Ievlev on 19,Март,2019
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private Calcul calcul = new Calcul();
    private Expression expression = new Expression();
    private boolean isResult;
    private boolean isArithmeticException;

    public void createExpression(String token) {
        if (isResult && !expression.isOperator(token)) {
            expression.clearExpression();
            expression.addTokenToExpression(token);
            updateStatusError();
            showResult(ADD_TOKEN);
            isResult = false;
            isArithmeticException = false;
        } else {
            expression.addTokenToExpression(token);
            updateStatusError();
            showResult(ADD_TOKEN);
            isResult = false;
            isArithmeticException = false;
        }
    }

    public void changeExpression(Command command) {
        switch (command) {
            case DELETE:
                expression.deleteTokenFromExpression();
                updateStatusError();
                showResult(DELETE);
                isArithmeticException = false;
                break;
            case ClEAR:
                expression.clearExpression();
                updateStatusError();
                showResult(ClEAR);
                expression.setError(false);
                isResult = false;
                isArithmeticException = false;
                break;
            case GET_RESULT:
                if (!expression.getIsError() && expression.getIsMathOperator()) {
                    try {
                        String st = calcul.RPNToAnswer(calcul.ExpressionToRPN(expression.showExpression()));
                        updateStatusError();
                        getViewState().showResult(st);
                        expression.clearExpression();
                        expression.setError(false);
                        getViewState().showError(getErrorCode());
                        createExpression(st);
                        isResult = true;
                    } catch (Exception e) {
                        isArithmeticException = true;
                        updateStatusError();
                        getViewState().showRedScreen();
                        showResult(GET_RESULT_ERROR);
                        e.printStackTrace();
                    }
                } else {
                    updateStatusError();
                    getViewState().showRedScreen();
                    showResult(GET_RESULT_ERROR);
                }
                break;
            case CHANGE_SIGN:
                expression.changeSing();
                updateStatusError();
                showResult(CHANGE_SIGN);
                break;
        }
    }

    private void showResult(Command command) {
        if (command.equals(GET_RESULT_ERROR)) {
            getViewState().showResult(expression.showExpression());
            getViewState().showError(getErrorCode());
        } else {
            getViewState().showResult(expression.showExpression());
            getViewState().showError(getErrorCode());
        }
    }

    private ErrorCode getErrorCode() {
        if (expression.getIsErrorStartOrEnd()) {
            return ERROR_START_OR_END;
        } else if (expression.getIsErrorTokens()) {
            return ERROR_TOKENS;
        } else if (expression.getIsErrorBrackets()) {
            return ERROR_BRACKETS;
        } else if (isArithmeticException) {
            return ARITHMETIC_EXCEPTION;
        }
        return NO_ERROR;
    }

    private void updateStatusError() {
        expression.setErrorBrackets(false);
        expression.setErrorStartOrEnd(false);
        expression.setErrorTokens(false);
        expression.setMathOperator(false);
    }
}
