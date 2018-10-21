/*
This file is part of the OdinMS Maple Story Server
Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
Matthias Butz <matze@odinms.de>
Jan Christian Meyer <vimes@odinms.de>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation version 3 as published by
the Free Software Foundation. You may not use, modify or distribute
this program under any other version of the GNU Affero General Public
License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package server.quest.requirements;

import client.MapleCharacter;
import client.MapleQuestStatus;
import java.util.HashMap;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataTool;
import server.quest.MapleQuest;
import server.quest.MapleQuestRequirementType;
import tools.FilePrinter;

/** @author Tyler (Twdtwd) */
public class MobRequirement extends MapleQuestRequirement {
    Map<Integer, Integer> mobs = new HashMap<>();
    private final int questID;

    public MobRequirement(MapleQuest quest, MapleData data) {
        super(MapleQuestRequirementType.MOB);
        processData(data);
        questID = quest.getId();
    }

    /** @param data */
    @Override
    public void processData(MapleData data) {
        for (MapleData questEntry : data.getChildren()) {
            int mobID = MapleDataTool.getInt(questEntry.getChildByPath("id"));
            int countReq = MapleDataTool.getInt(questEntry.getChildByPath("count"));
            mobs.put(mobID, countReq);
        }
    }

    @Override
    public boolean check(MapleCharacter chr, Integer npcid) {
        MapleQuestStatus status = chr.getQuest(MapleQuest.getInstance(questID));
        for (Map.Entry<Integer, Integer> integerIntegerEntry : mobs.entrySet()) {
            int countReq = integerIntegerEntry.getValue();
            int progress;

            try {
                progress = Integer.parseInt(status.getProgress(integerIntegerEntry.getKey()));
            } catch (NumberFormatException ex) {
                FilePrinter.printError(
                        FilePrinter.EXCEPTION_CAUGHT,
                        ex,
                        "Mob: "
                                + integerIntegerEntry.getKey()
                                + " Quest: "
                                + questID
                                + "CID: "
                                + chr.getId()
                                + " Progress: "
                                + status.getProgress(integerIntegerEntry.getKey()));
                return false;
            }

            if (progress < countReq) return false;
        }
        return true;
    }

    public int getRequiredMobCount(int mobid) {
        if (mobs.containsKey(mobid)) {
            return mobs.get(mobid);
        }
        return 0;
    }
}
