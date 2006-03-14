/*
 * Created on Sep 2, 2005
 *
 */
package plugin.lsttokens;

import java.util.Map;

import pcgen.core.PObject;
import pcgen.persistence.lst.GlobalLstToken;
import pcgen.persistence.lst.SourceLoader;
import pcgen.persistence.lst.SourceLstToken;


/**
 * @author djones4
 *
 */
public class SourcelongLst implements GlobalLstToken, SourceLstToken  {

	public String getTokenName() {
		return "SOURCELONG";
	}

	public boolean parse(PObject obj, String value, int anInt) {
		obj.setSourceMap(SourceLoader.parseSource("SOURCELONG:" + value));
		return true;
	}

	public boolean parse(Map sourceMap, String value) {
		sourceMap.putAll(SourceLoader.parseSource("SOURCELONG:" + value));
		return true;
	}
}

