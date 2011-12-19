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
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.actualIsNull;
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.assertions.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;

import static org.mockito.Mockito.*;

import org.junit.*;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.test.ExpectedException;

/**
 * Tests for <code>{@link Longs#assertEqual(AssertionInfo, Long, long)}</code>.
 *
 * @author Alex Ruiz
 */
public class Longs_assertEqual_Test {

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
    longs.assertEqual(someInfo(), null, 8L);
  }

  @Test public void should_pass_if_longs_are_equal() {
    longs.assertEqual(someInfo(), 8L, 8L);
  }

  @Test public void should_fail_if_longs_are_not_equal() {
    AssertionInfo info = someInfo();
    try {
      longs.assertEqual(info, 6L, 8L);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeEqual(6L, 8L));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
