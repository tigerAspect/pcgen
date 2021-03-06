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
package plugin.lsttokens.weaponprof;

import org.junit.Test;

import pcgen.cdom.enumeration.IntegerKey;
import pcgen.core.WeaponProf;
import pcgen.persistence.PersistenceLayerException;
import pcgen.rules.persistence.CDOMLoader;
import pcgen.rules.persistence.token.CDOMPrimaryToken;
import plugin.lsttokens.testsupport.AbstractIntegerTokenTestCase;
import plugin.lsttokens.testsupport.CDOMTokenLoader;

public class HandsTokenTest extends AbstractIntegerTokenTestCase<WeaponProf>
{

	static HandsToken token = new HandsToken();
	static CDOMTokenLoader<WeaponProf> loader = new CDOMTokenLoader<WeaponProf>(
			WeaponProf.class);

	@Override
	public Class<WeaponProf> getCDOMClass()
	{
		return WeaponProf.class;
	}

	@Override
	public CDOMLoader<WeaponProf> getLoader()
	{
		return loader;
	}

	@Override
	public CDOMPrimaryToken<WeaponProf> getToken()
	{
		return token;
	}

	@Override
	public IntegerKey getIntegerKey()
	{
		return IntegerKey.HANDS;
	}

	@Override
	public boolean isNegativeAllowed()
	{
		return false;
	}

	@Override
	public boolean isZeroAllowed()
	{
		return true;
	}

	@Test
	public void testValidSpecialCase() throws PersistenceLayerException
	{
		assertTrue(parse("1IFLARGERTHANWEAPON"));
		assertEquals(Integer.valueOf(-1), primaryProf.get(IntegerKey.HANDS));
		assertCleanConstruction();
		assertTrue(secondaryContext.ref.validate(null));
		assertTrue(secondaryContext.ref.resolveReferences(null));
	}

	@Test
	public void testUnparseSpecialCase() throws PersistenceLayerException
	{
		String[] unparsed = setAndUnparse(-1);
		assertNotNull(unparsed);
		assertEquals(1, unparsed.length);
		assertEquals("Expected item to be equal", "1IFLARGERTHANWEAPON", unparsed[0]);
	}

	@Test
	public void testRoundRobinSpecialCase() throws PersistenceLayerException
	{
		runRoundRobin("1IFLARGERTHANWEAPON");
	}

	@Override
	public boolean isPositiveAllowed()
	{
		return true;
	}
}
