package com.olskrain.calculator.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.olskrain.calculator.Command;
import com.olskrain.calculator.mvp.model.Calcul;
import com.olskrain.calculator.mvp.model.entity.Expression;
import com.olskrain.calculator.mvp.view.MainView;

import timber.log.Timber;

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

            getViewState().showResult(expression.showExpression());
            isResult = false;
        }else {
            expression.addTokenToExpression(token);
            getViewState().showResult(expression.showExpression());
            isResult = false;
        }
    }

    public void changeExpression(Command command){
        switch (command){
            case DELETE:
                expression.deleteTokenFromExpression();
                getViewState().showResult(expression.showExpression());
                getViewState().showError("");
                break;
            case ClEAR:
                expression.clearExpression();
                getViewState().showResult(expression.showExpression());
                getViewState().showError("");
                expression.setError(false);
                isResult = false;
                break;
            case GET_RESULT:
                if(!expression.getIsError()){
                    try{
                        String st = calcul.RPNToAnswer(calcul.ExpressionToRPN(expression.showExpression()));
                        getViewState().showResult(st);
                        expression.clearExpression();
                        expression.setError(false);
                        getViewState().showError("");
                        createExpression(st);
                        isResult = true;
                    }catch (Exception e){
                        getViewState().showError("Error");
                        e.printStackTrace();
                    }
                }else {
                    getViewState().showResult(expression.showExpression());
                    getViewState().showError("Error");
                }
                break;
            case CHANGE_SIGN:
                expression.changeSing();
                getViewState().showResult(expression.showExpression());
                getViewState().showError("");
                break;
        }
    }
}
