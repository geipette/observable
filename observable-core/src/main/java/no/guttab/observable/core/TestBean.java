package no.guttab.observable.core;

import no.guttab.observable.core.annotation.ObservableBean;

@ObservableBean
public class TestBean {
   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
