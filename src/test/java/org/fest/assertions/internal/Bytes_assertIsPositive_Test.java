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

import org.junit.Test;

import org.fest.assertions.core.AssertionInfo;

/**
 * Tests for <code>{@link Bytes#assertIsPositive(AssertionInfo, Byte)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Bytes_assertIsPositive_Test extends AbstractTest_for_Bytes {

  @Test
  public void should_succeed_since_actual_is_positive() {
    bytes.assertIsPositive(someInfo(), (byte) 6);
  }

  @Test
  public void should_fail_since_actual_is_not_positive() {
    thrown.expectAssertionError("expected:<-6> to be greater than:<0>");
    bytes.assertIsPositive(someInfo(), (byte) -6);
  }

  @Test
  public void should_succeed_since_actual_is_positive_according_to_custom_comparison_strategy() {
    bytesWithAbsValueComparisonStrategy.assertIsPositive(someInfo(), (byte) -1);
  }
  
  @Test
  public void should_fail_since_actual_is_not_positive_according_to_custom_comparison_strategy() {
    thrown.expectAssertionError("expected:<0> to be greater than:<0> according to 'AbsValueComparator' comparator");
    bytesWithAbsValueComparisonStrategy.assertIsPositive(someInfo(), (byte) 0);
  }
}
