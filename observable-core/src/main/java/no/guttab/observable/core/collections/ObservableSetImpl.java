package no.guttab.observable.core.collections;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;


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
         return true;
      }
      return false;
   }


   @Override
   public Iterator<E> iterator() {
      return new Iterator<E>() {
         final Iterator setIt = set.iterator();
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
         }
      };
   }

   @Override
   public int size() {
      return set.size();
   }

}
