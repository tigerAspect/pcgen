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
package plugin.lsttokens.editcontext.template;

import org.junit.Test;

import pcgen.cdom.inst.CDOMTemplate;
import pcgen.persistence.PersistenceLayerException;
import pcgen.rules.persistence.CDOMLoader;
import pcgen.rules.persistence.CDOMTokenLoader;
import pcgen.rules.persistence.token.CDOMPrimaryToken;
import plugin.lsttokens.editcontext.testsupport.AbstractIntegrationTestCase;
import plugin.lsttokens.editcontext.testsupport.TestContext;
import plugin.lsttokens.template.FaceToken;

public class FaceIntegrationTest extends
		AbstractIntegrationTestCase<CDOMTemplate>
{

	static FaceToken token = new FaceToken();
	static CDOMTokenLoader<CDOMTemplate> loader = new CDOMTokenLoader<CDOMTemplate>(
			CDOMTemplate.class);

	@Override
	public Class<CDOMTemplate> getCDOMClass()
	{
		return CDOMTemplate.class;
	}

	@Override
	public CDOMLoader<CDOMTemplate> getLoader()
	{
		return loader;
	}

	@Override
	public CDOMPrimaryToken<CDOMTemplate> getToken()
	{
		return token;
	}

	@Test
	public void testRoundRobinSimple() throws PersistenceLayerException
	{
		verifyCleanStart();
		TestContext tc = new TestContext();
		commit(testCampaign, tc, "10");
		commit(modCampaign, tc, "5");
		completeRoundRobin(tc);
	}

	@Test
	public void testRoundRobinComplex() throws PersistenceLayerException
	{
		verifyCleanStart();
		TestContext tc = new TestContext();
		commit(testCampaign, tc, "5");
		commit(modCampaign, tc, "5,10");
		completeRoundRobin(tc);
	}

	@Test
	public void testRoundRobinRemove() throws PersistenceLayerException
	{
		verifyCleanStart();
		TestContext tc = new TestContext();
		commit(testCampaign, tc, "5,20");
		commit(modCampaign, tc, "10");
		completeRoundRobin(tc);
	}

	@Test
	public void testRoundRobinNoSet() throws PersistenceLayerException
	{
		verifyCleanStart();
		TestContext tc = new TestContext();
		emptyCommit(testCampaign, tc);
		commit(modCampaign, tc, "5,10");
		completeRoundRobin(tc);
	}

	@Test
	public void testRoundRobinNoReset() throws PersistenceLayerException
	{
		verifyCleanStart();
		TestContext tc = new TestContext();
		commit(testCampaign, tc, "10,5");
		emptyCommit(modCampaign, tc);
		completeRoundRobin(tc);
	}
}
