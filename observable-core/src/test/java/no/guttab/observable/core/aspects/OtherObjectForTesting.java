package no.guttab.observable.core.aspects;

import no.guttab.observable.core.annotation.Observable;

@Observable
public class OtherObjectForTesting {
   private String someValue;

   public void setSomeValue(String someValue) {
      this.someValue = someValue;
   }
}
