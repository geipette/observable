package no.guttab.observable.example;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;
import no.guttab.observable.el.PropertyChangeExecutor;

public class Main {
   public static void main(String[] args) {
      Model origin = new Model();
      Model target = new Model();
      final PropertyChangeExecutor executor = new PropertyChangeExecutor(target);

      ((Subject) origin).addListener(new PropertyChangeListener() {
         @Override
         public void notifyChange(PropertyChange change) {
            executor.execute(change);
         }
      });

      origin.setName("newName");

      System.out.println(target.getName());
   }
}
