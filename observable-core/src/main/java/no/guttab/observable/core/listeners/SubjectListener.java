package no.guttab.observable.core.listeners;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;

public class SubjectListener implements PropertyChangeListener {
   private final String propertyName;
   private final Subject subject;

   public SubjectListener(String propertyName, Subject subject) {
      this.propertyName = propertyName;
      this.subject = subject;
   }

   @Override
   public void notifyChange(PropertyChange change) {
      final PropertyChange adaptedChange = new PropertyChange(propertyName + "." + change.getName(), change.getValue());
      subject.notifyListeners(adaptedChange);
   }
}
