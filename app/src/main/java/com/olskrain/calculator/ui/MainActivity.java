package com.olskrain.calculator.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.olskrain.calculator.R;
import com.olskrain.calculator.mvp.model.Calcul;
import com.olskrain.calculator.mvp.presenter.MainPresenter;
import com.olskrain.calculator.mvp.view.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Andrey Ievlev on 19,Март,2019
 */

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.answer) TextView textView;

    @InjectPresenter
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initUi();
    }

    @Override
    public void initUi() {
        textView.setMovementMethod(new ScrollingMovementMethod());
        Calcul calcul = new Calcul();
        String x = calcul.ExpressionToRPN("(5+2)*2/(3+4)+1");
        double y = calcul.RPNToAnswer(x);
        Timber.d("sdf " + x);
        Timber.d("sdf " + y);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        return new MainPresenter();
    }

    @Override
    public void snowResult(String result) {

    }
}
