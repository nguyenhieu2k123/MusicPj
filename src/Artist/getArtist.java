package Artist;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import GUI.*;
import Jwiki.Jwiki;
import java.util.ArrayList;

public class getArtist {
    public String key = "23bada79a5msh6058f55ecefadb6p1e2c08jsn1920e07802eb";
    Gson gson = new Gson();

    public JsonObject getArtist(String artist) {
        HttpResponse<String> response = Unirest.get("https://shazam-core.p.rapidapi.com/v1/artists/search?query=" + artist)
                .header("x-rapidapi-host", "shazam-core.p.rapidapi.com")
                .header("x-rapidapi-key", key)
                .asString();
        String infString = response.getBody();
        JsonArray result = new JsonParser().parse(infString).getAsJsonArray();
        JsonObject artistInf = new JsonObject();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getAsJsonObject().get("name").getAsString().equalsIgnoreCase(artist)) {
                artistInf = result.get(i).getAsJsonObject();
            }
        }
        return artistInf;
    }
    
    //cút
    public String getArtistsInf(String name) {
        Jwiki jwiki = new Jwiki(name);
        String arttistInf = jwiki.getDisplayTitle() + "\n" + jwiki.getExtractText() + "\n" + jwiki.getImageURL();
        return arttistInf;
    }
    //
    
    public JsonArray getTracksOfArtist(String id){
        HttpResponse<String> response = Unirest.get("https://shazam-core.p.rapidapi.com/v1/artists/tracks?artist_id="+id+"&limit=7")
	.header("x-rapidapi-host", "shazam-core.p.rapidapi.com")
	.header("x-rapidapi-key", key)
	.asString();
        String infString = response.getBody();
        JsonArray result = new JsonParser().parse(infString).getAsJsonArray();
        return result;
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        getArtist app = new getArtist();
        String query = "Hồ ngọc hà";
        System.out.println(app.getArtist(query));
        System.out.println(app.getTracksOfArtist(app.getArtist(query).getAsJsonObject().get("id").getAsString()));
//        System.out.println(app.getArtistsInf(app.getArtist(query).getAsJsonObject().get("name").getAsString()));
        

    }

}
