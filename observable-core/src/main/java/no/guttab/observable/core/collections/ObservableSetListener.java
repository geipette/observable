package no.guttab.observable.core.collections;

import no.guttab.observable.core.PropertyChange;

public interface ObservableSetListener<E> {
   /**
    * Notification that elements have been added to the list.
    *
    * @param set     the {@code ObservableSet} that has changed
    * @param element the element that has been added
    */
   public void setElementAdded(ObservableSet<E> set, E element);

   /**
    * Notification that elements have been removed from the list.
    *
    * @param set        the {@code ObservableSet} that has changed
    * @param oldElement the element that has been removed
    */
   public void setElementsRemoved(ObservableSet<E> set, E oldElement);

   /**
    * Notification than a property of an element in this set has changed.
    *
    * @param set            the {@code ObservableList} that has changed
    * @param element        the element that has changed
    * @param propertyChange the property change
    */
   public void setElementPropertyChanged(ObservableSet<E> set, E element, PropertyChange propertyChange);

}
