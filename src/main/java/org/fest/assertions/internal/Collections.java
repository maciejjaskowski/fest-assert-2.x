/*
 * Created on Sep 17, 2010
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

import static org.fest.assertions.error.ShouldBeEmpty.shouldBeEmpty;
import static org.fest.assertions.error.ShouldBeNullOrEmpty.shouldBeNullOrEmpty;
import static org.fest.assertions.error.ShouldContain.shouldContain;
import static org.fest.assertions.error.ShouldContainNull.shouldContainNull;
import static org.fest.assertions.error.ShouldContainOnly.shouldContainOnly;
import static org.fest.assertions.error.ShouldContainSequence.shouldContainSequence;
import static org.fest.assertions.error.ShouldEndWith.shouldEndWith;
import static org.fest.assertions.error.ShouldHaveSize.shouldHaveSize;
import static org.fest.assertions.error.ShouldNotBeEmpty.shouldNotBeEmpty;
import static org.fest.assertions.error.ShouldNotContain.shouldNotContain;
import static org.fest.assertions.error.ShouldNotContainNull.shouldNotContainNull;
import static org.fest.assertions.error.ShouldNotHaveDuplicates.shouldNotHaveDuplicates;
import static org.fest.assertions.error.ShouldStartWith.shouldStartWith;
import static org.fest.assertions.internal.CommonErrors.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.fest.assertions.core.AssertionInfo;
import org.fest.util.ComparisonStrategy;
import org.fest.util.StandardComparisonStrategy;
import org.fest.util.VisibleForTesting;

/**
 * Reusable assertions for <code>{@link Collection}</code>s.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Collections {

  private static final Collections INSTANCE = new Collections();

  /**
   * Returns the singleton instance of this class based on {@link StandardComparisonStrategy}.
   * @return the singleton instance of this class based on {@link StandardComparisonStrategy}.
   */
  public static Collections instance() {
    return INSTANCE;
  }

  private ComparisonStrategy comparisonStrategy;

  @VisibleForTesting
  Failures failures = Failures.instance();

  @VisibleForTesting
  Collections() {
    this(StandardComparisonStrategy.instance());
  }

  public Collections(ComparisonStrategy comparisonStrategy) {
    this.comparisonStrategy = comparisonStrategy;
  }

  /**
   * Asserts that the given <code>{@link Collection}</code> is {@code null} or empty.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @throws AssertionError if the given {@code Collection} is not {@code null} *and* contains one or more elements.
   */
  public void assertNullOrEmpty(AssertionInfo info, Collection<?> actual) {
    if (actual == null || actual.isEmpty()) return;
    throw failures.failure(info, shouldBeNullOrEmpty(actual));
  }

  /**
   * Asserts that the given {@code Collection} is empty.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws AssertionError if the given {@code Collection} is not empty.
   */
  public void assertEmpty(AssertionInfo info, Collection<?> actual) {
    assertNotNull(info, actual);
    if (actual.isEmpty()) return;
    throw failures.failure(info, shouldBeEmpty(actual));
  }

  /**
   * Asserts that the given {@code Collection} is not empty.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws AssertionError if the given {@code Collection} is empty.
   */
  public void assertNotEmpty(AssertionInfo info, Collection<?> actual) {
    assertNotNull(info, actual);
    if (!actual.isEmpty()) return;
    throw failures.failure(info, shouldNotBeEmpty());
  }

  /**
   * Asserts that the number of elements in the given {@code Collection} is equal to the expected one.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @param expectedSize the expected size of {@code actual}.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws AssertionError if the number of elements in the given {@code Collection} is different than the expected
   *           one.
   */
  public void assertHasSize(AssertionInfo info, Collection<?> actual, int expectedSize) {
    assertNotNull(info, actual);
    int sizeOfActual = actual.size();
    if (sizeOfActual == expectedSize) return;
    throw failures.failure(info, shouldHaveSize(actual, sizeOfActual, expectedSize));
  }

  /**
   * Asserts that the given {@code Collection} contains the given values, in any order.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @param values the values that are expected to be in the given {@code Collection}.
   * @throws NullPointerException if the array of values is {@code null}.
   * @throws IllegalArgumentException if the array of values is empty.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws AssertionError if the given {@code Collection} does not contain the given values.
   */
  public void assertContains(AssertionInfo info, Collection<?> actual, Object[] values) {
    checkIsNotNullAndNotEmpty(values);
    assertNotNull(info, actual);
    Set<Object> notFound = new LinkedHashSet<Object>();
    for (Object value : values)
      if (!collectionContains(actual, value)) notFound.add(value);
    if (notFound.isEmpty()) return;
    throw failures.failure(info, shouldContain(actual, values, notFound, comparisonStrategy));
  }

  /**
   * Delegates to {@link ComparisonStrategy#collectionContains(Collection, Object)}
   */
  private boolean collectionContains(Collection<?> actual, Object value) {
    return comparisonStrategy.collectionContains(actual, value);
  }

  /**
   * Delegates to {@link ComparisonStrategy#collectionRemoves(Collection, Object)}
   */
  private void collectionRemoves(Collection<?> actual, Object value) {
    comparisonStrategy.collectionRemoves(actual, value);
  }

  /**
   * Asserts that the given {@code Collection} contains only the given values and nothing else, in any order.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @param values the values that are expected to be in the given {@code Collection}.
   * @throws NullPointerException if the array of values is {@code null}.
   * @throws IllegalArgumentException if the array of values is empty.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws AssertionError if the given {@code Collection} does not contain the given values or if the given
   *           {@code Collection} contains values that are not in the given array.
   */
  public void assertContainsOnly(AssertionInfo info, Collection<?> actual, Object[] values) {
    checkIsNotNullAndNotEmpty(values);
    assertNotNull(info, actual);
    Set<Object> notExpected = set(actual.toArray());
    Set<Object> notFound = containsOnly(notExpected, values);
    if (notExpected.isEmpty() && notFound.isEmpty()) return;
    throw failures.failure(info, shouldContainOnly(actual, values, notFound, notExpected, comparisonStrategy));
  }

  private Set<Object> containsOnly(Set<Object> actual, Object[] values) {
    Set<Object> notFound = new LinkedHashSet<Object>();
    for (Object o : set(values)) {
      if (collectionContains(actual, o)) collectionRemoves(actual, o);
      else notFound.add(o);
    }
    return notFound;
  }

  /**
   * build a Set with that avoid duplicates <b>according to given comparison strategy</b>
   * @param elements to feed the Set we want to build
   * @return a Set without duplicates <b>according to given comparison strategy</b>
   */
  private Set<Object> set(Object... elements) {
    if (elements == null) return null;
    Set<Object> set = new HashSet<Object>();
    for (Object e : elements) {
      // only add is not already there 
      if (!collectionContains(set, e)) set.add(e);
    }
    return set;
  }

  /**
   * Verifies that the given <code>{@link Collection}</code> contains the given sequence of objects, without any other
   * objects between them.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @param sequence the sequence of objects to look for.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws NullPointerException if the given sequence is {@code null}.
   * @throws IllegalArgumentException if the given sequence is empty.
   * @throws AssertionError if the given {@code Collection} does not contain the given sequence of objects.
   */
  public void assertContainsSequence(AssertionInfo info, Collection<?> actual, Object[] sequence) {
    checkIsNotNullAndNotEmpty(sequence);
    assertNotNull(info, actual);
    boolean firstAlreadyFound = false;
    int i = 0;
    int sequenceSize = sequence.length;
    for (Object o : actual) {
      if (i >= sequenceSize) break;
      if (!firstAlreadyFound) {
        if (!areEqual(o, sequence[i])) continue;
        firstAlreadyFound = true;
        i++;
        continue;
      }
      if (areEqual(o, sequence[i++])) continue;
      throw actualDoesNotContainSequence(info, actual, sequence);
    }
    if (!firstAlreadyFound || i < sequenceSize) throw actualDoesNotContainSequence(info, actual, sequence);
  }

  /**
   * Delegates to {@link ComparisonStrategy#areEqual(Object, Object)}
   */
  private boolean areEqual(Object actual, Object other) {
    return comparisonStrategy.areEqual(actual, other);
  }

  private AssertionError actualDoesNotContainSequence(AssertionInfo info, Collection<?> actual, Object[] sequence) {
    return failures.failure(info, shouldContainSequence(actual, sequence, comparisonStrategy));
  }

  /**
   * Asserts that the given {@code Collection} does not contain the given values.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @param values the values that are expected not to be in the given {@code Collection}.
   * @throws NullPointerException if the array of values is {@code null}.
   * @throws IllegalArgumentException if the array of values is empty.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws AssertionError if the given {@code Collection} contains any of given values.
   */
  public void assertDoesNotContain(AssertionInfo info, Collection<?> actual, Object[] values) {
    checkIsNotNullAndNotEmpty(values);
    assertNotNull(info, actual);
    Set<Object> found = new LinkedHashSet<Object>();
    for (Object o : values)
      if (collectionContains(actual, o)) found.add(o);
    if (found.isEmpty()) return;
    throw failures.failure(info, shouldNotContain(actual, values, found, comparisonStrategy));
  }

  /**
   * Asserts that the given {@code Collection} does not have duplicate values.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @throws NullPointerException if the array of values is {@code null}.
   * @throws IllegalArgumentException if the array of values is empty.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws AssertionError if the given {@code Collection} contains duplicate values.
   */
  public void assertDoesNotHaveDuplicates(AssertionInfo info, Collection<?> actual) {
    assertNotNull(info, actual);
    Collection<?> duplicates = comparisonStrategy.duplicatesFrom(actual);
    if (duplicates.isEmpty()) return;
    throw failures.failure(info, shouldNotHaveDuplicates(actual, duplicates, comparisonStrategy));
  }

  /**
   * Verifies that the given {@code Collection} starts with the given sequence of objects, without any other objects
   * between them. Similar to <code>{@link #assertContainsSequence(AssertionInfo, Collection, Object[])}</code>, but it
   * also verifies that the first element in the sequence is also the first element of the given {@code Collection}.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @param sequence the sequence of objects to look for.
   * @throws NullPointerException if the given argument is {@code null}.
   * @throws IllegalArgumentException if the given argument is an empty array.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws AssertionError if the given {@code Collection} does not start with the given sequence of objects.
   */
  public void assertStartsWith(AssertionInfo info, Collection<?> actual, Object[] sequence) {
    checkIsNotNullAndNotEmpty(sequence);
    assertNotNull(info, actual);
    int sequenceSize = sequence.length;
    if (actual.size() < sequenceSize) throw actualDoesNotStartWithSequence(info, actual, sequence);
    int i = 0;
    for (Object o : actual) {
      if (i >= sequenceSize) break;
      if (areEqual(o, sequence[i++])) continue;
      throw actualDoesNotStartWithSequence(info, actual, sequence);
    }
  }

  private AssertionError actualDoesNotStartWithSequence(AssertionInfo info, Collection<?> actual, Object[] sequence) {
    return failures.failure(info, shouldStartWith(actual, sequence, comparisonStrategy));
  }

  /**
   * Verifies that the given {@code Collection} ends with the given sequence of objects, without any other objects
   * between them. Similar to <code>{@link #assertContainsSequence(AssertionInfo, Collection, Object[])}</code>, but it
   * also verifies that the last element in the sequence is also the last element of the given {@code Collection}.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @param sequence the sequence of objects to look for.
   * @throws NullPointerException if the given argument is {@code null}.
   * @throws IllegalArgumentException if the given argument is an empty array.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws AssertionError if the given {@code Collection} does not end with the given sequence of objects.
   */
  public void assertEndsWith(AssertionInfo info, Collection<?> actual, Object[] sequence) {
    checkIsNotNullAndNotEmpty(sequence);
    assertNotNull(info, actual);
    int sequenceSize = sequence.length;
    int sizeOfActual = actual.size();
    if (sizeOfActual < sequenceSize) throw actualDoesNotEndWithSequence(info, actual, sequence);
    int start = actual.size() - sequenceSize;
    int sequenceIndex = 0, indexOfActual = 0;
    for (Object o : actual) {
      if (indexOfActual++ < start) continue;
      if (areEqual(o, sequence[sequenceIndex++])) continue;
      throw actualDoesNotEndWithSequence(info, actual, sequence);
    }
  }

  /**
   * Asserts that the given {@code Collection} contains at least a null element.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws AssertionError if the given {@code Collection} does not contain at least a null element.
   */
  public void assertContainsNull(AssertionInfo info, Collection<?> actual) {
    assertNotNull(info, actual);
    if (!actual.contains(null)) throw failures.failure(info, shouldContainNull(actual));
  }

  /**
   * Asserts that the given {@code Collection} does not contain null elements.
   * @param info contains information about the assertion.
   * @param actual the given {@code Collection}.
   * @throws AssertionError if the given {@code Collection} is {@code null}.
   * @throws AssertionError if the given {@code Collection} contains a null element.
   */
  public void assertDoesNotContainNull(AssertionInfo info, Collection<?> actual) {
    assertNotNull(info, actual);
    if (actual.contains(null)) throw failures.failure(info, shouldNotContainNull(actual));
  }

  private void checkIsNotNullAndNotEmpty(Object[] values) {
    if (values == null) throw arrayOfValuesToLookForIsNull();
    if (values.length == 0) throw arrayOfValuesToLookForIsEmpty();
  }

  private void assertNotNull(AssertionInfo info, Collection<?> actual) {
    Objects.instance().assertNotNull(info, actual);
  }

  private AssertionError actualDoesNotEndWithSequence(AssertionInfo info, Collection<?> actual, Object[] sequence) {
    return failures.failure(info, shouldEndWith(actual, sequence, comparisonStrategy));
  }

}
