package com.cab.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.cab.cardGame.Player;
import com.cab.cardGame.PunkteArt;
import com.cab.GamePanel;
import com.cab.card.Art;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;



public class Connection extends Thread {
	GamePanel gp;
	CardGame cg;
	public ObjectInputStream inputStream;
	public ObjectOutputStream outputStream;
	public Integer id;
	public Integer idOponent;
	public List<Integer> idsOfRunningServers = new ArrayList<Integer>();
	public Socket socket;
	boolean isServer;

	public Connection(GamePanel gp) {
		this.gp = gp;
		this.cg = gp.cardGame;
	}

	public void close() {
		try {
			outputStream.writeObject("Stop Connection");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeGame() {
		try {
			outputStream.writeObject("Close Own Connection");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void joinToServer(int idServer) {
		try {
			outputStream.writeObject("Connect to Server");
			outputStream.writeObject(id);
			outputStream.writeObject(idServer);
			idOponent = idServer;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void acceptClientForGame() {
		try {
			outputStream.writeObject("Server Accepted Game");
			outputStream.writeObject(id);
			outputStream.writeObject(idOponent);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void sendDuellInfos(List<Integer> stapel, boolean isPStart) {
		try {
			outputStream.writeObject("Stapel");		
			outputStream.writeObject(stapel);
			outputStream.writeObject(isPStart);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	public void send(Boolean isPlayer, Integer argIntOne, Integer argIntTwo, Boolean argBoolean, Boolean aBooleanTwo, Art art, int[] array, String argString, String msg) {
		if (gp.cardGame.isOnline) {
			try {
				outputStream.writeObject(msg);

				if (isPlayer != null) {
					outputStream.writeObject(isPlayer);
				}

				if (argIntOne != null) {
					outputStream.writeObject(argIntOne);
				}

				if (argIntTwo != null) {
					outputStream.writeObject(argIntTwo);
				}

				if (argBoolean != null) {
					outputStream.writeObject(argBoolean);
				}

				if (aBooleanTwo != null) {
					outputStream.writeObject(aBooleanTwo);
				}

				if (art != null) {
					outputStream.writeObject(art.toString()); //Server kenn keine Art, wird beim Empfaneger wieder gecastet 
				}

				if (array != null) {
					outputStream.writeObject(array);
				}

				if (argString != null) {
					outputStream.writeObject(argString);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
    public void startMsgReceiver(ObjectInputStream in) {
		boolean stop = false;
    	while (!stop) {
    		String response = "";
    		try {
    			response = (String) in.readObject();
				System.out.println("Masterserver msg: " + response);

				switch (response) {
					case "Client Connected": //Server: client ist beigetreten
						idOponent = (Integer) in.readObject();
						gp.createServer.switchState(gp.createServer.clientJoinedState);
						break;
					case "Connected to Server": //Client: serverraum konnte betreten werden
						gp.joinServer.currentState = gp.joinServer.serverJoinedState;
						break;
					case "Connection to Server Failed": //Client: serverraum konnte nicht betreten werden
						gp.mainMenu.start();
						break;
					case "Connection zum Partner verloren": //Connection zum Partner ist verloren gegangen
						in.close();
						outputStream.close();
						socket.close();
						stop = true;
						gp.mainMenu.start();
						break;
					case "Start Game": 
						String msg = isServer? "Server start forwarding" : "Client start forwarding";
						outputStream.writeObject(msg);
						outputStream.writeObject(idOponent);
						sendDuellInfos(gp.player.stapel, isServer);
						break;
					case "Stop Connection":
						in.close();
						outputStream.close();
						socket.close();
						stop = true;
						break;
					
					case "Stapel":
						@SuppressWarnings("unchecked")
						List<Integer> oponentStapel = (List<Integer>) in.readObject();
						boolean isPlayerStart = (Boolean) in.readObject();
						gp.cardGame.createGame(oponentStapel, isPlayerStart, true);
						gp.gameState = gp.cardGameState; 
						break;

					case "sortCards":
						cg.sortKarten(getPlayer((boolean) in.readObject()), (int[]) in.readObject(), (String) in.readObject(), false);
						break;
					case "spielerPunkteAendern":
						cg.spielerPunkteAendern(getPlayer((boolean) in.readObject()), (int) in.readObject(), PunkteArt.valueOf((String) in.readObject()), false);
						break;
						
					case "moveCardFromHandToStapel":
					cg.karteVonHandAufStapel(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "drawCard":
						cg.kartenZiehen(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "moveKreaturFormHandToBoard":
						cg.karteVonHandAufBoard(getPlayer((boolean) in.readObject()), (int) in.readObject(), (boolean) in.readObject(), (boolean) in.readObject(), false);
						break;
					case "moveCardFromStapelToBoard":
						cg.karteVonStapelAufBoard(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "moveCardFromBoardToGrave":
						cg.karteVomBoardInFriedhof(getPlayer((boolean) in.readObject()), (int) in.readObject(), false, false);
						break;
					case "moveCardFromGraveToHand":
						cg.karteVomFriedhofInHand(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "moveCardFromGraveToBoard":
						cg.karteVomFriedhofAufBoard(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "moveCardFromBoardToHand":
						cg.karteVonBoardInHand(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "moveCardFromOponentBoardToOwnBoard":
						cg.karteBoardKontrolleUebernehmen(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "karteVonHandAufSpellGrave":
						cg.karteVonHandAufSpellGrave(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "karteVonStapelAufDieHand":
						cg.karteVonStapelAufHand(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "karteVonHandZerstoeren":
						cg.karteVonHandAufFriedhof(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "switchHandCardsWithOponent":
						cg.kartenTauschenHand(getPlayer((boolean) in.readObject()), (int) in.readObject(), (int) in.readObject(), false);
						break;
					case "setArtOfCard":
						cg.setArtOfCard((int) in.readObject(),  Art.valueOf((String) in.readObject()), false);
						break;
					case "setHide":
						cg.karteDrehen((int) in.readObject(), (boolean) in.readObject(), false);
						break;
					case "setAtkVerringernOfCardOnBoard":
						cg.karteAngriffVerringern((int) in.readObject(), (int) in.readObject(), false);
						break;
					case "setAtkErhoehenOfCardOnBoard":
						cg.karteAngriffErhoehen((int) in.readObject(), (int) in.readObject(), false);
						break;
					case "setSchadenOfBoardCard":
						cg.karteSchaden(getPlayer((boolean) in.readObject()), (int) in.readObject(), (int) in.readObject(), false, false);
						break;
					case "setHeilenOfBoardCard":
						cg.karteHeilen((int) in.readObject(), (int) in.readObject(), false);
						break;
					case "setKarteStatus":
						cg.setKarteStatus((int) in.readObject(), (boolean) in.readObject(), Status.valueOf((String) in.readObject()), false);
						break;
					case "setBlockAufrufArtNextTurn":
						cg.setBlockAufrufArtNextTurn(getPlayer((boolean) in.readObject()), (boolean) in.readObject(), Art.valueOf((String) in.readObject()), false);
						break;
					case "setBlockAttackOnTurn":
						cg.setKarteBlockAttackOnTurn((int) in.readObject(), (boolean) in.readObject(), false);
						break;
					case "playerEndTurn":
						cg.startTurn();
						break;
					case "setUpDirectAttack":
						cg.setUpDirekterAngriff(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "directAttack":
						cg.direkterAngriff(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
						
					case "attackPhaseOne":
						cg.attackPhaseOne(getPlayer((boolean) in.readObject()), (int) in.readObject(), (int) in.readObject(), false);
						break;
					case "attackPhaseTwo":
						cg.attackPhaseTwo(getPlayer((boolean) in.readObject()), (int) in.readObject(), (int) in.readObject(), false);
						break;
					case "attackPhaseThree":
						cg.attackPhaseThree(getPlayer((boolean) in.readObject()), (int) in.readObject(), (int) in.readObject(), false);
						break;
					case "changeSavedIdPlayerAttack":
						cg.changeSavedIdPlayerAttack((int) in.readObject(), false);
						break;
					case "changeSavedIdOponentAttack":
						cg.changeSavedIdOponentAttack((int) in.readObject(), false);
						break;
					case "selectOptionFromList":
						cg.selectOptionFromList((String) in.readObject(), false);
						break;
					case "selectTargetCard": 
						cg.selectTargetCard(getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case "manualEffekt":
						cg.manualEffekt((int) in.readObject(), false);
						break;
					case "resumeAfterEffekt":
						cg.resumeState();
						break;
					case "foreToEndTurn": 
						cg.endTurn();
						break;
					default:
						break;
				}
			} catch (IOException | ClassNotFoundException e) {
    			e.printStackTrace();
    		}  
    	}
    }


	private Player getPlayer(boolean isPlayer) {
		return isPlayer? cg.oponent : cg.player;
	}
}