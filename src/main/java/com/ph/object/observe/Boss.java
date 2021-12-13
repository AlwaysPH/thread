package com.ph.object.observe;

import java.util.ArrayList;
import java.util.List;

public class Boss implements Subject {

    private List<Observer> observerList = new ArrayList<Observer>();

    private String states;

    public void add(Observer observer) {
        observerList.add(observer);
    }

    public void delete(Observer observer) {
        observerList.remove(observer);
    }

    public void notifyAllObserver() {
        for(Observer observer : observerList){
            observer.update();
        }
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }
}
