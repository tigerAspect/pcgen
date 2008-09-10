/*
 * StatGeneratorSelectionDialog.java
 * Copyright 2008 Connor Petty <cpmeister@users.sourceforge.net>
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
 * Created on Aug 31, 2008, 1:09:10 AM
 */
package pcgen.gui.generator.stat;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import pcgen.gui.generator.Generator;
import pcgen.gui.tools.AbstractSelectionDialog;
import pcgen.gui.tools.ResourceManager;

/**
 *
 * @author Connor Petty <cpmeister@users.sourceforge.net>
 */
public class StatGeneratorSelectionDialog extends AbstractSelectionDialog<Generator<Integer>>
{

    static
    {
        ResourceManager.ensureLoaded(ResourceManager.GENERATOR_BUNDLE);
    }

    private CardLayout cards;
    private JPanel cardPanel;

    public StatGeneratorSelectionDialog()
    {
        super(ResourceManager.getText("availStatGen"),
              ResourceManager.getText("selStatGen"),
              ResourceManager.getToolTip("newStatGen"),
              ResourceManager.getToolTip("copyStatGen"),
              ResourceManager.getToolTip("deleteStatGen"),
              ResourceManager.getToolTip("addStatGen"),
              ResourceManager.getToolTip("removeStatGen"));
    }

    protected void initComponents()
    {
        this.cards = new CardLayout();
        this.cardPanel = new JPanel(cards);
    }

    @Override
    protected Component getLeftComponent()
    {
        return cardPanel;
    }

    @Override
    protected Generator<Integer> createMutableItem(Generator<Integer> item)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean isMutable(Object item)
    {
        return false;//TODO
    }

    private class SelectionHandler implements ListSelectionListener
    {

        public void valueChanged(ListSelectionEvent e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}
