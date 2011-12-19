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

import static org.fest.assertions.error.ShouldBeGreaterOrEqual.shouldBeGreaterOrEqual;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.actualIsNull;
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.assertions.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;

import static org.mockito.Mockito.*;

import org.junit.*;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.test.ExpectedException;

/**
 * Tests for <code>{@link Longs#assertGreaterThanOrEqualTo(AssertionInfo, Long, long)}</code>.
 *
 * @author Alex Ruiz
 */
public class Longs_assertGreaterThanOrEqualTo_Test {

  @Rule public ExpectedException thrown = none();

  private Failures failures;
  private Longs longs;

  @Before public void setUp() {
    failures = spy(new Failures());
    longs = new Longs();
    longs.setFailures(failures);
  }

  @Test public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    longs.assertGreaterThanOrEqualTo(someInfo(), null, 8L);
  }

  @Test public void should_pass_if_actual_is_greater_than_other() {
    longs.assertGreaterThanOrEqualTo(someInfo(), 8L, 6L);
  }

  @Test public void should_pass_if_actual_is_equal_to_other() {
    longs.assertGreaterThanOrEqualTo(someInfo(), 6L, 6L);
  }

  @Test public void should_fail_if_actual_is_less_than_other() {
    AssertionInfo info = someInfo();
    try {
      longs.assertGreaterThanOrEqualTo(info, 6L, 8L);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeGreaterOrEqual(6L, 8L));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
