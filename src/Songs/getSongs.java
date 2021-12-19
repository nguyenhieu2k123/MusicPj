package Songs;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;

public class getSongs {

    public String key = "816000848fmshd35ce00429d6043p1296fdjsnea474485598e";
    Gson gson = new Gson();

    //Get songs data from api
    public JsonArray getSongDataFromAPI(String query) {
        HttpResponse<String> response = Unirest.get("https://shazam-core.p.rapidapi.com/v1/tracks/search?query=" + query)
                .header("x-rapidapi-host", "shazam-core.p.rapidapi.com")
                .header("x-rapidapi-key", key)
                .asString();
        String infString = response.getBody();
        JsonArray result = new JsonParser().parse(infString).getAsJsonArray();
        return result;
    }

    public JsonObject getLyricsDataFromAPI(String id) {
        HttpResponse<String> response = Unirest.get("https://shazam-core.p.rapidapi.com/v1/tracks/details?track_id=" + id)
                .header("x-rapidapi-host", "shazam-core.p.rapidapi.com")
                .header("x-rapidapi-key", key)
                .asString();
        String inf = response.getBody();
        JsonObject lyricsData = new JsonParser().parse(inf).getAsJsonObject();
        return lyricsData;
    }


    public static String getUrl(String id) {
        HttpResponse<String> response = Unirest.get("https://shazam-core.p.rapidapi.com/v1/tracks/details?track_id=" + id)
                .header("x-rapidapi-host", "shazam-core.p.rapidapi.com")
                .header("x-rapidapi-key", "07d1713438mshdbecaa42a8624d7p14f904jsn5a18473bb78e")
                .asString();

        String container = response.getBody();
        JsonObject urlTmp = new JsonParser().parse(container).getAsJsonObject();
        JsonArray jsonUrlContainer = urlTmp.get("sections").getAsJsonArray();//.get(2).getAsJsonObject().get("youtubeurl").getAsString();
        
        String jsonUrl = "";
        for (int i=0; i<jsonUrlContainer.size(); i++){
            try {
                jsonUrl = jsonUrlContainer.get(i).getAsJsonObject().get("youtubeurl").getAsString();
                break;
            } catch (Exception e) {
                System.out.println("error" + i);
            }
        }
        String url = "null";
        String json;
        try {
            json = Jsoup.connect(jsonUrl).ignoreContentType(true).execute().body();
            JsonObject utubeUrl = new JsonParser().parse(json).getAsJsonObject();
            url = utubeUrl.get("actions").getAsJsonArray().get(0).getAsJsonObject().get("uri").getAsString();
        } catch (IOException ex) {
            return url;
        }

        return url;
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        //getSongs app = new getSongs();
        //String query = "Nơi này có anh";
        String id = "105845771";
        System.out.println(getUrl(id));
        //System.out.println(app.getLyricsDataFromAPI(id).getAsJsonObject().get("id").getAsString());

    }

}
