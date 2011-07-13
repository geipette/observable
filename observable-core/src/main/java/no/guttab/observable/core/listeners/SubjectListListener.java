package no.guttab.observable.core.listeners;

import java.util.List;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.Subject;
import no.guttab.observable.core.collections.ObservableList;
import no.guttab.observable.core.collections.ObservableListListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubjectListListener implements ObservableListListener {
   private static final Logger log = LoggerFactory.getLogger(SubjectListListener.class);
   private final Subject subject;

   public SubjectListListener(Subject subject) {
      this.subject = subject;
   }

   @Override
   public void listElementsAdded(ObservableList observableList, int index, int length) {
      log.debug("Elements added");
      final String listId = getListId(observableList);
      for (int i = index; i < (index + length); i++) {
         subject.notifyListeners(new PropertyChange(listId + "[" + i + "]", observableList.get(i)));
      }
   }

   private static String getListId(ObservableList observableList) {
      return observableList.getListId() == null ? "" : observableList.getListId();
   }

   @Override
   public void listElementsRemoved(ObservableList observableList, int index, List oldElements) {
      log.debug("Elements removed");
      final String listId = getListId(observableList);
      for (Object oldElement : oldElements) {
         subject.notifyListeners(new PropertyChange(listId + ".remove(" + index + ")", null));
      }
   }

   @Override
   public void listElementReplaced(ObservableList observableList, int index, Object oldElement) {
      log.debug("Element replaced");
      subject.notifyListeners(new PropertyChange(getListId(observableList) + "[" + index + "]", observableList.get(index)));
   }

   @Override
   public void listElementPropertyChanged(ObservableList observableList, int index, PropertyChange propertyChange) {
      subject.notifyListeners(new PropertyChange(getListId(observableList) + "[" + index + "]." + propertyChange.getName(), propertyChange.getProperty()));
   }
}

