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
package client.command.commands.gm1;

import client.MapleCharacter;
import client.MapleClient;
import client.command.Command;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import server.MapleItemInformationProvider;
import server.life.MapleMonsterInformationProvider;
import tools.DatabaseConnection;
import tools.MaplePacketCreator;
import tools.Pair;

public class WhoDropsCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        if (params.length < 1) {
            player.dropMessage(5, "Please do @whodrops <item name>");
            return;
        }
        String searchString = joinStringFrom(params, 0);
        StringBuilder output = new StringBuilder();
        Iterator<Pair<Integer, String>> listIterator =
                MapleItemInformationProvider.getItemDataByName(searchString).iterator();
        if (listIterator.hasNext()) {
            int count = 1;
            while (listIterator.hasNext() && count <= 3) {
                Pair<Integer, String> data = listIterator.next();
                output.append("#b").append(data.getRight()).append("#k is dropped by:\n");
                try {
                    Connection con = DatabaseConnection.getConnection();
                    PreparedStatement ps =
                            con.prepareStatement(
                                    "SELECT dropperid FROM drop_data WHERE itemid = ? LIMIT 50");
                    ps.setInt(1, data.getLeft());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String resultName =
                                MapleMonsterInformationProvider.getMobNameFromID(
                                        rs.getInt("dropperid"));
                        if (resultName != null) {
                            output.append(resultName).append(", ");
                        }
                    }
                    rs.close();
                    ps.close();
                    con.close();
                } catch (Exception e) {
                    player.dropMessage(
                            6,
                            "There was a problem retrieving the required data. Please try again.");
                    e.printStackTrace();
                    return;
                }
                output.append("\n\n");
                count++;
            }
        } else {
            player.dropMessage(5, "The item you searched for doesn't exist.");
            return;
        }
        c.announce(
                MaplePacketCreator.getNPCTalk(
                        9010000, (byte) 0, output.toString(), "00 00", (byte) 0));
    }
}
