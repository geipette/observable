package no.guttab.observable.core.listeners;

import java.util.List;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;
import no.guttab.observable.core.collections.ObservableList;
import no.guttab.observable.core.collections.ObservableListListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubjectListListener<T> implements ObservableListListener<T> {
   private static final Logger log = LoggerFactory.getLogger(SubjectListListener.class);
   private final Subject subject;

   public SubjectListListener(Subject subject) {
      this.subject = subject;
   }

   @Override
   public void listElementsAdded(ObservableList<T> observableList, int index, int length) {
      log.debug("Elements added");
      final String listId = getListId(observableList);
      for (int i = index; i < (index + length); i++) {
         final T value = observableList.get(i);
         addElementChangedListener(observableList, value);
         subject.notifyListeners(new PropertyChange(listId + "[" + i + "]", value));
      }
   }

   private static <T> String getListId(ObservableList<T> observableList) {
      return observableList.getListId() == null ? "" : observableList.getListId();
   }

   @Override
   public void listElementsRemoved(ObservableList<T> observableList, int index, List<T> oldElements) {
      log.debug("Elements removed");
      final String listId = getListId(observableList);
      for (T oldElement : oldElements) {
         removeElementChangedListener(oldElement);
         // TODO: OldElement supplied twice as a quick fix, PropertyChange does not support one arg and no value.
         // TODO: Should be able to use index instead
         subject.notifyListeners(new PropertyChange(listId + ".remove(#arg0)", null, index));
      }
   }

   @Override
   public void listElementReplaced(ObservableList<T> observableList, int index, T oldElement) {
      log.debug("Element replaced");
      T value = observableList.get(index);
      removeElementChangedListener(oldElement);
      addElementChangedListener(observableList, value);
      subject.notifyListeners(new PropertyChange(getListId(observableList) + "[" + index + "]", value));
   }

   @Override
   public void listElementPropertyChanged(ObservableList<T> observableList, int index, PropertyChange propertyChange) {
      subject.notifyListeners(new PropertyChange(getListId(observableList) + "[" + index + "]." + propertyChange.getName(), propertyChange.getValue()));
   }

   private void removeElementChangedListener(T oldValue) {
      if (oldValue instanceof Subject) {
         Subject subject = (Subject) oldValue;
         subject.deleteAllListeners();
      }
   }

   private void addElementChangedListener(ObservableList<T> observableList, T element) {
      if (element instanceof Subject) {
         Subject subject = (Subject) element;
         subject.addListener(new ListElementChangedListener(observableList, element));
      }
   }

   private class ListElementChangedListener implements PropertyChangeListener {
      private final ObservableList<T> observableList;
      private final T element;

      public ListElementChangedListener(ObservableList<T> observableList, T element) {
         this.observableList = observableList;
         this.element = element;
      }

      @Override
      public void notifyChange(PropertyChange change) {
         listElementPropertyChanged(observableList, observableList.indexOf(element), change);
      }
   }

}

