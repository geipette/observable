package no.guttab.observable.core.collections;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <tt>ObservableCollections</tt> provides factory methods for creating
 * observable lists and maps.
 */
public final class ObservableCollections {
   /**
    * Creates and returns an <tt>ObservableSet</tt> wrapping the supplied
    * <tt>Set</tt>}.
    *
    * @param set the <tt>Set</tt> to wrap
    * @return an <tt>ObservableSet</tt>
    * @throws IllegalArgumentException if <tt>set</tt> is <tt>null</tt>
    */
   public static <E> ObservableSet<E> observableSet(Set<E> set) {
      return observableSet(null, set);
   }

   /**
    * Creates and returns an <tt>ObservableSet</tt> wrapping the supplied
    * <tt>Set</tt>}.
    *
    * @param setId an id that identifies this set for observers. May be null.
    * @param set   the <tt>Set</tt> to wrap
    * @return an <tt>ObservableSet</tt>
    * @throws IllegalArgumentException if <tt>set</tt> is <tt>null</tt>
    */
   public static <E> ObservableSet<E> observableSet(String setId, Set<E> set) {
      if (set == null) {
         throw new IllegalArgumentException("Observed Set must be non-null");
      }
      return new ObservableSetImpl<E>(setId, set);
   }

   /**
    * Creates and returns an <tt>ObservableMap</tt> wrapping the supplied
    * <tt>Map</tt>.
    *
    * @param map the <tt>Map</tt> to wrap  @return an <tt>ObservableMap</tt>
    * @return an <tt>ObservableMap</tt> wrapping the supplied
    * @throws IllegalArgumentException if <tt>map</tt> is <tt>null</tt>
    */
   public static <K, V> ObservableMap<K, V> observableMap(Map<K, V> map) {
      return observableMap(null, map);
   }

   /**
    * Creates and returns an <tt>ObservableMap</tt> wrapping the supplied
    * <tt>Map</tt>.
    *
    * @param mapId an id that identifies this map for observers. May be null.
    * @param map   the <tt>Map</tt> to wrap  @return an <tt>ObservableMap</tt>
    * @return an <tt>ObservableMap</tt> wrapping the supplied
    * @throws IllegalArgumentException if <tt>map</tt> is <tt>null</tt>
    */
   public static <K, V> ObservableMap<K, V> observableMap(String mapId, Map<K, V> map) {
      if (map == null) {
         throw new IllegalArgumentException("Observed Map must be non-null");
      }
      return new ObservableMapImpl<K, V>(mapId, map);
   }


   /**
    * Creates and returns an <tt>ObservableList</tt> wrapping the supplied
    * <tt>List</tt>.
    *
    * @param list the <tt>List</tt> to wrap
    * @return an <tt>ObservableList</tt>
    * @throws IllegalArgumentException if <tt>list</tt> is <tt>null</tt>
    */
   public static <E> ObservableList<E> observableList(List<E> list) {
      return observableList(null, list);
   }

   /**
    * Creates and returns an <tt>ObservableList</tt> wrapping the supplied
    * <tt>List</tt>.
    *
    * @param listId a id that identifies this list for observers. May be null.
    * @param list   the <tt>List</tt> to wrap
    * @return an <tt>ObservableList</tt>
    * @throws IllegalArgumentException if <tt>list</tt> is <tt>null</tt>
    */
   public static <E> ObservableList<E> observableList(String listId, List<E> list) {
      if (list == null) {
         throw new IllegalArgumentException("Observed List must be non-null");
      }
      return new ObservableListImpl<E>(listId, list);
   }


}