package no.guttab.observable.core.aspects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;
import no.guttab.observable.core.annotation.Observable;
import no.guttab.observable.core.annotation.ObservableCollection;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

public class ObservableMapTest {

   private PropertyChange change;
   private ObservableTypeWithMap observableTypeWithMap;

   @Before
   public void setUp() throws Exception {
      observableTypeWithMap = new ObservableTypeWithMap();

      addChangeListenerFor(observableTypeWithMap);
   }

   @Test
   public void mapContent_Put_ShouldTriggerChange() {
      String newKey = "keyStr";
      String newValue1 = "valueStr1";
      Map<String, String> mapWithStrings = observableTypeWithMap.getMapWithStrings();

      mapWithStrings.put(newKey, newValue1);

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("mapWithStrings[#arg0]"));
      assertThat((String) change.getArg(0), sameInstance(newKey));
      assertThat((String) change.getValue(), equalTo(newValue1));
   }

   @Test
   public void mapContent_PutReplace_ShouldTriggerChange() {
      String newKey = "keyStr";
      String newValue1 = "valueStr1";
      String newValue2 = "valueStr2";
      Map<String, String> mapWithStrings = observableTypeWithMap.getMapWithStrings();

      mapWithStrings.put(newKey, newValue1);

      change = null;
      mapWithStrings.put(newKey, newValue2);

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("mapWithStrings[#arg0]"));
      assertThat((String) change.getArg(0), sameInstance(newKey));
      assertThat((String) change.getValue(), equalTo("valueStr2"));
   }


   @Test
   public void mapContent_Remove_ShouldTriggerChange() {
      Map<String, String> mapWithStrings = observableTypeWithMap.getMapWithStrings();
      mapWithStrings.put("key1", "value1");
      mapWithStrings.put("key2", "value2");

      change = null;

      mapWithStrings.remove("key1");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("mapWithStrings.remove(#arg0)"));
      assertThat((String) change.getArg(0), equalTo("key1"));
      assertThat((String) change.getValue(), equalTo("value1"));

   }

   @Test
   public void mapContent_AddedWithPutAll_Remove_ShouldTriggerChange() {
      Map<String, String> mapWithStrings = observableTypeWithMap.getMapWithStrings();
      Map<String, String> additionalMapWithStrings = new HashMap<String, String>();
      additionalMapWithStrings.put("key3", "value3");
      mapWithStrings.putAll(additionalMapWithStrings);

      change = null;

      mapWithStrings.remove("key3");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("mapWithStrings.remove(#arg0)"));
      assertThat((String) change.getArg(0), equalTo("key3"));
      assertThat((String) change.getValue(), equalTo("value3"));
   }


   @Test
   public void mapContent_RemoveWithKeySetIterator_ShouldTriggerChange() {
      Map<String, String> mapWithStrings = observableTypeWithMap.getMapWithStrings();
      mapWithStrings.put("key2", "value2");
      change = null;
      Iterator<String> iterator = mapWithStrings.keySet().iterator();
      iterator.next();
      iterator.remove();

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("mapWithStrings.remove(#arg0)"));
      assertThat((String) change.getArg(0), equalTo("key2"));
      assertThat((String) change.getValue(), equalTo("value2"));
   }

   @Test
   public void mapContent_RemoveWithEntrySetIterator_ShouldTriggerChange() {
      Map<String, String> mapWithStrings = observableTypeWithMap.getMapWithStrings();

      mapWithStrings.put("key1", "value1");
      change = null;
      Iterator<Map.Entry<String, String>> entryIterator = mapWithStrings.entrySet().iterator();
      entryIterator.next();
      entryIterator.remove();

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("mapWithStrings.remove(#arg0)"));
      assertThat((String) change.getArg(0), equalTo("key1"));
      assertThat((String) change.getValue(), equalTo("value1"));
   }

   @Test
   public void mapWithObservableTypeValue_ShouldTriggerChange_IfObservableTypeChanged() {
      ObservableType value1 = new ObservableType("value1");
      ObservableType value2 = new ObservableType("value2");
      ObservableType value3 = new ObservableType("value3");

      Map<String, ObservableType> mapWithObservableValues = observableTypeWithMap.getMapWithObservableValues();

      mapWithObservableValues.put("key1", value1);
      mapWithObservableValues.put("key2", value2);
      Map<String, ObservableType> additionalMap = new HashMap<String, ObservableType>();
      additionalMap.put("key3", value3);
      mapWithObservableValues.putAll(additionalMap);

      change = null;
      value1.setSimpleProperty("value1Changed");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("mapWithObservableValues[#arg0].simpleProperty"));
      assertThat((String) change.getArg(0), equalTo("key1"));
      assertThat((String) change.getValue(), equalTo("value1Changed"));

      change = null;
      value3.setSimpleProperty("value3Changed");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("mapWithObservableValues[#arg0].simpleProperty"));
      assertThat((String) change.getArg(0), equalTo("key3"));
      assertThat((String) change.getValue(), equalTo("value3Changed"));
   }

   @Test
   public void mapWithObservableTypeValue_ShouldNotTriggerChange_IfObservableTypeRemoved() {
      ObservableType value1 = new ObservableType("value1");
      ObservableType value2 = new ObservableType("value2");
      ObservableType value3 = new ObservableType("value3");

      Map<String, ObservableType> mapWithObservableValues = observableTypeWithMap.getMapWithObservableValues();

      mapWithObservableValues.put("key1", value1);
      mapWithObservableValues.put("key2", value2);
      Map<String, ObservableType> additionalMap = new HashMap<String, ObservableType>();
      additionalMap.put("key3", value3);
      mapWithObservableValues.putAll(additionalMap);

      mapWithObservableValues.remove("key1");
      mapWithObservableValues.put("key2", new ObservableType("value2replaced"));
      mapWithObservableValues.clear();

      change = null;
      value1.setSimpleProperty("value1changed");
      assertThat(change, nullValue());

      value2.setSimpleProperty("value2changed");
      assertThat(change, nullValue());
      value3.setSimpleProperty("value3changed");
      assertThat(change, nullValue());
   }

   private void addChangeListenerFor(Object observableForTesting) {
      ((Subject) observableForTesting).addListener(new PropertyChangeListener() {
         @Override
         public void notifyChange(PropertyChange notifiedChange) {
            change = notifiedChange;
         }
      });
   }

   @Observable
   public static class ObservableTypeWithMap {
      @ObservableCollection
      private Map<String, String> mapWithStrings = new HashMap<String, String>();
      @ObservableCollection
      private Map<String, ObservableType> mapWithObservableValues = new HashMap<String, ObservableType>();

      public Map<String, String> getMapWithStrings() {
         return mapWithStrings;
      }

      public Map<String, ObservableType> getMapWithObservableValues() {
         return mapWithObservableValues;
      }

   }

}
