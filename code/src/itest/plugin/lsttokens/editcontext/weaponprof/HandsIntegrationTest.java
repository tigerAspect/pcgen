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
package plugin.lsttokens.editcontext.weaponprof;

import org.junit.Test;

import pcgen.cdom.inst.CDOMWeaponProf;
import pcgen.persistence.PersistenceLayerException;
import pcgen.rules.persistence.CDOMLoader;
import pcgen.rules.persistence.CDOMTokenLoader;
import pcgen.rules.persistence.token.CDOMPrimaryToken;
import plugin.lsttokens.editcontext.testsupport.AbstractIntegerIntegrationTestCase;
import plugin.lsttokens.editcontext.testsupport.TestContext;
import plugin.lsttokens.weaponprof.HandsToken;

public class HandsIntegrationTest extends
		AbstractIntegerIntegrationTestCase<CDOMWeaponProf>
{

	static HandsToken token = new HandsToken();
	static CDOMTokenLoader<CDOMWeaponProf> loader = new CDOMTokenLoader<CDOMWeaponProf>(
			CDOMWeaponProf.class);

	@Override
	public Class<CDOMWeaponProf> getCDOMClass()
	{
		return CDOMWeaponProf.class;
	}

	@Override
	public CDOMLoader<CDOMWeaponProf> getLoader()
	{
		return loader;
	}

	@Override
	public CDOMPrimaryToken<CDOMWeaponProf> getToken()
	{
		return token;
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
	public void testRoundRobinSpecialCaseOne() throws PersistenceLayerException
	{
		verifyCleanStart();
		TestContext tc = new TestContext();
		commit(testCampaign, tc, "1IFLARGERTHANWEAPON");
		commit(modCampaign, tc, "2");
		completeRoundRobin(tc);
	}

	@Test
	public void testRoundRobinSpecialCaseTwo() throws PersistenceLayerException
	{
		verifyCleanStart();
		TestContext tc = new TestContext();
		commit(testCampaign, tc, "2");
		commit(modCampaign, tc, "1IFLARGERTHANWEAPON");
		completeRoundRobin(tc);
	}

	@Test
	public void testRoundRobinSpecialNoSet() throws PersistenceLayerException
	{
		verifyCleanStart();
		TestContext tc = new TestContext();
		emptyCommit(testCampaign, tc);
		commit(modCampaign, tc, "1IFLARGERTHANWEAPON");
		completeRoundRobin(tc);
	}

	@Test
	public void testRoundRobinSpecialNoReset() throws PersistenceLayerException
	{
		verifyCleanStart();
		TestContext tc = new TestContext();
		commit(testCampaign, tc, "1IFLARGERTHANWEAPON");
		emptyCommit(modCampaign, tc);
		completeRoundRobin(tc);
	}

	@Override
	public boolean isPositiveAllowed()
	{
		return true;
	}
}
