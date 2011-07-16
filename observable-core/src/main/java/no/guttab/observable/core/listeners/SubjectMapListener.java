package no.guttab.observable.core.listeners;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.Subject;
import no.guttab.observable.core.collections.ObservableMap;
import no.guttab.observable.core.collections.ObservableMapListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubjectMapListener implements ObservableMapListener {
   private static final Logger log = LoggerFactory.getLogger(SubjectMapListener.class);
   private final Subject subject;

   public SubjectMapListener(Subject subject) {
      this.subject = subject;
   }

   @Override
   public void mapKeyValueChanged(ObservableMap map, Object key, Object lastValue) {
      subject.notifyListeners(new PropertyChange(getMapId(map) + "['" + key.toString() + "']", map.get(key)));
   }

   private String getMapId(ObservableMap map) {
      return map.getMapId() == null ? "" : map.getMapId();
   }

   @Override
   public void mapKeyAdded(ObservableMap map, Object key) {
      subject.notifyListeners(new PropertyChange(getMapId(map) + "['" + key.toString() + "']", map.get(key)));
   }

   @Override
   public void mapKeyRemoved(ObservableMap map, Object key, Object value) {
      subject.notifyListeners(new PropertyChange(getMapId(map) + ".remove('" + key.toString() + "')", null));
   }

   @Override
   public void mapValuePropertyChanged(ObservableMap observableMap, Object key, PropertyChange propertyChange) {
      subject.notifyListeners(new PropertyChange(getMapId(observableMap) + "['" + key.toString() + "']." + propertyChange.getName(), propertyChange.getValue()));
   }
}
