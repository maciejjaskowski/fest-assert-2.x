/*
 * Created on Oct 21, 2010
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

/**
 * Tests for <code>{@link Bytes#assertGreaterThan(AssertionInfo, Byte, byte)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Bytes_assertGreaterThan_Test extends AbstractTest_for_Bytes_with_custom_comparison_strategy {

  @Rule
  public ExpectedException thrown = none();

  private Failures failures;
  private Bytes bytes;

  @Before
  public void setUp() {
    failures = spy(new Failures());
    bytes = new Bytes();
    bytes.setFailures(failures);
    initBytesWithCustomComparisonStrategy(failures);
  }

  @Test
  public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    bytes.assertGreaterThan(someInfo(), null, (byte) 8);
  }

  @Test
  public void should_pass_if_actual_is_greater_than_other() {
    bytes.assertGreaterThan(someInfo(), (byte) 8, (byte) 6);
  }

  @Test
  public void should_fail_if_actual_is_equal_to_other() {
    try {
      bytes.assertGreaterThan(someInfo(), (byte) 6, (byte) 6);
    } catch (AssertionError e) {
      verify(failures).failure(someInfo(), shouldBeGreater((byte) 6, (byte) 6));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test
  public void should_fail_if_actual_is_less_than_other() {
    AssertionInfo info = someInfo();
    try {
      bytes.assertGreaterThan(info, (byte) 6, (byte) 8);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeGreater((byte) 6, (byte) 8));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  // ------------------------------------------------------------------------------------------------------------------
  // tests using a custom comparison strategy
  // ------------------------------------------------------------------------------------------------------------------

  @Test
  public void should_pass_if_actual_is_greater_than_other_according_to_custom_comparison_strategy() {
    bytesWithAbsValueComparisonStrategy.assertGreaterThan(someInfo(), (byte) 8, (byte) 6);
  }

  @Test
  public void should_fail_if_actual_is_equal_to_other_according_to_custom_comparison_strategy() {
    try {
      bytesWithAbsValueComparisonStrategy.assertGreaterThan(someInfo(), (byte) 6, (byte) 6);
    } catch (AssertionError e) {
      verify(failures).failure(someInfo(), shouldBeGreater((byte) 6, (byte) 6, absValueComparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test
  public void should_fail_if_actual_is_less_than_other_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    try {
      bytesWithAbsValueComparisonStrategy.assertGreaterThan(info, (byte) 6, (byte) 8);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeGreater((byte) 6, (byte) 8, absValueComparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

}
