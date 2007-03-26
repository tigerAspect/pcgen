/*
 * Copyright (c) 2007 Tom Parker <thpr@users.sourceforge.net>
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */
package plugin.lsttokens.testsupport;

import org.junit.Test;

import pcgen.cdom.enumeration.IntegerKey;
import pcgen.core.PObject;
import pcgen.persistence.PersistenceLayerException;

public abstract class AbstractIntegerTokenTestCase<T extends PObject> extends
		AbstractTokenTestCase<T>
{

	public abstract IntegerKey getIntegerKey();

	public abstract boolean isZeroAllowed();

	public abstract boolean isNegativeAllowed();

	public abstract boolean isPositiveAllowed();

	@Test
	public void testInvalidInputUnset() throws PersistenceLayerException
	{
		testInvalidInputs(null);
	}

	@Test
	public void testInvalidInputSet() throws PersistenceLayerException
	{
		Integer con;
		if (isPositiveAllowed())
		{
			con = Integer.valueOf(3);
		}
		else
		{
			con = Integer.valueOf(-3);
		}
		assertTrue(getToken()
			.parse(primaryContext, primaryProf, con.toString()));
		assertEquals(con, primaryProf.get(getIntegerKey()));
		testInvalidInputs(con);
	}

	public void testInvalidInputs(Integer val) throws PersistenceLayerException
	{
		// Always ensure get is unchanged
		// since no invalid item should set or reset the value
		assertEquals(val, primaryProf.get(getIntegerKey()));
		assertFalse(getToken().parse(primaryContext, primaryProf, "TestWP"));
		assertEquals(val, primaryProf.get(getIntegerKey()));
		assertFalse(getToken().parse(primaryContext, primaryProf, "String"));
		assertEquals(val, primaryProf.get(getIntegerKey()));
		assertFalse(getToken().parse(primaryContext, primaryProf,
			"TYPE=TestType"));
		assertEquals(val, primaryProf.get(getIntegerKey()));
		assertFalse(getToken().parse(primaryContext, primaryProf,
			"TYPE.TestType"));
		assertEquals(val, primaryProf.get(getIntegerKey()));
		assertFalse(getToken().parse(primaryContext, primaryProf, "ALL"));
		assertEquals(val, primaryProf.get(getIntegerKey()));
		assertFalse(getToken().parse(primaryContext, primaryProf, "ANY"));
		assertEquals(val, primaryProf.get(getIntegerKey()));
		assertFalse(getToken().parse(primaryContext, primaryProf, "FIVE"));
		assertEquals(val, primaryProf.get(getIntegerKey()));
		assertFalse(getToken().parse(primaryContext, primaryProf, "4.5"));
		assertEquals(val, primaryProf.get(getIntegerKey()));
		assertFalse(getToken().parse(primaryContext, primaryProf, "1/2"));
		assertEquals(val, primaryProf.get(getIntegerKey()));
		assertFalse(getToken().parse(primaryContext, primaryProf, "1+3"));
		assertEquals(val, primaryProf.get(getIntegerKey()));
		// Require Integer greater than or equal to zero
		if (!isNegativeAllowed())
		{
			assertFalse(getToken().parse(primaryContext, primaryProf, "-1"));
			assertEquals(val, primaryProf.get(getIntegerKey()));
		}
		if (!isPositiveAllowed())
		{
			assertFalse(getToken().parse(primaryContext, primaryProf, "1"));
			assertEquals(val, primaryProf.get(getIntegerKey()));
		}
		if (!isZeroAllowed())
		{
			assertFalse(getToken().parse(primaryContext, primaryProf, "0"));
			assertEquals(val, primaryProf.get(getIntegerKey()));
		}
	}

	@Test
	public void testValidInputs() throws PersistenceLayerException
	{
		if (isPositiveAllowed())
		{
			assertTrue(getToken().parse(primaryContext, primaryProf, "5"));
			assertEquals(Integer.valueOf(5), primaryProf.get(getIntegerKey()));
			assertTrue(getToken().parse(primaryContext, primaryProf, "1"));
			assertEquals(Integer.valueOf(1), primaryProf.get(getIntegerKey()));
		}
		if (isZeroAllowed())
		{
			assertTrue(getToken().parse(primaryContext, primaryProf, "0"));
			assertEquals(Integer.valueOf(0), primaryProf.get(getIntegerKey()));
		}
		if (isNegativeAllowed())
		{
			assertTrue(getToken().parse(primaryContext, primaryProf, "-2"));
			assertEquals(Integer.valueOf(-2), primaryProf.get(getIntegerKey()));
		}
	}

	@Test
	public void testOutputOne() throws PersistenceLayerException
	{
		assertTrue(0 == primaryContext.getWriteMessageCount());
		primaryProf.put(getIntegerKey(), 1);
		String[] unparsed = getToken().unparse(primaryContext, primaryProf);
		if (isPositiveAllowed())
		{
			assertEquals(1, unparsed.length);
			assertEquals("1", unparsed[0]);
		}
		else
		{
			assertNull(unparsed);
			assertTrue(0 != primaryContext.getWriteMessageCount());
		}
	}

	@Test
	public void testOutputZero() throws PersistenceLayerException
	{
		assertTrue(0 == primaryContext.getWriteMessageCount());
		primaryProf.put(getIntegerKey(), 0);
		String[] unparsed = getToken().unparse(primaryContext, primaryProf);
		if (isZeroAllowed())
		{
			assertEquals(1, unparsed.length);
			assertEquals("0", unparsed[0]);
		}
		else
		{
			assertNull(unparsed);
			assertTrue(0 != primaryContext.getWriteMessageCount());
		}
	}

	@Test
	public void testOutputMinusTwo() throws PersistenceLayerException
	{
		assertTrue(0 == primaryContext.getWriteMessageCount());
		primaryProf.put(getIntegerKey(), -2);
		String[] unparsed = getToken().unparse(primaryContext, primaryProf);
		if (isNegativeAllowed())
		{
			assertEquals(1, unparsed.length);
			assertEquals("-2", unparsed[0]);
		}
		else
		{
			assertNull(unparsed);
			assertTrue(0 != primaryContext.getWriteMessageCount());
		}
	}

	@Test
	public void testRoundRobinOne() throws PersistenceLayerException
	{
		if (isPositiveAllowed())
		{
			runRoundRobin("1");
		}
	}

	@Test
	public void testRoundRobinZero() throws PersistenceLayerException
	{
		if (isZeroAllowed())
		{
			runRoundRobin("0");
		}
	}

	@Test
	public void testRoundRobinNegative() throws PersistenceLayerException
	{
		if (isNegativeAllowed())
		{
			runRoundRobin("-3");
		}
	}

	@Test
	public void testRoundRobinFive() throws PersistenceLayerException
	{
		if (isPositiveAllowed())
		{
			runRoundRobin("5");
		}
	}
}
