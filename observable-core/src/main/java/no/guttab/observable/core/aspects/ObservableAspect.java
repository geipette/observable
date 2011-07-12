package no.guttab.observable.core.aspects;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.Subject;
import no.guttab.observable.core.SubjectImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Locale.ENGLISH;

@Aspect
public class ObservableAspect {
   private static final Logger log = LoggerFactory.getLogger(ObservableAspect.class);

   @DeclareParents(value = "(@no.guttab.observable.core.annotation.ObservableBean *)",
         defaultImpl = SubjectImpl.class)
   private Subject subjectMixin;

   @Pointcut("execution(public void set*(..)) && " +
         "@target(no.guttab.observable.core.annotation.ObservableBean)")
   void notifySetProperty() {
   }

//   @Pointcut("execution(public java.util.List get*(..)) && " +
//         "@target(no.guttab.boardgame.smallworld.observable.annotation.ObservableBean) && " +
//         "@annotation(no.guttab.boardgame.smallworld.observable.annotation.ObservableCollection)")
//   void notifyGetList() {
//   }
//
//   @Pointcut("execution(public java.util.Map get*(..)) && " +
//         "@target(no.guttab.boardgame.smallworld.observable.annotation.ObservableBean) && " +
//         "@annotation(no.guttab.boardgame.smallworld.observable.annotation.ObservableCollection)")
//   void notifyGetMap() {
//   }
//
//   @Pointcut("execution(public java.util.Set get*(..)) && " +
//         "@target(no.guttab.boardgame.smallworld.observable.annotation.ObservableBean) && " +
//         "@annotation(no.guttab.boardgame.smallworld.observable.annotation.ObservableCollection)")
//   void notifyGetSet() {
//   }

   @SuppressWarnings({"unchecked"})
   @Around(value = "notifySetProperty() && " +
         "this(subject)", argNames = "pjp,subject")
   public void notifyChangeAdvice(ProceedingJoinPoint pjp, Subject subject) throws Throwable {
      if (pjp.getArgs().length == 1) {
         final String propertyName = getPropertyName(pjp.getSignature().getName());
         final Object arg = pjp.getArgs()[0];
         log.debug("Property '{}' was set to '{}'", propertyName, arg);
         pjp.proceed();
         subject.notifyListeners(new PropertyChange(propertyName, arg));
      }
   }

//   @SuppressWarnings({"unchecked"})
//   @Around(value = "notifyGetList() && " +
//         "@annotation(no.guttab.boardgame.smallworld.observable.annotation.ObservableCollection) && " +
//         "this(subject)", argNames = "pjp,subject")
//   Object wrapList(ProceedingJoinPoint pjp, Subject subject) throws Throwable {
//      log.debug("Observable list accessed: {}", pjp.getSignature().toLongString());
//      final String propertyName = getPropertyName(pjp.getSignature().getName());
//      final ObservableList observableList = ObservableCollections.observableList(propertyName, (List) pjp.proceed());
//      observableList.addObservableListListener(new SubjectListListener(subject));
//      return observableList;
//   }
//
//   @SuppressWarnings({"unchecked"})
//   @Around(value = "notifyGetMap() && " +
//         "@annotation(no.guttab.boardgame.smallworld.observable.annotation.ObservableCollection) && " +
//         "this(subject)", argNames = "pjp,subject")
//   Object wrapMap(ProceedingJoinPoint pjp, Subject subject) throws Throwable {
//      log.debug("Observable map accessed: {}", pjp.getSignature().toLongString());
//      final String propertyName = getPropertyName(pjp.getSignature().getName());
//      final ObservableMap observableMap = ObservableCollections.observableMap(propertyName, (Map) pjp.proceed());
//      observableMap.addObservableMapListener(new SubjectMapListener(subject));
//      return observableMap;
//   }
//
//   @SuppressWarnings({"unchecked"})
//   @Around(value = "notifyGetSet() && " +
//         "@annotation(no.guttab.boardgame.smallworld.observable.annotation.ObservableCollection) && " +
//         "this(subject)", argNames = "pjp,subject")
//   Object wrapSet(ProceedingJoinPoint pjp, Subject subject) throws Throwable {
//      log.debug("Observable set accessed: {}", pjp.getSignature().toLongString());
//      final String propertyName = getPropertyName(pjp.getSignature().getName());
//      final ObservableSet observableSet = ObservableCollections.observableSet(propertyName, (Set) pjp.proceed());
//      observableSet.addObservableSetListener(new SubjectSetListener(subject));
//      return observableSet;
//   }


   private static String getPropertyName(String signatureName) {
      if (signatureName.startsWith("is")) {
         return deCapitalize(signatureName.substring(2));
      } else if (signatureName.startsWith("get") || signatureName.startsWith("set")) {
         return deCapitalize(signatureName.substring(3));
      } else {
         throw new IllegalArgumentException(signatureName + " is not a property read or write method");
      }
   }

   private static String deCapitalize(String s) {
      return s.substring(0, 1).toLowerCase(ENGLISH) + s.substring(1);
   }


}
