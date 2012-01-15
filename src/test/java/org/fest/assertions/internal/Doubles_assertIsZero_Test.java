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

import static org.fest.assertions.test.TestData.someInfo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.fest.assertions.core.AssertionInfo;

/**
 * Tests for <code>{@link Doubles#assertIsNegative(AssertionInfo, Double)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Doubles_assertIsZero_Test extends AbstractTest_for_Doubles{

  @Test
  public void should_succeed_since_actual_is_zero() {
    doubles.assertIsZero(someInfo(), 0.0d);
  }

  @Test
  public void should_fail_since_actual_is_not_zero() {
    try {
      doubles.assertIsZero(someInfo(), 2.0d);
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "expected:<[0].0> but was:<[2].0>");
    }
  }

  @Test
  public void should_succeed_since_actual_is_zero_whatever_custom_comparison_strategy_is() {
    doublesWithAbsValueComparisonStrategy.assertIsZero(someInfo(), 0.0d);
  }
  
  @Test
  public void should_fail_since_actual_is_not_zero_whatever_custom_comparison_strategy_is() {
    try {
      doublesWithAbsValueComparisonStrategy.assertIsZero(someInfo(), 2.0d);
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "expected:<[0].0> but was:<[2].0>");
    }
  }
  
}
