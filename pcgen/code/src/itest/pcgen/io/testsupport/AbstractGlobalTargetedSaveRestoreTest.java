/*
 * Copyright (c) 2013 Tom Parker <thpr@users.sourceforge.net>
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
package pcgen.io.testsupport;

import org.junit.Test;

import pcgen.cdom.base.CDOMObject;
import pcgen.cdom.enumeration.ListKey;
import pcgen.cdom.enumeration.ObjectKey;
import pcgen.cdom.enumeration.SkillCost;
import pcgen.cdom.enumeration.Type;
import pcgen.cdom.reference.CDOMDirectSingleRef;
import pcgen.core.ArmorProf;
import pcgen.core.Equipment;
import pcgen.core.Language;
import pcgen.core.PCClass;
import pcgen.core.ShieldProf;
import pcgen.core.Skill;
import pcgen.core.WeaponProf;
import plugin.lsttokens.CcskillLst;
import plugin.lsttokens.CskillLst;
import plugin.lsttokens.TypeLst;
import plugin.lsttokens.auto.WeaponProfToken;
import plugin.lsttokens.choose.SkillToken;
import plugin.lsttokens.skill.ExclusiveToken;

public abstract class AbstractGlobalTargetedSaveRestoreTest<T extends CDOMObject>
		extends AbstractSaveRestoreTest
{

	public abstract Class<T> getObjectClass();

	protected abstract Object prepare(T obj);

	protected abstract void remove(Object o);

	protected abstract void applyObject(T obj);

	@Test
	public void testGlobalCSkill()
	{
		PCClass monclass = create(PCClass.class, "MonClass");
		new TypeLst().parseToken(context, monclass, "Monster");
		Skill granted = create(Skill.class, "Granted");
		new ExclusiveToken().parseToken(context, granted, "Yes");
		T target = create(getObjectClass(), "Target");
		Skill skill = create(Skill.class, "MySkill");
		new ExclusiveToken().parseToken(context, skill, "Yes");
		new CskillLst().parseToken(context, target, "LIST");
		new SkillToken().parseToken(context, target, "Granted|MySkill");
		Object o = prepare(target);
		finishLoad();
		applyObject(target);
		dumpPC(pc);
		runRoundRobin();
		assertEquals(SkillCost.CLASS,
			reloadedPC.getSkillCostForClass(granted, monclass));
		remove(o);
		reloadedPC.setDirty(true);
		assertEquals(SkillCost.EXCLUSIVE,
			reloadedPC.getSkillCostForClass(granted, monclass));
	}

	@Test
	public void testGlobalCCSkill()
	{
		PCClass myclass = create(PCClass.class, "SomeClass");
		Skill granted = create(Skill.class, "Granted");
		new ExclusiveToken().parseToken(context, granted, "Yes");
		T target = create(getObjectClass(), "Target");
		create(Skill.class, "MySkill");
		new CcskillLst().parseToken(context, target, "LIST");
		new SkillToken().parseToken(context, target, "Granted|MySkill");
		Object o = prepare(target);
		finishLoad();
		applyObject(target);
		runRoundRobin();
		assertEquals(SkillCost.CROSS_CLASS,
			reloadedPC.getSkillCostForClass(granted, myclass));
		remove(o);
		reloadedPC.setDirty(true);
		assertEquals(SkillCost.EXCLUSIVE,
			reloadedPC.getSkillCostForClass(granted, myclass));
	}

	@Test
	public void testAutoWeaponProf()
	{
		WeaponProf granted = create(WeaponProf.class, "Granted");
		create(WeaponProf.class, "Ignored");
		T target = create(getObjectClass(), "Target");
		new WeaponProfToken().parseToken(context, target, "%LIST");
		new plugin.lsttokens.choose.WeaponProficiencyToken().parseToken(
			context, target, "Granted|Ignored");
		Object o = prepare(target);
		finishLoad();
		assertFalse(pc.hasWeaponProf(granted));
		applyObject(target);
		assertTrue(pc.hasWeaponProf(granted));
		runRoundRobin();
		assertTrue(pc.hasWeaponProf(granted));
		assertTrue(reloadedPC.hasWeaponProf(granted));
		remove(o);
		reloadedPC.setDirty(true);
		assertFalse(reloadedPC.hasWeaponProf(granted));
	}

	@Test
	public void testAutoShieldProf()
	{
		ShieldProf granted = create(ShieldProf.class, "Granted");
		create(ShieldProf.class, "Ignored");
		T target = create(getObjectClass(), "Target");
		new plugin.lsttokens.auto.ShieldProfToken().parseToken(context, target,
			"%LIST");
		new plugin.lsttokens.choose.ShieldProficiencyToken().parseToken(
			context, target, "Granted|Ignored");
		Object o = prepare(target);
		finishLoad();
		Equipment e = new Equipment();
		e.addToListFor(ListKey.TYPE, Type.SHIELD);
		e.put(ObjectKey.SHIELD_PROF, CDOMDirectSingleRef.getRef(granted));
		assertFalse(pc.isProficientWith(e));
		applyObject(target);
		assertTrue(pc.isProficientWith(e));
		runRoundRobin();
		assertTrue(pc.isProficientWith(e));
		assertTrue(reloadedPC.isProficientWith(e));
		remove(o);
		reloadedPC.setDirty(true);
		assertFalse(reloadedPC.isProficientWith(e));
	}

	@Test
	public void testAutoArmorProf()
	{
		T target = create(getObjectClass(), "Target");
		ArmorProf granted = create(ArmorProf.class, "Granted");
		create(ArmorProf.class, "Ignored");
		new plugin.lsttokens.auto.ArmorProfToken().parseToken(context, target,
			"%LIST");
		new plugin.lsttokens.choose.ArmorProficiencyToken().parseToken(context,
			target, "Granted|Ignored");
		Object o = prepare(target);
		finishLoad();
		Equipment e = new Equipment();
		e.addToListFor(ListKey.TYPE, Type.ARMOR);
		e.put(ObjectKey.ARMOR_PROF, CDOMDirectSingleRef.getRef(granted));
		assertFalse(pc.isProficientWith(e));
		applyObject(target);
		assertTrue(pc.isProficientWith(e));
		runRoundRobin();
		assertTrue(pc.isProficientWith(e));
		assertTrue(reloadedPC.isProficientWith(e));
		remove(o);
		reloadedPC.setDirty(true);
		assertFalse(reloadedPC.isProficientWith(e));
	}

	@Test
	public void testAutoLanguage()
	{
		T target = create(getObjectClass(), "Target");
		Language granted = create(Language.class, "Granted");
		create(Language.class, "Ignored");
		new plugin.lsttokens.auto.LangToken().parseToken(context, target,
			"%LIST");
		new plugin.lsttokens.choose.LangToken().parseToken(context,
			target, "Granted|Ignored");
		Object o = prepare(target);
		finishLoad();
		assertFalse(pc.hasLanguage(granted));
		applyObject(target);
		assertTrue(pc.hasLanguage(granted));
		dumpPC(pc);
		runRoundRobin();
		assertTrue(pc.hasLanguage(granted));
		assertTrue(reloadedPC.hasLanguage(granted));
		remove(o);
		reloadedPC.setDirty(true);
		assertFalse(reloadedPC.hasLanguage(granted));
	}
}
