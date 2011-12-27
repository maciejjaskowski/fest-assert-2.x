package org.fest.assertions.internal;

import org.fest.assertions.error.CaseInsensitiveStringComparator;
import org.fest.util.ComparatorBasedComparisonStrategy;

public class AbstractTest_for_Collections_with_custom_comparison_strategy {

  protected ComparatorBasedComparisonStrategy comparisonStrategy;
  protected Collections collectionsWithCaseInsensitiveComparisonStrategy;

  public AbstractTest_for_Collections_with_custom_comparison_strategy() {
    super();
  }

  protected void initCollectionsWithCustomComparisonStrategy(Failures failures) {
    comparisonStrategy = new ComparatorBasedComparisonStrategy(CaseInsensitiveStringComparator.instance);
    collectionsWithCaseInsensitiveComparisonStrategy = new Collections(comparisonStrategy);
    collectionsWithCaseInsensitiveComparisonStrategy.failures = failures;
  }

}