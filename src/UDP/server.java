package UDP;

import Artist.getArtist;
import Songs.getSongs;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class server {

    public static int buffsize = 5242880;
    public static int port = 1234;

    public static void main(String[] args) {
        DatagramSocket socket;
        DatagramPacket dpreceive, dpsend;
        server run = new server();
        getSongs song = new getSongs();
        getArtist artist = new getArtist();
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
                    result = song.getSongDataFromAPI(command[0]);
                    if (result.size() != 0) {
                        dpsend = new DatagramPacket(result.toString().getBytes(), result.toString().getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
                        System.out.println("Server sent back " + tmp + " to client");
                        socket.send(dpsend);
                    } else {
                        String error = "Trên API không có bài này <3 ";
                        dpsend = new DatagramPacket(error.getBytes(), error.getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
                        socket.send(dpsend);
                    }

                } else if (command[1].equals("getChosenSong")) {
                    JsonObject result = new JsonObject();
                    result = song.getLyricsDataFromAPI(command[0]);
                    dpsend = new DatagramPacket(result.toString().getBytes(), result.toString().getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
                    System.out.println("Server sent back " + tmp + " to client");
                    socket.send(dpsend);
                } else if (command[1].equals("searchArtist")) {
                    //Dung jwiki lay thong tin nghe si
                    String artistInf = "";
                    artistInf = artist.getArtistsInf(command[0]);
                    String result = "";
                    JsonArray trackResult = new JsonArray();
                    //goi API lay nghe si can tim
                    JsonObject respond = artist.getArtist(command[0]);
                    if (respond.size() != 1) {
                        try {
                            String artistId = respond.get("id").getAsString();
                            if (artistId != null) {
                                //Link anh
                                String imgLink = respond.get("avatar").getAsJsonObject().get("default").getAsString();
                                trackResult = artist.getTracksOfArtist(artistId);
                                result = artistInf + "~" + trackResult + "~" + imgLink;
                                dpsend = new DatagramPacket(result.toString().getBytes(), result.toString().getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
                                System.out.println("Server sent back " + tmp + " to client");
                                socket.send(dpsend);
                            }
                        } catch (Exception e) {
                            String error = "";
                            dpsend = new DatagramPacket(error.getBytes(), error.getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
                            socket.send(dpsend);
                        }

                    } else {
                        String msg = respond.get("key").getAsString();
                        dpsend = new DatagramPacket(msg.toString().getBytes(), msg.toString().getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
                        System.out.println("Server sent back " + msg + " to client");
                        socket.send(dpsend);
                    }
                }else if(command[1].equals("getUrl")){
                    String result = song.getUrl(command[0]);
                    dpsend = new DatagramPacket(result.getBytes(), result.getBytes().length, dpreceive.getAddress(), dpreceive.getPort());
                    System.out.println("Server sent back " + tmp + " to client");
                    socket.send(dpsend);
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
