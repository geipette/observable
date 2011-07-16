package no.guttab.observable.core.aspects;

import java.util.ArrayList;
import java.util.List;

import no.guttab.observable.core.annotation.Observable;
import no.guttab.observable.core.annotation.ObservableCollection;

@Observable
public class ObservableForTesting {

   private OtherObservableForTesting otherObservableForTesting = new OtherObservableForTesting();

   @ObservableCollection
   private List<String> listField = new ArrayList<String>();

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

   public OtherObservableForTesting getOtherObservableForTesting() {
      return otherObservableForTesting;
   }

   public List<String> getListField() {
      return listField;
   }
}