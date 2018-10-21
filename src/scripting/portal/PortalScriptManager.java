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
package scripting.portal;

import client.MapleClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.script.*;
import server.MaplePortal;
import tools.FilePrinter;

public class PortalScriptManager {

    private static final PortalScriptManager instance = new PortalScriptManager();

    public static PortalScriptManager getInstance() {
        return instance;
    }

    private final Map<String, PortalScript> scripts = new HashMap<>();
    private final ScriptEngineFactory sef;

    private PortalScriptManager() {
        ScriptEngineManager sem = new ScriptEngineManager();
        sef = sem.getEngineByName("nashorn").getFactory();
    }

    private PortalScript getPortalScript(String scriptName) {
        if (scripts.containsKey(scriptName)) {
            return scripts.get(scriptName);
        }
        FileInputStream scriptFileStream;
        try {
            scriptFileStream = new FileInputStream("scripts/portal/" + scriptName + ".js");
        } catch (final FileNotFoundException fnfe) {
            scripts.put(scriptName, null);
            return null;
        }
        InputStreamReader fr = null;
        ScriptEngine portal = sef.getScriptEngine();
        try {
            fr = new InputStreamReader(scriptFileStream, StandardCharsets.UTF_8);

            // java 8 support here thanks to Arufonsu
            portal.eval("load('nashorn:mozilla_compat.js');" + System.lineSeparator());

            ((Compilable) portal).compile(fr).eval();
        } catch (ScriptException | UndeclaredThrowableException e) {
            FilePrinter.printError(FilePrinter.PORTAL + scriptName + ".txt", e);
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        PortalScript script = ((Invocable) portal).getInterface(PortalScript.class);
        scripts.put(scriptName, script);
        return script;
    }

    public boolean executePortalScript(MaplePortal portal, MapleClient c) {
        try {
            PortalScript script = getPortalScript(portal.getScriptName());
            if (script != null) {
                return script.enter(new PortalPlayerInteraction(c, portal));
            }
        } catch (UndeclaredThrowableException ute) {
            FilePrinter.printError(FilePrinter.PORTAL + portal.getScriptName() + ".txt", ute);
        } catch (final Exception e) {
            FilePrinter.printError(FilePrinter.PORTAL + portal.getScriptName() + ".txt", e);
        }
        return false;
    }

    public void reloadPortalScripts() {
        scripts.clear();
    }
}
