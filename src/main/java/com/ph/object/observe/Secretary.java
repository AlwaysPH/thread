package com.ph.object.observe;

import java.util.ArrayList;
import java.util.List;

public class Secretary implements Subject {

    private List<Observer> observerList = new ArrayList<Observer>();

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
}
