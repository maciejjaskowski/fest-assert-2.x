/*
 * Created on Sep 17, 2010
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
package org.fest.assertions.error;

import static junit.framework.Assert.assertEquals;

import static org.fest.assertions.error.ShouldBeSorted.shouldHaveComparableElementsAccordingToGivenComparator;
import static org.fest.util.Arrays.array;

import org.junit.*;

import org.fest.assertions.description.Description;
import org.fest.assertions.internal.TestDescription;

/**
 * Tests for <code>{@link ShouldBeSorted#create(Description)}</code>.
 * 
 * @author Joel Costigliola
 */
public class ShouldHaveComparableElementsAccordingToComparator_create_Test {

  private ErrorMessageFactory factory;

  @Before
  public void setUp() {
    factory = shouldHaveComparableElementsAccordingToGivenComparator(array("b", "c", "a"), new CaseInsensitiveStringComparator());
  }

  @Test
  public void should_create_error_message() {
    String message = factory.create(new TestDescription("Test"));
    assertEquals(
        "[Test] some elements are not mutually comparable according to 'StringComparator' comparator in group:<['b', 'c', 'a']>",
        message);
  }
}
