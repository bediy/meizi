package com.example.administrator.mydemos.model;

import java.util.List;

/**
 * Created by Administrator on 2016/5/20.
 */
public class AllData {

    private boolean error;

    private List<Results> results;

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean getError() {
        return this.error;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public List<Results> getResults() {
        return this.results;
    }
}
