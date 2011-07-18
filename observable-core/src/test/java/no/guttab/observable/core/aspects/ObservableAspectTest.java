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
   private PropertyChange change;

   @Before
   public void setUp() throws Exception {
      observableForTesting = new ObservableForTesting();
      addChangeListener(observableForTesting);
   }

   @Test
   public void fieldChangeShouldTriggerChange() throws Exception {

      observableForTesting.changeName("jodle");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("name"));
      assertThat((String) change.getValue(), equalTo("jodle"));
   }

   @Test
   public void transientFieldChangeShouldNotTriggerChange() throws Exception {
      observableForTesting.setSecretName("jodle");

      assertThat(change, nullValue());
   }

   @Test
   public void fieldOfTypeAnnotatedWithObservableInternalChangeShouldTriggerChange() {
      observableForTesting.setSomeValueInOtherObject("aValue");

      assertThat(change, not(nullValue()));
      assertThat(change.getName(), equalTo("otherObservableForTesting.someValue"));
      assertThat((String) change.getValue(), equalTo("aValue"));
   }

   @Test
   public void fieldListContentChangeShouldTriggerChange() {
      observableForTesting.getListField().add("aNewListItem");

      assertThat("Field list content change should trigger change", change, not(nullValue()));
      assertThat(change.getName(), equalTo("listField[0]"));
      assertThat((String) change.getValue(), equalTo("aNewListItem"));
   }

   @Test
   public void fieldMapContentChangeShouldTriggerChange() {
      observableForTesting.getMapField().put("aNewKey", "aNewValue");

      assertThat("Field map content change should trigger change", change, not(nullValue()));
      assertThat(change.getName(), equalTo("mapField[#arg0]"));

      String key = (String) change.getValue();
      assertThat(key, equalTo("aNewKey"));
      assertThat(observableForTesting.getMapField().get(key), equalTo("aNewValue"));
   }

   @Test
   public void fieldSetContentChangeShouldTriggerChange() {
      observableForTesting.getSetField().add("aNewValue");

      assertThat("Field set content change should trigger change", change, not(nullValue()));
      assertThat(change.getName(), equalTo("setField.add(#arg0)"));
      assertThat((String) change.getValue(), equalTo("aNewValue"));
   }

   @Test
   public void fieldOfTypeAnnotatedWithObservableFieldListContentChangeShouldTriggerChange() {
      observableForTesting.getOtherObservableForTesting().getListField().add("aNewListItem");

      assertThat("Field list content change should trigger change", change, not(nullValue()));
      assertThat(change.getName(), equalTo("otherObservableForTesting.listField[0]"));
      assertThat((String) change.getValue(), equalTo("aNewListItem"));
   }

   private void addChangeListener(Object observableForTesting) {
      ((Subject) observableForTesting).addListener(new PropertyChangeListener() {
         @Override
         public void notifyChange(PropertyChange notifiedChange) {
            change = notifiedChange;
         }
      });
   }


}
