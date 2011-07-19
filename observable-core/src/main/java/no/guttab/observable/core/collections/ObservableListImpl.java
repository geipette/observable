package no.guttab.observable.core.collections;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;

import static java.util.Collections.singletonList;

/**
 * @version $Revision$
 */
final class ObservableListImpl<E> extends AbstractList<E> implements ObservableList<E> {
   private final List<E> list;
   private final String listId;
   private final List<ObservableListListener<E>> listeners = new CopyOnWriteArrayList<ObservableListListener<E>>();

   ObservableListImpl(String listId, List<E> list) {
      this.listId = listId;
      this.list = list;
   }

   @Override
   public String getListId() {
      return listId;
   }

   @Override
   public E get(int index) {
      return list.get(index);
   }

   @Override
   public int size() {
      return list.size();
   }

   @Override
   public E set(int index, E element) {
      E oldValue = list.set(index, element);
      removeElementChangedListener(oldValue);
      for (ObservableListListener<E> listener : listeners) {
         listener.listElementReplaced(this, index, oldValue);
      }
      addElementChangedListener(element);
      return oldValue;
   }

   @Override
   public void add(int index, E element) {
      list.add(index, element);
      modCount++;
      addElementChangedListener(element);
      for (ObservableListListener<E> listener : listeners) {
         listener.listElementsAdded(this, index, 1);
      }
   }

   @Override
   public E remove(int index) {
      E oldValue = list.remove(index);
      modCount++;
      removeElementChangedListener(oldValue);
      for (ObservableListListener<E> listener : listeners) {
         listener.listElementsRemoved(this, index, singletonList(oldValue));
      }
      return oldValue;
   }

   @Override
   public boolean addAll(Collection<? extends E> c) {
      return addAll(size(), c);
   }

   @Override
   public boolean addAll(int index, Collection<? extends E> c) {
      boolean modified = false;
      for (E e : c) {
         if (list.add(e)) {
            addElementChangedListener(e);
            modified = true;
         }
         modCount++;
      }
      if (modified) {
         for (ObservableListListener<E> listener : listeners) {
            listener.listElementsAdded(this, index, c.size());
         }
      }
      return modified;
   }

   @Override
   public void clear() {
      List<E> dup = new ArrayList<E>(list);
      list.clear();
      modCount++;
      if (!dup.isEmpty()) {
         for (E e : dup) {
            removeElementChangedListener(e);
         }
         for (ObservableListListener<E> listener : listeners) {
            listener.listElementsRemoved(this, 0, dup);
         }
      }
   }

   @Override
   public boolean containsAll(Collection<?> c) {
      return list.containsAll(c);
   }

   @Override
   public <T> T[] toArray(T[] a) {
      return list.toArray(a);
   }

   @Override
   public Object[] toArray() {
      return list.toArray();
   }

   @Override
   public void addObservableListListener(ObservableListListener<E> listener) {
      listeners.add(listener);
   }

   @Override
   public void removeObservableListListener(ObservableListListener listener) {
      listeners.remove(listener);
   }

   @Override
   public String toString() {
      return "ObservableListImpl" + super.toString();
   }

   private void removeElementChangedListener(E oldValue) {
      if (oldValue instanceof Subject) {
         Subject subject = (Subject) oldValue;
         subject.deleteAllListeners();
      }
   }

   private void addElementChangedListener(E element) {
      if (element instanceof Subject) {
         Subject subject = (Subject) element;
         subject.addListener(new ListElementChangedListener(this, element));
      }
   }

   private class ListElementChangedListener implements PropertyChangeListener {
      private final ObservableListImpl<E> t;

      private final E element;

      public ListElementChangedListener(ObservableListImpl<E> t, E element) {
         this.t = t;
         this.element = element;
      }

      @Override
      public void notifyChange(PropertyChange change) {
         for (ObservableListListener<E> listener : listeners) {
            listener.listElementPropertyChanged(t, list.indexOf(element), change);
         }
      }
   }
}
