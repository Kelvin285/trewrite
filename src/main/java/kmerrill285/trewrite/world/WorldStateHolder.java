package kmerrill285.trewrite.world;

import java.util.HashMap;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class WorldStateHolder extends WorldSavedData {

	private static final WorldStateHolder CLIENT_DUMMY = new WorldStateHolder();

	public IWorld world;
	
	public boolean eyeDefeated = false;
	public int shadowOrbsMined = 0;
	public boolean hardmode = false;
	public boolean skeletronDefeated = false;
	public boolean twinsDefeated = false;
	public boolean destroyerDefeated = false;
	public boolean skeletronPrimeDefeated = false;
	public boolean planteraDefeated = false;
	public boolean golemDefeated = false;
	public boolean cultistDefeated = false;
	public boolean solarPillarDefeated = false;
	public boolean vortexPillarDefeated = false;
	public boolean nebulaPillarDefeated = false;
	public boolean stardustPillarDefeated = false;
	public int solarEnemiesDefeated = 0;
	public int vortexEnemiesDefeated = 0;
	public int nebulaEnemiesDefeated = 0;
	public int stardustEnemiesDefeated = 0;
	public int invasionEnemiesDefeated = 0;
	
	public HashMap<String, BlockPos> spawnPositions = new HashMap<String, BlockPos>();
	
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
		eyeDefeated = nbt.getBoolean("eyeDefeated");
		shadowOrbsMined = nbt.getInt("shadowOrbsMined");
		hardmode = nbt.getBoolean("hardmode");
		skeletronDefeated = nbt.getBoolean("skeletronDefeated");
		twinsDefeated = nbt.getBoolean("twinsDefeated");
		destroyerDefeated = nbt.getBoolean("destroyerDefeated");
		skeletronPrimeDefeated = nbt.getBoolean("skeletronPrimeDefeated");
		planteraDefeated = nbt.getBoolean("planteraDefeated");
		golemDefeated = nbt.getBoolean("golemDefeated");
		cultistDefeated = nbt.getBoolean("cultistDefeated");
		solarPillarDefeated = nbt.getBoolean("solarPillarDefeated");
		vortexPillarDefeated = nbt.getBoolean("vortexPillarDefeated");
		nebulaPillarDefeated = nbt.getBoolean("nebulaPillarDefeated");
		stardustPillarDefeated = nbt.getBoolean("stardustPillarDefeated");
		solarEnemiesDefeated = nbt.getInt("solarEnemiesDefeated");
		vortexEnemiesDefeated = nbt.getInt("vortexEnemiesDefeated");
		nebulaEnemiesDefeated = nbt.getInt("nebulaEnemiesDefeated");
		stardustEnemiesDefeated = nbt.getInt("stardustEnemiesDefeated");
		invasionEnemiesDefeated = nbt.getInt("invasionEnemiesDefeated");
		
		int size = nbt.getInt("sposLength");
		for (int i = 0; i < size; i++) {
			String s = nbt.getString("spawnpos["+i+"]").trim();
			String[] data = s.split(",");
			String name = data[0];
			if (data[1].contentEquals("null")) {
				spawnPositions.put(name, null);
			} else {
				int x = Integer.parseInt(data[1]);
				int y = Integer.parseInt(data[2]);
				int z = Integer.parseInt(data[3]);
				spawnPositions.put(name, new BlockPos(x, y, z));
			}
			
		}
		
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putBoolean("eyeDefeated", eyeDefeated);
		compound.putInt("shadowOrbsMined", shadowOrbsMined);
		compound.putBoolean("hardmode", hardmode);
		compound.putBoolean("skeletronDefeated", skeletronDefeated);
		compound.putBoolean("twinsDefeated", twinsDefeated);
		compound.putBoolean("destroyerDefeated", destroyerDefeated);
		compound.putBoolean("skeletronPrimeDefeated", skeletronPrimeDefeated);
		compound.putBoolean("planteraDefeated", planteraDefeated);
		compound.putBoolean("golemDefeated", golemDefeated);
		compound.putBoolean("cultistDefeated", cultistDefeated);
		compound.putBoolean("solarPillarDefeated", solarPillarDefeated);
		compound.putBoolean("vortexPillarDefeated", vortexPillarDefeated);
		compound.putBoolean("nebulaPillarDefeated", nebulaPillarDefeated);
		compound.putBoolean("stardustPillarDefeated", stardustPillarDefeated);
		compound.putInt("solarEnemiesDefeated", solarEnemiesDefeated);
		compound.putInt("vortexEnemiesDefeated", vortexEnemiesDefeated);
		compound.putInt("nebulaEnemiesDefeated", nebulaEnemiesDefeated);
		compound.putInt("stardustEnemiesDefeated", stardustEnemiesDefeated);
		compound.putInt("invasionEnemiesDefeated", invasionEnemiesDefeated);
		compound.putInt("sposLength", spawnPositions.size());
		int i = 0;
		for (String p : spawnPositions.keySet()) {
			BlockPos pos = spawnPositions.get(p);
			if (pos == null) {
				compound.putString("spawnpos["+i+"]", p+",null");
			} else {
				compound.putString("spawnpos["+i+"]", p+","+pos.getX()+","+pos.getY()+","+pos.getZ());
			}
			i++;
		}
		return compound;
	}
	
//	public long lastTick;
//	public void tick() {
//		World world = (World)this.world;
//		
//		this.markDirty();
//	}

}
