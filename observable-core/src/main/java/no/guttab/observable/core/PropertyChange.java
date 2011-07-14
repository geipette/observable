package no.guttab.observable.core;

public class PropertyChange {
   private String name;
   private Object value;

   public PropertyChange(String name, Object value) {
      this.name = name;
      setValue(value);
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Object getValue() {
      return value;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append("PropertyChange");
      sb.append("{name='").append(name).append('\'');
      sb.append(", value=").append(value);
      sb.append('}');
      return sb.toString();
   }

}
