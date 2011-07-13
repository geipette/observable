package no.guttab.observable.core.aspects;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ObservableAspectTest {

   @Test
   public void fieldChangeShouldTriggerChangeOnListener() throws Exception {
      ObservableForTesting observableForTesting = new ObservableForTesting();

      final PropertyChange[] change = new PropertyChange[1];
      ((Subject) observableForTesting).addListener(new PropertyChangeListener() {
         @Override
         public void notifyChange(PropertyChange notifiedChange) {
            change[0] = notifiedChange;
         }
      });

      observableForTesting.changeName("jodle");
      assertThat(change[0], not(nullValue()));
      assertThat(change[0].getName(), equalTo("name"));
      assertThat((String) change[0].getProperty(), equalTo("jodle"));
   }

   @Test
   public void transientFieldChangeShouldNotTriggerChangeOnListener() throws Exception {
      ObservableForTesting observableForTesting = new ObservableForTesting();

      final PropertyChange[] change = new PropertyChange[1];
      ((Subject) observableForTesting).addListener(new PropertyChangeListener() {
         @Override
         public void notifyChange(PropertyChange notifiedChange) {
            change[0] = notifiedChange;
         }
      });

      observableForTesting.setSecretName("jodle");
      assertThat(change[0], nullValue());
   }

   @Test
   public void fieldOfTypeAnnotatedWithObservableInternalChangeShouldTriggerChangeOnListener() {
      ObservableForTesting observableForTesting = new ObservableForTesting();

      final PropertyChange[] change = new PropertyChange[1];
      ((Subject) observableForTesting).addListener(new PropertyChangeListener() {
         @Override
         public void notifyChange(PropertyChange notifiedChange) {
            change[0] = notifiedChange;
         }
      });

      observableForTesting.setSomeValueInOtherObject("aValue");
      assertThat(change[0], not(nullValue()));
      assertThat(change[0].getName(), equalTo("otherObjectForTesting.someValue"));
      assertThat((String) change[0].getProperty(), equalTo("aValue"));
   }


}
