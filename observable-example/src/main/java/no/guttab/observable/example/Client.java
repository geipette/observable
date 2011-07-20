package no.guttab.observable.example;


import no.guttab.observable.core.annotation.Observable;

@Observable
public class Client {
   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
