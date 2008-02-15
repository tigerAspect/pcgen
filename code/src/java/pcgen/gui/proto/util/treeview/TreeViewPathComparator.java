/*
 * TreeViewPathComparator.java
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
 * Created on Feb 10, 2008, 3:06:15 AM
 */
package pcgen.gui.proto.util.treeview;

import java.util.Comparator;
import pcgen.util.StringComparator;

/**
 *
 * @author Connor Petty <mistercpp2000@gmail.com>
 */
final class TreeViewPathComparator<E> implements Comparator<TreeViewPath<E>>
{

    private static final Comparator<Object> defaultcomparator = new StringComparator<Object>();
    private final Comparator<? super E> viewobjectcomparator;
    private final Comparator<String> stringcomparator;

    public TreeViewPathComparator()
    {
	this(defaultcomparator);
    }

    public TreeViewPathComparator(Comparator<? super E> pobjectcomparator)
    {
	this(pobjectcomparator, TreeViewMode.ASCENDING);
    }

    public TreeViewPathComparator(Comparator<? super E> pobjectcomparator, Comparator<String> stringcomparator)
    {
	this.viewobjectcomparator = pobjectcomparator;
	this.stringcomparator = stringcomparator;
    }

    public int compare(TreeViewPath o1, TreeViewPath o2)
    {
	Object obj1 = o1.getLastPathComponent();
	Object obj2 = o2.getLastPathComponent();
	if (obj1 instanceof String && obj2 instanceof String)
	{
	    return stringcomparator.compare((String)obj1, (String)obj2);
	}
	else if(obj1 instanceof String)
	{
	    return -1;
	}
	else if(obj2 instanceof String)
	{
	    return 1;
	}
	else
	{
	    return viewobjectcomparator.compare((E) obj1, (E) obj2);
	}
    }

}
