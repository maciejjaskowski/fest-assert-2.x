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
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.assertions.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;

import static org.mockito.Mockito.verify;

import org.junit.Test;

import org.fest.assertions.core.AssertionInfo;

/**
 * Tests for <code>{@link Objects#assertNotEqual(AssertionInfo, Object, Object)}</code>.
 * 
 * @author Alex Ruiz
 */
public class Objects_assertNotEqual_Test extends AbstractTest_for_Objects {

  @Test
  public void should_pass_if_objects_are_not_equal() {
    objects.assertNotEqual(someInfo(), "Yoda", "Luke");
  }

  @Test
  public void should_fail_if_objects_are_equal() {
    AssertionInfo info = someInfo();
    try {
      objects.assertNotEqual(info, "Yoda", "Yoda");
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldNotBeEqual("Yoda", "Yoda"));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test
  public void should_pass_if_objects_are_not_equal_according_to_custom_comparison_strategy() {
    objectsWithCustomComparisonStrategy.assertNotEqual(someInfo(), "Yoda", "Luke");
  }

  @Test
  public void should_fail_if_objects_are_equal_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    try {
      objectsWithCustomComparisonStrategy.assertNotEqual(info, "YoDA", "Yoda");
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldNotBeEqual("YoDA", "Yoda", customComparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
