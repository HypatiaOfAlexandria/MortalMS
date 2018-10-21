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
package scripting.item;

import client.MapleClient;
import java.io.*;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.script.*;
import tools.FilePrinter;
import tools.MaplePacketCreator;

public class ItemScriptManager {

    private static final ItemScriptManager instance = new ItemScriptManager();

    public static ItemScriptManager getInstance() {
        return instance;
    }

    private final Map<String, Invocable> scripts = new HashMap<>();
    private final ScriptEngineFactory sef;

    private ItemScriptManager() {
        ScriptEngineManager sem = new ScriptEngineManager();
        sef = sem.getEngineByName("javascript").getFactory();
    }

    public static boolean scriptExists(String scriptName) {
        File scriptFile = new File("scripts/item/" + scriptName + ".js");
        return scriptFile.exists();
    }

    public void getItemScript(MapleClient c, String scriptName) {
        if (scripts.containsKey(scriptName)) {
            try {
                scripts.get(scriptName).invokeFunction("start", new ItemScriptMethods(c));
            } catch (ScriptException | NoSuchMethodException ex) {
                FilePrinter.printError(FilePrinter.ITEM + scriptName + ".txt", ex);
            }
            return;
        }
        FileInputStream scriptFileStream;
        try {
            scriptFileStream = new FileInputStream("scripts/item/" + scriptName + ".js");
        } catch (final FileNotFoundException fnfe) {
            c.announce(MaplePacketCreator.enableActions());
            return;
        }
        InputStreamReader fr = null;
        ScriptEngine portal = sef.getScriptEngine();
        try {
            fr = new InputStreamReader(scriptFileStream, StandardCharsets.UTF_8);

            // java 8 support here thanks to Arufonsu
            portal.eval("load('nashorn:mozilla_compat.js');" + System.lineSeparator());

            ((Compilable) portal).compile(fr).eval();

            final var script = (Invocable) portal;
            scripts.put(scriptName, script);
            script.invokeFunction("start", new ItemScriptMethods(c));
        } catch (final UndeclaredThrowableException | ScriptException ute) {
            FilePrinter.printError(FilePrinter.ITEM + scriptName + ".txt", ute);
        } catch (final Exception e) {
            FilePrinter.printError(FilePrinter.ITEM + scriptName + ".txt", e);
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}
