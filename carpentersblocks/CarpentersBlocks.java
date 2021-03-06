package carpentersblocks;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.classloading.FMLForgePlugin;
import net.minecraftforge.common.config.Configuration;

import org.apache.commons.io.FilenameUtils;

import carpentersblocks.proxy.CommonProxy;
import carpentersblocks.util.CarpentersBlocksTab;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(
        modid = CarpentersBlocks.MODID,
        name = "Carpenter's Blocks",
        version = CarpentersBlocks.VERSION,
        dependencies = "required-after:Forge@[10.12.1.1060,)"
        )
public class CarpentersBlocks {

    public static final String MODID = "CarpentersBlocks";
    public static final String VERSION = "3.2.4";
    public static FMLEventChannel channel;
    public static CreativeTabs creativeTab = new CarpentersBlocksTab(MODID);
    public static String modDir = "";

    @Instance(MODID)
    public static CarpentersBlocks instance;

    @SidedProxy(clientSide = "carpentersblocks.proxy.ClientProxy", serverSide = "carpentersblocks.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(MODID);

        String srcPath = event.getSourceFile().getAbsolutePath().replace('\\', '/');
        modDir = FMLForgePlugin.RUNTIME_DEOBF ? FilenameUtils.getFullPathNoEndSeparator(event.getSourceFile().getAbsolutePath()) : new File("").getAbsolutePath() + "\\mods";

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        proxy.preInit(event, config);

        if (config.hasChanged()) {
            config.save();
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

}
