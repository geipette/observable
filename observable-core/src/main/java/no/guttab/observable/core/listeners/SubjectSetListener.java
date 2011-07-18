package no.guttab.observable.core.listeners;

import no.guttab.observable.core.PropertyChange;
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
      subject.notifyListeners(new PropertyChange(getSetId(set) + ".add(#arg0)", element));
   }

   @Override
   public void setElementsRemoved(ObservableSet<T> set, T oldElement) {
      subject.notifyListeners(new PropertyChange(getSetId(set) + ".remove(#arg0)", oldElement));
   }

   @Override
   public void setElementPropertyChanged(ObservableSet<T> set, T element, PropertyChange propertyChange) {
      subject.notifyListeners(new PropertyChange(getSetId(set) + ".get(#arg0)." + propertyChange.getName() + " = #arg1",
            element, propertyChange.getValue()));
   }
}
