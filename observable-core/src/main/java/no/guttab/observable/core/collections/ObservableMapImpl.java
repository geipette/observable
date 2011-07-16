package no.guttab.observable.core.collections;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @version $Revision$
 */
final class ObservableMapImpl<K, V> extends AbstractMap<K, V>
      implements ObservableMap<K, V> {
   private final String mapId;
   private Map<K, V> map;
   private List<ObservableMapListener<K, V>> listeners;
   private Set<Entry<K, V>> entrySet;

   ObservableMapImpl(String mapId, Map<K, V> map) {
      this.mapId = mapId;
      this.map = map;
      listeners = new CopyOnWriteArrayList<ObservableMapListener<K, V>>();
   }

   @Override
   public String getMapId() {
      return mapId;
   }

   @Override
   public void clear() {
      // Remove all elements via iterator to trigger notification
      Iterator<K> iterator = keySet().iterator();
      while (iterator.hasNext()) {
         iterator.next();
         iterator.remove();
      }
   }

   @Override
   public boolean containsKey(Object key) {
      return map.containsKey(key);
   }

   @Override
   public boolean containsValue(Object value) {
      return map.containsValue(value);
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      Set<Entry<K, V>> es = entrySet;
      return es != null ? es : (entrySet = new EntrySet());
   }

   @Override
   public V get(Object key) {
      return map.get(key);
   }

   @Override
   public boolean isEmpty() {
      return map.isEmpty();
   }

   @Override
   public V put(K key, V value) {
      V lastValue;

      if (containsKey(key)) {
         lastValue = map.put(key, value);
         for (ObservableMapListener<K, V> listener : listeners) {
            listener.mapKeyValueChanged(this, key, lastValue);
         }
      } else {
         lastValue = map.put(key, value);
         for (ObservableMapListener<K, V> listener : listeners) {
            listener.mapKeyAdded(this, key);
         }
      }
      return lastValue;
   }

   @Override
   public void putAll(Map<? extends K, ? extends V> m) {
      for (K key : m.keySet()) {
         put(key, m.get(key));
      }
   }

   @SuppressWarnings({"unchecked"})
   @Override
   public V remove(Object key) {
      if (containsKey(key)) {
         V value = map.remove(key);
         for (ObservableMapListener<K, V> listener : listeners) {
            listener.mapKeyRemoved(this, (K) key, value);
         }
         return value;
      }
      return null;
   }

   @Override
   public int size() {
      return map.size();
   }

   @Override
   public void addObservableMapListener(ObservableMapListener<K, V> listener) {
      listeners.add(listener);
   }

   @Override
   public void removeObservableMapListener(ObservableMapListener<K, V> listener) {
      listeners.remove(listener);
   }


   private class EntryIterator implements Iterator<Entry<K, V>> {
      private Iterator<Entry<K, V>> realIterator;
      private Entry<K, V> last;

      EntryIterator() {
         realIterator = map.entrySet().iterator();
      }

      @Override
      public boolean hasNext() {
         return realIterator.hasNext();
      }

      @Override
      public Entry<K, V> next() {
         last = realIterator.next();
         return last;
      }

      @Override
      public void remove() {
         if (last == null) {
            throw new IllegalStateException();
         }
         Object toRemove = last.getKey();
         last = null;
         ObservableMapImpl.this.remove(toRemove);
      }
   }


   private class EntrySet extends AbstractSet<Entry<K, V>> {
      @Override
      public Iterator<Entry<K, V>> iterator() {
         return new EntryIterator();
      }

      @Override
      @SuppressWarnings("unchecked")
      public boolean contains(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         }
         Entry<K, V> e = (Entry<K, V>) o;
         return containsKey(e.getKey());
      }

      @Override
      @SuppressWarnings("unchecked")
      public boolean remove(Object o) {
         if (o instanceof Entry) {
            K key = ((Entry<K, V>) o).getKey();
            if (containsKey(key)) {
               remove(key);
               return true;
            }
         }
         return false;
      }

      @Override
      public int size() {
         return ObservableMapImpl.this.size();
      }

      @Override
      public void clear() {
         ObservableMapImpl.this.clear();
      }
   }
}

