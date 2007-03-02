/*
 * PCClass.java
 * Copyright 2006 (C) Tom Parker <thpr@users.sourceforge.net>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Created on October 25, 2006
 *
 * $Id: PCClass.java 1526 2006-10-25 03:56:08Z thpr $
 */
package pcgen.core;

import java.util.StringTokenizer;

import pcgen.cdom.base.ConcretePrereqObject;
import pcgen.util.enumeration.VisionType;

public class Vision extends ConcretePrereqObject implements Comparable<Vision> {

	private final VisionType visionType;

	private final String distance;

	public Vision(VisionType type, String dist) {
		if (type == null) {
			throw new IllegalArgumentException("Vision Type cannot be null");
		}
		visionType = type;
		distance = dist;
	}

	public String getDistance() {
		return distance;
	}

	public VisionType getType() {
		return visionType;
	}

	@Override
	public String toString() {
		try {
			return toString(Integer.parseInt(distance));
		} catch (NumberFormatException e) {
			return visionType + " (" + distance + "')";
		}
	}

	private String toString(int d) {
		if (d <= 0) {
			return visionType.toString();
		} else {
			return visionType + " (" + d + "')";
		}
	}

	@Override
	public int hashCode() {
		return visionType.hashCode()
				^ (distance == null ? 0 : distance.hashCode());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vision) {
			Vision v2 = (Vision) o;
			if (v2.visionType.equals(visionType)) {
				return v2.distance == null && distance == null
						|| distance != null && distance.equals(v2.distance);
			}
		}
		return false;
	}

	public int compareTo(Vision v) {
		// CONSIDER This is potentially a slow method, but definitely works -
		// thpr 10/26/06
		return toString().compareTo(v.toString());
	}

	public static Vision getVision(String visionType) {
		// expecting value in form of Darkvision (60')
		StringTokenizer cTok = new StringTokenizer(visionType, "(')");
		String aKey = cTok.nextToken().trim(); // e.g. Darkvision
		String aVal = "0";
		if (cTok.hasMoreTokens()) {
			aVal = cTok.nextToken(); // e.g. 60
		}
		return new Vision(VisionType.getVisionType(aKey), aVal);
	}

	/*
	 * REFACTOR NEED TO GET RID OF THIS - REFERENCES PlayerCharacter :(
	 */
	public String toString(PlayerCharacter aPC) {
		return toString(aPC.getVariableValue(distance, "").intValue());
	}
}
