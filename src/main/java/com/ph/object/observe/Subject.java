package com.ph.object.observe;


public interface Subject {

    public void add(Observer observer);

    public void delete(Observer observer);

    public void notifyAllObserver();

}
