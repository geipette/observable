package no.guttab.observable.core.aspects;

import java.util.HashSet;
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
   public void fieldSetContentChangeShouldTriggerChange() {
      observableTypeWithSet.getSetWithStrings().add("aNewValue");

      assertThat("Field set content change should trigger change", change, not(nullValue()));
      assertThat(change.getName(), equalTo("setWithStrings.add(#arg0)"));
      assertThat((String) change.getValue(), equalTo("aNewValue"));
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
