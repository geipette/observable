package no.guttab.observable.core.aspects;

import java.util.Set;

import no.guttab.observable.core.Subject;
import no.guttab.observable.core.collections.ObservableCollections;
import no.guttab.observable.core.collections.ObservableSet;
import no.guttab.observable.core.listeners.SubjectSetListener;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ObservableSetAspect {

   @Pointcut("get(!transient java.util.Set+ *) && " +
         "@target(no.guttab.observable.core.annotation.Observable) && " +
         "@annotation(no.guttab.observable.core.annotation.ObservableCollection)")
   void getSetFieldObservable() {
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
