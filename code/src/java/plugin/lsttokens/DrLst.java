/*
 * Created on Sep 2, 2005
 *
 */
package plugin.lsttokens;

import pcgen.core.PObject;
import pcgen.persistence.lst.GlobalLstToken;

/**
 * @author djones4
 *
 */

public class DrLst implements GlobalLstToken {

	public String getTokenName() {
		return "DR";
	}

	public boolean parse(PObject obj, String value, int anInt) {
		if (anInt > -9) {
			obj.setDR(anInt + "|" + value);
		} else {
			obj.setDR(value);
		}
		return true;
	}
}

