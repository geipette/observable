package no.guttab.observable.core.collections;

import java.util.List;

/**
 * A {@code List} that notifies listeners of changes.
 */
public interface ObservableList<E> extends List<E> {

   String getListId();

   /**
    * Adds a listener that is notified when the list changes.
    *
    * @param listener the listener to add
    */
   void addObservableListListener(ObservableListListener<E> listener);

   /**
    * Removes a listener.
    *
    * @param listener the listener to remove
    */
   void removeObservableListListener(ObservableListListener<E> listener);

}
