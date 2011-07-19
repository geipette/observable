package no.guttab.observable.core.collections;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;


final class ObservableSetImpl<E> extends AbstractSet<E>
      implements ObservableSet<E> {
   private final String setId;
   private final Set<E> set;
   private final CopyOnWriteArrayList<ObservableSetListener<E>> listeners = new CopyOnWriteArrayList<ObservableSetListener<E>>();

   public ObservableSetImpl(String setId, Set<E> set) {
      this.setId = setId;
      this.set = set;
   }

   @Override
   public String getSetId() {
      return setId;
   }

   @Override
   public void addObservableSetListener(ObservableSetListener<E> listener) {
      listeners.add(listener);
   }

   @Override
   public void removeObservableSetListener(ObservableSetListener<E> listener) {
      listeners.remove(listener);
   }

   @Override
   public boolean add(E e) {
      if (set.add(e)) {
         for (ObservableSetListener<E> listener : listeners) {
            listener.setElementAdded(this, e);
         }
         addElementListener(e);
         return true;
      }
      return false;
   }


   @Override
   public Iterator<E> iterator() {
      return new Iterator<E>() {
         Iterator setIt = set.iterator();
         E current;

         @Override
         public boolean hasNext() {
            return setIt.hasNext();
         }

         @SuppressWarnings({"unchecked"})
         @Override
         public E next() {
            current = (E) setIt.next();
            return current;
         }

         @Override
         public void remove() {
            setIt.remove();
            for (ObservableSetListener<E> listener : listeners) {
               listener.setElementsRemoved(ObservableSetImpl.this, current);
            }
            removeElementListener(current);
         }
      };
   }

   @Override
   public int size() {
      return set.size();
   }

   private void removeElementListener(E value) {
      if (value instanceof Subject) {
         ((Subject) value).deleteAllListeners();
      }
   }

   private void addElementListener(E value) {
      if (value instanceof Subject) {
         ((Subject) value).addListener(new SetElementChangedListener(this, value));
      }
   }

   private class SetElementChangedListener implements PropertyChangeListener {
      private final ObservableSetImpl<E> t;
      private final E element;

      public SetElementChangedListener(ObservableSetImpl<E> t, E element) {
         this.t = t;
         this.element = element;
      }

      @Override
      public void notifyChange(PropertyChange change) {
         for (ObservableSetListener<E> listener : listeners) {
            listener.setElementPropertyChanged(t, element, change);
         }
      }

   }
}
