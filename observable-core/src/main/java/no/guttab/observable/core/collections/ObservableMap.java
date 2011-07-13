package no.guttab.observable.core.collections;

import java.util.Map;

/**
 * A {@code Map} that notifies listeners of changes to the {@code Map}.
 */
public interface ObservableMap<K, V> extends Map<K, V> {
   /**
    * Adds a listener to this observable map.
    *
    * @param listener the listener to add
    */
   public void addObservableMapListener(ObservableMapListener<K, V> listener);

   /**
    * Removes a listener from this observable map.
    *
    * @param listener the listener to remove
    */
   public void removeObservableMapListener(ObservableMapListener<K, V> listener);

   String getMapId();
}