package no.guttab.observable.core.aspects;

import java.util.ArrayList;
import java.util.List;

import no.guttab.observable.core.annotation.Observable;
import no.guttab.observable.core.annotation.ObservableCollection;

@Observable
public class OtherObservableForTesting {
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
