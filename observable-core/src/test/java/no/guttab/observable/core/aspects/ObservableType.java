package no.guttab.observable.core.aspects;

import no.guttab.observable.core.annotation.Observable;

@Observable
class ObservableType {
   private String simpleProperty;

   public ObservableType(String simpleProperty) {
      this.simpleProperty = simpleProperty;
   }

   public void setSimpleProperty(String simpleProperty) {
      this.simpleProperty = simpleProperty;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      ObservableType that = (ObservableType) o;

      if (simpleProperty != null ? !simpleProperty.equals(that.simpleProperty) : that.simpleProperty != null)
         return false;

      return true;
   }

   @Override
   public int hashCode() {
      return simpleProperty != null ? simpleProperty.hashCode() : 0;
   }
}
