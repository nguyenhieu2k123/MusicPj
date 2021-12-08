package test_DUC;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class runApp {

    Gson gson = new Gson();

    public JsonArray getSongDataFromAPI (String query){
        //Get songs data from api
        HttpResponse<String> response = Unirest.get("https://shazam-core.p.rapidapi.com/v1/tracks/search?query=" + query)
                .header("x-rapidapi-host", "shazam-core.p.rapidapi.com")
                .header("x-rapidapi-key", "533d42cd24msheb307f07f2b6174p1c9e7fjsn952ba01d8c63")
                .asString();
        String infString = response.getBody();
        JsonArray result = new JsonParser().parse(infString).getAsJsonArray();
        JsonArray listSong = new JsonArray();
        for(int i = 0; i < result.size(); i++){
            JsonObject findSong = result.get(i).getAsJsonObject();
            JsonObject heading = findSong.get("heading").getAsJsonObject();
            if(query.equalsIgnoreCase(heading.get("title").getAsString())) {
                listSong.add(findSong);
            }
        }
        return listSong;
    }

    public JsonArray getLyricsDataFromAPI(String id) {
        HttpResponse<String> response = Unirest.get("https://shazam-core.p.rapidapi.com/v1/tracks/details?track_id=" + id)
                .header("x-rapidapi-host", "shazam-core.p.rapidapi.com")
                .header("x-rapidapi-key", "533d42cd24msheb307f07f2b6174p1c9e7fjsn952ba01d8c63")
                .asString();
        String inf = response.getBody();
        JsonObject lyricsData = new JsonParser().parse(inf).getAsJsonObject();
        JsonObject lyrics =  lyricsData.get("sections").getAsJsonArray().get(1).getAsJsonObject();
        return lyrics.get("text").getAsJsonArray();
    }

    public static void main (String[] args) throws Exception {
        // TODO Auto-generated method stub
        runApp app = new runApp();
        String query = "Nơi này có anh";
        String id = "341759650";
        JsonArray resultList = app.getSongDataFromAPI(query);
        for(int i = 0; i < resultList.size(); i++){
            System.out.println(resultList.get(i));
        }
        JsonArray data = app.getLyricsDataFromAPI(id);
        for(int i = 0; i < data.size(); i++){
            System.out.println(data.get(i).getAsString());
        }
    }
}
