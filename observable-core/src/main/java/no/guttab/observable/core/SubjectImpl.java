package no.guttab.observable.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SubjectImpl implements Subject {
   private final List<PropertyChangeListener> listeners = new CopyOnWriteArrayList<PropertyChangeListener>();
   private Object target;

   @Override
   public void addListener(PropertyChangeListener listener) {
      if (listener == null)
         throw new NullPointerException();
      listeners.add(listener);
   }

   @Override
   public void deleteListener(PropertyChangeListener listener) {
      listeners.remove(listener);
   }

   @Override
   public void notifyListeners(PropertyChange change) {
      for (PropertyChangeListener listener : listeners) {
         listener.notifyChange(change);
      }
   }

   @Override
   public void deleteAllListeners() {
      listeners.clear();
   }

//   @Override
//   public void initTarget(Object target) {
//      this.target = target;
//   }
//
//   @Override
//   public Object target() {
//      return target;
//   }

}
