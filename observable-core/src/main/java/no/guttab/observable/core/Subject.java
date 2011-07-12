package no.guttab.observable.core;

public interface Subject {
   void addListener(PropertyChangeListener listener);

   void deleteListener(PropertyChangeListener listener);

   void deleteAllListeners();

   void notifyListeners(PropertyChange change);

}
