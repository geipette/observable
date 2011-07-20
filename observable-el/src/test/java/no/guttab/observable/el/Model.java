package no.guttab.observable.el;


import java.util.List;

import no.guttab.observable.core.annotation.Observable;
import no.guttab.observable.core.annotation.ObservableCollection;

@Observable
class Model {

   private String name;
   private SubModel subModel = new SubModel();

   @ObservableCollection
   private List<String> stringList;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public SubModel getSubModel() {
      return subModel;
   }

   public void setSubModel(SubModel subModel) {
      this.subModel = subModel;
   }

   public List<String> getStringList() {
      return stringList;
   }

   public void setStringList(List<String> stringList) {
      this.stringList = stringList;
   }
}
