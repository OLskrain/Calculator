package com.olskrain.calculator.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.olskrain.calculator.ErrorCode;

/**
 * Created by Andrey Ievlev on 19,Март,2019
 */

@StateStrategyType(SingleStateStrategy.class)
public interface MainView extends MvpView {
    void initUi();

    void showResult(String result);

    void showError(ErrorCode errorCode);
}
