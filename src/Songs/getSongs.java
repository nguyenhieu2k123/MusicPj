package Songs;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class getSongs {
    public String key = "23bada79a5msh6058f55ecefadb6p1e2c08jsn1920e07802eb";
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


    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        getSongs app = new getSongs();
        String query = "Nơi này có anh";
        String id = "341759650";
        
        System.out.println(app.getLyricsDataFromAPI(id).getAsJsonObject().get("id").getAsString());

    }

}
