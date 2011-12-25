package org.fest.assertions.internal;

import org.fest.assertions.error.CaseInsensitiveStringComparator;
import org.fest.util.ComparatorBasedComparisonStrategy;

public class AbstractTest_for_Strings_with_custom_comparison_strategy {

  protected ComparatorBasedComparisonStrategy comparisonStrategy;
  protected Strings stringsWithCaseInsensitiveComparisonStrategy;

  public AbstractTest_for_Strings_with_custom_comparison_strategy() {
    super();
  }

  protected void initStringsWithCustomComparisonStrategy(Failures failures) {
    comparisonStrategy = new ComparatorBasedComparisonStrategy(CaseInsensitiveStringComparator.instance);
    stringsWithCaseInsensitiveComparisonStrategy = new Strings(comparisonStrategy);
    stringsWithCaseInsensitiveComparisonStrategy.failures = failures;
  }

}