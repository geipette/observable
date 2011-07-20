package no.guttab.observable.core.listeners;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;
import no.guttab.observable.core.collections.ObservableSet;
import no.guttab.observable.core.collections.ObservableSetListener;

public class SubjectSetListener<T> implements ObservableSetListener<T> {
   private final Subject subject;

   public SubjectSetListener(Subject subject) {
      this.subject = subject;
   }

   private String getSetId(ObservableSet<T> set) {
      return set.getSetId() == null ? "" : set.getSetId();
   }

   @Override
   public void setElementAdded(ObservableSet<T> set, T element) {
      addElementListener(set, element);
      subject.notifyListeners(new PropertyChange(getSetId(set) + ".add(#arg0)", element));
   }

   @Override
   public void setElementsRemoved(ObservableSet<T> set, T oldElement) {
      removeElementListener(oldElement);
      subject.notifyListeners(new PropertyChange(getSetId(set) + ".remove(#arg0)", oldElement));
   }

   @Override
   public void setElementPropertyChanged(ObservableSet<T> set, T element, PropertyChange propertyChange) {
      subject.notifyListeners(new PropertyChange(getSetId(set) + "[#arg0]." + propertyChange.getName(),
            element, propertyChange.getValue()));
   }

   private void removeElementListener(T value) {
      if (value instanceof Subject) {
         ((Subject) value).deleteAllListeners();
      }
   }

   private void addElementListener(ObservableSet<T> set, T value) {
      if (value instanceof Subject) {
         ((Subject) value).addListener(new SetElementChangedListener(set, value));
      }
   }

   private class SetElementChangedListener implements PropertyChangeListener {
      private final ObservableSet<T> observableSet;
      private final T element;

      public SetElementChangedListener(ObservableSet<T> observableSet, T element) {
         this.observableSet = observableSet;
         this.element = element;
      }

      @Override
      public void notifyChange(PropertyChange change) {
         setElementPropertyChanged(observableSet, element, change);
      }

   }

}
