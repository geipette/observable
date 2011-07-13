package no.guttab.observable.core;

public class PropertyChange {
   private String name;
   private Object property;

   public PropertyChange(String name, Object property) {
      this.name = name;
      setProperty(property);
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Object getProperty() {
      return property;
   }

   public void setProperty(Object property) {
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
