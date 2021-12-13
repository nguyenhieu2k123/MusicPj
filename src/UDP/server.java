package UDP;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import org.jsoup.Jsoup;

public class server {

    public static int buffsize = 1000;
    public static int port = 1234;
    
    //==========================================================================
    public static String key = "abc2b021-ffb9-453b-8b2d-c83812bbf61d";
    //==========================================================================
    
    public static void main(String[] args) {
        DatagramSocket socket;
        DatagramPacket dpreceive, dpsend;
        server run = new server();
        try {
            socket = new DatagramSocket(1234);
            dpreceive = new DatagramPacket(new byte[buffsize], buffsize);
            while (true) {
                socket.receive(dpreceive);
                String tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
                System.out.println("Server received: " + tmp + " from "
                        + dpreceive.getAddress().getHostAddress() + " at port "
                        + socket.getLocalPort());
                String[] trimL = tmp.split(";");
                if (tmp.equals("bye")) {
                    System.out.println("Server socket closed");
                    socket.close();
                    break;
                }
                // Uppercase, sent back to client, server status test
                if (tmp.equals("test")) {
                    tmp = tmp.toUpperCase();
                    dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                            dpreceive.getAddress(), dpreceive.getPort());
                    System.out.println("Server sent back " + tmp + " to client");
                    socket.send(dpsend);
                    continue;
                }
                //global search
                if (tmp.equals("hello")) {
                    System.out.println("---Global's searching status---");
                    tmp = run.global();
                    dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                            dpreceive.getAddress(), dpreceive.getPort());
                    socket.send(dpsend);
                    continue;
                }
                //Load state
                if (trimL.length == 1) {
                    String country = trimL[0];
                    System.out.println("---State's loading status---");
                    tmp = run.country(country);
                    dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                            dpreceive.getAddress(), dpreceive.getPort());
                    socket.send(dpsend);
                    continue;
                }
                //Load city
                if (trimL.length == 2) {
                    String country = trimL[0];
                    String state = trimL[1];
                    System.out.println("---City's loading status---");
                    tmp = run.state(country, state);
                    dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                            dpreceive.getAddress(), dpreceive.getPort());
                    socket.send(dpsend);
                    continue;
                }
                //Get aquius
                if (trimL.length == 3) {
                    String country = trimL[0];
                    String state = trimL[1];
                    String city = trimL[2];
                    System.out.println("---Aquius's loading status---");
                    tmp = run.getData(country, state, city);
                    dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                            dpreceive.getAddress(), dpreceive.getPort());
                    socket.send(dpsend);
                    
                }                
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private String global() throws IOException {
        String str = "";
        String add = "https://api.airvisual.com/v2/countries?key=" + key;
        String json = Jsoup.connect(add).ignoreContentType(true).execute().body();
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        String stat = jsonObject.getAsJsonObject().get("status").getAsString();
        System.out.println(stat);

        if (stat.equals("success")) {
            JsonArray arr = jsonObject.getAsJsonArray("data");
            for (int i = 0; i < arr.size(); i++) {
                String post_id = arr.get(i).getAsJsonObject().get("country").getAsString();
                //System.out.println(post_id);
                str += ("\n" + post_id);
            }
        } else {
            str = "false";
        }
        //System.out.println(str);
        return str;
    }

    private String country(String country) throws IOException {
        String str = "";
        String add = "https://api.airvisual.com/v2/states?country=" + country + "&key=" + key;

        try {
            String json = Jsoup.connect(add).ignoreContentType(true).execute().body();
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

            String stat = jsonObject.getAsJsonObject().get("status").getAsString();
            System.out.println(stat);

            JsonArray arr = jsonObject.getAsJsonArray("data");
            for (int i = 0; i < arr.size(); i++) {
                String post_id = arr.get(i).getAsJsonObject().get("state").getAsString();
                //System.out.println(post_id);
                str += ("\n" + post_id);
            }
        } catch (Exception e) {
            str = "country: " + country + " not found!?";
            System.out.println("fail");
        }

        //System.out.println(str);
        return str;
    }

    private String state(String country, String state) throws IOException {
        String str = "";
        String add = "https://api.airvisual.com/v2/cities?state=" + state + "&country=" + country + "&key=" + key;

        try {
            String json = Jsoup.connect(add).ignoreContentType(true).execute().body();
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

            String stat = jsonObject.getAsJsonObject().get("status").getAsString();
            System.out.println(stat);

            JsonArray arr = jsonObject.getAsJsonArray("data");
            for (int i = 0; i < arr.size(); i++) {
                String post_id = arr.get(i).getAsJsonObject().get("city").getAsString();
                //System.out.println(post_id);
                str += ("\n" + post_id);
            }
        } catch (Exception e) {
            str = state + " of country: " + country + " not found!?";
            System.out.println("fail");
        }

        //System.out.println(str);
        return str;
    }

    private String getData(String country, String state, String city) throws IOException {
        String aquius = "";
        String add = "https://api.airvisual.com/v2/city?city=" + city + "&state=" + state + "&country=" + country + "&key=" + key;

        try {
            String json = Jsoup.connect(add).ignoreContentType(true).execute().body();
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

            String stat = jsonObject.getAsJsonObject().get("status").getAsString();
            System.out.println(stat);
            
            aquius = "Aquius's stat: " + jsonObject.getAsJsonObject("data").getAsJsonObject("current").getAsJsonObject("pollution").get("aqius").getAsString();
            //System.out.println(aquius);
        } catch (Exception e) {
            aquius = "City " + city + " of " + state + " notfound!?";
        }

        return aquius;
    }

}