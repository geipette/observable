package no.guttab.observable.el;

import no.guttab.observable.core.PropertyChange;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class PropertyChangeExecutor {

   private final ExpressionParser parser = new SpelExpressionParser();
   private StandardEvaluationContext evaluationContext;

   public PropertyChangeExecutor(Object model) {
      this.evaluationContext = new StandardEvaluationContext(model);
   }

   public void execute(PropertyChange propertyChange) {
      Expression exp = parser.parseExpression(propertyChange.getName());
//      evaluationContext.setVariable();
//      propertyChange.getValue();

      exp.setValue(evaluationContext, propertyChange.getValue());
   }
}
