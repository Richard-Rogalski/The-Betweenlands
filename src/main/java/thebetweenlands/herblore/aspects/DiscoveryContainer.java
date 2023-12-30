package thebetweenlands.herblore.aspects;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import thebetweenlands.herblore.aspects.AspectManager.AspectItem;
import thebetweenlands.herblore.aspects.DiscoveryContainer.AspectDiscovery.EnumDiscoveryResult;

import java.util.*;
import java.util.Map.Entry;

public class DiscoveryContainer {
	private final Map<AspectItem, List<IAspectType>> discoveredStaticAspects = new HashMap<AspectItem, List<IAspectType>>();
	private final IDiscoveryProvider provider;
	private final Object providerObj;
	public DiscoveryContainer(IDiscoveryProvider provider, Object providerObj) {
		this.provider = provider;
		this.providerObj = providerObj;
	}

	public DiscoveryContainer() {
		this.provider = null;
		this.providerObj = null;
    }

    /**
     * Reads a discovery container from an NBT
     *
     * @param nbt
     * @return
     */
    public static DiscoveryContainer readFromNBT(NBTTagCompound nbt) {
        DiscoveryContainer container = new DiscoveryContainer();
        container.updateFromNBT(nbt, false);
        return container;
    }

	/**
	 * Returns how many aspects of the specified item are already discovered
	 * @param item
	 * @return
	 */
	private int getDiscoveryCount(AspectItem item) {
		return !this.discoveredStaticAspects.containsKey(item) ? 0 : this.discoveredStaticAspects.get(item).size();
	}

	private DiscoveryContainer saveContainer() {
		if(this.provider != null && this.providerObj != null)
			this.provider.saveContainer(this.providerObj, this);
		return this;
	}

	/**
	 * Discovers a new aspect of an item and returns the discovery result
	 * @param manager
	 * @param stack
	 * @return
	 */
	public AspectDiscovery discover(AspectManager manager, AspectItem item) {
		List<Aspect> staticAspects = manager.getStaticAspects(item);
		if(staticAspects.size() == 0) {
			return new AspectDiscovery(EnumDiscoveryResult.NONE, null, false);
		}
		int discoveryCount = this.getDiscoveryCount(item) + 1;
		if(discoveryCount > staticAspects.size()) {
			return new AspectDiscovery(EnumDiscoveryResult.END, null, false);
		}
		Aspect undiscovered = this.getUndiscoveredAspect(staticAspects, this.discoveredStaticAspects.get(item));
		this.addDiscovery(item, undiscovered.type);
		if(discoveryCount == staticAspects.size()) {
			this.saveContainer();
			return new AspectDiscovery(EnumDiscoveryResult.LAST, undiscovered, true);
		} else {
			this.saveContainer();
			return new AspectDiscovery(EnumDiscoveryResult.NEW, undiscovered, true);
		}
	}

	/**
	 * Discovers all aspects of all aspect items
	 * @param manager
	 */
	public void discoverAll(AspectManager manager) {
		for(Entry<AspectItem, List<Aspect>> e : manager.getMatchedAspects().entrySet()) {
			for(Aspect a : e.getValue())
				this.addDiscovery(e.getKey(), a.type);
		}
		this.saveContainer();
	}

	/**
	 * Removes all discoveries of the specified item
	 * @param item
	 */
	public void resetDiscovery(AspectItem item) {
		this.discoveredStaticAspects.remove(item);
		this.saveContainer();
	}

	/**
	 * Resets all aspect discoveries of all aspect items
	 */
	public void resetAllDiscovery() {
		this.discoveredStaticAspects.clear();
		this.saveContainer();
	}

	/**
	 * Adds a single aspect discovery
	 * @param item
	 * @param discovered
	 */
	public void addDiscovery(AspectItem item, IAspectType discovered) {
		List<IAspectType> discoveredAspects = this.discoveredStaticAspects.get(item);
		if(discoveredAspects == null) {
			this.discoveredStaticAspects.put(item, discoveredAspects = new ArrayList<IAspectType>());
		}
		if(!discoveredAspects.contains(discovered))
			discoveredAspects.add(discovered);
		this.saveContainer();
	}

	private Aspect getUndiscoveredAspect(List<Aspect> all, List<IAspectType> discovered) {
		if(discovered == null) {
			return all.size() == 0 ? null : all.get(0);
		}
		for(Aspect a : all) {
			if(!discovered.contains(a.type))
				return a;
		}
		return null;
	}

	/**
	 * Writes this discovery container to an NBT
	 * @param nbt
	 */
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList discoveryList = new NBTTagList();
		Iterator<Entry<AspectItem, List<IAspectType>>> discoveryIT = this.discoveredStaticAspects.entrySet().iterator();
		while(discoveryIT.hasNext()) {
			Entry<AspectItem, List<IAspectType>> e = discoveryIT.next();
			if(e.getValue() == null || e.getValue().size() == 0) {
				discoveryIT.remove();
				continue;
			}
			NBTTagCompound discoveryEntry = new NBTTagCompound();
			e.getKey().writeToNBT(discoveryEntry);
			NBTTagList aspectListCompound = new NBTTagList();
			for(IAspectType type : e.getValue()) {
				aspectListCompound.appendTag(AspectManager.writeAspectTypeNBT(type, new NBTTagCompound()));
			}
			discoveryEntry.setTag("aspects", aspectListCompound);
			discoveryList.appendTag(discoveryEntry);
		}
		nbt.setTag("discoveries", discoveryList);
    }

    /**
     * Updates this discovery container from an NBT
     * @param nbt
     * @return
     */
    public DiscoveryContainer updateFromNBT(NBTTagCompound nbt, boolean save) {
        this.discoveredStaticAspects.clear();
        NBTTagList discoveryList = nbt.getTagList("discoveries", Constants.NBT.TAG_COMPOUND);
        int discoveryEntries = discoveryList.tagCount();
        for (int i = 0; i < discoveryEntries; i++) {
            NBTTagCompound discoveryEntry = discoveryList.getCompoundTagAt(i);
            AspectItem item = AspectItem.readFromNBT(discoveryEntry);
            List<IAspectType> aspectTypeList = new ArrayList<IAspectType>();
            NBTTagList aspectListCompound = discoveryEntry.getTagList("aspects", Constants.NBT.TAG_COMPOUND);
            for (int c = 0; c < aspectListCompound.tagCount(); c++) {
                NBTTagCompound aspectTypeCompound = aspectListCompound.getCompoundTagAt(c);
                aspectTypeList.add(AspectManager.readAspectTypeFromNBT(aspectTypeCompound));
            }
            this.discoveredStaticAspects.put(item, aspectTypeList);
        }
        if (save)
            this.saveContainer();
        return this;
    }

    /**
     * Merges the discoveries of another container into this container
     * @param other
     * @return
     */
    public DiscoveryContainer mergeDiscoveries(DiscoveryContainer other) {
        boolean changed = false;
        for (Entry<AspectItem, List<IAspectType>> entry : other.discoveredStaticAspects.entrySet()) {
            AspectItem otherItem = entry.getKey();
            List<IAspectType> otherTypes = entry.getValue();
            if (!this.discoveredStaticAspects.containsKey(otherItem)) {
                this.discoveredStaticAspects.put(otherItem, otherTypes);
                changed = true;
            } else {
                List<IAspectType> aspectTypes = this.discoveredStaticAspects.get(otherItem);
                for (IAspectType otherType : otherTypes) {
                    if (!aspectTypes.contains(otherType)) {
                        aspectTypes.add(otherType);
                        changed = true;
                    }
                }
            }
		}
        if(changed)
			this.saveContainer();
		return this;
	}

	/**
	 * Returns the list of all the discovered aspects of the specified item.
	 * @param manager
	 * @param item
	 * @return
	 */
	public List<Aspect> getDiscoveredStaticAspects(AspectManager manager, AspectItem item) {
		List<Aspect> discoveredStaticAspects = new ArrayList<Aspect>();
		if(this.discoveredStaticAspects.containsKey(item)) {
			List<IAspectType> discoveredAspects = this.discoveredStaticAspects.get(item);
			List<Aspect> staticAspects = manager.getStaticAspects(item);
			for(Aspect a : staticAspects) {
				if(discoveredAspects.contains(a.type))
					discoveredStaticAspects.add(a);
            }
        }
        return discoveredStaticAspects;
    }

    public static class AspectDiscovery {
        public final EnumDiscoveryResult result;
        public final boolean successful;
        public final Aspect discovered;

        private AspectDiscovery(EnumDiscoveryResult result, Aspect discovered, boolean successful) {
            this.result = result;
            this.discovered = discovered;
            this.successful = successful;
        }

        public enum EnumDiscoveryResult {
            NONE, NEW, LAST, END
        }
    }

}
