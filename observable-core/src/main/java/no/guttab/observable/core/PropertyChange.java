package no.guttab.observable.core;

public class PropertyChange<T> {
   private String name;
   private T property;

   public PropertyChange(String name, T property) {
      this.name = name;
      setProperty(property);
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public T getProperty() {
      return property;
   }

   public void setProperty(T property) {
      this.property = property;
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append("PropertyChange");
      sb.append("{name='").append(name).append('\'');
      sb.append(", property=").append(property);
      sb.append('}');
      return sb.toString();
   }

}
