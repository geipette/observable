package no.guttab.observable.core.listeners;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;
import no.guttab.observable.core.collections.ObservableMap;
import no.guttab.observable.core.collections.ObservableMapListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubjectMapListener<K, V> implements ObservableMapListener<K, V> {
   private static final Logger log = LoggerFactory.getLogger(SubjectMapListener.class);
   private final Subject subject;

   public SubjectMapListener(Subject subject) {
      this.subject = subject;
   }

   @Override
   public void mapKeyValueChanged(ObservableMap<K, V> map, K key, V oldValue) {
      V value = map.get(key);
      removeElementListener(oldValue);
      addElementListener(map, key, value);
      subject.notifyListeners(new PropertyChange(getMapId(map) + "[#arg0]", key, value));
   }

   private String getMapId(ObservableMap<K, V> map) {
      return map.getMapId() == null ? "" : map.getMapId();
   }

   @Override
   public void mapKeyAdded(ObservableMap<K, V> map, K key) {
      V value = map.get(key);
      addElementListener(map, key, value);
      subject.notifyListeners(new PropertyChange(getMapId(map) + "[#arg0]", key, value));
   }

   @Override
   public void mapKeyRemoved(ObservableMap<K, V> map, K key, V oldValue) {
      removeElementListener(oldValue);
      subject.notifyListeners(new PropertyChange(getMapId(map) + ".remove(#arg0)", key, oldValue));
   }

   @Override
   public void mapValuePropertyChanged(ObservableMap<K, V> observableMap, K key, PropertyChange propertyChange) {
      subject.notifyListeners(new PropertyChange(getMapId(observableMap) + "[#arg0]." + propertyChange.getName(), key, propertyChange.getValue()));
   }

   private void removeElementListener(V value) {
      if (value instanceof Subject) {
         ((Subject) value).deleteAllListeners();
      }
   }

   private void addElementListener(ObservableMap<K, V> observableMap, K key, V value) {
      if (value instanceof Subject) {
         ((Subject) value).addListener(new MapElementChangedListener(observableMap, key));
      }
   }

   private class MapElementChangedListener implements PropertyChangeListener {
      private final ObservableMap<K, V> observableMap;
      private final K key;

      public MapElementChangedListener(ObservableMap<K, V> observableMap, K key) {
         this.observableMap = observableMap;
         this.key = key;
      }

      @Override
      public void notifyChange(PropertyChange change) {
         mapValuePropertyChanged(observableMap, key, change);
      }
   }

}
