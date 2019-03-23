package com.olskrain.calculator.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.olskrain.calculator.Command;
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

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @BindView(R.id.answer) TextView answer;
    @BindView(R.id.mistake) TextView mistake;
    @BindView(R.id.button_number_1) Button numberOne;
    @BindView(R.id.button_number_2) Button numberTwo;
    @BindView(R.id.button_number_3) Button numberThree;
    @BindView(R.id.button_number_4) Button numberFour;
    @BindView(R.id.button_number_5) Button numberFive;
    @BindView(R.id.button_number_6) Button numberSix;
    @BindView(R.id.button_number_7) Button numberSeven;
    @BindView(R.id.button_number_8) Button numberEight;
    @BindView(R.id.button_number_9) Button numberNine;
    @BindView(R.id.button_number_0) Button numberZero;

    @BindView(R.id.button_symbol_bracket_open) Button bracketOpen;
    @BindView(R.id.button_symbol_bracket_close) Button bracketClose;
    @BindView(R.id.button_symbol_multiplication) Button multiplication;
    @BindView(R.id.button_symbol_division) Button division;
    @BindView(R.id.button_symbol_plus) Button plus;
    @BindView(R.id.button_symbol_minus) Button minus;
    @BindView(R.id.button_symbol_equally) Button equally;
    @BindView(R.id.button_symbol_unary_minus) Button unaryMinus;
    @BindView(R.id.button_symbol_point) Button point;

    @BindView(R.id.button_del) Button del;
    @BindView(R.id.button_ac) Button ac;

    Vibrator vib;
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
        answer.setMovementMethod(new ScrollingMovementMethod());
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        initOnClick();
    }

    private void initOnClick() {
        numberOne.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.number_1));
        });
        numberTwo.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.number_2));
        });
        numberThree.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.number_3));
        });
        numberFour.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.number_4));
        });
        numberFive.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.number_5));
        });
        numberSix.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.number_6));
        });
        numberSeven.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.number_7));
        });
        numberEight.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.number_8));
        });
        numberNine.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.number_9));
        });
        numberZero.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.number_0));
        });

        bracketOpen.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.symbol_bracket_open));
        });
        bracketClose.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.symbol_bracket_close));
        });
        multiplication.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.symbol_multiplication2));
        });
        division.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.symbol_division));
        });
        plus.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.symbol_plus));
        });
        minus.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.symbol_minus));
        });
        point.setOnClickListener(v -> { vibRun();
            mainPresenter.createExpression(getResources().getString(R.string.symbol_point));
        });

        equally.setOnClickListener(v -> { vibRun();
            mainPresenter.changeExpression(Command.GET_RESULT);
        });
        unaryMinus.setOnClickListener(v -> { vibRun();
            mainPresenter.changeExpression(Command.CHANGE_SIGN);
        });

        del.setOnClickListener(v -> { vibRun();
            mainPresenter.changeExpression(Command.DELETE);
        });
        ac.setOnClickListener(v -> { vibRun();
            mainPresenter.changeExpression(Command.ClEAR);
        });
    }

    private void vibRun(){
        vib.vibrate(25);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showResult(String result) {
        answer.setText(result);
    }

    @Override
    public void showError(String error) {
        mistake.setText(error);
    }
}
