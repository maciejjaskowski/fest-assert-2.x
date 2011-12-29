package org.fest.assertions.internal;

import org.fest.assertions.util.AbsValueComparator;
import org.fest.util.ComparatorBasedComparisonStrategy;

public class AbstractTest_for_Doubles_with_custom_comparison_strategy {

  protected ComparatorBasedComparisonStrategy absValueComparisonStrategy;
  protected Doubles doublesWithAbsValueComparisonStrategy;

  public AbstractTest_for_Doubles_with_custom_comparison_strategy() {
    super();
  }

  protected void initDoublesWithCustomComparisonStrategy(Failures failures) {
    absValueComparisonStrategy = new ComparatorBasedComparisonStrategy(new AbsValueComparator<Double>());
    doublesWithAbsValueComparisonStrategy = new Doubles(absValueComparisonStrategy);
    doublesWithAbsValueComparisonStrategy.failures = failures;
  }

}