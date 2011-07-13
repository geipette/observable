package no.guttab.observable.core.collections;

import java.util.Set;

public interface ObservableSet<E> extends Set<E> {

   String getSetId();

   /**
    * Adds a listener that is notified when the set changes.
    *
    * @param listener the listener to add
    */
   void addObservableSetListener(ObservableSetListener<E> listener);

   /**
    * Removes a listener.
    *
    * @param listener the listener to remove
    */
   void removeObservableSetListener(ObservableSetListener<E> listener);


}
