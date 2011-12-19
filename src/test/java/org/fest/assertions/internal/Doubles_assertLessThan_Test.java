/*
 * Created on Oct 28, 2010
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

import static org.fest.assertions.error.ShouldBeLess.shouldBeLess;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.actualIsNull;
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.assertions.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.mockito.Mockito.*;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link Doubles#assertLessThan(AssertionInfo, Double, double)}</code>.
 *
 * @author Alex Ruiz
 */
public class Doubles_assertLessThan_Test {

  @Rule public ExpectedException thrown = none();

  private Failures failures;
  private Doubles doubles;

  @Before public void setUp() {
    failures = spy(new Failures());
    doubles = new Doubles();
    doubles.setFailures(failures);
  }

  @Test public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    doubles.assertLessThan(someInfo(), null, 8d);
  }

  @Test public void should_pass_if_actual_is_less_than_other() {
    doubles.assertLessThan(someInfo(), 6d, 8d);
  }

  @Test public void should_fail_if_actual_is_equal_to_other() {
    AssertionInfo info = someInfo();
    try {
      doubles.assertLessThan(info, 6d, 6d);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeLess(6d, 6d));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test public void should_fail_if_actual_is_greater_than_other() {
    AssertionInfo info = someInfo();
    try {
      doubles.assertLessThan(info, 8d, 6d);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeLess(8d, 6d));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
