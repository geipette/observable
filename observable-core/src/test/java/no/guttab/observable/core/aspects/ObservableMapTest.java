package no.guttab.observable.core.aspects;

import java.util.HashMap;
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
   public void fieldMapContentChangeShouldTriggerChange() {
      observableTypeWithMap.getMapWithStrings().put("aNewKey", "aNewValue");

      assertThat("Field map content change should trigger change", change, not(nullValue()));
      assertThat(change.getName(), equalTo("mapWithStrings[#arg0]"));

      String key = (String) change.getValue();
      assertThat(key, equalTo("aNewKey"));
      assertThat(observableTypeWithMap.getMapWithStrings().get(key), equalTo("aNewValue"));
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
      private Map<ObservableType, ObservableType> mapWithObservables = new HashMap<ObservableType, ObservableType>();

      public Map<String, String> getMapWithStrings() {
         return mapWithStrings;
      }

      public Map<ObservableType, ObservableType> getMapWithObservables() {
         return mapWithObservables;
      }
   }

}
