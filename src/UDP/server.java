package UDP;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import musicpj.runApp;
import org.jsoup.Jsoup;

public class server {

    public static int buffsize = 1000000000;
    public static int port = 1234;

    public static void main(String[] args) {
        DatagramSocket socket;
        DatagramPacket dpreceive, dpsend;
        server run = new server();
        runApp app = new runApp();
        System.out.println("Server Start");
        try {
            socket = new DatagramSocket(1234);
            dpreceive = new DatagramPacket(new byte[buffsize], buffsize);
            while (true) {
                socket.receive(dpreceive);
                String tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
                System.out.println("Server received: " + tmp + " from "
                        + dpreceive.getAddress().getHostAddress() + " at port "
                        + socket.getLocalPort());
                String[] command = tmp.split(";");

                // Uppercase, sent back to client, server status test
                if (command[1].equals("btnSearch")) {
                    JsonArray result = new JsonArray();
                    result = app.getSongDataFromAPI(command[0]);    
                    dpsend = new DatagramPacket(result.toString().getBytes(), result.toString().getBytes().length,dpreceive.getAddress(), dpreceive.getPort());
                    System.out.println("Server sent back " + tmp + " to client");
                    socket.send(dpsend);
                }
                else if (command[1].equals("getChosenSong")) {
                    JsonArray result = new JsonArray();
                    result = app.getLyricsDataFromAPI(command[0]);
                    dpsend = new DatagramPacket(result.toString().getBytes(), result.toString().getBytes().length,dpreceive.getAddress(), dpreceive.getPort());
                    System.out.println("Server sent back " + tmp + " to client");
                    socket.send(dpsend);
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    

}
