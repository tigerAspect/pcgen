package plugin.lsttokens.pcclass;

import pcgen.core.PCClass;
import pcgen.persistence.lst.PCClassLstToken;

/**
 * Class deals with XPPENALTY Token
 */
public class XppenaltyToken implements PCClassLstToken {

	/**
     * Get token name
     * @return token name
	 */
    public String getTokenName() {
		return "XPPENALTY";
	}

    /**
     * Parse XPPENALTY token
     * 
     * @param pcclass 
     * @param value 
     * @param level 
     * @return true
     */
	public boolean parse(PCClass pcclass, String value, int level) {
		pcclass.setXPPenalty(value);
		return true;
	}
}
