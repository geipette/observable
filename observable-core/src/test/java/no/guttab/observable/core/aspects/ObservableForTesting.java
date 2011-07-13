package no.guttab.observable.core.aspects;

import no.guttab.observable.core.annotation.Observable;

@Observable
public class ObservableForTesting {

   private OtherObjectForTesting otherObjectForTesting = new OtherObjectForTesting();

   private String name;
   public transient String secretName;

   public void changeName(String name) {
      this.name = name;
   }

   public void setSecretName(String secretName) {
      this.secretName = secretName;
   }

   public void setSomeValueInOtherObject(String someValue) {
      otherObjectForTesting.setSomeValue(someValue);
   }
}
