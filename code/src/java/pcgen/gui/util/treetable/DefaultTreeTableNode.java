/*
 * DefaultTreeTableNode.java
 * Copyright 2008 (C) Connor Petty <mistercpp2000@gmail.com>
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
 * Created on Feb 20, 2008, 6:55:50 PM
 */
package pcgen.gui.util.treetable;

import java.util.Collections;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import pcgen.util.UnboundedArrayList;

/**
 *
 * @author Connor Petty <mistercpp2000@gmail.com>
 */
public class DefaultTreeTableNode extends DefaultMutableTreeNode implements TreeTableNode
{

    private List<Object> data;

    public DefaultTreeTableNode()
    {
        this(Collections.emptyList());
    }

    public DefaultTreeTableNode(List<Object> data)
    {
        setValues(data);
    }

    public DefaultTreeTableNode(TreeNode node)
    {
        this();
        if (node instanceof TreeTableNode)
        {
            TreeTableNode treeTableNode = (TreeTableNode) node;
            setValues(treeTableNode.getValues());
        }
        for (int x = 0; x < node.getChildCount(); x++)
        {
            add(new DefaultTreeTableNode(node.getChildAt(x)));
        }
    }

    public Object getValueAt(int column)
    {
        return data.get(column);
    }

    public void setValueAt(Object value, int column)
    {
        data.set(column, value);
    }

    public List<Object> getValues()
    {
        return data;
    }

    private void setValues(List<Object> values)
    {
        this.data = new UnboundedArrayList<Object>(values);
    }

    @Override
    public String toString()
    {
        Object name = data.get(0);
        if (name != null)
        {
            return name.toString();
        }
        return super.toString();
    }

}
