package no.guttab.observable.el;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.guttab.observable.core.PropertyChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class PropertyChangeExecutor {
   private static final Logger log = LoggerFactory.getLogger(PropertyChangeExecutor.class);

   private final ExpressionParser parser = new SpelExpressionParser();
   private StandardEvaluationContext evaluationContext;

   public PropertyChangeExecutor(Object model) {
      this.evaluationContext = new StandardEvaluationContext(model);
      evaluationContext.addMethodResolver(new OneIntArgMethodResolver(List.class, "remove"));
   }

   public void execute(PropertyChange propertyChange) {
      log.debug("Executing propertyChange: '{}'", propertyChange.getName());
      final Expression exp = parser.parseExpression(propertyChange.getName());
      setVariables(propertyChange);
      if (exp.isWritable(evaluationContext)) {
         exp.setValue(evaluationContext, propertyChange.getValue());
      } else {
         exp.getValue(evaluationContext);
      }
   }

   private void setVariables(PropertyChange propertyChange) {
      final Map<String, Object> variables = new HashMap<String, Object>();
      for (int i = 0; i < propertyChange.getArgCount(); i++) {
         final String argName = "arg" + i;
         variables.put(argName, propertyChange.getArg(i));
      }
      if (variables.size() > 0) {
         evaluationContext.setVariables(variables);
      } else {
         evaluationContext.setVariables(Collections.<String, Object>emptyMap());
      }
   }
}
