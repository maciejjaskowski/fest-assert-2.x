package org.fest.assertions.internal;

import org.fest.assertions.util.AbsValueComparator;
import org.fest.util.ComparatorBasedComparisonStrategy;

public class AbstractTest_for_Shorts_with_custom_comparison_strategy {

  protected ComparatorBasedComparisonStrategy absValueComparisonStrategy;
  protected Shorts shortsWithAbsValueComparisonStrategy;

  public AbstractTest_for_Shorts_with_custom_comparison_strategy() {
    super();
  }

  protected void initShortsWithCustomComparisonStrategy(Failures failures) {
    absValueComparisonStrategy = new ComparatorBasedComparisonStrategy(new AbsValueComparator<Short>());
    shortsWithAbsValueComparisonStrategy = new Shorts(absValueComparisonStrategy);
    shortsWithAbsValueComparisonStrategy.failures = failures;
  }

}