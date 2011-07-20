package no.guttab.observable.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PropertyChange {
   private String name;
   private List<?> values = new ArrayList<Object>();

   public PropertyChange(String name, Object... values) {
      this.name = name;
      if (values != null) {
         this.values = Arrays.asList(values);
      } else {
         this.values = Collections.emptyList();
      }
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Object getValue() {
      return getArg(getArgCount());
   }

   public Object getArg(int argNum) {
      return values.get(argNum);
   }

   public int getArgCount() {
      return values.size() - 1;
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append("PropertyChange");
      sb.append("{name='").append(name).append('\'');
      sb.append(", values=").append(values);
      sb.append('}');
      return sb.toString();
   }

}
