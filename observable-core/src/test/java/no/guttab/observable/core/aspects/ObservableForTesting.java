package no.guttab.observable.core.aspects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import no.guttab.observable.core.annotation.Observable;
import no.guttab.observable.core.annotation.ObservableCollection;

@Observable
public class ObservableForTesting {

   private OtherObservableForTesting otherObservableForTesting = new OtherObservableForTesting();

   @ObservableCollection
   private Map<String, String> mapField = new HashMap<String, String>();

   @ObservableCollection
   private Set<String> setField = new HashSet<String>();

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

   public Map<String, String> getMapField() {
      return mapField;
   }

   public Set<String> getSetField() {
      return setField;
   }

}
