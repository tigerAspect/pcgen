/*
 * ClassChooserPane.java
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
 * Created on Jun 27, 2008, 1:36:26 PM
 */
package pcgen.gui.tabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import pcgen.gui.UIContext;
import pcgen.gui.facade.CharacterFacade;
import pcgen.gui.facade.ClassFacade;
import pcgen.gui.tools.ChooserPane;
import pcgen.gui.tools.FilteredTreeViewDisplay;
import pcgen.gui.util.JTablePane;
import pcgen.gui.util.JTreeViewPane;
import pcgen.gui.util.table.DefaultSortableTableModel;
import pcgen.gui.util.table.SortableTableModel;
import pcgen.util.PropertyFactory;

/**
 *
 * @author Connor Petty <cpmeister@users.sourceforge.net>
 */
public class ClassChooserPane extends ChooserPane implements Tab
{

    private final FilteredTreeViewDisplay treeviewDisplay;
    private final JTablePane tablePane;
    private final UIContext context;
    private SortableTableModel tableModel;
    private CharacterFacade character;
    private int spinnerValue;

    public ClassChooserPane(UIContext context)
    {
        this.treeviewDisplay = new FilteredTreeViewDisplay(context);
        this.tablePane = new JTablePane();
        this.context = context;
        initComponents();
    }

    private void initComponents()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JButton button;
        Dimension buttonSize = new Dimension(100, 23);

        button = new JButton(new AddClassAction());
        button.setDefaultCapable(false);
        button.setMinimumSize(buttonSize);
        button.setPreferredSize(buttonSize);

        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 1.0;
        constraints.insets = new java.awt.Insets(2, 2, 2, 2);
        panel.add(button, constraints);

        Dimension spinnerSize = new Dimension(50, 18);
        final JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, null,
                                                                      1));
        spinner.addChangeListener(
                new ChangeListener()
                {

                    public void stateChanged(ChangeEvent e)
                    {
                        spinnerValue = (Integer) spinner.getValue();
                    }

                });
        spinner.setMinimumSize(spinnerSize);
        spinner.setPreferredSize(spinnerSize);

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 0.0;
        panel.add(spinner, constraints);

        button = new JButton(new RemoveClassAction());
        button.setDefaultCapable(false);
        button.setMinimumSize(buttonSize);
        button.setPreferredSize(buttonSize);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.weightx = 1.0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(button, constraints);

        tablePane.getSelectionModel().addListSelectionListener(
                new ListSelectionListener()
                {

                    public void valueChanged(ListSelectionEvent e)
                    {
                        if (e.getValueIsAdjusting())
                        {

                        }
                    }

                });
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weighty = 1.0;
        panel.add(tablePane, constraints);

        setSecondaryChooserComponent(panel);
    }

    private Map<String, Object> saveModels()
    {
        return null;
    }

    private void loadModels(Map<String, Object> map)
    {

    }

    private class AddClassAction extends AbstractAction
    {

        public AddClassAction()
        {
            super(PropertyFactory.getString("in_add"));
        }

        public void actionPerformed(ActionEvent e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    private class RemoveClassAction extends AbstractAction
    {

        public RemoveClassAction()
        {
            super(PropertyFactory.getString("in_remove"));
        }

        public void actionPerformed(ActionEvent e)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    private class ClassTableModel extends DefaultSortableTableModel
    {
    }

    public void setCharacter(CharacterFacade character)
    {
        if (this.character != character)
        {
            saveModels();
            this.character = character;
        }

    }

}
