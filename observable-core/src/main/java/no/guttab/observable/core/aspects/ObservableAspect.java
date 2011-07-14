package no.guttab.observable.core.aspects;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.Subject;
import no.guttab.observable.core.SubjectImpl;
import no.guttab.observable.core.listeners.SubjectListener;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareMixin;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class ObservableAspect {
   private static final Logger log = LoggerFactory.getLogger(ObservableAspect.class);

   @DeclareMixin("(@no.guttab.observable.core.annotation.Observable *)")
   public static Subject createSubjectImplementationForObservable() {
      return new SubjectImpl();
   }

   @Pointcut("set(!transient * *) && " +
         "@target(no.guttab.observable.core.annotation.Observable)")
   void setFieldOnObservable() {
   }

   @Pointcut("set(!transient (@no.guttab.observable.core.annotation.Observable *) *) && " +
         "@target(no.guttab.observable.core.annotation.Observable)")
   void setObservedFieldOnObservable() {
   }

   @After(value = "setFieldOnObservable() && " +
         "this(subject)", argNames = "jp, subject")
   public void setFieldOnObservableAdvice(JoinPoint jp, Subject subject) throws Throwable {
      if (jp.getArgs().length == 1) {
         final String propertyName = jp.getSignature().getName();
         final Object arg = jp.getArgs()[0];
         log.debug("Field '{}' was set to '{}'", propertyName, arg);
         subject.notifyListeners(new PropertyChange(propertyName, arg));
      }
   }

   @After(value = "setObservedFieldOnObservable() && " +
         "this(subject)", argNames = "jp, subject")
   public void setObservedFieldOnObservableAdvice(JoinPoint jp, Subject subject) throws Throwable {
      if (jp.getArgs().length == 1) {
         final String propertyName = jp.getSignature().getName();
         final Subject arg = (Subject) jp.getArgs()[0];
         arg.addListener(new SubjectListener(propertyName, subject));
         subject.notifyListeners(new PropertyChange(propertyName, arg));
      }
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
//
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

}
