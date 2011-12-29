package org.fest.assertions.internal;

import org.fest.assertions.util.AbsValueComparator;
import org.fest.util.ComparatorBasedComparisonStrategy;

public class AbstractTest_for_Floats_with_custom_comparison_strategy {

  protected ComparatorBasedComparisonStrategy absValueComparisonStrategy;
  protected Floats floatsWithAbsValueComparisonStrategy;

  public AbstractTest_for_Floats_with_custom_comparison_strategy() {
    super();
  }

  protected void initFloatsWithCustomComparisonStrategy(Failures failures) {
    absValueComparisonStrategy = new ComparatorBasedComparisonStrategy(new AbsValueComparator<Float>());
    floatsWithAbsValueComparisonStrategy = new Floats(absValueComparisonStrategy);
    floatsWithAbsValueComparisonStrategy.failures = failures;
  }

}