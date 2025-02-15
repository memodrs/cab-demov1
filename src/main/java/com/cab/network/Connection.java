package com.cab.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;
import com.cab.configs.Messages;
import com.cab.GamePanel;
import com.cab.card.Art;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.*;



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
						gp.cardGame.createGame(oponentStapel, isPlayerStart, this);
						gp.switchState(gp.cardGameState);
						break;
						
					case Messages.SORT_KARTEN:
						new SortKarten().execute(cg, getPlayer((boolean) in.readObject()), (int[]) in.readObject(), (String) in.readObject(), false);
						break;
					case Messages.SPIELER_PUNKTE_AENDERN:
						new SpielerPunkteAendern().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), PunkteArt.valueOf((String) in.readObject()), false);
						break;
					case Messages.KARTE_VON_HAND_AUF_STAPEL:
						new KarteVonHandAufStapel().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.KARTEN_ZIEHEN:
						new KartenZiehen().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.KARTE_VON_HAND_AUF_BOARD:
						new KarteVonHandAufBoard().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), 
							(boolean) in.readObject(), (boolean) in.readObject(), false);
						break;
					case Messages.KARTE_VON_STAPEL_AUF_BOARD:
						new KarteVonStapelAufBoard().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.KARTE_VOM_BOARD_IN_FRIEDHOF:
						new KarteVomBoardInFriedhof().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false, false);
						break;
					case Messages.KARTE_VOM_FRIEDHOF_IN_HAND:
						new KarteVomFriedhofInHand().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.KARTE_VOM_FRIEDHOF_AUF_BOARD:
						new KarteVomFriedhofAufBoard().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.KARTE_VON_BOARD_IN_HAND:
						new KarteVonBoardInHand().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.KARTE_BOARD_KONTROLLE_UEBERNEHMEN:
						new KarteBoardKontrolleUebernehmen().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.KARTE_VON_HAND_AUF_SPELL_GRAVE:
						new KarteVonHandAufSpellGrave().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.KARTE_VON_STAPEL_AUF_HAND:
						new KarteVonStapelAufHand().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.KARTE_VON_HAND_AUF_FRIEDHOF:
						new KarteVonHandAufFriedhof().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.KARTEN_TAUSCHEN_HAND:
						new KartenTauschenHand().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), (int) in.readObject(), false);
						break;
					case Messages.SET_ART_OF_CARD:
						new SetArtOfCard().execute(cg, (int) in.readObject(), Art.valueOf((String) in.readObject()), false);
						break;
					case Messages.KARTE_DREHEN:
						new KarteDrehen().execute(cg, (int) in.readObject(), (boolean) in.readObject(), false);
						break;
					case Messages.KARTE_ANGRIFF_VERRINGERN:
						new KarteAngriffVerringern().execute(cg, (int) in.readObject(), (int) in.readObject(), false);
						break;
					case Messages.KARTE_ANGRIFF_ERHOEHEN:
						new KarteAngriffErhoehen().execute(cg, (int) in.readObject(), (int) in.readObject(), false);
						break;
					case Messages.KARTE_SCHADEN:
						new KarteSchaden().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), (int) in.readObject(), false, false);
						break;
					case Messages.KARTE_HEILEN:
						new KarteHeilen().execute(cg, (int) in.readObject(), (int) in.readObject(), false);
						break;
					case Messages.SET_KARTE_STATUS:
						new SetKarteStatus().execute(cg, (int) in.readObject(), (boolean) in.readObject(), Status.valueOf((String) in.readObject()), false);
						break;
					case Messages.SET_BLOCK_AUFRUF_ART_NEXT_TURN:
						new SetBlockAufrufArtNextTurn().execute(cg, getPlayer((boolean) in.readObject()), (boolean) in.readObject(), Art.valueOf((String) in.readObject()), false, false);
						break;
					case Messages.SET_KARTE_BLOCK_ATTACK_ON_TURN:
						new SetKarteBlockAttackOnTurn().execute(cg, (int) in.readObject(), (boolean) in.readObject(), false);
						break;
					case Messages.END_TURN:
						new EndTurn().execute(cg, getPlayer((boolean) in.readObject()), false);
						break;
					case Messages.SETUP_DIREKTER_ANGRIFF:
						new SetUpDirekterAngriff().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.DIREKTER_ANGRIFF:
						new DirekterAngriff().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), false);
						break;
					case Messages.ATTACK_PHASE_ONE:
						new AttackPhaseOne().execute(cg, getPlayer((boolean) in.readObject()), (int) in.readObject(), (int) in.readObject(), false);
						break;
					case Messages.ATTACK_PHASE_TWO:
						new AttackPhaseTwo().execute(cg, getPlayer((boolean) in.readObject()), false);
						break;
					case Messages.ATTACK_PHASE_THREE:
						new AttackPhaseThree().execute(cg, getPlayer((boolean) in.readObject()), false);
						break;
					case Messages.CHANGE_SAVED_ID_PLAYER_ATTACK:
						new ChangeSavedIdPlayerAttack().execute(cg, (int) in.readObject(), false);
						break;
					case Messages.CHANGE_SAVED_ID_OPONENT_ATTACK:
						new ChangeSavedIdOponentAttack().execute(cg, (int) in.readObject(), false);
						break;
					case Messages.SELECT_OPTION_FROM_LIST:
						new SelectOptionFromList().execute(cg, (String) in.readObject(), false);
						break;
					case Messages.SELECT_TARGET_CARD:
						new SelectTargetCard().execute(cg, (int) in.readObject(), false);
						break;
					case Messages.MANUAL_EFFEKT:
						new ManualEffekt().execute(cg, (int) in.readObject(), false);
						break;
					case Messages.FORCE_OPONENT_TO_END_TURN:
						new EndTurn().execute(cg, cg.player, false);
						break;
					case "resumeAfterEffekt":
						cg.resumeState();
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