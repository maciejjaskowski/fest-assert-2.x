package org.fest.assertions.internal;

import org.fest.assertions.util.CaseInsensitiveStringComparator;
import org.fest.util.ComparatorBasedComparisonStrategy;

public class AbstractTest_for_Lists_with_custom_comparison_strategy {

  protected ComparatorBasedComparisonStrategy comparisonStrategy;
  protected Lists listsWithCaseInsensitiveComparisonStrategy;

  public AbstractTest_for_Lists_with_custom_comparison_strategy() {
    super();
  }

  protected void initListsWithCustomComparisonStrategy(Failures failures) {
    comparisonStrategy = new ComparatorBasedComparisonStrategy(CaseInsensitiveStringComparator.instance);
    listsWithCaseInsensitiveComparisonStrategy = new Lists(comparisonStrategy);
    listsWithCaseInsensitiveComparisonStrategy.failures = failures;
  }

}