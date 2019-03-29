package com.olskrain.calculator.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.olskrain.calculator.Command;
import com.olskrain.calculator.mvp.model.Calcul;
import com.olskrain.calculator.mvp.model.entity.Expression;
import com.olskrain.calculator.mvp.view.MainView;

import static com.olskrain.calculator.Command.ADD_TOKEN;
import static com.olskrain.calculator.Command.CHANGE_SIGN;
import static com.olskrain.calculator.Command.ClEAR;
import static com.olskrain.calculator.Command.DELETE;
import static com.olskrain.calculator.Command.GET_RESULT_ERROR;


/**
 * Created by Andrey Ievlev on 19,Март,2019
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private Calcul calcul = new Calcul();
    private Expression expression = new Expression();
    private boolean isResult;

    public void createExpression(String token) {
        if (isResult && !expression.isOperator(token)) {
            expression.clearExpression();
            expression.addTokenToExpression(token);
            showResult(ADD_TOKEN);
            isResult = false;
        } else {
            expression.addTokenToExpression(token);
            showResult(ADD_TOKEN);
            isResult = false;
        }
    }

    public void changeExpression(Command command) {
        switch (command) {
            case DELETE:
                expression.deleteTokenFromExpression();
                showResult(DELETE);
                break;
            case ClEAR:
                expression.clearExpression();
                showResult(ClEAR);
                expression.setError(false);
                isResult = false;
                break;
            case GET_RESULT:
                if (!expression.getIsError()) {
                    try {
                        String st = calcul.RPNToAnswer(calcul.ExpressionToRPN(expression.showExpression()));
                        getViewState().showResult(st);
                        expression.clearExpression();
                        expression.setError(false);
                        getViewState().showError("");
                        createExpression(st);
                        isResult = true;
                    } catch (Exception e) {
                        showResult(GET_RESULT_ERROR);
                        e.printStackTrace();
                    }
                } else {
                    showResult(GET_RESULT_ERROR);
                }
                break;
            case CHANGE_SIGN:
                expression.changeSing();
                showResult(CHANGE_SIGN);
                break;
        }
    }

    private void showResult(Command command) {
        if (command.equals(GET_RESULT_ERROR)) {
            getViewState().showResult(expression.showExpression());
            getViewState().showError("Error");
        } else {
            getViewState().showResult(expression.showExpression());
            getViewState().showError("");
        }
    }
}
