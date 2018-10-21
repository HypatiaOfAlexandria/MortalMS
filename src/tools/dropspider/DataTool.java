/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.dropspider;

import java.io.File;
import java.util.*;
import provider.*;
import server.MapleItemInformationProvider;
import tools.Pair;

/** @author Simon */
public class DataTool {
    private static final Map<String, Integer> hardcodedMobs = new HashMap<>();

    private static ArrayList<Pair<Integer, String>> npc_list = null;
    private static ArrayList<Pair<Integer, String>> mob_pairs = null;
    private static final MapleDataProvider data =
            MapleDataProviderFactory.getDataProvider(
                    MapleDataProviderFactory.fileInWZPath("Mob.wz"));
    private static HashSet<Integer> bosses = null;

    public static void setHardcodedMobNames() {
        hardcodedMobs.put("Red Slime [2]", 712_0103);
        hardcodedMobs.put("Gold Slime", 712_0105);
        hardcodedMobs.put("Nibelung [3]", 822_0015);
    }

    public static void addMonsterIdsFromHardcodedName(
            List<Integer> monster_ids, String monster_name) {
        Integer id = hardcodedMobs.get(monster_name);
        if (id != null) {
            monster_ids.add(id);
        }
    }

    public static ArrayList<Integer> monsterIdsFromName(String name) {
        var dataProvider =
                MapleDataProviderFactory.getDataProvider(
                        new File(System.getProperty("wzpath") + "/String.wz"));
        var ret = new ArrayList<Integer>();
        var data = dataProvider.getData("Mob.img");
        if (mob_pairs == null) {
            mob_pairs = new ArrayList<>();
            for (var mobIdData : data.getChildren()) {
                int mobIdFromData = Integer.parseInt(mobIdData.getName());
                String mobNameFromData =
                        MapleDataTool.getString(mobIdData.getChildByPath("name"), "NO-NAME");
                mob_pairs.add(new Pair<>(mobIdFromData, mobNameFromData));
            }
        }
        for (var mobPair : mob_pairs) {
            if (mobPair.getRight().toLowerCase().equals(name.toLowerCase())) {
                ret.add(mobPair.getLeft());
            }
        }
        return ret;
    }

    private static void populateBossList() {
        bosses = new HashSet<>();
        MapleDataDirectoryEntry mob_data = data.getRoot();
        for (MapleDataFileEntry mdfe : mob_data.getFiles()) {
            MapleData boss_candidate = data.getData(mdfe.getName());
            MapleData monsterInfoData = boss_candidate.getChildByPath("info");
            int mid = Integer.valueOf(boss_candidate.getName().replaceAll("[^0-9]", ""));
            boolean boss =
                    MapleDataTool.getIntConvert("boss", monsterInfoData, 0) > 0
                            || mid == 8810018
                            || mid == 9410066;
            if (boss) {
                bosses.add(mid);
            }
        }
    }

    public static boolean isBoss(int mid) {
        if (bosses == null) {
            populateBossList();
        }
        return bosses.contains(mid);
    }

    public static ArrayList<Integer> itemIdsFromName(String name) {
        var ret = new ArrayList<Integer>();
        for (var itemPair : MapleItemInformationProvider.getInstance().getAllItems()) {
            String item_name = itemPair.getRight().toLowerCase().replaceAll("&quot;", "");
            item_name = item_name.replaceAll("'", "");
            item_name = item_name.replaceAll("'", "");

            name = name.toLowerCase().replaceAll("&quot;", "");
            name = name.replaceAll("'", "");
            name = name.replaceAll("'", "");

            if (item_name.equals(name)) {
                ret.add(itemPair.getLeft());
                return ret;
            }
        }
        return ret;
    }

    public static ArrayList<Integer> npcIdsFromName(String name) {
        MapleDataProvider dataProvider =
                MapleDataProviderFactory.getDataProvider(
                        new File(System.getProperty("wzpath") + "/String.wz"));
        var ret = new ArrayList<Integer>();
        if (npc_list == null) {
            var searchList = new ArrayList<Pair<Integer, String>>();
            for (var searchData : dataProvider.getData("Npc.img").getChildren()) {
                int searchFromData = Integer.parseInt(searchData.getName());
                String infoFromData =
                        MapleDataTool.getString(searchData.getChildByPath("name"), "NO-NAME");
                searchList.add(new Pair<>(searchFromData, infoFromData));
            }
            npc_list = searchList;
        }
        for (var searched : npc_list) {
            if (searched.getRight().toLowerCase().contains(name.toLowerCase())) {
                ret.add(searched.getLeft());
            }
        }
        return ret;
    }
}
