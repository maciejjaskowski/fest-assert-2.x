/*
 * Created on Oct 3, 2010
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

import static java.util.Collections.emptyList;

import static org.fest.assertions.error.ShouldContainOnly.shouldContainOnly;
import static org.fest.assertions.test.ErrorMessages.*;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.actualIsNull;
import static org.fest.assertions.test.ObjectArrayFactory.emptyArray;
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.assertions.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.fest.util.Arrays.array;
import static org.fest.util.Collections.*;

import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.test.ExpectedException;

/**
 * Tests for <code>{@link Collections#assertContainsOnly(AssertionInfo, Collection, Object[])}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Collections_assertContainsOnly_Test extends AbstractTest_for_Collections_with_custom_comparison_strategy {

  @Rule
  public ExpectedException thrown = none();

  private List<String> actual;
  private Failures failures;
  private Collections collections;

  @Before
  public void setUp() {
    actual = list("Luke", "Yoda", "Leia");
    failures = spy(new Failures());
    collections = new Collections();
    collections.failures = failures;
    initCollectionsWithCustomComparisonStrategy(failures);
  }

  @Test
  public void should_pass_if_actual_contains_given_values_only() {
    collections.assertContainsOnly(someInfo(), actual, array("Luke", "Yoda", "Leia"));
  }

  @Test
  public void should_pass_if_actual_contains_given_values_only_in_different_order() {
    collections.assertContainsOnly(someInfo(), actual, array("Leia", "Yoda", "Luke"));
  }

  @Test
  public void should_pass_if_actual_contains_given_values_only_more_than_once() {
    actual.addAll(list("Luke", "Luke"));
    collections.assertContainsOnly(someInfo(), actual, array("Luke", "Yoda", "Leia"));
  }

  @Test
  public void should_pass_if_actual_contains_given_values_only_even_if_duplicated() {
    collections.assertContainsOnly(someInfo(), actual, array("Luke", "Luke", "Luke", "Yoda", "Leia"));
  }

  @Test
  public void should_throw_error_if_array_of_values_to_look_for_is_empty() {
    thrown.expectIllegalArgumentException(valuesToLookForIsEmpty());
    collections.assertContainsOnly(someInfo(), actual, emptyArray());
  }

  @Test
  public void should_throw_error_if_array_of_values_to_look_for_is_null() {
    thrown.expectNullPointerException(valuesToLookForIsNull());
    collections.assertContainsOnly(someInfo(), emptyList(), null);
  }

  @Test
  public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    collections.assertContainsOnly(someInfo(), null, array("Yoda"));
  }

  @Test
  public void should_fail_if_actual_does_not_contain_given_values_only() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Luke", "Yoda", "Han" };
    try {
      collections.assertContainsOnly(info, actual, expected);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldContainOnly(actual, expected, set("Han"), set("Leia")));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  // ------------------------------------------------------------------------------------------------------------------
  // tests using a custom comparison strategy
  // ------------------------------------------------------------------------------------------------------------------

  @Test
  public void should_pass_if_actual_contains_given_values_only_according_to_custom_comparison_strategy() {
    collectionsWithCaseInsensitiveComparisonStrategy.assertContainsOnly(someInfo(), actual,
        array("LUKE", "YODA", "Leia"));
  }

  @Test
  public void should_pass_if_actual_contains_given_values_only_in_different_order_according_to_custom_comparison_strategy() {
    collectionsWithCaseInsensitiveComparisonStrategy.assertContainsOnly(someInfo(), actual,
        array("LEIA", "yoda", "LukE"));
  }

  @Test
  public void should_pass_if_actual_contains_given_values_only_more_than_once_according_to_custom_comparison_strategy() {
    actual.addAll(list("Luke", "Luke"));
    collectionsWithCaseInsensitiveComparisonStrategy.assertContainsOnly(someInfo(), actual,
        array("luke", "YOda", "LeIA"));
  }

  @Test
  public void should_pass_if_actual_contains_given_values_only_even_if_duplicated_according_to_custom_comparison_strategy() {
    collectionsWithCaseInsensitiveComparisonStrategy.assertContainsOnly(someInfo(), actual,
        array("LUke", "LUke", "lukE", "YOda", "Leia"));
  }

  @Test
  public void should_fail_if_actual_does_not_contain_given_values_only_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    Object[] expected = { "Luke", "Yoda", "Han" };
    try {
      collectionsWithCaseInsensitiveComparisonStrategy.assertContainsOnly(info, actual, expected);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldContainOnly(actual, expected, set("Han"), set("Leia"), comparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

}
