package no.guttab.observable.core.collections;

import no.guttab.observable.core.PropertyChange;

/**
 * @version $Revision$
 */
public interface ObservableMapListener<K, V> {
   /**
    * Notification that the value of an existing key has changed.
    *
    * @param map       the <tt>ObservableMap</tt> that changed
    * @param key       the key
    * @param lastValue the previous value
    */
   public void mapKeyValueChanged(ObservableMap<K, V> map, K key,
                                  V lastValue);

   /**
    * Notification that a key has been added.
    *
    * @param map the <tt>ObservableMap</tt> that changed
    * @param key the key
    */
   public void mapKeyAdded(ObservableMap<K, V> map, K key);

   /**
    * Notification that a key has been removed
    *
    * @param map   the <tt>ObservableMap</tt> that changed
    * @param key   the key
    * @param value value for key before key was removed
    */
   public void mapKeyRemoved(ObservableMap<K, V> map, K key, V value);

   public void mapValuePropertyChanged(ObservableMap<K, V> map, K key, PropertyChange propertyChange);

}