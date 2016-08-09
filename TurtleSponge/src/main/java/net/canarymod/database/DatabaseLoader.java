// Copyright (c) 2012 - 2015, CanaryMod Team
// Under the management of PlayBlack and Visual Illusions Entertainment
// All rights reserved.
// 
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//     * Redistributions of source code must retain the above copyright
//       notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above copyright
//       notice, this list of conditions and the following disclaimer in the
//       documentation and/or other materials provided with the distribution.
//     * Neither the name of the CanaryMod Team nor the
//       names of its contributors may be used to endorse or promote products
//       derived from this software without specific prior written permission.
// 
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL CANARYMOD TEAM OR ITS CONTRIBUTORS BE LIABLE FOR ANY
// DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// 
// Any source code from the Minecraft Server is not owned by CanaryMod Team, PlayBlack,
// Visual Illusions Entertainment, or its contributors and is not covered by above license.
// Usage of source code from the Minecraft Server is subject to the Minecraft End User License Agreement as set forth by Mojang AB.
// The Minecraft EULA can be viewed at https://account.mojang.com/documents/minecraft_eula
// CanaryMod Team, PlayBlack, Visual Illusions Entertainment, CanaryLib, CanaryMod, and its contributors
// are NOT affiliated with, endorsed, or sponsored by Mojang AB, makers of Minecraft.
// "Minecraft" is a trademark of Notch Development AB
// "CanaryMod" name is used with permission from FallenMoonNetwork.

package net.canarymod.database;

import java.io.File;

import org.knoxcraft.serverturtle.TurtlePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.canarymod.database.exceptions.DatabaseException;


/**
 * Checks a database folder in CanaryMods root folder for
 * external Database Implementations and loads them
 *
 * @author Chris (damagefilter)
 */
public class DatabaseLoader {
    private static Logger log=LoggerFactory.getLogger(TurtlePlugin.ID);
    /**
     * Scans db adapters folder, loads all valid databases and registers them
     * at Database.Type. This must be the first bootstrapping step,
     * as all other steps require a functional database.
     * This also means this must not make use of anything that isn't loaded already
     */
    public static void load() {
        File dbFolder = new File("dbadapters/");
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }
        for (File file : dbFolder.listFiles()) {
            if (!file.getName().endsWith(".jar")) {
                continue;
            }
            try {
                // TODO: support multiple different DB adapters by loading each one from
                // a folder or some other well-known location
                // For now, let's just load JDBC
                
                //PropertiesFile inf = new PropertiesFile(file.getAbsolutePath(), "Canary.inf");
                //CanaryClassLoader ploader = new CanaryClassLoader(file.toURI().toURL(), DatabaseLoader.class.getClassLoader());
                //String mainclass = inf.getString("main-class");
                //String dbName = inf.getString("database-name");
                String mainclass="com.mysql.cj.jdbc.Driver";
                String dbName="mysql";
                Class<?> dbClass = TurtlePlugin.class.getClassLoader().loadClass(mainclass);

                Database db = (Database)dbClass.newInstance();
                if (db != null) {
                    Database.Type.registerDatabase(dbName, db);
                }
            }
            catch (ClassNotFoundException e) {
                log.error("Could not find databases mainclass", e);
            }
            catch (IllegalAccessException e) {
                log.error("Could not create database", e);
            }
            catch (DatabaseException e) {
                log.error("Could not add database", e);
            }
            catch (SecurityException e) {
                log.error(e.getMessage(), e);
            }
            catch (InstantiationException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
