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
package pcgen.cdom.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import pcgen.base.formula.Formula;
import pcgen.cdom.enumeration.AssociationKey;

public class Slot<T extends PrereqObject> extends ConcretePrereqObject
{

	private String name;

	private final Class<T> slotClass;

	private final Formula slotCount;

	public Slot(Class<T> cl)
	{
		slotClass = cl;
		slotCount = FormulaFactory.getFormulaFor("1");
	}

	public Slot(Class<T> cl, Formula count)
	{
		slotClass = cl;
		slotCount = count;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String slotName)
	{
		name = slotName;
	}

	public boolean isValid(PrereqObject o)
	{
		if (!slotClass.isAssignableFrom(o.getClass()))
		{
			return false;
		}
		if (!hasSinkRestrictions())
		{
			return true;
		}
		for (Restriction r : getSinkRestrictions())
		{
			if (!r.qualifies(o))
			{
				return false;
			}
		}
		return true;
	}

	public Class<T> getSlotClass()
	{
		return slotClass;
	}

	public String toLSTform()
	{
		return slotCount.toString();
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Slot: ").append(slotCount.toString());
		sb.append(" objects of ").append(slotClass.getSimpleName());
		if (hasSinkRestrictions())
		{
			sb.append(" [").append(getSinkRestrictions().toString())
				.append(']');
		}
		return sb.toString();
	}

	@Override
	public int hashCode()
	{
		return slotCount.hashCode() ^ slotClass.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
		{
			return true;
		}
		if (!(o instanceof Slot))
		{
			return false;
		}
		Slot<?> otherSlot = (Slot) o;
		if (name == null)
		{
			if (otherSlot.name != null)
			{
				return false;
			}
		}
		else
		{
			if (!name.equals(otherSlot.name))
			{
				return false;
			}
		}
		return slotClass.equals(otherSlot.slotClass)
			&& slotCount.equals(otherSlot.slotCount);
	}

	/*
	 * CONSIDER Use AssociationSupport? - Tom Parker Apr 7 07
	 */
	private Map<AssociationKey<?>, Object> associationMap;
	
	public <AKT> void setAssociation(AssociationKey<AKT> key, AKT value)
	{
		if (associationMap == null)
		{
			associationMap = new HashMap<AssociationKey<?>, Object>();
		}
		associationMap.put(key, value);
	}

	public <AKT> AKT getAssociation(AssociationKey<AKT> key)
	{
		return (AKT) (associationMap == null ? null : associationMap.get(key));
	}

	public Collection<AssociationKey<?>> getAssociationKeys()
	{
		return new HashSet<AssociationKey<?>>(associationMap.keySet());
	}

	public boolean hasAssociations()
	{
		return associationMap != null && !associationMap.isEmpty();
	}

}
