package org.fest.assertions.internal;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.data.Offset;
import org.fest.util.ComparisonStrategy;

/**
 * Base class of reusable assertions for real numbers (float and double).
 * 
 * @author Joel Costigliola
 */
public abstract class RealNumbers<NUMBER extends Comparable<NUMBER>> extends Numbers<NUMBER> {

  public RealNumbers() {
    super();
  }

  public RealNumbers(ComparisonStrategy comparisonStrategy) {
    super(comparisonStrategy);
  }

  /**
   * Verifies that the actual value is equal to {@code NaN}.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @throws AssertionError if the actual value is not equal to {@code NaN}.
   */
  public void assertIsNaN(AssertionInfo info, NUMBER actual) {
    assertEqualByComparison(info, actual, NaN());
  }

  protected abstract NUMBER NaN();

  /**
   * Verifies that the actual value is not equal to {@code NaN}.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @throws AssertionError if the actual value is equal to {@code NaN}.
   */
  public void assertIsNotNaN(AssertionInfo info, NUMBER actual) {
    assertNotEqualByComparison(info, actual, NaN());
  }

  protected abstract boolean isEqualTo(NUMBER actual, NUMBER expected, Offset<?> offset);

}