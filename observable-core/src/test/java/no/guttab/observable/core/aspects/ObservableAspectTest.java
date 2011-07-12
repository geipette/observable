package no.guttab.observable.core.aspects;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;
import no.guttab.observable.core.TestBean;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class ObservableAspectTest {
   @Test
   public void testPropertyChangeAspect() throws Exception {
      final boolean[] changeNotified = {false};
      TestBean testBean = new TestBean();

      ((Subject) testBean).addListener(new PropertyChangeListener() {
         @Override
         public void notifyChange(PropertyChange change) {
            assertTrue("name".equals(change.getName()));
            assertTrue("jodle".equals(change.getProperty()));
            changeNotified[0] = true;
         }
      });
      testBean.setName("jodle");
      assertTrue(changeNotified[0]);
   }
}
