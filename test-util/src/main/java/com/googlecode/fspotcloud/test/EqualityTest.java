/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUObject ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.fspotcloud.test;

import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;

/**
 * @author steven
 */
public abstract class EqualityTest {

	private Object obj = new Object();

	protected abstract List<Provider<Object>> getUniqueObjects();

	private List<Object> uniqueElementsA = newArrayList();
	private List<Object> uniqueElementsB = newArrayList();

	@Before
	public void createTestObjects() {
		for (Provider<Object> provider : getUniqueObjects()) {
			uniqueElementsA.add(provider.get());
			uniqueElementsB.add(provider.get());
		}
	}

	@Test
	public void testEqualsIsSymmetrical() {
		for (int i = 0; i < getUniqueObjects().size(); i++) {
			Object a = uniqueElementsA.get(i);
			Object b = uniqueElementsA.get(i);
			assertEquals(a, b);
			assertEquals(b, a);
		}
	}

	@Test
	public void testSameImpliesEquals() {
		for (Object a : uniqueElementsA) {
			assertEquals(a, a);
		}
	}

	@Test
	public void shouldNotBeEqualToAnObject() {
		for (Object a : uniqueElementsA) {
			assertFalse(a.equals(obj));
		}
	}

	@Test
	public void shouldNotEqual() {
		int count = getUniqueObjects().size();
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < count; j++) {
				if (i != j) {
					Object one = uniqueElementsA.get(i);
					Object other = uniqueElementsB.get(j);
					if (one.equals(other)) {
						System.out.println("Failing " + one + " " + other
								+ " should not be equal");
						fail();
					}
					if (other.equals(one)) {
						System.out.println("Failing " + other + " " + one
								+ " should not be equal");
						fail();
					}
				}
			}
		}
	}

	@Test
	public void shouldNotEqualNull() {
		for (Object a : uniqueElementsA) {
			assertFalse(a.equals(null));
		}
	}

	@Test
	public void hashCodeContract() {
		for (int i = 0; i < getUniqueObjects().size(); i++) {
			Object one = uniqueElementsA.get(i);
			Object theOther = uniqueElementsB.get(i);
			if (!(one.hashCode() == theOther.hashCode())) {
				System.out.println("Failing: hashcodes differ for: ");
				System.out.println(one);
				System.out.println(theOther);
				fail();
			}
		}
	}
}
