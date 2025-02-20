package mods.thecomputerizer.dimensionhoppertweaks;

import mods.thecomputerizer.dimensionhoppertweaks.client.ClientHandler;
import mods.thecomputerizer.dimensionhoppertweaks.common.commands.RandomTP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = DimensionHopperTweaks.MODID, name = DimensionHopperTweaks.NAME, version = DimensionHopperTweaks.VERSION, dependencies = DimensionHopperTweaks.DEPENDENCIES)
public class DimensionHopperTweaks
{
    public static final String MODID = "dimensionhoppertweaks";
    public static final String NAME = "Dimension Hopper Tweaks";
    public static final String VERSION = "1.3.0";
    public static final String DEPENDENCIES = "required-after:forge@[14.23.5.2847,);required-after:fermion;required-after:avaritia;required-after:sgcraft;";

    @Mod.Instance(MODID)
    public static DimensionHopperTweaks INSTANCE;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            ClientHandler.registerRenderers();
        }
    }

    @Mod.EventHandler
    public void start(FMLServerStartingEvent event){
        event.registerServerCommand(new RandomTP());
    }
}
