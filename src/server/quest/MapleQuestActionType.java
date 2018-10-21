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
package server.quest;

/** @author Matze */
public enum MapleQuestActionType {
    UNDEFINED(-1),
    EXP(0),
    ITEM(1),
    NEXTQUEST(2),
    MESO(3),
    QUEST(4),
    SKILL(5),
    FAME(6),
    BUFF(7),
    PETSKILL(8),
    YES(9),
    NO(10),
    NPC(11),
    MIN_LEVEL(12),
    NORMAL_AUTO_START(13),
    PETTAMENESS(14),
    ZERO(15);
    final byte type;

    private MapleQuestActionType(int type) {
        this.type = (byte) type;
    }

    public static MapleQuestActionType getByWZName(String name) {
        if (name.equals("exp")) {
            return EXP;
        }
        if (name.equals("money")) {
            return MESO;
        }
        if (name.equals("item")) {
            return ITEM;
        }
        if (name.equals("skill")) {
            return SKILL;
        }
        if (name.equals("nextQuest")) {
            return NEXTQUEST;
        }
        if (name.equals("pop")) {
            return FAME;
        }
        if (name.equals("buffItemID")) {
            return BUFF;
        }
        if (name.equals("petskill")) {
            return PETSKILL;
        }
        if (name.equals("no")) {
            return NO;
        }
        if (name.equals("yes")) {
            return YES;
        }
        if (name.equals("npc")) {
            return NPC;
        }
        if (name.equals("lvmin")) {
            return MIN_LEVEL;
        }
        if (name.equals("normalAutoStart")) {
            return NORMAL_AUTO_START;
        }
        if (name.equals("pettameness")) {
            return PETTAMENESS;
        }
        if (name.equals("0")) {
            return ZERO;
        }
        return UNDEFINED;
    }
}
