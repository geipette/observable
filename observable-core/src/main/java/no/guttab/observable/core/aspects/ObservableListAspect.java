package no.guttab.observable.core.aspects;

import java.util.List;
import java.util.WeakHashMap;

import no.guttab.observable.core.Subject;
import no.guttab.observable.core.collections.ObservableCollections;
import no.guttab.observable.core.collections.ObservableList;
import no.guttab.observable.core.listeners.SubjectListListener;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ObservableListAspect {
    private static final WeakHashMap<List, ObservableList> weakListMap = new WeakHashMap<List, ObservableList>();

   @Pointcut("get(!transient java.util.List+ *) && " +
         "@target(no.guttab.observable.core.annotation.Observable) && " +
         "@annotation(no.guttab.observable.core.annotation.ObservableCollection)")
   void getListFieldObservable() {
   }

   @Around(value = "getListFieldObservable() && " +
         "this(subject)", argNames = "pjp,subject")
   public List wrapList(ProceedingJoinPoint pjp, Subject subject) throws Throwable {
      final String propertyName = pjp.getSignature().getName();
      List list = (List) pjp.proceed(new Object[]{subject});
      if (list != null) {
         return getObservableList(propertyName, list, subject);
      }
      return null;
   }

    @SuppressWarnings({"unchecked"})
    private ObservableList getObservableList(String listId, List list, Subject subject) {
        if (list instanceof ObservableList) {
            return (ObservableList) list;
        }
        ObservableList observableList = weakListMap.get(list);
        if (observableList == null) {
            observableList = ObservableCollections.observableList(listId, list);
            observableList.addObservableListListener(new SubjectListListener(subject));
            weakListMap.put(list, observableList);
        }
        return observableList;
    }
}
