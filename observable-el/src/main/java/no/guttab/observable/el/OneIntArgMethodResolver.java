package no.guttab.observable.el;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodExecutor;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.TypedValue;
import org.springframework.util.ReflectionUtils;

public class OneIntArgMethodResolver implements MethodResolver {
   private static final Logger log = LoggerFactory.getLogger(OneIntArgMethodResolver.class);

   private final Class<?> clazz;
   private final String methodName;

   public OneIntArgMethodResolver(Class<?> clazz, String methodName) {
      this.clazz = clazz;
      this.methodName = methodName;
   }

   @Override
   public MethodExecutor resolve(
         EvaluationContext context, Object targetObject, String name, List<TypeDescriptor> argumentTypes)
         throws AccessException {
      if (clazz.isAssignableFrom(targetObject.getClass()) &&
            name.equals(methodName) &&
            argumentTypes.size() == 1 &&
            argumentTypes.get(0).getType().equals(Integer.class)) {
         log.debug("Using OneIntArgMethodResolver for {}.{}", targetObject, name);
         try {
            final Method method = targetObject.getClass().getMethod(methodName, int.class);
            ReflectionUtils.makeAccessible(method);
            return new MethodExecutor() {
               @Override
               public TypedValue execute(EvaluationContext context, Object target, Object... arguments)
                     throws AccessException {
                  try {
                     return new TypedValue(
                           method.invoke(target, arguments), new TypeDescriptor(new MethodParameter(method, -1)));
                  } catch (IllegalAccessException e) {
                     throw new AccessException("Illegal access", e);
                  } catch (InvocationTargetException e) {
                     throw new AccessException("Target method threw an exception", e);
                  }
               }
            };
         } catch (NoSuchMethodException e) {
            log.error("Could not find method '{}(int)' on: {}", targetObject);
         }
      }
      return null;
   }
}
