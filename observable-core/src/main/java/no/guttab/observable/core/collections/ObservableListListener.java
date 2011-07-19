package no.guttab.observable.core.collections;

import java.util.List;

import no.guttab.observable.core.PropertyChange;


/**
 * Notification types from an <tt>ObservableList</tt>.
 */
public interface ObservableListListener<T> {
   /**
    * Notification that elements have been added to the list.
    *
    * @param list   the <tt>ObservableList</tt> that has changed
    * @param index  the index the elements were added to
    * @param length the number of elements that were added
    */
   public void listElementsAdded(ObservableList<T> list, int index, int length);

   /**
    * Notification that elements have been removed from the list.
    *
    * @param list        the <tt>ObservableList</tt> that has changed
    * @param index       the starting index the elements were removed from
    * @param oldElements a list containing the elements that were removed.
    */
   public void listElementsRemoved(ObservableList<T> list, int index,
                                   List<T> oldElements);

   /**
    * Notification that an element has been replaced by another in the list.
    *
    * @param list       the <tt>ObservableList</tt> that has changed
    * @param index      the index of the element that was replaced
    * @param oldElement the element at the index before the change
    */
   public void listElementReplaced(ObservableList<T> list, int index,
                                   T oldElement);

   /**
    * Notification than a property of an element in this list has changed.
    *
    * @param list           the <tt>ObservableList</tt> that has changed
    * @param index          the index of the element that changed
    * @param propertyChange the property change
    */
   public void listElementPropertyChanged(ObservableList<T> list, int index, PropertyChange propertyChange);
}