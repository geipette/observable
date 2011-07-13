package no.guttab.observable.core.collections;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@code ObservableCollections} provides factory methods for creating
 * observable lists and maps.
 */
public final class ObservableCollections {

   public static <E> ObservableSet<E> observableSet(Set<E> set) {
      return observableSet(set);
   }

   public static <E> ObservableSet<E> observableSet(String setId, Set<E> set) {
      if (set == null) {
         throw new IllegalArgumentException("Set must be non-null");
      }
      return new ObservableSetImpl<E>(setId, set);
   }


   /**
    * Creates and returns an {@code ObservableMap} wrapping the supplied
    * {@code Map}.
    *
    * @param map the {@code Map} to wrap  @return an {@code ObservableMap}
    * @throws IllegalArgumentException if {@code map} is {@code null}
    */
   public static <K, V> ObservableMap<K, V> observableMap(Map<K, V> map) {
      return observableMap(null, map);
   }

   /**
    * Creates and returns an {@code ObservableMap} wrapping the supplied
    * {@code Map}.
    *
    * @param mapId a id that identifies this map for observers. May be null.
    * @param map   the {@code Map} to wrap  @return an {@code ObservableMap}
    * @throws IllegalArgumentException if {@code map} is {@code null}
    */
   public static <K, V> ObservableMap<K, V> observableMap(String mapId, Map<K, V> map) {
      if (map == null) {
         throw new IllegalArgumentException("Map must be non-null");
      }
      return new ObservableMapImpl<K, V>(mapId, map);
   }


   /**
    * Creates and returns an {@code ObservableList} wrapping the supplied
    * {@code List}.
    *
    * @param list the {@code List} to wrap
    * @return an {@code ObservableList}
    * @throws IllegalArgumentException if {@code list} is {@code null}
    */
   public static <E> ObservableList<E> observableList(List<E> list) {
      return observableList(null, list);
   }

   /**
    * Creates and returns an {@code ObservableList} wrapping the supplied
    * {@code List}.
    *
    * @param listId a id that identifies this list for observers. May be null.
    * @param list   the {@code List} to wrap
    * @return an {@code ObservableList}
    * @throws IllegalArgumentException if {@code list} is {@code null}
    */
   public static <E> ObservableList<E> observableList(String listId, List<E> list) {
      if (list == null) {
         throw new IllegalArgumentException("List must be non-null");
      }
      return new ObservableListImpl<E>(listId, list);
   }


}