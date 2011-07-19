package no.guttab.observable.core.aspects;

import java.util.Map;

import no.guttab.observable.core.Subject;
import no.guttab.observable.core.collections.ObservableCollections;
import no.guttab.observable.core.collections.ObservableMap;
import no.guttab.observable.core.listeners.SubjectMapListener;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ObservableMapAspect {
   @Pointcut("get(!transient java.util.Map+ *) && " +
         "@target(no.guttab.observable.core.annotation.Observable) && " +
         "@annotation(no.guttab.observable.core.annotation.ObservableCollection)")
   void getMapFieldObservable() {
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

}
