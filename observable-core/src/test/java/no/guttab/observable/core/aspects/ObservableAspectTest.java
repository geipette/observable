package no.guttab.observable.core.aspects;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ObservableAspectTest {
   private ObservableForTesting observableForTesting;
   private PropertyChange[] change;

   @Before
   public void setUp() throws Exception {
      observableForTesting = new ObservableForTesting();
      change = new PropertyChange[1];
      addChangeListener(observableForTesting, change);
   }

   @Test
   public void fieldChangeShouldTriggerChange() throws Exception {

      observableForTesting.changeName("jodle");

      assertThat(change[0], not(nullValue()));
      assertThat(change[0].getName(), equalTo("name"));
      assertThat((String) change[0].getValue(), equalTo("jodle"));
   }

   @Test
   public void transientFieldChangeShouldNotTriggerChange() throws Exception {
      observableForTesting.setSecretName("jodle");

      assertThat(change[0], nullValue());
   }

   @Test
   public void fieldOfTypeAnnotatedWithObservableInternalChangeShouldTriggerChange() {
      observableForTesting.setSomeValueInOtherObject("aValue");

      assertThat(change[0], not(nullValue()));
      assertThat(change[0].getName(), equalTo("otherObservableForTesting.someValue"));
      assertThat((String) change[0].getValue(), equalTo("aValue"));
   }

   @Test
   public void fieldListContentChangeShouldTriggerChange() {
      observableForTesting.getListField().add("aNewListItem");

      assertThat("Field list content change should trigger change", change[0], not(nullValue()));
      assertThat(change[0].getName(), equalTo("listField[0]"));
      assertThat((String) change[0].getValue(), equalTo("aNewListItem"));
   }

   @Test
   public void fieldOfTypeAnnotatedWithObservableFieldListContentChangeShouldTriggerChange() {
      observableForTesting.getOtherObservableForTesting().getListField().add("aNewListItem");

      assertThat("Field list content change should trigger change", change[0], not(nullValue()));
      assertThat(change[0].getName(), equalTo("otherObservableForTesting.listField[0]"));
      assertThat((String) change[0].getValue(), equalTo("aNewListItem"));
   }

   @Test
   public void fieldOfTypeAnnotatedWithObservableFieldMapContentChangeShouldTriggerChange() {
      observableForTesting.getOtherObservableForTesting().getMapField().put("aNewKey", "aNewValue");

      assertThat("Field map content change should trigger change", change[0], not(nullValue()));
      assertThat(change[0].getName(), equalTo("otherObservableForTesting.mapField['aNewKey']"));
      assertThat((String) change[0].getValue(), equalTo("aNewValue"));
   }


   private static void addChangeListener(Object observableForTesting, final PropertyChange[] change) {
      ((Subject) observableForTesting).addListener(new PropertyChangeListener() {
         @Override
         public void notifyChange(PropertyChange notifiedChange) {
            change[0] = notifiedChange;
         }
      });
   }


}
