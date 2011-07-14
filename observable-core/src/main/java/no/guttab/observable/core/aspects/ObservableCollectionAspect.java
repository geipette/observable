package no.guttab.observable.core.aspects;

import java.util.List;

import no.guttab.observable.core.Subject;
import no.guttab.observable.core.collections.ObservableCollections;
import no.guttab.observable.core.collections.ObservableList;
import no.guttab.observable.core.listeners.SubjectListListener;
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

   @SuppressWarnings({"unchecked"})
   @Around(value = "getListFieldObservable() && " +
         "this(subject)", argNames = "pjp,subject")
   public List wrapList(ProceedingJoinPoint pjp, Subject subject) throws Throwable {
      final String propertyName = pjp.getSignature().getName();
      List list = (List) pjp.proceed(new Object[]{subject});
      if (list != null) {
         ObservableList observableList = ObservableCollections.observableList(propertyName, list);
         observableList.addObservableListListener(new SubjectListListener(subject));
         return observableList;
      }
      return null;
   }

}
