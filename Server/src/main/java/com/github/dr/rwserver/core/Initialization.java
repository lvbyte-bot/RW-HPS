package com.github.dr.rwserver.core;

import com.github.dr.rwserver.data.global.Data;
import com.github.dr.rwserver.util.file.FileUtil;
import com.github.dr.rwserver.util.log.Log;
import com.ip2location.IP2Location;

import java.io.IOException;

/**
 * @author Dr
 */
public class Initialization {

    public Initialization() {
    	initMaps();

		loadLang();

		Runtime.getRuntime().addShutdownHook(new ExitHandler());
    }

	private void initMaps() {
		Data.MapsMap.put("Beachlanding(2p)[byhxyy]","Beach landing (2p) [by hxyy]@[p2]");
		Data.MapsMap.put("BigIsland(2p)","Big Island (2p)@[p2]");
		Data.MapsMap.put("DireStraight(2p)[byuber]","Dire_Straight (2p) [by uber]@[p2]");
		Data.MapsMap.put("FireBridge(2p)[byuber]","Fire Bridge (2p) [by uber]@[p2]");
		Data.MapsMap.put("Hills(2p)[ByTstis&KPSS]","Hills_(2p)_[By Tstis & KPSS]@[p2]");
		Data.MapsMap.put("IceIsland(2p)","Ice Island (2p)@[p2]");
		Data.MapsMap.put("Lake(2p)","Lake (2p)@[p2]");
		Data.MapsMap.put("SmallIsland(2p)","Small_Island (2p)@[p2]");
		Data.MapsMap.put("Twocoldsides(2p)","Two_cold_sides (2p)@[p2]");
		Data.MapsMap.put("Hercules(2vs1p)[byuber]","Hercules_(2vs1p) [by_uber]@[p3]");
		Data.MapsMap.put("KingoftheMiddle(3p)","King of the Middle (3p)@[p3]");
		Data.MapsMap.put("Depthcharges(4p)[byhxyy]","Depth charges (4p) [by hxyy]@[p4]");
		Data.MapsMap.put("Desert(4p)","Desert (4p)@[p4]");
		Data.MapsMap.put("IceLake(4p)[byhxyy]","Ice Lake (4p) [by hxyy]@[p4]");
		Data.MapsMap.put("Islandfreeze(4p)[byhxyy]","Island freeze (4p) [by hxyy]@[p4]");
		Data.MapsMap.put("Islands(4p)","Islands (4p)@[p4]");
		Data.MapsMap.put("LavaMaze(4p)","Lava Maze (4p)@[p4]");
		Data.MapsMap.put("LavaVortex(4p)","Lava Vortex (4p)@[p4]");
		Data.MapsMap.put("MagmaIsland(4p)","Magma Island (4p)@[p4]");
		Data.MapsMap.put("Manipulation(4p)[ByTstis]","Manipulation_(4p)_[By Tstis]@[p4]");
		Data.MapsMap.put("Nuclearwar(4p)[byhxyy]","Nuclear war (4p) [by hxyy]@[p4]");
		Data.MapsMap.put("Crossing(6p)","Crossing (6p)@[p6]");
		Data.MapsMap.put("ShoretoShore(6p)","Shore to Shore (6p)@[p6]");
		Data.MapsMap.put("ValleyPass(6p)","Valley Pass (6p)@[p6]");
		Data.MapsMap.put("BridgesOverLava(8p)","Bridges Over Lava (8p)@[p8]");
		Data.MapsMap.put("Coastline(8p)[byhxyy]","Coastline (8p) [by hxyy]@[p8]");
		Data.MapsMap.put("HugeSubdivide(8p)","Huge Subdivide (8p)@[p8]");
		Data.MapsMap.put("Interlocked(8p)","Interlocked (8p)@[p8]");
		Data.MapsMap.put("InterlockedLarge(8p)","Interlocked Large (8p)@[p8]");
		Data.MapsMap.put("IsleRing(8p)","Isle Ring (8p)@[p8]");
		Data.MapsMap.put("LargeIceOutcrop(8p)","Large Ice Outcrop (8p)@[p8]");
		Data.MapsMap.put("LavaBiogrid(8p)","Lava Bio-grid(8p)@[p8]");
		Data.MapsMap.put("LavaDivide(8p)","Lava Divide(8p)@[p8]");
		Data.MapsMap.put("ManyIslands(8p)","Many Islands (8p)@[p8]");
		Data.MapsMap.put("RandomIslands(8p)","Random Islands (8p)@[p8]");
		Data.MapsMap.put("Tornadoeye(8p)[byhxyy]","Tornado eye (8p) [by hxyy]@[p8]");
		Data.MapsMap.put("TwoSides(8p)","Two Sides (8p)@[p8]");
		Data.MapsMap.put("Volcano(8p)","Volcano (8p)@[p8]");
		Data.MapsMap.put("VolcanoCrater(8p)","Volcano Crater(8p)@[p8]");
		Data.MapsMap.put("TwoSidesRemake(10p)","Two Sides Remake (10p)@[z;p10]");
		Data.MapsMap.put("ValleyArena(10p)[byuber]","Valley Arena (10p) [by_uber]@[z;p10]");
		Data.MapsMap.put("ManyIslandsLarge(10p)","Many Islands Large (10p)@[z;p10]");
		Data.MapsMap.put("CrossingLarge(10p)","Crossing Large (10p)@[z;p10]");
		Data.MapsMap.put("Kingdoms(10p)[byVulkan]","Kingdoms (10p) [by Vulkan]@[z;p10]");
		Data.MapsMap.put("LargeLavaDivide(10p)","Large Lava Divide (10p)@[z;p10]");
		Data.MapsMap.put("EnclosedIsland(10p)","Enclosed Island (10p)@[z;p10]");
		Data.MapsMap.put("TwoLargeIslands(10p)","Two_Large_Islands_(10p)@[z;p10]");
		Data.MapsMap.put("Wetlands(10p)","Wetlands (10p)@[z;p10]");
	}

	private void loadIpBin() {
		if (!Data.config.readBoolean("ipCheckMultiLanguageSupport",false)) {
			return;
		}
		try {
			Data.ip2Location = new IP2Location();
			Data.ip2Location.Open(FileUtil.File(Data.Plugin_Data_Path).toPath("IP.bin").getPath(), true);
		} catch (IOException e) {
			Log.error("IP-LOAD ERR",e);
		}
	}

	private void loadLang() {
		Data.localeUtilMap.put("CN",Data.localeUtil);
	}

	static class ExitHandler extends Thread {
		public ExitHandler() {
			super("Exit Handler");
		}
		@Override
		public void run() {
			System.out.println("Exit Save Ok");
			Data.core.save();
		}
	}
}
