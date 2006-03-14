package plugin.lsttokens.gamemode;

import pcgen.core.GameMode;
import pcgen.persistence.lst.GameModeLstToken;

/**
 * Class deals with HIDDENEQUIPTYPES Token
 */
public class HiddenfeattypesToken implements GameModeLstToken {

	public String getTokenName() {
		return "HIDDENFEATTYPES";
	}

	public boolean parse(GameMode gameMode, String value) {
		gameMode.setHiddenAbilityTypes(value);
		return true;
	}
}
