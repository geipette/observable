package no.guttab.observable.core.listeners;

import no.guttab.observable.core.PropertyChange;
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
      subject.notifyListeners(new PropertyChange(getMapId(map) + "[#arg0]", key, map.get(key), oldValue));
   }

   private String getMapId(ObservableMap<K, V> map) {
      return map.getMapId() == null ? "" : map.getMapId();
   }

   @Override
   public void mapKeyAdded(ObservableMap<K, V> map, K key) {
      subject.notifyListeners(new PropertyChange(getMapId(map) + "[#arg0]", key, map.get(key)));
   }

   @Override
   public void mapKeyRemoved(ObservableMap<K, V> map, K key, V oldValue) {
      subject.notifyListeners(new PropertyChange(getMapId(map) + ".remove(#arg0)", key, oldValue));
   }

   @Override
   public void mapValuePropertyChanged(ObservableMap<K, V> observableMap, K key, PropertyChange propertyChange) {
      subject.notifyListeners(new PropertyChange(getMapId(observableMap) + "[#arg0]." + propertyChange.getName(), key, propertyChange.getValue()));
   }
}
