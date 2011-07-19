package no.guttab.observable.core.aspects;

import java.util.ArrayList;
import java.util.List;

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

   private void addChangeListener(Object observableForTesting) {
      ((Subject) observableForTesting).addListener(new PropertyChangeListener() {
         @Override
         public void notifyChange(PropertyChange notifiedChange) {
            change = notifiedChange;
         }
      });
   }

   @Observable
   public static class ObservableForTesting {
      private OtherObservableForTesting otherObservableForTesting = new OtherObservableForTesting();

      private String name;

      public transient String secretName;

      public void changeName(String name) {
         this.name = name;
      }

      public void setSecretName(String secretName) {
         this.secretName = secretName;
      }

      public void setSomeValueInOtherObject(String someValue) {
         otherObservableForTesting.setSomeValue(someValue);
      }
   }

   @Observable
   public static class OtherObservableForTesting {
      private String someValue;

      @ObservableCollection
      private List<String> listField = new ArrayList<String>();

      public void setSomeValue(String someValue) {
         this.someValue = someValue;
      }

      public List<String> getListField() {
         return listField;
      }

   }
}
