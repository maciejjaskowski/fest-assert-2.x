package org.fest.assertions.internal;

import org.fest.assertions.util.AbsValueComparator;
import org.fest.util.ComparatorBasedComparisonStrategy;

public class AbstractTest_for_Integers_with_custom_comparison_strategy {

  protected ComparatorBasedComparisonStrategy absValueComparisonStrategy;
  protected Integers integersWithAbsValueComparisonStrategy;

  public AbstractTest_for_Integers_with_custom_comparison_strategy() {
    super();
  }

  protected void initIntegersWithCustomComparisonStrategy(Failures failures) {
    absValueComparisonStrategy = new ComparatorBasedComparisonStrategy(new AbsValueComparator<Integer>());
    integersWithAbsValueComparisonStrategy = new Integers(absValueComparisonStrategy);
    integersWithAbsValueComparisonStrategy.failures = failures;
  }

}