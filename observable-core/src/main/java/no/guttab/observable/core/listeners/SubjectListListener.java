package no.guttab.observable.core.listeners;

import java.util.List;

import no.guttab.observable.core.PropertyChange;
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
         subject.notifyListeners(new PropertyChange(listId + "[" + i + "]", observableList.get(i)));
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
         subject.notifyListeners(new PropertyChange(listId + ".remove(" + index + ")", oldElement));
      }
   }

   @Override
   public void listElementReplaced(ObservableList<T> observableList, int index, T oldElement) {
      log.debug("Element replaced");
      subject.notifyListeners(new PropertyChange(getListId(observableList) + "[" + index + "]", observableList.get(index)));
   }

   @Override
   public void listElementPropertyChanged(ObservableList<T> observableList, int index, PropertyChange propertyChange) {
      subject.notifyListeners(new PropertyChange(getListId(observableList) + "[" + index + "]." + propertyChange.getName(), propertyChange.getValue()));
   }
}

