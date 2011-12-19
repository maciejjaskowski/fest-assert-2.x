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
 * Tests for <code>{@link Shorts#assertLessThan(AssertionInfo, Short, short)}</code>.
 *
 * @author Alex Ruiz
 */
public class Shorts_assertLessThan_Test {

  @Rule public ExpectedException thrown = none();

  private Failures failures;
  private Shorts shorts;

  @Before public void setUp() {
    failures = spy(new Failures());
    shorts = new Shorts();
    shorts.setFailures(failures);
  }

  @Test public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    shorts.assertLessThan(someInfo(), null, (short)8);
  }

  @Test public void should_pass_if_actual_is_less_than_other() {
    shorts.assertLessThan(someInfo(), (short)6, (short)8);
  }

  @Test public void should_fail_if_actual_is_equal_to_other() {
    AssertionInfo info = someInfo();
    try {
      shorts.assertLessThan(info, (short)6, (short)6);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeLess((short)6, (short)6));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test public void should_fail_if_actual_is_greater_than_other() {
    AssertionInfo info = someInfo();
    try {
      shorts.assertLessThan(info, (short)8, (short)6);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeLess((short)8, (short)6));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
