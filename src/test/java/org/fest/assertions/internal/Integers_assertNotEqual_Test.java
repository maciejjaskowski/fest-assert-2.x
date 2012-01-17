/*
 * Created on Sep 14, 2010
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

import static org.fest.assertions.error.ShouldNotBeEqual.shouldNotBeEqual;
import static org.fest.assertions.test.FailureMessages.actualIsNull;
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.assertions.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;

import static org.mockito.Mockito.verify;

import org.junit.Test;

import org.fest.assertions.core.AssertionInfo;

/**
 * Tests for <code>{@link Integers#assertNotEqual(AssertionInfo, Integer, int)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Integers_assertNotEqual_Test extends AbstractTest_for_Integers {

  @Test
  public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    integers.assertNotEqual(someInfo(), null, 8);
  }

  @Test
  public void should_pass_if_integers_are_not_equal() {
    integers.assertNotEqual(someInfo(), 8, 6);
  }

  @Test
  public void should_fail_if_integers_are_equal() {
    AssertionInfo info = someInfo();
    try {
      integers.assertNotEqual(info, 6, 6);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldNotBeEqual(6, 6));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
  
  @Test
  public void should_fail_if_actual_is_null_according_to_custom_comparison_strategy() {
    thrown.expectAssertionError(actualIsNull());
    integersWithAbsValueComparisonStrategy.assertNotEqual(someInfo(), null, 8);
  }
  
  @Test
  public void should_pass_if_integers_are_not_equal_according_to_custom_comparison_strategy() {
    integersWithAbsValueComparisonStrategy.assertNotEqual(someInfo(), 8, 6);
  }
  
  @Test
  public void should_fail_if_integers_are_equal_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    try {
      integersWithAbsValueComparisonStrategy.assertNotEqual(info, 6, -6);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldNotBeEqual(6, -6, absValueComparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
