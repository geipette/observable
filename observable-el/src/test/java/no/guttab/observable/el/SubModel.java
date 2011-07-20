package no.guttab.observable.el;

import no.guttab.observable.core.annotation.Observable;

@Observable
class SubModel {
   private String name;

   public SubModel() {
   }

   public SubModel(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
