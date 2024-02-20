package modele;

import java.util.ArrayList;

public abstract class Observable {
    // classe reprise de Conway.java

    private ArrayList<Observer> observers;

    public Observable(){
        this.observers = new ArrayList<Observer>();
    }

    public void addObserver(Observer o){
        observers.add(o);
    }

    public void notifyObservers(){
        for (Observer o : this.observers){
            o.update();
        }
    }
}
