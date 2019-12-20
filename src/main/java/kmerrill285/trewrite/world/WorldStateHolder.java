package kmerrill285.trewrite.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class WorldStateHolder extends WorldSavedData {

	private static final WorldStateHolder CLIENT_DUMMY = new WorldStateHolder();

	public IWorld world;
	
	
	public class WorldState {
		
	}
	public WorldStateHolder() {
		super("trewrite:worldstate");
	}

	public WorldStateHolder(String name) {
		super(name);
	}
	
	// get the data from the world saved data manager, instantiating it first if it doesn't exist

	public static WorldStateHolder get(IWorld world)
	{
		if (!(world instanceof ServerWorld))
		{
			return CLIENT_DUMMY;
		}

		

		ServerWorld overworld = ((ServerWorld)world).getServer().getWorld(DimensionType.OVERWORLD);

		DimensionSavedDataManager storage = overworld.getSavedData();
		WorldStateHolder stateHolder = storage.getOrCreate(WorldStateHolder::new, "trewrite:worldstate");
		stateHolder.world = world;
		return stateHolder;
	}
	
	@Override
	public void read(CompoundNBT nbt) {
		
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		
		return compound;
	}
	
//	public long lastTick;
//	public void tick() {
//		World world = (World)this.world;
//		
//		this.markDirty();
//	}

}
