/*
 * Created on Aug 5, 2010
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
package org.fest.assertions.error;

import static java.lang.Integer.toHexString;

import static org.fest.util.Arrays.array;
import static org.fest.util.Objects.*;
import static org.fest.util.ToString.toStringOf;

import java.util.Comparator;

import org.fest.assertions.description.Description;
import org.fest.assertions.internal.Failures;
import org.fest.util.VisibleForTesting;

/**
 * Creates an <code>{@link AssertionError}</code> indicating that an assertion that verifies that two objects are equal
 * failed.
 * <p>
 * The built {@link AssertionError}'s message differentiates {@link #actual} and {@link #expected} description if their
 * string representation are the same (e.g. 42 float and 42 double). It also mentions the comparator in case of a custom
 * comparator is used (instead of equals method).
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Joel Costigliola
 */
public class ShouldBeEqual implements AssertionErrorFactory {

  private static final String EXPECTED_BUT_WAS_MESSAGE = "expected:<%s> but was:<%s>";
  private static final String EXPECTED_BUT_WAS_MESSAGE_USING_COMPARATOR = "Expecting actual:<%s> to be equal to <%s> according to %s comparator but was not.";

  private static final Class<?>[] MSG_ARG_TYPES = new Class<?>[] { String.class, String.class, String.class };

  @VisibleForTesting
  ConstructorInvoker constructorInvoker = new ConstructorInvoker();
  @VisibleForTesting
  MessageFormatter messageFormatter = MessageFormatter.instance();
  @VisibleForTesting
  DescriptionFormatter descriptionFormatter = DescriptionFormatter.instance();

  private final Object actual;
  private final Object expected;
  private final Comparator<Object> comparator;

  /**
   * Creates a new <code>{@link ShouldBeEqual}</code>.
   * @param actual the actual value in the failed assertion.
   * @param expected the expected value in the failed assertion.
   * @return the created {@code AssertionErrorFactory}.
   */
  public static AssertionErrorFactory shouldBeEqual(Object actual, Object expected) {
    return new ShouldBeEqual(actual, expected);
  }

  /**
   * Creates a new <code>{@link ShouldBeEqual}</code>.
   * @param actual the actual value in the failed assertion.
   * @param expected the expected value in the failed assertion.
   * @return the created {@code AssertionErrorFactory}.
   */
  public static AssertionErrorFactory shouldBeEqualUsingComparator(Object actual, Object expected,
      Comparator<Object> comparator) {
    return new ShouldBeEqual(actual, expected, comparator);
  }

  @VisibleForTesting
  ShouldBeEqual(Object actual, Object expected) {
    this(actual, expected, null);
  }

  @VisibleForTesting
  ShouldBeEqual(Object actual, Object expected, Comparator<Object> comparator) {
    this.actual = actual;
    this.expected = expected;
    this.comparator = comparator;
  }

  /**
   * Creates an <code>{@link AssertionError}</code> indicating that an assertion that verifies that two objects are
   * equal failed.<br>
   * The <code>{@link AssertionError}</code> message is built so that it differentiates {@link #actual} and
   * {@link #expected} description in case their string representation are the same (like 42 float and 42 double).
   * <p>
   * If JUnit 4 is in the classpath and the description is standard (no comparator was used {@link #actual} and
   * {@link #expected} string representation were differents), this method will instead create a
   * {@code org.junit.ComparisonFailure} that highlights the difference(s) between the expected and actual objects.
   * </p>
   * {@link AssertionError} stack trace won't show Fest related elements if {@link Failures} is configured to filter
   * them (see {@link Failures#setRemoveFestRelatedElementsFromStackTrace(boolean)}).
   * 
   * @param description the description of the failed assertion.
   * @return the created {@code AssertionError}.
   */
  public AssertionError newAssertionError(Description description) {
    if (actualAndExpectedHaveSameStringRepresentation()) {
      // Example : actual = 42f and expected = 42d gives actual : "42" and expected : "42" and
      // JUnit 4 manages this case even worst, it will output something like :
      // "java.lang.String expected:java.lang.String<42.0> but was: java.lang.String<42.0>"
      // which does not solve the problem and makes things even more confusing since we lost the fact that 42 was a
      // float or a double, it is then better to built our own description, with the drawback of not using a
      // ComparisonFailure (which looks nice in eclipse)
      String failureMessage = comparatorWasUsed() ? defaultDetailedErrorMessageWithComparatorDescription(description)
          : defaultDetailedErrorMessage(description);
      return Failures.instance().failure(failureMessage);
    }
    // if comparison strategy was based on a custom comparator, we build the assertion error message, the result is
    // better than the JUnit ComparisonFailure we could build (that would not mention the comparator).
    if (comparatorWasUsed())
      return Failures.instance().failure(defaultErrorMessageWithComparatorDescription(description));
    // try to build a JUnit ComparisonFailure that offers a nice IDE integration.
    AssertionError error = comparisonFailure(description);
    if (error != null) {
      Failures.instance().removeFestRelatedElementsFromStackTraceIfNeeded(error);
      return error;
    }
    // No JUnit in the classpath => fall back to default error message.
    return Failures.instance().failure(defaultErrorMessage(description));
  }

  private boolean actualAndExpectedHaveSameStringRepresentation() {
    return areEqual(toStringOf(actual), toStringOf(expected));
  }

  private String defaultErrorMessageWithComparatorDescription(Description description) {
    return messageFormatter.format(description, EXPECTED_BUT_WAS_MESSAGE_USING_COMPARATOR, actual, expected,
        comparatorDescription());
  }

  private String defaultErrorMessage(Description description) {
    return messageFormatter.format(description, EXPECTED_BUT_WAS_MESSAGE, expected, actual);
  }

  private String defaultDetailedErrorMessageWithComparatorDescription(Description description) {
    return messageFormatter.format(description, EXPECTED_BUT_WAS_MESSAGE_USING_COMPARATOR, expectedDetailedToString(),
        actualDetailedToString(), comparatorDescription());
  }

  /**
   * Returns a human readable {@link #comparator} description.
   * @return a human readable {@link #comparator} description.
   */
  private String comparatorDescription() {
    String comparatorSimpleClassName = comparator.getClass().getSimpleName();
    return comparatorSimpleClassName.length() > 0 ? comparatorSimpleClassName : "anonymous class";
  }

  private String defaultDetailedErrorMessage(Description description) {
    return messageFormatter.format(description, EXPECTED_BUT_WAS_MESSAGE, expectedDetailedToString(),
        actualDetailedToString());
  }

  private boolean comparatorWasUsed() {
    return comparator != null;
  }

  private AssertionError comparisonFailure(Description description) {
    try {
      return newComparisonFailure(descriptionFormatter.format(description).trim());
    } catch (Throwable e) {
      return null;
    }
  }

  private AssertionError newComparisonFailure(String description) throws Exception {
    String className = "org.junit.ComparisonFailure";
    Object o = constructorInvoker.newInstance(className, MSG_ARG_TYPES, msgArgs(description));
    if (o instanceof AssertionError) return (AssertionError) o;
    return null;
  }

  private Object[] msgArgs(String description) {
    return array(description, toStringOf(expected), toStringOf(actual));
  }

  private static String detailedToStringOf(Object obj) {
    return toStringOf(obj) + " (" + obj.getClass().getSimpleName() + "@" + toHexString(obj.hashCode()) + ")";
  }

  private String actualDetailedToString() {
    return detailedToStringOf(actual);
  }

  private String expectedDetailedToString() {
    return detailedToStringOf(expected);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (getClass() != o.getClass()) return false;
    ShouldBeEqual other = (ShouldBeEqual) o;
    if (!areEqual(actual, other.actual)) return false;
    return areEqual(expected, other.expected);
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = HASH_CODE_PRIME * result + hashCodeFor(actual);
    result = HASH_CODE_PRIME * result + hashCodeFor(expected);
    return result;
  }
}
