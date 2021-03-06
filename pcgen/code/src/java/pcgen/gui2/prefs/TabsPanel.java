/*
 * TabsPanel.java
 * Copyright 2010(C) James Dempsey
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
 * Created on 17/11/2010 19:50:00
 *
 * $Id$
 */
package pcgen.gui2.prefs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import pcgen.cdom.base.Constants;
import pcgen.core.SettingsHandler;
import pcgen.gui2.tools.Utility;
import pcgen.gui2.util.JComboBoxEx;
import pcgen.system.LanguageBundle;
import pcgen.util.Logging;

/**
 * The Class <code>TabsPanel</code> is responsible for 
 * displaying tabs display related preferences and allowing the 
 * preferences to be edited by the user.
 * 
 * Last Editor: $Author$
 * Last Edited: $Date$
 * 
 * @author James Dempsey <jdempsey@users.sourceforge.net>
 * @version $Revision$
 */
@SuppressWarnings("serial")
public class TabsPanel extends PCGenPrefsPanel
{
	private static String in_tabs = LanguageBundle.getString("in_Prefs_tabs");

	private static String in_charTabPlacement =
		LanguageBundle.getString("in_Prefs_charTabPlacement");
	private static String in_charTabLabel =
		LanguageBundle.getString("in_Prefs_charTabLabel");
	private static String in_mainTabPlacement =
		LanguageBundle.getString("in_Prefs_mainTabPlacement");
	private static String in_tabLabelPlain =
		LanguageBundle.getString("in_Prefs_tabLabelPlain");
	private static String in_tabLabelEpic =
			LanguageBundle.getString("in_Prefs_tabLabelEpic");
	private static String in_tabLabelRace =
			LanguageBundle.getString("in_Prefs_tabLabelRace");
	private static String in_tabLabelNetHack =
			LanguageBundle.getString("in_Prefs_tabLabelNetHack");
	private static String in_tabLabelFull =
			LanguageBundle.getString("in_Prefs_tabLabelFull");
	private static String in_tabPosTop =
			LanguageBundle.getString("in_Prefs_tabPosTop");
	private static String in_tabPosBottom =
			LanguageBundle.getString("in_Prefs_tabPosBottom");
	private static String in_tabPosLeft =
			LanguageBundle.getString("in_Prefs_tabPosLeft");
	private static String in_tabPosRight =
			LanguageBundle.getString("in_Prefs_tabPosRight");

	private JComboBoxEx charTabPlacementCombo;
	private JComboBoxEx mainTabPlacementCombo;
	private JComboBoxEx tabLabelsCombo;
	
	/**
	 * Instantiates a new Tabs panel.
	 */
	public TabsPanel()
	{
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JLabel label;
		Border etched = null;
		TitledBorder title1 = BorderFactory.createTitledBorder(etched, in_tabs);

		title1.setTitleJustification(TitledBorder.LEFT);
		this.setBorder(title1);
		gridbag = new GridBagLayout();
		this.setLayout(gridbag);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(2, 2, 2, 2);

		Utility.buildConstraints(c, 0, 0, 2, 1, 0, 0);
		label = new JLabel(in_mainTabPlacement + ": ");
		gridbag.setConstraints(label, c);
		this.add(label);
		Utility.buildConstraints(c, 2, 0, 1, 1, 0, 0);
		mainTabPlacementCombo =
				new JComboBoxEx(new String[]{in_tabPosTop, in_tabPosBottom,
					in_tabPosLeft, in_tabPosRight});
		gridbag.setConstraints(mainTabPlacementCombo, c);
		this.add(mainTabPlacementCombo);

		Utility.buildConstraints(c, 0, 1, 2, 1, 0, 0);
		label = new JLabel(in_charTabPlacement + ": ");
		gridbag.setConstraints(label, c);
		this.add(label);
		Utility.buildConstraints(c, 2, 1, 1, 1, 0, 0);
		charTabPlacementCombo =
				new JComboBoxEx(new String[]{in_tabPosTop, in_tabPosBottom,
					in_tabPosLeft, in_tabPosRight});
		gridbag.setConstraints(charTabPlacementCombo, c);
		this.add(charTabPlacementCombo);

		Utility.buildConstraints(c, 0, 2, 2, 1, 0, 0);
		label = new JLabel(in_charTabLabel + ": ");
		gridbag.setConstraints(label, c);
		this.add(label);
		Utility.buildConstraints(c, 2, 2, 1, 1, 0, 0);
		tabLabelsCombo =
				new JComboBoxEx(new String[]{in_tabLabelPlain, in_tabLabelEpic,
					in_tabLabelRace, in_tabLabelNetHack, in_tabLabelFull});
		gridbag.setConstraints(tabLabelsCombo, c);
		this.add(tabLabelsCombo);

		Utility.buildConstraints(c, 5, 20, 1, 1, 1, 1);
		c.fill = GridBagConstraints.BOTH;
		label = new JLabel(" ");
		gridbag.setConstraints(label, c);
		this.add(label);
	}

	/* (non-Javadoc)
	 * @see pcgen.gui2.prefs.PCGenPrefsPanel#getTitle()
	 */
	@Override
	public String getTitle()
	{
		return in_tabs;
	}
	
	/* (non-Javadoc)
	 * @see pcgen.gui2.prefs.PreferencesPanel#applyPreferences()
	 */
	@Override
	public void setOptionsBasedOnControls()
	{
		switch (mainTabPlacementCombo.getSelectedIndex())
		{
			case 0:
				SettingsHandler.setTabPlacement(SwingConstants.TOP);

				break;

			case 1:
				SettingsHandler.setTabPlacement(SwingConstants.BOTTOM);

				break;

			case 2:
				SettingsHandler.setTabPlacement(SwingConstants.LEFT);

				break;

			case 3:
				SettingsHandler.setTabPlacement(SwingConstants.RIGHT);

				break;

			default:
				Logging
					.errorPrint("In PreferencesDialog.setOptionsBasedOnControls (mainTabPlacementCombo) the index "
						+ mainTabPlacementCombo.getSelectedIndex()
						+ " is unsupported.");

				break;
		}

		switch (charTabPlacementCombo.getSelectedIndex())
		{
			case 0:
				SettingsHandler.setChaTabPlacement(SwingConstants.TOP);

				break;

			case 1:
				SettingsHandler.setChaTabPlacement(SwingConstants.BOTTOM);

				break;

			case 2:
				SettingsHandler.setChaTabPlacement(SwingConstants.LEFT);

				break;

			case 3:
				SettingsHandler.setChaTabPlacement(SwingConstants.RIGHT);

				break;

			default:
				Logging
					.errorPrint("In PreferencesDialog.setOptionsBasedOnControls (charTabPlacementCombo) the index "
						+ charTabPlacementCombo.getSelectedIndex()
						+ " is unsupported.");

				break;
		}

		switch (tabLabelsCombo.getSelectedIndex())
		{
			case 0:
				SettingsHandler
					.setNameDisplayStyle(Constants.DISPLAY_STYLE_NAME);

				break;

			case 1:
				SettingsHandler
					.setNameDisplayStyle(Constants.DISPLAY_STYLE_NAME_CLASS);

				break;

			case 2:
				SettingsHandler
					.setNameDisplayStyle(Constants.DISPLAY_STYLE_NAME_RACE);

				break;

			case 3:
				SettingsHandler
					.setNameDisplayStyle(Constants.DISPLAY_STYLE_NAME_RACE_CLASS);

				break;

			case 4:
				SettingsHandler
					.setNameDisplayStyle(Constants.DISPLAY_STYLE_NAME_FULL);

				break;

			default:
				Logging
					.errorPrint("In PreferencesDialog.setOptionsBasedOnControls (tabLabelsCombo) the index "
						+ tabLabelsCombo.getSelectedIndex()
						+ " is unsupported.");

				break;
		}
	}

	/* (non-Javadoc)
	 * @see pcgen.gui2.prefs.PreferencesPanel#initPreferences()
	 */
	@Override
	public void applyOptionValuesToControls()
	{
		switch (SettingsHandler.getTabPlacement())
		{
			case SwingConstants.TOP:
				mainTabPlacementCombo.setSelectedIndex(0);

				break;

			case SwingConstants.BOTTOM:
				mainTabPlacementCombo.setSelectedIndex(1);

				break;

			case SwingConstants.LEFT:
				mainTabPlacementCombo.setSelectedIndex(2);

				break;

			case SwingConstants.RIGHT:
				mainTabPlacementCombo.setSelectedIndex(3);

				break;

			default:
				Logging
					.errorPrint("In PreferencesDialog.applyOptionValuesToControls (tab placement) the tab option "
						+ SettingsHandler.getTabPlacement()
						+ " is unsupported.");

				break;
		}

		switch (SettingsHandler.getChaTabPlacement())
		{
			case SwingConstants.TOP:
				charTabPlacementCombo.setSelectedIndex(0);

				break;

			case SwingConstants.BOTTOM:
				charTabPlacementCombo.setSelectedIndex(1);

				break;

			case SwingConstants.LEFT:
				charTabPlacementCombo.setSelectedIndex(2);

				break;

			case SwingConstants.RIGHT:
				charTabPlacementCombo.setSelectedIndex(3);

				break;

			default:
				Logging
					.errorPrint("In PreferencesDialog.applyOptionValuesToControls (cha tab placement) the tab option "
						+ SettingsHandler.getChaTabPlacement()
						+ " is unsupported.");

				break;
		}

		switch (SettingsHandler.getNameDisplayStyle())
		{
			case Constants.DISPLAY_STYLE_NAME:
				tabLabelsCombo.setSelectedIndex(0);

				break;

			case Constants.DISPLAY_STYLE_NAME_CLASS:
				tabLabelsCombo.setSelectedIndex(1);

				break;

			case Constants.DISPLAY_STYLE_NAME_RACE:
				tabLabelsCombo.setSelectedIndex(2);

				break;

			case Constants.DISPLAY_STYLE_NAME_RACE_CLASS:
				tabLabelsCombo.setSelectedIndex(3);

				break;

			case Constants.DISPLAY_STYLE_NAME_FULL:
				tabLabelsCombo.setSelectedIndex(4);

				break;

			default:
				Logging
					.errorPrint("In PreferencesDialog.applyOptionValuesToControls (name display style) the tab option "
						+ SettingsHandler.getNameDisplayStyle()
						+ " is unsupported.");

				break;
		}
	}

}
