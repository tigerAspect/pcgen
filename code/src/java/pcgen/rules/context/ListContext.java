package pcgen.rules.context;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import pcgen.base.util.DoubleKeyMap;
import pcgen.base.util.DoubleKeyMapToList;
import pcgen.base.util.HashMapToList;
import pcgen.base.util.TreeMapToList;
import pcgen.base.util.TripleKeyMap;
import pcgen.cdom.base.AssociatedPrereqObject;
import pcgen.cdom.base.CDOMList;
import pcgen.cdom.base.CDOMObject;
import pcgen.cdom.base.CDOMReference;
import pcgen.cdom.base.LSTWriteable;
import pcgen.cdom.enumeration.AssociationKey;
import pcgen.cdom.inst.SimpleAssociatedObject;
import pcgen.rules.persistence.TokenUtilities;
import pcgen.util.MapToList;

public class ListContext
{

	private final TrackingListCommitStrategy edits = new TrackingListCommitStrategy();

	private final ListCommitStrategy commit;

	public ListContext()
	{
		commit = new TrackingListCommitStrategy();
	}

	public ListContext(ListCommitStrategy commitStrategy)
	{
		if (commitStrategy == null)
		{
			throw new IllegalArgumentException("Commit Strategy cannot be null");
		}
		commit = commitStrategy;
	}

	public URI getSourceURI()
	{
		return edits.getSourceURI();
	}

	public void setSourceURI(URI sourceURI)
	{
		edits.setSourceURI(sourceURI);
		commit.setSourceURI(sourceURI);
	}

	public URI getExtractURI()
	{
		return edits.getExtractURI();
	}

	public void setExtractURI(URI extractURI)
	{
		edits.setExtractURI(extractURI);
		commit.setExtractURI(extractURI);
	}

	public AssociatedPrereqObject addToMasterList(String tokenName,
			CDOMObject owner, CDOMReference<? extends CDOMList<?>> list,
			LSTWriteable allowed)
	{
		return edits.addToMasterList(tokenName, owner, list, allowed);
	}

	public void clearAllMasterLists(String tokenName, CDOMObject owner)
	{
		edits.clearAllMasterLists(tokenName, owner);
	}

	public <T extends CDOMObject> void clearMasterList(String tokenName,
			CDOMObject owner, CDOMReference<? extends CDOMList<T>> list)
	{
		edits.clearMasterList(tokenName, owner, list);
	}

	public <T extends CDOMObject> AssociatedPrereqObject addToList(
			String tokenName, CDOMObject owner,
			CDOMReference<? extends CDOMList<T>> list, CDOMReference<T> allowed)
	{
		return edits.addToList(tokenName, owner, list, allowed);
	}

	public <T extends CDOMObject> void removeFromList(String tokenName,
			CDOMObject owner, CDOMReference<? extends CDOMList<T>> list,
			CDOMReference<T> ref)
	{
		edits.removeFromList(tokenName, owner, list, ref);
	}

	public void removeAllFromList(String tokenName, CDOMObject owner,
			CDOMReference<? extends CDOMList<?>> swl)
	{
		edits.removeAllFromList(tokenName, owner, swl);
	}

	public void commit()
	{
		// for (CDOMReference<? extends CDOMList<?>> list : masterClearSet
		// .getKeySet())
		// {
		// for (OwnerURI ou : masterClearSet.getListFor(list))
		// {
		// context.commitClearMasterList("FOO", ou.owner, list);
		// }
		// }
		for (CDOMReference<? extends CDOMList<?>> list : edits.positiveMasterMap
				.getKeySet())
		{
			for (OwnerURI ou : edits.positiveMasterMap.getSecondaryKeySet(list))
			{
				for (LSTWriteable child : edits.positiveMasterMap
						.getTertiaryKeySet(list, ou))
				{
					AssociatedPrereqObject assoc = edits.positiveMasterMap.get(
							list, ou, child);
					AssociatedPrereqObject edge = commit.addToMasterList(assoc
							.getAssociation(AssociationKey.TOKEN), ou.owner,
							list, child);
					Collection<AssociationKey<?>> associationKeys = assoc
							.getAssociationKeys();
					for (AssociationKey<?> ak : associationKeys)
					{
						setAssoc(assoc, edge, ak);
					}
					edge.addAllPrerequisites(assoc.getPrerequisiteList());
				}
			}
		}
		for (URI uri : edits.globalClearSet.getKeySet())
		{
			for (CDOMObject owner : edits.globalClearSet
					.getSecondaryKeySet(uri))
			{
				for (CDOMReference<? extends CDOMList<?>> list : edits.globalClearSet
						.getListFor(uri, owner))
				{
					commit.removeAllFromList("FOO", owner, list);
				}
			}
		}
		for (URI uri : edits.negativeMap.getKeySet())
		{
			for (CDOMObject owner : edits.negativeMap.getSecondaryKeySet(uri))
			{
				CDOMObject neg = edits.negativeMap.get(uri, owner);
				Collection<CDOMReference<? extends CDOMList<? extends CDOMObject>>> modifiedLists = neg
						.getModifiedLists();
				for (CDOMReference list : modifiedLists)
				{
					remove(owner, neg, list);
				}
			}
		}
		for (URI uri : edits.positiveMap.getKeySet())
		{
			for (CDOMObject owner : edits.positiveMap.getSecondaryKeySet(uri))
			{
				CDOMObject neg = edits.positiveMap.get(uri, owner);
				Collection<CDOMReference<? extends CDOMList<? extends CDOMObject>>> modifiedLists = neg
						.getModifiedLists();
				for (CDOMReference list : modifiedLists)
				{
					add(owner, neg, list);
				}
			}
		}
		for (String token : edits.masterAllClear.getKeySet())
		{
			for (OwnerURI ou : edits.masterAllClear.getListFor(token))
			{
				commit.clearAllMasterLists(token, ou.owner);
			}
		}
		decommit();
	}

	public void decommit()
	{
		edits.decommit();
	}

	public Collection<CDOMReference<? extends CDOMList<? extends CDOMObject>>> getChangedLists(
			CDOMObject owner, Class<? extends CDOMList<?>> cl)
	{
		return commit.getChangedLists(owner, cl);
	}

	public <T extends CDOMObject> AssociatedChanges<CDOMReference<T>> getChangesInList(
			String tokenName, CDOMObject owner,
			CDOMReference<? extends CDOMList<T>> swl)
	{
		return commit.getChangesInList(tokenName, owner, swl);
	}

	public AssociatedChanges<LSTWriteable> getChangesInMasterList(
			String tokenName, CDOMObject owner,
			CDOMReference<? extends CDOMList<?>> swl)
	{
		return commit.getChangesInMasterList(tokenName, owner, swl);
	}

	public Changes<CDOMReference> getMasterListChanges(String tokenName,
			CDOMObject owner, Class<? extends CDOMList<?>> cl)
	{
		return commit.getMasterListChanges(tokenName, owner, cl);
	}

	public boolean hasMasterLists()
	{
		return commit.hasMasterLists();
	}

	private <BT extends CDOMObject> void remove(CDOMObject owner,
			CDOMObject neg, CDOMReference<CDOMList<BT>> list)
	{
		Collection<CDOMReference<BT>> mods = neg.getListMods(list);
		for (CDOMReference<BT> ref : mods)
		{
			for (AssociatedPrereqObject assoc : neg.getListAssociations(list,
					ref))
			{
				commit
						.removeFromList(assoc
								.getAssociation(AssociationKey.TOKEN), owner,
								list, ref);
			}
		}
	}

	private <BT extends CDOMObject> void add(CDOMObject owner, CDOMObject neg,
			CDOMReference<CDOMList<BT>> list)
	{
		Collection<CDOMReference<BT>> mods = neg.getListMods(list);
		for (CDOMReference<BT> ref : mods)
		{
			for (AssociatedPrereqObject assoc : neg.getListAssociations(list,
					ref))
			{
				String token = assoc.getAssociation(AssociationKey.TOKEN);
				AssociatedPrereqObject edge = commit.addToList(token, owner,
						list, ref);
				Collection<AssociationKey<?>> associationKeys = assoc
						.getAssociationKeys();
				for (AssociationKey<?> ak : associationKeys)
				{
					setAssoc(assoc, edge, ak);
				}
				edge.addAllPrerequisites(assoc.getPrerequisiteList());
			}
		}
	}

	private <T> void setAssoc(AssociatedPrereqObject assoc,
			AssociatedPrereqObject edge, AssociationKey<T> ak)
	{
		edge.setAssociation(ak, assoc.getAssociation(ak));
	}

	public class TrackingListCommitStrategy implements ListCommitStrategy
	{

		protected class CDOMShell extends CDOMObject
		{
			@Override
			public CDOMObject clone() throws CloneNotSupportedException
			{
				throw new CloneNotSupportedException();
			}
		}

		private URI sourceURI;

		private URI extractURI;

		public URI getExtractURI()
		{
			return extractURI;
		}

		public void setExtractURI(URI extractURI)
		{
			this.extractURI = extractURI;
		}

		public URI getSourceURI()
		{
			return sourceURI;
		}

		public void setSourceURI(URI sourceURI)
		{
			this.sourceURI = sourceURI;
		}

		private TripleKeyMap<CDOMReference<? extends CDOMList<?>>, OwnerURI, LSTWriteable, AssociatedPrereqObject> positiveMasterMap = new TripleKeyMap<CDOMReference<? extends CDOMList<?>>, OwnerURI, LSTWriteable, AssociatedPrereqObject>();

		private HashMapToList<CDOMReference<? extends CDOMList<?>>, OwnerURI> masterClearSet = new HashMapToList<CDOMReference<? extends CDOMList<?>>, OwnerURI>();

		private HashMapToList<String, OwnerURI> masterAllClear = new HashMapToList<String, OwnerURI>();

		public AssociatedPrereqObject addToMasterList(String tokenName,
				CDOMObject owner, CDOMReference<? extends CDOMList<?>> list,
				LSTWriteable allowed)
		{
			SimpleAssociatedObject a = new SimpleAssociatedObject();
			a.setAssociation(AssociationKey.OWNER, owner);
			a.setAssociation(AssociationKey.TOKEN, tokenName);
			positiveMasterMap.put(list, new OwnerURI(sourceURI, owner),
					allowed, a);
			return a;
		}

		public Changes<CDOMReference> getMasterListChanges(String tokenName,
				CDOMObject owner, Class<? extends CDOMList<?>> cl)
		{
			OwnerURI lo = new OwnerURI(extractURI, owner);
			ArrayList<CDOMReference> list = new ArrayList<CDOMReference>();
			Set<CDOMReference<? extends CDOMList<?>>> set = positiveMasterMap
					.getKeySet();
			if (set != null)
			{
				LIST: for (CDOMReference<? extends CDOMList<?>> ref : set)
				{
					if (!cl.equals(ref.getReferenceClass()))
					{
						continue;
					}
					for (LSTWriteable allowed : positiveMasterMap
							.getTertiaryKeySet(ref, lo))
					{
						AssociatedPrereqObject assoc = positiveMasterMap.get(
								ref, lo, allowed);
						if (owner.equals(assoc
								.getAssociation(AssociationKey.OWNER))
								&& tokenName.equals(assoc
										.getAssociation(AssociationKey.TOKEN)))
						{
							list.add(ref);
							continue LIST;
						}
					}
				}
			}
			return new CollectionChanges<CDOMReference>(list, null,
					masterAllClear.containsInList(tokenName, lo));
		}

		public AssociatedChanges<LSTWriteable> getChangesInMasterList(
				String tokenName, CDOMObject owner,
				CDOMReference<? extends CDOMList<?>> swl)
		{
			MapToList<LSTWriteable, AssociatedPrereqObject> map = new TreeMapToList<LSTWriteable, AssociatedPrereqObject>(
					TokenUtilities.WRITEABLE_SORTER);
			OwnerURI lo = new OwnerURI(extractURI, owner);
			Set<LSTWriteable> added = positiveMasterMap.getTertiaryKeySet(swl,
					lo);
			for (LSTWriteable lw : added)
			{
				map.addToListFor(lw, positiveMasterMap.get(swl, lo, lw));
			}
			return new AssociatedCollectionChanges<LSTWriteable>(map, null,
					masterClearSet.containsInList(swl, lo));
		}

		public <T extends CDOMObject> void clearMasterList(String tokenName,
				CDOMObject owner, CDOMReference<? extends CDOMList<T>> list)
		{
			masterClearSet.addToListFor(list, new OwnerURI(sourceURI, owner));
		}

		public void clearAllMasterLists(String tokenName, CDOMObject owner)
		{
			masterAllClear.addToListFor(tokenName, new OwnerURI(sourceURI,
					owner));
		}

		private DoubleKeyMap<URI, CDOMObject, CDOMObject> positiveMap = new DoubleKeyMap<URI, CDOMObject, CDOMObject>();

		private DoubleKeyMap<URI, CDOMObject, CDOMObject> negativeMap = new DoubleKeyMap<URI, CDOMObject, CDOMObject>();

		private DoubleKeyMapToList<URI, CDOMObject, CDOMReference<? extends CDOMList<?>>> globalClearSet = new DoubleKeyMapToList<URI, CDOMObject, CDOMReference<? extends CDOMList<?>>>();

		private CDOMObject getPositive(URI source, CDOMObject cdo)
		{
			CDOMObject positive = positiveMap.get(source, cdo);
			if (positive == null)
			{
				positive = new CDOMShell();
				positiveMap.put(source, cdo, positive);
			}
			return positive;
		}

		private CDOMObject getNegative(URI source, CDOMObject cdo)
		{
			CDOMObject negative = negativeMap.get(source, cdo);
			if (negative == null)
			{
				negative = new CDOMShell();
				negativeMap.put(source, cdo, negative);
			}
			return negative;
		}

		public <T extends CDOMObject> AssociatedPrereqObject addToList(
				String tokenName, CDOMObject owner,
				CDOMReference<? extends CDOMList<T>> list,
				CDOMReference<T> allowed)
		{
			SimpleAssociatedObject a = new SimpleAssociatedObject();
			a.setAssociation(AssociationKey.TOKEN, tokenName);
			getPositive(sourceURI, owner).putToList(list, allowed, a);
			return a;
		}

		public <T extends CDOMObject> void removeFromList(String tokenName,
				CDOMObject owner, CDOMReference<? extends CDOMList<T>> list,
				CDOMReference<T> ref)
		{
			SimpleAssociatedObject a = new SimpleAssociatedObject();
			a.setAssociation(AssociationKey.TOKEN, tokenName);
			getNegative(sourceURI, owner).putToList(list, ref, a);
		}

		public Collection<CDOMReference<? extends CDOMList<? extends CDOMObject>>> getChangedLists(
				CDOMObject owner, Class<? extends CDOMList<?>> cl)
		{
			ArrayList<CDOMReference<? extends CDOMList<? extends CDOMObject>>> list = new ArrayList<CDOMReference<? extends CDOMList<? extends CDOMObject>>>();
			for (CDOMReference<? extends CDOMList<? extends CDOMObject>> ref : owner
					.getModifiedLists())
			{
				if (cl.equals(ref.getReferenceClass()))
				{
					list.add(ref);
				}
			}
			return list;
		}

		public void removeAllFromList(String tokenName, CDOMObject owner,
				CDOMReference<? extends CDOMList<?>> swl)
		{
			globalClearSet.addToListFor(sourceURI, owner, swl);
		}

		public <T extends CDOMObject> AssociatedChanges<CDOMReference<T>> getChangesInList(
				String tokenName, CDOMObject owner,
				CDOMReference<? extends CDOMList<T>> swl)
		{
			return new ListChanges<T>(getPositive(extractURI, owner),
					getNegative(extractURI, owner), swl, globalClearSet
							.containsInList(extractURI, owner, swl));
		}

		public boolean hasMasterLists()
		{
			return !positiveMasterMap.isEmpty() && !masterClearSet.isEmpty()
					&& !masterAllClear.isEmpty();
		}

		public void decommit()
		{
			masterAllClear.clear();
			masterClearSet.clear();
			positiveMasterMap.clear();
			positiveMap.clear();
			negativeMap.clear();
			globalClearSet.clear();
		}
	}

	private static class OwnerURI
	{
		public final CDOMObject owner;
		public final URI source;

		public OwnerURI(URI sourceURI, CDOMObject cdo)
		{
			source = sourceURI;
			owner = cdo;
		}

		@Override
		public int hashCode()
		{
			return owner.hashCode();
		}

		@Override
		public boolean equals(Object o)
		{
			if (o instanceof OwnerURI)
			{
				OwnerURI other = (OwnerURI) o;
				if (source == null)
				{
					if (other.source != null)
					{
						return false;
					}
				}
				else
				{
					if (!source.equals(other.source))
					{
						return false;
					}
				}
				return owner.equals(other.owner);
			}
			return false;
		}
	}

}
