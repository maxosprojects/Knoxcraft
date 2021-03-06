package org.knoxcraft.serverturtle;

import java.util.HashMap;
import java.util.Map;

import org.knoxcraft.turtle3d.KCTScript;
import org.slf4j.Logger;

import com.google.inject.Inject;

public class ScriptManager {
	
	
    @Inject
    private Logger log;
    
    //PlayerName-> (scriptName -> script)
    private Map<String, Map<String, KCTScript>> map;  
    
    /**
     * Constructor.
     */
    public ScriptManager()  {
        map = new HashMap<String, Map<String, KCTScript>>();
    }
    
    /**
     * Return a map containing all scripts belonging to the given player.
     * 
     * @param playerName
     * @return map of player's scripts
     */
    public Map<String, KCTScript> getAllScriptsForPlayer(String playerName) {
        return map.get(playerName.toLowerCase());
    }
    
    /**
     * Return the map containing all players' scripts.
     * 
     * @return map of scripts
     */
    public Map<String, Map<String,KCTScript>> getAllScripts() {
        return map;
    }
   
    /**
     * Put a script into the map.
     * 
     * @param playerName
     * @param script
     */
    public void putScript(String playerName, KCTScript script)  {
        playerName=playerName.toLowerCase();
        if (!map.containsKey(playerName)) {
            //create a map for the player if one doesn't exist
            map.put(playerName, new HashMap<String, KCTScript>());
        }
        
        //put script into player's map
        Map<String,KCTScript> scriptMap=map.get(playerName);
        scriptMap.put(script.getScriptName(), script);
        
     
    }
    
    /**
     * Get a script from the map.
     * 
     * @param playerName
     * @param scriptName
     * @return the script
     */
    public KCTScript getScript(String playerName, String scriptName)  {
    	//log.info("map ==null" + (map == null));
    	if(map.get(playerName.toLowerCase()) == null){
    		//log.info("map == null" + (map == null));
    		return null;		
    	}
        return map.get(playerName.toLowerCase()).get(scriptName);
        //get map from map.getPlayername, if that map is null return null 
    }    
}