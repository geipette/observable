package no.guttab.observable.core.aspects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

public class ObservableSetTest {

   private PropertyChange change;
   private ObservableTypeWithSet observableTypeWithSet;

   @Before
   public void setUp() throws Exception {
      observableTypeWithSet = new ObservableTypeWithSet();

      addChangeListenerFor(observableTypeWithSet);
   }

   @Test
   public void setContent_Add_ShouldTriggerChange() {
      String newValue1 = "valueStr1";
      Set<String> setWithStrings = observableTypeWithSet.getSetWithStrings();

      setWithStrings.add(newValue1);

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("setWithStrings.add(#arg0)"));
      assertThat((String) change.getValue(), sameInstance(newValue1));
   }

   @Test
   public void setContent_Replace_ShouldTriggerChange() {
      String newValue1 = "valueStr1";
      String newValue2 = "valueStr2";
      Set<String> setWithStrings = observableTypeWithSet.getSetWithStrings();

      setWithStrings.add(newValue1);

      change = null;
      setWithStrings.add(newValue2);

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("setWithStrings.add(#arg0)"));
      assertThat((String) change.getValue(), sameInstance(newValue2));
   }

   @Test
   public void setContent_Remove_ShouldTriggerChange() {
      Set<String> setWithStrings = observableTypeWithSet.getSetWithStrings();
      setWithStrings.add("value1");

      change = null;

      setWithStrings.remove("value1");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("setWithStrings.remove(#arg0)"));
      assertThat((String) change.getValue(), equalTo("value1"));
   }

   @Test
   public void setContent_AddedWithAddAll_Remove_ShouldTriggerChange() {
      Set<String> setWithStrings = observableTypeWithSet.getSetWithStrings();
      setWithStrings.addAll(Arrays.asList("value1"));

      change = null;

      setWithStrings.remove("value1");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("setWithStrings.remove(#arg0)"));
      assertThat((String) change.getValue(), equalTo("value1"));
   }

   @Test
   public void setContent_RemoveWithIterator_ShouldTriggerChange() {
      Set<String> setWithStrings = observableTypeWithSet.getSetWithStrings();
      setWithStrings.add("value1");

      change = null;
      Iterator<String> iterator = setWithStrings.iterator();
      iterator.next();
      iterator.remove();

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("setWithStrings.remove(#arg0)"));
      assertThat((String) change.getValue(), equalTo("value1"));
   }

   @Test
   public void setWithObservableType_ShouldTriggerChange_IfObservableTypeChanged() {
      ObservableType value1 = new ObservableType("value1");

      Set<ObservableType> setWithObservables = observableTypeWithSet.getSetWithObservables();
      setWithObservables.add(value1);

      change = null;
      value1.setSimpleProperty("value1changed");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("setWithObservables[#arg0].simpleProperty"));
      assertThat((ObservableType) change.getArg(0), equalTo(value1));
      assertThat((String) change.getValue(), equalTo("value1changed"));
   }

   @Test
   public void setWithObservableType_ShouldNotTriggerChange_IfObservableRemovedFromSet() {
      ObservableType value1 = new ObservableType("value1");
      Set<ObservableType> setWithObservables = observableTypeWithSet.getSetWithObservables();
      setWithObservables.add(value1);
      setWithObservables.remove(value1);

      change = null;
      value1.setSimpleProperty("value1changed");

      assertThat(change, nullValue());
   }

   @Observable
   public static class ObservableTypeWithSet {
      @ObservableCollection
      private Set<String> setWithStrings = new HashSet<String>();
      @ObservableCollection
      private Set<ObservableType> setWithObservables = new HashSet<ObservableType>();

      public Set<String> getSetWithStrings() {
         return setWithStrings;
      }

      public Set<ObservableType> getSetWithObservables() {
         return setWithObservables;
      }
   }

   private void addChangeListenerFor(Object observableForTesting) {
      ((Subject) observableForTesting).addListener(new PropertyChangeListener() {
         @Override
         public void notifyChange(PropertyChange notifiedChange) {
            change = notifiedChange;
         }
      });
   }


}
