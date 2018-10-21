/*
    This file is part of the HeavenMS MapleStory Server, commands OdinMS-based
    Copyleft (L) 2016 - 2018 RonanLana

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

/*
   @Author: Arthur L - Refactored command content into modules
*/
package client.command.commands.gm0;

import client.MapleCharacter;
import client.MapleClient;
import client.command.Command;
import constants.ServerConstants;

public class ShowRatesCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        String showMsg = "#eEXP RATE#n\n";
        showMsg += "Server EXP Rate: #k" + c.getWorldServer().getExpRate() + "x#k\n";
        showMsg += "Player EXP Rate: #k" + player.getRawExpRate() + "x#k\n";
        if (player.getCouponExpRate() != 1) {
            showMsg += "Coupon EXP Rate: #k" + player.getCouponExpRate() + "x#k\n";
        }
        showMsg += "EXP Rate: #e#b" + player.getExpRate() + "x#k#n\n";

        showMsg += "\n#eMESO RATE#n\n";
        showMsg += "Server MESO Rate: #k" + c.getWorldServer().getMesoRate() + "x#k\n";
        showMsg += "Player MESO Rate: #k" + player.getRawMesoRate() + "x#k\n";
        if (player.getCouponMesoRate() != 1) {
            showMsg += "Coupon MESO Rate: #k" + player.getCouponMesoRate() + "x#k\n";
        }
        showMsg += "MESO Rate: #e#b" + player.getMesoRate() + "x#k#n\n";

        showMsg += "\n#eDROP RATE#n\n";
        showMsg += "Server DROP Rate: #k" + c.getWorldServer().getDropRate() + "x#k\n";
        showMsg += "Player DROP Rate: #k" + player.getRawDropRate() + "x#k\n";
        if (player.getCouponDropRate() != 1) {
            showMsg += "Coupon DROP Rate: #k" + player.getCouponDropRate() + "x#k\n";
        }
        showMsg += "DROP Rate: #e#b" + player.getDropRate() + "x#k#n\n";

        if (ServerConstants.USE_QUEST_RATE) {
            showMsg += "\n#eQUEST RATE#n\n";
            showMsg += "Server QUEST Rate: #e#b" + c.getWorldServer().getQuestRate() + "x#k#n\n";
        }

        showMsg += "\n#eTRAVEL RATE#n\n";
        showMsg += "Server TRAVEL Rate: #e#b" + c.getWorldServer().getTravelRate() + "x#k#n\n";

        player.showHint(showMsg, 300);
    }
}
