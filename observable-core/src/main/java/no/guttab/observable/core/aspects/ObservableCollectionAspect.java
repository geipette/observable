package no.guttab.observable.core.aspects;

import java.util.List;
import java.util.Map;
import java.util.Set;

import no.guttab.observable.core.Subject;
import no.guttab.observable.core.collections.ObservableCollections;
import no.guttab.observable.core.collections.ObservableList;
import no.guttab.observable.core.collections.ObservableMap;
import no.guttab.observable.core.collections.ObservableSet;
import no.guttab.observable.core.listeners.SubjectListListener;
import no.guttab.observable.core.listeners.SubjectMapListener;
import no.guttab.observable.core.listeners.SubjectSetListener;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class ObservableCollectionAspect {
   private static final Logger log = LoggerFactory.getLogger(ObservableCollectionAspect.class);

   @Pointcut("get(!transient java.util.List+ *) && " +
         "@target(no.guttab.observable.core.annotation.Observable) && " +
         "@annotation(no.guttab.observable.core.annotation.ObservableCollection)")
   void getListFieldObservable() {
   }

   @Pointcut("get(!transient java.util.Map+ *) && " +
         "@target(no.guttab.observable.core.annotation.Observable) && " +
         "@annotation(no.guttab.observable.core.annotation.ObservableCollection)")
   void getMapFieldObservable() {
   }

   @Pointcut("get(!transient java.util.Set+ *) && " +
         "@target(no.guttab.observable.core.annotation.Observable) && " +
         "@annotation(no.guttab.observable.core.annotation.ObservableCollection)")
   void getSetFieldObservable() {
   }

   @SuppressWarnings({"unchecked"})
   @Around(value = "getListFieldObservable() && " +
         "this(subject)", argNames = "pjp,subject")
   public List wrapList(ProceedingJoinPoint pjp, Subject subject) throws Throwable {
      final String propertyName = pjp.getSignature().getName();
      List list = (List) pjp.proceed(new Object[]{subject});
      if (list != null) {
         final ObservableList observableList = ObservableCollections.observableList(propertyName, list);
         observableList.addObservableListListener(new SubjectListListener(subject));
         return observableList;
      }
      return null;
   }

   @SuppressWarnings({"unchecked"})
   @Around(value = "getMapFieldObservable() && " +
         "this(subject)", argNames = "pjp,subject")
   public Map wrapMap(ProceedingJoinPoint pjp, Subject subject) throws Throwable {
      final String propertyName = pjp.getSignature().getName();
      Map map = (Map) pjp.proceed(new Object[]{subject});
      if (map != null) {
         final ObservableMap observableMap = ObservableCollections.observableMap(propertyName, map);
         observableMap.addObservableMapListener(new SubjectMapListener(subject));
         return observableMap;
      }
      return null;
   }

   @SuppressWarnings({"unchecked"})
   @Around(value = "getSetFieldObservable() && " +
         "this(subject)", argNames = "pjp,subject")
   public Set wrapSet(ProceedingJoinPoint pjp, Subject subject) throws Throwable {
      final String propertyName = pjp.getSignature().getName();
      Set set = (Set) pjp.proceed(new Object[]{subject});
      if (set != null) {
         final ObservableSet observableSet = ObservableCollections.observableSet(propertyName, set);
         observableSet.addObservableSetListener(new SubjectSetListener(subject));
         return observableSet;
      }
      return null;
   }

}
