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

    public String key = "816000848fmshd35ce00429d6043p1296fdjsnea474485598e";
    Gson gson = new Gson();

    public JsonObject getArtist(String artist) {
        JsonObject artistInf = new JsonObject();
        try {
            HttpResponse<String> response = Unirest.get("https://shazam-core.p.rapidapi.com/v1/artists/search?query=" + artist)
                    .header("x-rapidapi-host", "shazam-core.p.rapidapi.com")
                    .header("x-rapidapi-key", key)
                    .asString();
            String infString = response.getBody();
            JsonArray result = new JsonParser().parse(infString).getAsJsonArray();
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).getAsJsonObject().get("name").getAsString().equalsIgnoreCase(artist)) {
                    artistInf = result.get(i).getAsJsonObject();
                } else {
                    artistInf.addProperty("key", "Xin lỗi thầy nhưng mà cái ông này không có sẵn trên API :(((( ");
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return artistInf;
    }

    //cút
    public String getArtistsInf(String name) {
        String artistInf = "Xin lỗi thầy nhưng mà cái ông này không có sẵn trên API :(((( ";
        try {
            Jwiki jwiki = new Jwiki(name);
            artistInf = jwiki.getDisplayTitle() + "\n \n" + jwiki.getExtractText();
        } catch (Exception e) {
            System.out.println(e);
        }

        return artistInf;
    }
    //

    public JsonArray getTracksOfArtist(String id) {
        HttpResponse<String> response = Unirest.get("https://shazam-core.p.rapidapi.com/v1/artists/tracks?artist_id=" + id + "&limit=10")
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
//        String query = "Sơn Tùng M-TP";
//        System.out.println(app.getArtistsInf(query));
//        System.out.println(app.getTracksOfArtist(app.getArtist(query)));
            JsonArray a = new JsonArray();
            if(a.size()==0){
                System.out.println("asdasd");
            }
    }

}
