package com.example;

import java.util.Observable;

/**
 * Created by Ye on 2017/4/1/0001.
 */

public class Obser extends Observable {
    public void post(String s) {
        setChanged();

    }
}
