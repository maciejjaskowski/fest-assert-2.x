package org.fest.assertions.internal;

import org.fest.assertions.util.AbsValueComparator;
import org.fest.util.ComparatorBasedComparisonStrategy;

public class AbstractTest_for_Longs_with_custom_comparison_strategy {

  protected ComparatorBasedComparisonStrategy absValueComparisonStrategy;
  protected Longs longsWithAbsValueComparisonStrategy;

  public AbstractTest_for_Longs_with_custom_comparison_strategy() {
    super();
  }

  protected void initLongsWithCustomComparisonStrategy(Failures failures) {
    absValueComparisonStrategy = new ComparatorBasedComparisonStrategy(new AbsValueComparator<Long>());
    longsWithAbsValueComparisonStrategy = new Longs(absValueComparisonStrategy);
    longsWithAbsValueComparisonStrategy.failures = failures;
  }

}