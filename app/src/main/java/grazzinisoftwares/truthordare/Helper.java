package grazzinisoftwares.truthordare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Helper {

    public static String PlayerMapToString(Map<String, Gender> map){
        String rs = "";
        for(String s : map.keySet()){
            rs += s + "-" + map.get(s).toString() + ";";
        }
        return rs;
    }

    public static ArrayList<Player> StringToPlayerList(String rs){
        String[] split = rs.split(";");
        ArrayList<Player> players = new ArrayList<Player>();
        for(String s : split){
            String[] playerInfo = s.split("-");
            if(playerInfo.length != 2)
                continue;
            players.add(new Player(playerInfo[0], Gender.valueOf(playerInfo[1])));
        }
        return players;
    }
}
