package no.guttab.observable.core.aspects;

import no.guttab.observable.core.PropertyChange;
import no.guttab.observable.core.Subject;
import no.guttab.observable.core.SubjectImpl;
import no.guttab.observable.core.listeners.SubjectListener;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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

   @AfterReturning(value = "setFieldOnObservable() && " +
         "this(subject)", argNames = "jp, subject")
   public void setFieldOnObservableAdvice(JoinPoint jp, Subject subject) throws Throwable {
      if (jp.getArgs().length == 1) {
         final String propertyName = jp.getSignature().getName();
         final Object value = jp.getArgs()[0];
         log.debug("Field '{}' was set to '{}'", propertyName, value);
         subject.notifyListeners(new PropertyChange(propertyName, value));
      }
   }

   @AfterReturning(value = "setObservedFieldOnObservable() && " +
         "this(subject)", argNames = "jp, subject")
   public void setObservedFieldOnObservableAdvice(JoinPoint jp, Subject subject) throws Throwable {
      if (jp.getArgs().length == 1) {
         final String propertyName = jp.getSignature().getName();
         final Subject value = (Subject) jp.getArgs()[0];
         value.addListener(new SubjectListener(propertyName, subject));
         subject.notifyListeners(new PropertyChange(propertyName, value));
      }
   }

}
