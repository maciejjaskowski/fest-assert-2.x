/*
 * Created on Oct 19, 2010
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

import static org.fest.assertions.error.ShouldBeGreater.shouldBeGreater;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.actualIsNull;
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.assertions.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.test.ExpectedException;
import org.fest.assertions.util.AbsValueComparator;
import org.fest.util.ComparatorBasedComparisonStrategy;

/**
 * Tests for <code>{@link Comparables#assertGreaterThan(AssertionInfo, Comparable, Comparable)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Comparables_assertGreaterThan_Test {

  @Rule
  public ExpectedException thrown = none();

  private Failures failures;
  private Comparables comparables;
  protected ComparatorBasedComparisonStrategy absValueComparisonStrategy;
  protected Bytes comparablesWithAbsValueComparisonStrategy;

  @Before
  public void setUp() {
    failures = spy(new Failures());
    comparables = new Comparables();
    comparables.failures = failures;
    absValueComparisonStrategy = new ComparatorBasedComparisonStrategy(new AbsValueComparator<Integer>());
    comparablesWithAbsValueComparisonStrategy = new Bytes(absValueComparisonStrategy);
    comparablesWithAbsValueComparisonStrategy.failures = failures;
  }

  @Test
  public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    comparables.assertGreaterThan(someInfo(), null, 8);
  }

  @Test
  public void should_pass_if_actual_is_greater_than_other() {
    comparables.assertGreaterThan(someInfo(), 8, 6);
  }

  @Test
  public void should_fail_if_actual_is_equal_to_other() {
    AssertionInfo info = someInfo();
    try {
      comparables.assertGreaterThan(info, "Yoda", "Yoda");
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeGreater("Yoda", "Yoda"));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test
  public void should_fail_if_actual_is_less_than_other() {
    AssertionInfo info = someInfo();
    try {
      comparables.assertGreaterThan(info, 6, 8);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeGreater(6, 8));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  // ------------------------------------------------------------------------------------------------------------------
  // tests using a custom comparison strategy
  // ------------------------------------------------------------------------------------------------------------------

  @Test
  public void should_pass_if_actual_is_greater_than_other_according_to_custom_comparison_strategy() {
    comparablesWithAbsValueComparisonStrategy.assertGreaterThan(someInfo(), 8, 6);
  }

  @Test
  public void should_fail_if_actual_is_equal_to_other_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    try {
      comparablesWithAbsValueComparisonStrategy.assertGreaterThan(info, 7, 7);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeGreater(7, 7, absValueComparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test
  public void should_fail_if_actual_is_less_than_other_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    try {
      comparablesWithAbsValueComparisonStrategy.assertGreaterThan(info, 6, 8);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeGreater(6, 8, absValueComparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

}
