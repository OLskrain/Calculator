package com.olskrain.calculator.mvp.model.entity;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Andrey Ievlev on 19,Март,2019
 */
public class Expression {

    private List<String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

    public void addTokenToExpression(String token){
        list.add(token);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getList().size(); i++) {
            stringBuilder.append(getList().get(i));
        }
        Timber.d("sr"+stringBuilder);
    }

    public void deleteTokenFromExpression(){
        if (!list.isEmpty()){
            list.remove(list.size() - 1);
        }


        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getList().size(); i++) {
            stringBuilder.append(getList().get(i));
        }
        Timber.d("sr"+stringBuilder);
    }

    public void clearExpression(){
        list.clear();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getList().size(); i++) {
            stringBuilder.append(getList().get(i));
        }
        Timber.d("sr"+stringBuilder);
    }
}
