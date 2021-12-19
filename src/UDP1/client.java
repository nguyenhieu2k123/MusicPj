package UDP1;

import UDP.*;
import GUI.mainGUI;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class client {

    public static int destPort = 1234;
    public static String hostname = "localhost";
    public static int maxSize = 5242880;
    public String sendRequest(String input) {
        DatagramSocket socket;
        DatagramPacket dpsend, dpreceive;
        InetAddress add;
        mainGUI gui = new mainGUI();
        String result="";
        try {
            add = InetAddress.getByName(hostname);	//UnknownHostException
            socket = new DatagramSocket();			//SocketException
            System.out.println("Connected");
            String tmp = input;
            System.out.println(tmp);
            byte[] data = tmp.getBytes();
            dpsend = new DatagramPacket(data, data.length, add, destPort);
            System.out.println("Client sent " + tmp + " to " + add.getHostAddress() + " from port " + socket.getLocalPort());
            socket.send(dpsend);				//IOExeption
            dpreceive = new DatagramPacket(new byte[maxSize], maxSize);
            socket.receive(dpreceive);
            tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
            System.out.println("Client get: data success from server");
            result = tmp;
            socket.close();
            System.out.println("close");
        } catch (IOException e) {
            System.err.println(e);
        }
        
        return result;
    }

    public static void main(String[] args) {

//        DatagramSocket socket;
//        DatagramPacket dpsend, dpreceive;
//        InetAddress add;
//        Scanner stdIn;
//        mainGUI gui = new mainGUI();
//        try {
//            add = InetAddress.getByName(hostname);	//UnknownHostException
//            socket = new DatagramSocket();			//SocketException
//
//            while (true) {
//                System.out.print("Client input: ");
//                String tmp = gui.txtSearch.getText();
//                byte[] data = tmp.getBytes();
//                dpsend = new DatagramPacket(data, data.length, add, destPort);
//                System.out.println("Client sent " + tmp + " to " + add.getHostAddress()
//                        + " from port " + socket.getLocalPort());
//                socket.send(dpsend);				//IOExeption
//                if (tmp.equals("bye")) {
//                    System.out.println("Client socket closed");
//
//                    socket.close();
//                    break;
//                }
//                // Get response from server
//                dpreceive = new DatagramPacket(new byte[1000000], 1000000);
//                socket.receive(dpreceive);
//                tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
//                System.out.println("Client get: " + tmp + " from server");
//            }
//        } catch (IOException e) {
//            System.err.println(e);
//        }
    }
}
