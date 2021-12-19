package Encryption;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connect {
    private static int len = 8;
	public static void send(String msg, DatagramSocket socket, InetAddress address, int port) {
    	DatagramPacket packet;
    	byte[] buf = msg.getBytes();
    	String length = String.valueOf(buf.length);
    	packet = new DatagramPacket(length.getBytes(), length.getBytes().length, address, port);
    	try {
			socket.send(packet);
			packet = new DatagramPacket(buf, buf.length, address, port);
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static ServerReceive receiveServer(DatagramSocket socket, InetAddress address, int port) {
    	DatagramPacket packet;
    	byte[] buf = new byte[len];
		packet = new DatagramPacket(buf, len);
		try {
			socket.receive(packet);
			String lengthStr = new String(buf);
			lengthStr = lengthStr.trim();
			int length = Integer.parseInt(lengthStr);
			buf = new byte[length];
			packet = new DatagramPacket(buf, length);
			socket.receive(packet);
			address = packet.getAddress();
			port = packet.getPort();
			return new ServerReceive(new String(buf), address, port);
		} catch (IOException e) {
			return null;
		}
    }
    
    public static class ServerReceive{
    	private String msg;
    	private InetAddress add;
    	private int port;
		public ServerReceive(String msg, InetAddress add, int port) {
			super();
			this.msg = msg;
			this.add = add;
			this.port = port;
		}
		public ServerReceive() {
			super();
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public InetAddress getAdd() {
			return add;
		}
		public void setAdd(InetAddress add) {
			this.add = add;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
    	
    }
    
    public static String receiveClient(DatagramSocket socket) {
    	DatagramPacket packet;
    	byte[] buf = new byte[len];
		packet = new DatagramPacket(buf, len);
		try {
			socket.receive(packet);
			String lengthStr = new String(buf);
			lengthStr = lengthStr.trim();
			int length = Integer.parseInt(lengthStr);
			buf = new byte[length];
			packet = new DatagramPacket(buf, length);
			socket.receive(packet);
			return new String(buf);
		} catch (IOException e) {
			return "Can't receive from server.";
		}
    }
}
