package tools;

import client.MapleCharacter;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.server.Server;
import server.MapleItemInformationProvider;
import server.MapleTrade;
import server.expeditions.MapleExpedition;

public class LogHelper {

    public static void logTrade(MapleTrade trade1, MapleTrade trade2) {
        String name1 = trade1.getChr().getName();
        String name2 = trade2.getChr().getName();
        StringBuilder log =
                new StringBuilder(
                        "TRADE BETWEEN " + name1 + " AND " + name2 + System.lineSeparator());
        // Trade 1 to trade 2
        log.append(trade1.getExchangeMesos())
                .append(" mesos from ")
                .append(name1)
                .append(" to ")
                .append(name2)
                .append(' ')
                .append(System.lineSeparator());
        for (final var item : trade1.viewItems()) {
            String itemName =
                    MapleItemInformationProvider.getInstance().getName(item.getItemId())
                            + '('
                            + item.getItemId()
                            + ')';
            log.append(item.getQuantity())
                    .append(' ')
                    .append(itemName)
                    .append(" from ")
                    .append(name1)
                    .append(" to ")
                    .append(name2)
                    .append(' ')
                    .append(System.lineSeparator());
        }
        // Trade 2 to trade 1
        log.append(trade2.getExchangeMesos())
                .append(" mesos from ")
                .append(name2)
                .append(" to ")
                .append(name1)
                .append(' ')
                .append(System.lineSeparator());
        for (final var item : trade2.viewItems()) {
            String itemName =
                    MapleItemInformationProvider.getInstance().getName(item.getItemId())
                            + '('
                            + item.getItemId()
                            + ')';
            log.append(item.getQuantity())
                    .append(' ')
                    .append(itemName)
                    .append(" from ")
                    .append(name2)
                    .append(" to ")
                    .append(name1)
                    .append(' ')
                    .append(System.lineSeparator());
        }
        log.append(System.lineSeparator()).append(System.lineSeparator());
        FilePrinter.print("trades.txt", log.toString());
    }

    public static void logExpedition(MapleExpedition expedition) {
        Server.getInstance()
                .broadcastGMMessage(
                        expedition.getLeader().getWorld(),
                        MaplePacketCreator.serverNotice(
                                6,
                                expedition.getType()
                                        + " Expedition with leader "
                                        + expedition.getLeader().getName()
                                        + " finished after "
                                        + getTimeString(expedition.getStartTime())));

        StringBuilder log =
                new StringBuilder(expedition.getType() + " EXPEDITION" + System.lineSeparator());
        log.append(getTimeString(expedition.getStartTime())).append(System.lineSeparator());

        for (MapleCharacter member : expedition.getMembers()) {
            log.append(">>").append(member.getName()).append(System.lineSeparator());
        }
        log.append("BOSS KILLS").append(System.lineSeparator());
        for (String message : expedition.getBossLogs()) {
            log.append(message);
        }
        log.append(System.lineSeparator()).append(System.lineSeparator());
        FilePrinter.print("expeditions.txt", log.toString());
    }

    public static String getTimeString(long then) {
        long duration = System.currentTimeMillis() - then;
        int seconds = (int) (duration / 1000) % 60;
        int minutes = (int) ((duration / (1000 * 60)) % 60);
        return minutes + " Minutes and " + seconds + " Seconds";
    }

    public static void logLeaf(MapleCharacter player, boolean gotPrize, String operation) {
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());
        String log =
                player.getName()
                        + (gotPrize
                                ? " used a maple leaf to buy " + operation
                                : " redeemed " + operation + " VP for a leaf")
                        + " - "
                        + timeStamp
                        + System.lineSeparator();
        FilePrinter.print("mapleleaves.txt", log);
    }

    public static void logGacha(MapleCharacter player, int itemid, String map) {
        String itemName = MapleItemInformationProvider.getInstance().getName(itemid);
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());
        String log =
                player.getName()
                        + " got a "
                        + itemName
                        + '('
                        + itemid
                        + ") from the "
                        + map
                        + " gachapon. - "
                        + timeStamp
                        + System.lineSeparator();
        FilePrinter.print("gachapon.txt", log);
    }
}
