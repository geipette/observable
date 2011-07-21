package no.guttab.observable.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PropertyChange {
   private String name;
   private Object value;
   private List<?> arguments = new ArrayList<Object>();

   public PropertyChange(String name, Object value, Object... arguments) {
      this.name = name;
      this.value = value;
      if (arguments != null) {
         this.arguments = Arrays.asList(arguments);
      } else {
         this.arguments = Collections.emptyList();
      }
   }

   public String getName() {
      return name;
   }

   public Object getValue() {
      return value;
   }

   public Object getArg(int argNum) {
      return arguments.get(argNum);
   }

   public int getArgCount() {
      return arguments.size();
   }

   @Override
   public String toString() {
      return "PropertyChange{" +
            "name='" + name + '\'' +
            ", value=" + value +
            ", arguments=" + arguments +
            '}';
   }
}
