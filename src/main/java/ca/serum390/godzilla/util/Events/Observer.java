package ca.serum390.godzilla.util.Events;

/**
 * Interface for representing the Observer Objects in the Observer pattern
 * The Observer is updated by the Observable Object
 */
public interface Observer {
    void update(ERPEvent e);
}
