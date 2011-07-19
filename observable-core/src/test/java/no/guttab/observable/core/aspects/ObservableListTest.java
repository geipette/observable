package no.guttab.observable.core.aspects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;
import no.guttab.observable.core.annotation.Observable;
import no.guttab.observable.core.annotation.ObservableCollection;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

public class ObservableListTest {

   private PropertyChange change;
   private ObservableTypeWithList observableTypeWithList;

   @Before
   public void setUp() throws Exception {
      observableTypeWithList = new ObservableTypeWithList();

      addChangeListenerFor(observableTypeWithList);
   }

   @Test
   public void listContentAddShouldTriggerChange() {
      String newValue = "aString";
      List<String> strings = observableTypeWithList.getListWithStrings();
      strings.add(newValue);

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("listWithStrings[0]"));
      assertThat((String) change.getValue(), sameInstance(newValue));

      change = null;
      strings.add(newValue);

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("listWithStrings[1]"));
      assertThat((String) change.getValue(), sameInstance(newValue));
   }

   @Test
   public void listContentRemoveShouldTriggerChange() {
      List<String> strings = observableTypeWithList.getListWithStrings();
      strings.add("value1");
      strings.add("value2");
      change = null;

      strings.remove(1);

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("listWithStrings.remove(1)"));
      assertThat((String) change.getValue(), equalTo("value2"));

      change = null;
      Iterator<String> iterator = strings.iterator();
      iterator.next();
      iterator.remove();

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("listWithStrings.remove(0)"));
      assertThat((String) change.getValue(), equalTo("value1"));
   }

   @Test
   public void listContentReplaceShouldTriggerChange() {
      List<String> strings = observableTypeWithList.getListWithStrings();
      strings.add("value1");
      strings.add("value2");
      change = null;

      strings.set(1, "value2changed");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("listWithStrings[1]"));
      assertThat((String) change.getValue(), equalTo("value2changed"));

   }

   @Test
   public void listWithObservableTypeShouldTriggerChangeIfObservableTypeChanged() {
      ObservableType propertyOnContentInPos0 = new ObservableType("propertyOnContentInPos0");
      ObservableType propertyOnContentInPos1 = new ObservableType("propertyOnContentInPos1");
      ObservableType propertyOnContentInPos2 = new ObservableType("propertyOnContentInPos2");

      List<ObservableType> observables = observableTypeWithList.getListWithObservables();
      observables.add(new ObservableType("tempPropertyOnContentInPos0"));
      observables.add(propertyOnContentInPos1);
      observables.set(0, propertyOnContentInPos0);
      observables.addAll(Arrays.asList(propertyOnContentInPos2));

      change = null;
      propertyOnContentInPos0.setSimpleProperty("propertyOnContentInPos0Changed");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("listWithObservables[0].simpleProperty"));
      assertThat((String) change.getValue(), equalTo("propertyOnContentInPos0Changed"));

      change = null;
      propertyOnContentInPos1.setSimpleProperty("propertyOnContentInPos1Changed");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("listWithObservables[1].simpleProperty"));
      assertThat((String) change.getValue(), equalTo("propertyOnContentInPos1Changed"));

      change = null;
      propertyOnContentInPos2.setSimpleProperty("propertyOnContentInPos2Changed");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("listWithObservables[2].simpleProperty"));
      assertThat((String) change.getValue(), equalTo("propertyOnContentInPos2Changed"));
   }

   @Test
   public void listWithObservableTypeShouldNotTriggerChangeIfObservableTypeRemovedFromList() {
      ObservableType propertyOnContentInPos0 = new ObservableType("propertyOnContentInPos0");
      ObservableType propertyOnContentInPos1 = new ObservableType("propertyOnContentInPos1");
      ObservableType propertyOnContentInPos2 = new ObservableType("propertyOnContentInPos2");

      List<ObservableType> observables = observableTypeWithList.getListWithObservables();
      observables.add(propertyOnContentInPos0);
      observables.add(propertyOnContentInPos1);
      observables.add(propertyOnContentInPos2);
      observables.remove(1);
      change = null;

      propertyOnContentInPos1.setSimpleProperty("propertyOnContentInPos1Changed");

      assertThat(change, nullValue());

      observables.clear();
      change = null;
      propertyOnContentInPos2.setSimpleProperty("propertyOnContentInPos2Changed");
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
   public static class ObservableTypeWithList {
      @ObservableCollection
      private List<String> listWithStrings = new ArrayList<String>();
      @ObservableCollection
      private List<ObservableType> listWithObservables = new ArrayList<ObservableType>();

      public List<String> getListWithStrings() {
         return listWithStrings;
      }

      public List<ObservableType> getListWithObservables() {
         return listWithObservables;
      }
   }

}
