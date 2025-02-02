package com.cab.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.cab.GamePanel;


public class ClientCreater extends Connection {
	String LOCAL_IP = "localhost";
    String SERVER_IP = "162.19.244.99";

    public ClientCreater(GamePanel gp) {
		super(gp);
	}

	@Override
    public void run() {
        try {        	            
            socket = new Socket(LOCAL_IP, 8065);
            isServer = true;
            // Eingangs- und Ausgangsströme für die Kommunikation mit dem Server
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeObject("Register Server");

            id = (int) inputStream.readObject();

            startMsgReceiver(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } 
    }
}