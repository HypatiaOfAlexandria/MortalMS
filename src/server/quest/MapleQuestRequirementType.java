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
public enum MapleQuestRequirementType {
    UNDEFINED(-1),
    JOB(0),
    ITEM(1),
    QUEST(2),
    MIN_LEVEL(3),
    MAX_LEVEL(4),
    END_DATE(5),
    MOB(6),
    NPC(7),
    FIELD_ENTER(8),
    INTERVAL(9),
    SCRIPT(10),
    PET(11),
    MIN_PET_TAMENESS(12),
    MONSTER_BOOK(13),
    NORMAL_AUTO_START(14),
    INFO_NUMBER(15),
    INFO_EX(16),
    COMPLETED_QUEST(17),
    START(18),
    END(19),
    DAY_BY_DAY(20),
    MESO(21),
    BUFF(22),
    EXCEPT_BUFF(23);
    final byte type;

    private MapleQuestRequirementType(int type) {
        this.type = (byte) type;
    }

    public byte getType() {
        return type;
    }

    public static MapleQuestRequirementType getByWZName(String name) {
        if (name.equals("job")) {
            return JOB;
        }
        if (name.equals("quest")) {
            return QUEST;
        }
        if (name.equals("item")) {
            return ITEM;
        }
        if (name.equals("lvmin")) {
            return MIN_LEVEL;
        }
        if (name.equals("lvmax")) {
            return MAX_LEVEL;
        }
        if (name.equals("end")) {
            return END_DATE;
        }
        if (name.equals("mob")) {
            return MOB;
        }
        if (name.equals("npc")) {
            return NPC;
        }
        if (name.equals("fieldEnter")) {
            return FIELD_ENTER;
        }
        if (name.equals("interval")) {
            return INTERVAL;
        }
        if (name.equals("startscript")) {
            return SCRIPT;
        }
        if (name.equals("endscript")) {
            return SCRIPT;
        }
        if (name.equals("pet")) {
            return PET;
        }
        if (name.equals("pettamenessmin")) {
            return MIN_PET_TAMENESS;
        }
        if (name.equals("mbmin")) {
            return MONSTER_BOOK;
        }
        if (name.equals("normalAutoStart")) {
            return NORMAL_AUTO_START;
        }
        if (name.equals("infoNumber")) {
            return INFO_NUMBER;
        }
        if (name.equals("infoex")) {
            return INFO_EX;
        }
        if (name.equals("questComplete")) {
            return COMPLETED_QUEST;
        }
        if (name.equals("start")) {
            return START;
            /*} else if(name.equals("end")) {   already coded
            return END;*/
        }
        if (name.equals("daybyday")) {
            return DAY_BY_DAY;
        }
        if (name.equals("money")) {
            return MESO;
        }
        if (name.equals("buff")) {
            return BUFF;
        }
        if (name.equals("exceptbuff")) {
            return EXCEPT_BUFF;
        }
        return UNDEFINED;
    }
}
