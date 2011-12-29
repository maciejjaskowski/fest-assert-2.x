package org.fest.assertions.internal;

import org.fest.assertions.util.CaseInsensitiveCharacterComparator;
import org.fest.util.ComparatorBasedComparisonStrategy;

public class AbstractTest_for_Characters_with_custom_comparison_strategy {

  protected ComparatorBasedComparisonStrategy caseInsensitiveComparisonStrategy;
  protected Characters charactersWithCaseInsensitiveComparisonStrategy;

  public AbstractTest_for_Characters_with_custom_comparison_strategy() {
    super();
  }

  protected void initCharactersWithCustomComparisonStrategy(Failures failures) {
    caseInsensitiveComparisonStrategy = new ComparatorBasedComparisonStrategy(CaseInsensitiveCharacterComparator.instance);
    charactersWithCaseInsensitiveComparisonStrategy = new Characters(caseInsensitiveComparisonStrategy);
    charactersWithCaseInsensitiveComparisonStrategy.failures = failures;
  }

}