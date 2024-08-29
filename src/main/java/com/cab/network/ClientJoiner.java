package com.cab.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import com.cab.GamePanel;

public class ClientJoiner extends Connection {
	String LAPTOP_IP = "192.168.2.198";
	String LOCAL_IP = "localhost";
    String SERVER_IP = "162.19.244.99";

    public ClientJoiner(GamePanel gp) {
		super(gp);
	}

	@SuppressWarnings("unchecked")
    @Override
    public void run() {
        try {        	            
            socket = new Socket(LOCAL_IP, 8065);
            isServer = false;
            
            // Eingangs- und Ausgangsströme für die Kommunikation mit dem Server
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeObject("Register Client");

            id = (int) inputStream.readObject();
            idsOfRunningServers = (List<Integer>) inputStream.readObject();

            startMsgReceiver(inputStream);            
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}