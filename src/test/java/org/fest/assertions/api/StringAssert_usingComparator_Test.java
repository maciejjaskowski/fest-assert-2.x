/*
 * Created on Nov 29, 2010
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

import org.junit.Test;

import org.fest.assertions.internal.Objects;
import org.fest.assertions.internal.Strings;
import org.fest.assertions.util.CaseInsensitiveStringComparator;

/**
 * Tests for <code>{@link StringAssert#usingComparator(java.util.Comparator)}</code> and
 * <code>{@link StringAssert#usingDefaultComparator()}</code>.
 * 
 * @author Joel Costigliola
 */
public class StringAssert_usingComparator_Test {

  private StringAssert assertions = new StringAssert("");

  @Test
  public void using_default_comparator_test() {
    assertions.usingDefaultComparator();
    assertSame(assertions.objects, Objects.instance());
    assertSame(assertions.strings, Strings.instance());
  }
  
  @Test
  public void using_custom_comparator_test() {
    assertions.usingComparator(CaseInsensitiveStringComparator.instance);
    assertSame(assertions.objects.getComparator(), CaseInsensitiveStringComparator.instance);
    assertSame(assertions.strings.getComparator(), CaseInsensitiveStringComparator.instance);
  }
}
