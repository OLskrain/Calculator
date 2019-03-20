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

    //Todo: сделать проверку на поворный знак точки
    private Calcul calcul = new Calcul();
    private Expression expression = new Expression();

    public void createExpression(String token) {
        expression.addTokenToExpression(token);
    }

    public void changeExpression(Command command){
        switch (command){
            case DELETE:
                expression.deleteTokenFromExpression();
                break;
            case ClEAR:
                expression.clearExpression();
                break;
            case GET_RESULT:
                Timber.d("srr" + "получить результат");
                break;
            case CHANGE_SIGN:
                Timber.d("srr" + "сменить знак");
                break;
        }
    }
}
