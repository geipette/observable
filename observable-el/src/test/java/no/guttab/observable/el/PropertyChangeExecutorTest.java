package no.guttab.observable.el;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.PropertyChangeListener;
import no.guttab.observable.core.Subject;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

public class PropertyChangeExecutorTest {
   Model origin = new Model();
   Model target = new Model();
   PropertyChangeExecutor executor;

   @Before
   public void setUp() throws Exception {
      executor = new PropertyChangeExecutor(target);
      ((Subject) origin).addListener(new PropertyChangeListener() {
         @Override
         public void notifyChange(PropertyChange change) {
            executor.execute(change);
         }
      });
   }

   @Test
   public void shouldPropagate_Model_NameChange_FromOriginToTarget() {
      origin.setName("aNewName");
      assertThat(target.getName(), equalTo("aNewName"));
   }

   @Test
   public void shouldPropagate_SubModel_NameChange_FromOriginToTarget() {
      origin.getSubModel().setName("subModelName");
      assertThat(target.getSubModel().getName(), equalTo("subModelName"));
   }

   @Test
   public void shouldPropagate_SubModelChange_FromOriginToTarget() {
      SubModel subModel = new SubModel("aNewSubModel");
      origin.setSubModel(subModel);
      assertThat(target.getSubModel().getName(), equalTo("aNewSubModel"));
   }

   @Test
   public void shouldPropagate_StringListChange_FromOriginToTarget() {
      List<String> list = new ArrayList<String>();
      origin.setStringList(list);
      assertThat(target.getStringList(), not(nullValue()));
   }

   @Test
   public void shouldPropagate_StringList_ContentAdd_FromOriginToTarget() {
      List<String> list = new ArrayList<String>();
      origin.setStringList(list);

      list.add("bil");
      list.add("båt");

      assertThat(target.getStringList(), not(nullValue()));
      assertThat(target.getStringList(), hasItems("bil", "båt"));
      assertThat(target.getStringList().size(), equalTo(2));
   }

   @Test
   public void shouldPropagate_StringList_ContentSet_FromOriginToTarget() {
      List<String> list = new ArrayList<String>(Arrays.asList("bil", "båt"));
      origin.setStringList(list);

      origin.getStringList().set(1, "fly");

      assertThat(target.getStringList(), not(nullValue()));
      assertThat(target.getStringList(), hasItems("bil", "fly"));
      assertThat(target.getStringList().size(), equalTo(2));
   }

   @Test
   public void shouldPropagate_StringList_ContentRemove_FromOriginToTarget() {
      List<String> list = new ArrayList<String>(Arrays.asList("bil", "båt"));
      origin.setStringList(list);

      origin.getStringList().remove("båt");

      assertThat(target.getStringList(), not(nullValue()));
      assertThat(target.getStringList(), hasItems("bil"));
      assertThat("List: " + target.getStringList(), target.getStringList().size(), equalTo(1));
   }


}
