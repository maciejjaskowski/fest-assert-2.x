/*
 * Created on Oct 20, 2010
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
import static org.fest.assertions.error.ShouldNotBeEqual.shouldNotBeEqual;

import org.fest.assertions.core.AssertionInfo;
import org.fest.util.*;

/**
 * Base class of reusable assertions for numbers.
 * 
 * @author Joel Costigliola
 */
public abstract class Numbers<NUMBER extends Comparable<NUMBER>> {

  @VisibleForTesting
  Comparables comparables;
  Failures failures;
  ComparisonStrategy comparisonStrategy;

  public Numbers() {
    this(StandardComparisonStrategy.instance());
  }

  public Numbers(ComparisonStrategy comparisonStrategy) {
    this.comparisonStrategy = comparisonStrategy;
    this.comparables = new Comparables(comparisonStrategy);
    setFailures(Failures.instance());
  }

  protected abstract NUMBER zero();

  @VisibleForTesting
  void setFailures(Failures failures) {
    this.failures = failures;
    this.comparables.failures = failures;
  }

  /**
   * Asserts that the actual value is equal to zero.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is not equal to zero.
   */
  public void assertIsZero(AssertionInfo info, NUMBER actual) {
    comparables.assertEqualByComparison(info, actual, zero());
  }

  /**
   * Asserts that the actual value is not equal to zero.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is equal to zero.
   */
  public void assertIsNotZero(AssertionInfo info, NUMBER actual) {
    comparables.assertNotEqualByComparison(info, actual, zero());
  }

  /**
   * Asserts that the actual value is negative.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is not negative: it is either equal to or greater than zero.
   */
  public void assertIsNegative(AssertionInfo info, NUMBER actual) {
    comparables.assertLessThan(info, actual, zero());
  }

  /**
   * Asserts that the actual value is positive.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is not positive: it is either equal to or less than zero.
   */
  public void assertIsPositive(AssertionInfo info, NUMBER actual) {
    comparables.assertGreaterThan(info, actual, zero());
  }

  /**
   * Asserts that two NUMBERs are equal.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param expected the expected value.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is not equal to the expected one. This method will throw a
   *           {@code org.junit.ComparisonFailure} instead if JUnit is in the classpath and the expected and actual
   *           values are not equal.
   */
  public void assertEqual(AssertionInfo info, NUMBER actual, NUMBER expected) {
    assertNotNull(info, actual);
    if (areEqual(actual, expected)) return;
    throw failures.failure(info, shouldBeEqual(actual, expected, comparisonStrategy));
  }

  protected boolean areEqual(NUMBER actual, NUMBER expected) {
    return comparisonStrategy.areEqual(actual, expected);
  }

  /**
   * Asserts that two NUMBERs are not equal.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param other the value to compare the actual value to.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is equal to the other one.
   */
  public void assertNotEqual(AssertionInfo info, NUMBER actual, NUMBER other) {
    assertNotNull(info, actual);
    if (!areEqual(actual, other)) return;
    throw failures.failure(info, shouldNotBeEqual(actual, other, comparisonStrategy));
  }

  /**
   * Asserts that the actual value is less than the other one.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param other the value to compare the actual value to.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is not less than the other one: this assertion will fail if the actual
   *           value is equal to or greater than the other value.
   */
  public void assertLessThan(AssertionInfo info, NUMBER actual, NUMBER other) {
    comparables.assertLessThan(info, actual, other);
  }

  /**
   * Asserts that the actual value is less than or equal to the other one.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param other the value to compare the actual value to.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is greater than the other one.
   */
  public void assertLessThanOrEqualTo(AssertionInfo info, NUMBER actual, NUMBER other) {
    comparables.assertLessThanOrEqualTo(info, actual, other);
  }

  /**
   * Asserts that the actual value is greater than the other one.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param other the value to compare the actual value to.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is not greater than the other one: this assertion will fail if the
   *           actual value is equal to or less than the other value.
   */
  public void assertGreaterThan(AssertionInfo info, NUMBER actual, NUMBER other) {
    comparables.assertGreaterThan(info, actual, other);
  }

  /**
   * Asserts that the actual value is greater than or equal to the other one.
   * @param info contains information about the assertion.
   * @param actual the actual value.
   * @param other the value to compare the actual value to.
   * @throws AssertionError if the actual value is {@code null}.
   * @throws AssertionError if the actual value is less than the other one.
   */
  public void assertGreaterThanOrEqualTo(AssertionInfo info, NUMBER actual, NUMBER other) {
    comparables.assertGreaterThanOrEqualTo(info, actual, other);
  }

  void assertNotNull(AssertionInfo info, NUMBER actual) {
    Objects.instance().assertNotNull(info, actual);
  }
}