package plugin.lsttokens.ability;

import pcgen.core.Ability;
import pcgen.persistence.lst.AbilityLstToken;
import pcgen.util.Logging;
import pcgen.util.enumeration.Visibility;

/**
 * <code>VisibleToken</code> handles the processing of the VISIBLE tag
 * in the definition of an Ability.
 *
 * Last Editor: $Author$
 * Last Edited: $Date$
 *
 * @author Devon Jones
 * @version $Revision$
 */
public class VisibleToken implements AbilityLstToken
{

	/**
	 * @see pcgen.persistence.lst.LstToken#getTokenName()
	 */
	public String getTokenName()
	{
		return "VISIBLE";
	}

	/**
	 * @see pcgen.persistence.lst.AbilityLstToken#parse(pcgen.core.Ability, java.lang.String)
	 */
	public boolean parse(Ability ability, String value)
	{
		final String visType = value.toUpperCase();
		if (visType.startsWith("EXPORT"))
		{
			if (!"EXPORT".equalsIgnoreCase(visType))
			{
				Logging.errorPrint(visType + " is not a valid value for " + getTokenName());
				Logging.errorPrint(" using EXPORT");
				Logging.errorPrint(" This will break after PCGen 5.12");
			}
			ability.setVisibility(Visibility.OUTPUT_ONLY);
		}
		else if (visType.startsWith("NO"))
		{
			if (!"NO".equalsIgnoreCase(visType))
			{
				Logging.errorPrint(visType + " is not a valid value for " + getTokenName());
				Logging.errorPrint(" using NO");
				Logging.errorPrint(" This will break after PCGen 5.12");
			}
			ability.setVisibility(Visibility.HIDDEN);
		}
		else if (visType.startsWith("DISPLAY"))
		{
			if (!"DISPLAY".equalsIgnoreCase(visType))
			{
				Logging.errorPrint(visType + " is not a valid value for " + getTokenName());
				Logging.errorPrint(" using DISPLAY");
				Logging.errorPrint(" This will break after PCGen 5.12");
			}
			ability.setVisibility(Visibility.DISPLAY_ONLY);
		}
		else
		{
			ability.setVisibility(Visibility.DEFAULT);
		}
		return true;
	}
}
