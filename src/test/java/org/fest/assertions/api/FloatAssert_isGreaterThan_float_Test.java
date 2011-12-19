/*
 * Created on Oct 24, 2010
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
package org.fest.assertions.api;

import static junit.framework.Assert.assertSame;
import static org.mockito.Mockito.*;

import org.junit.*;

import org.fest.assertions.internal.Floats;

/**
 * Tests for <code>{@link FloatAssert#isGreaterThan(float)}</code>.
 *
 * @author Alex Ruiz
 */
public class FloatAssert_isGreaterThan_float_Test {

  private Floats floats;
  private FloatAssert assertions;

  @Before public void setUp() {
    floats = mock(Floats.class);
    assertions = new FloatAssert(8f);
    assertions.floats = floats;
  }

  @Test public void should_verify_that_actual_is_greater_than_expected() {
    assertions.isGreaterThan(6);
    verify(floats).assertGreaterThan(assertions.info, assertions.actual, 6f);
  }

  @Test public void should_return_this() {
    FloatAssert returned = assertions.isGreaterThan(6);
    assertSame(assertions, returned);
  }
}
