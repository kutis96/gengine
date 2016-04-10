package gengine.util;

import gengine.rendering.WorldRenderer;
import gengine.rendering.WorldRendererOptions;
import gengine.world.World;

/**
 * A class containing World and the World-related stuff in a neat package. Meant
 * to avoid a bit of confusion which world is which and whatnot.
 *
 * @author Richard Kutina <kutinric@fel.cvut.cz>
 */
public class WorldPack {

    public WorldPack(World world, WorldRenderer wren, WorldRendererOptions wropt) {
        this(0, world, wren, wropt);
    }
    
    public WorldPack(int type, World world, WorldRenderer wren, WorldRendererOptions wropt) {
        this.type = type;
        this.world = world;
        this.wren = wren;
        this.wropt = wropt;
    }

    public final int type;
    public final World world;
    public final WorldRenderer wren;
    public final WorldRendererOptions wropt;
}
