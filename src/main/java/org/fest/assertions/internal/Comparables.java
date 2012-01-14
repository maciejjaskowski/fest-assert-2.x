/*
 * Created on Oct 17, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010-2011 the original author or authors.
 */
package org.fest.assertions.internal;

import static org.fest.assertions.error.ShouldBeEqual.shouldBeEqual;
import static org.fest.assertions.error.ShouldBeGreater.shouldBeGreater;
import static org.fest.assertions.error.ShouldBeGreaterOrEqual.shouldBeGreaterOrEqual;
import static org.fest.assertions.error.ShouldBeLess.shouldBeLess;
import static org.fest.assertions.error.ShouldBeLessOrEqual.shouldBeLessOrEqual;
import static org.fest.assertions.error.ShouldNotBeEqual.shouldNotBeEqual;

import java.util.Comparator;

import org.fest.assertions.core.AssertionInfo;
import org.fest.util.ComparatorBasedComparisonStrategy;
import org.fest.util.ComparisonStrategy;
import org.fest.util.StandardComparisonStrategy;
import org.fest.util.VisibleForTesting;

/**
 * Reusable assertions for <code>{@link Comparable}</code>s.
 *
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Comparables {

  private static final Comparables INSTANCE = new Comparables();

  /**
   * Returns the singleton instance of this class based on {@link StandardComparisonStrategy}.
   * @return the singleton instance of this class based on {@link StandardComparisonStrategy}.
   */
  public static Comparables instance() {
    return INSTANCE;
  }

  @VisibleForTesting Failures failures = Failures.instance();
  ComparisonStrategy comparisonStrategy;

  @VisibleForTesting Comparables() {
    this(StandardComparisonStrategy.instance());
  }

  public Comparables(ComparisonStrategy comparisonStrategy) {
    this.comparisonStrategy = comparisonStrategy;
  }  

  @VisibleForTesting
  public Comparator<?> getComparator() {
    if (comparisonStrategy instanceof ComparatorBasedComparisonStrategy) {
      return ((ComparatorBasedComparisonStrategy)comparisonStrategy).getComparator();
    }
    return null;
  }

  @VisibleForTesting
  void setFailures(Failures failures) {
    this.failures = failures;
  }

  /**
   * Asserts that two T instances are equal.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param expected the expected value.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is not equal to the expected one. This method will throw a
   *           {@code org.junit.ComparisonFailure} instead if JUnit is in the classpath and the expected and actual
   *           values are not equal.
   */
  public <T> void assertEqual(AssertionInfo info, T actual, T expected) {
    assertNotNull(info, actual);
    if (areEqual(actual, expected)) return;
    throw failures.failure(info, shouldBeEqual(actual, expected, comparisonStrategy));
  }

  protected <T> boolean areEqual(T actual, T expected) {
    return comparisonStrategy.areEqual(actual, expected);
  }

  /**
   * Asserts that two T instances are not equal.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param other the value to compare the actual value to.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is equal to the other one.
   */
  public <T> void assertNotEqual(AssertionInfo info, T actual, T other) {
    assertNotNull(info, actual);
    if (!areEqual(actual, other)) return;
    throw failures.failure(info, shouldNotBeEqual(actual, other, comparisonStrategy));
  }

  /**
   * Asserts that two <code>{@link Comparable}</code>s are equal by invoking
   * <code>{@link Comparable#compareTo(Object)}</code>.
   * @param <T> used to guarantee that two objects of the same type are being compared against each other.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param expected the expected value.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is not equal to the expected one. This method will throw a
   * {@code org.junit.ComparisonFailure} instead if JUnit is in the classpath and the expected and actual
   * values are not equal.
   */
  public <T extends Comparable<T>> void assertEqualByComparison(AssertionInfo info, T actual, T expected) {
    assertNotNull(info, actual);
    // we don't delagate to comparisonStrategy, as this assertion makes it clear it relies on Comparable
    if (actual.compareTo(expected) == 0) return;
    throw failures.failure(info, shouldBeEqual(actual, expected, comparisonStrategy));
  }

  /**
   * Asserts that two <code>{@link Comparable}</code>s are not equal by invoking
   * <code>{@link Comparable#compareTo(Object)}</code>.
   * @param <T> used to guarantee that two objects of the same type are being compared against each other.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param other the value to compare the actual value to.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is equal to the other one.
   */
  public <T extends Comparable<T>> void assertNotEqualByComparison(AssertionInfo info, T actual, T other) {
    assertNotNull(info, actual);
    // we don't delagate to comparisonStrategy, as this assertion makes it clear it relies on Comparable
    if (actual.compareTo(other) != 0) return;
    throw failures.failure(info, shouldNotBeEqual(actual, other));
  }

  /**
   * Asserts that the actual value is less than the other one.
   * @param <T> used to guarantee that two objects of the same type are being compared against each other.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param other the value to compare the actual value to.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is not less than the other one: this assertion will
   * fail if the actual value is equal to or greater than the other value.
   */
  public <T extends Comparable<T>> void assertLessThan(AssertionInfo info, T actual, T other) {
    assertNotNull(info, actual);
    if (isLessThan(actual, other)) return;
    throw failures.failure(info, shouldBeLess(actual, other, comparisonStrategy));
  }

  /**
   * Asserts that the actual value is less than or equal to the other one.
   * @param <T> used to guarantee that two objects of the same type are being compared against each other.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param other the value to compare the actual value to.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is greater than the other one.
   */
  public <T extends Comparable<T>> void assertLessThanOrEqualTo(AssertionInfo info, T actual, T other) {
    assertNotNull(info, actual);
    if (!isGreaterThan(actual, other)) return;
    throw failures.failure(info, shouldBeLessOrEqual(actual, other, comparisonStrategy));
  }

  /**
   * Asserts that the actual value is greater than the other one.
   * @param <T> used to guarantee that two objects of the same type are being compared against each other.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param other the value to compare the actual value to.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is not greater than the other one: this assertion will
   * fail if the actual value is equal to or less than the other value.
   */
  public <T extends Comparable<T>> void assertGreaterThan(AssertionInfo info, T actual, T other) {
    assertNotNull(info, actual);
    if (isGreaterThan(actual, other)) return;
    throw failures.failure(info, shouldBeGreater(actual, other, comparisonStrategy));
  }

  /**
   * delagates to {@link #comparisonStrategy#isGreaterThan(Object, Object)} 
   */
  private boolean isGreaterThan(Object actual, Object other) {
    return comparisonStrategy.isGreaterThan(actual, other);
  }

  /**
   * Asserts that the actual value is greater than or equal to the other one.
   * @param <T> used to guarantee that two objects of the same type are being compared against each other.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param other the value to compare the actual value to.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is less than the other one.
   */
  public <T extends Comparable<T>> void assertGreaterThanOrEqualTo(AssertionInfo info, T actual, T other) {
    assertNotNull(info, actual);
    if (!isLessThan(actual, other)) return;
    throw failures.failure(info, shouldBeGreaterOrEqual(actual, other, comparisonStrategy));
  }

  private boolean isLessThan(Object actual, Object other) {
    return comparisonStrategy.isLessThan(actual, other);
  }

  protected static <T> void assertNotNull(AssertionInfo info, T actual) {
    Objects.instance().assertNotNull(info, actual);
  }
}
