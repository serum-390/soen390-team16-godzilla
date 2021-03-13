package ca.serum390.godzilla.util.Events;

/**
 * interface for representing the Observable objects in the Observer pattern
 * The Observable Object notifies the observers
 */
public interface Observable {
    void notifyObservers(ERPEvent e);

    void attachObserver(Observer o);

    void detachObserver(Observer o);
}
