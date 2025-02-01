package com.cab.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cab.GamePanel;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.configs.Colors;
import com.cab.draw.SelectedCard;

public class CardMenu extends GameState {
	GamePanel gp;

	List<Integer> truheAllCards = new ArrayList<>();
	List<Integer> truhe = new ArrayList<Integer>();
	List<Integer> stapel = new ArrayList<Integer>();
    List<List<Integer>> savedStapel = new ArrayList<List<Integer>>();
	List<Integer> newCardIds = new ArrayList<>();

	int state;
	final int filterState = 0;
	final int truheState = 1;
	final int stapelState = 2;
	final int saveLoadState = 3;
	final int loadStapelState = 4;
	final int askLoadOrDeleteState = 5;
	
	int selectedIdx;
	int selectedLoadStapelIdx;
	
	final int limitCardsInRowTruhe = 5;
	final int limitCardRowsTruhe = 4;
	final int limitCardsPerPageTruhe = limitCardsInRowTruhe * limitCardRowsTruhe; 
	
	int totalPages;
	int currentPage;

	final int limitCardsInRowStapel = 7;
	final int limitSaves = 7;
	public final int limitMaxStapel = 20;

	List<Art> filterArten = new ArrayList<>();
	List<Boolean> filterValues = new ArrayList<>();
	List<Integer> xPositionFilterArten = new ArrayList<>();

	SelectedCard selectedCard;

	public CardMenu(GamePanel gp) {
		this.gp = gp;

		int idx = 0;
		for (Art art : Art.values()) {
			filterArten.add(art);
			filterValues.add(true);
			xPositionFilterArten.add(gp.p(2) + idx * gp.p(1.4));
			idx++;
		}

		selectedCard = new SelectedCard(gp, gp.p(33), gp.p(1.2));
	}

	public void start() {
		truheAllCards = gp.player.truhe;
		truhe = gp.player.truhe;
		stapel = gp.player.stapel;
		savedStapel = gp.player.savedStapel;
		newCardIds = gp.player.newCardIds;
		
		selectedIdx = 0;
		selectedLoadStapelIdx = 0;
		currentPage = 0;
		totalPages = (int) Math.ceil((double) truhe.size() / limitCardsPerPageTruhe);
		state = truheState;
		gp.switchState(gp.cardMenuState);
		gp.playMusic(3);
	}

private void filterTruhe() {
    List<Integer> filterList = filterArten.stream()
        .filter(filter -> filterValues.get(filterArten.indexOf(filter))) 
        .flatMap(art -> truheAllCards.stream()
		.filter(id -> gp.cardLoader.getCard(id).getArt() == art))
        .distinct() // Vermeide Duplikate
        .collect(Collectors.toList());

    truhe = filterList;
    totalPages = (int) Math.ceil((double) truhe.size() / limitCardsPerPageTruhe);
}

	private void switchState(int newState) {
		selectedIdx = 0;
		currentPage = 0;
		state = newState;
	}

	private void removeHoloEffekt() {
		if (truhe.size() > 0 && newCardIds.contains(truhe.get(selectedIdx))) {
			newCardIds.remove(truhe.get(selectedIdx));
		}
	}

	private void saveGameAndExit() {
		gp.player.truhe = truheAllCards;
		gp.player.stapel = stapel;
		gp.player.savedStapel = savedStapel;
		gp.player.newCardIds = newCardIds;
		gp.save();
		gp.mainMenu.start();
	}

	private void handle_qPressed() {
		if (state == loadStapelState) {
			switchState(stapelState);
		} else if (state == askLoadOrDeleteState) {
			switchState(loadStapelState);
			selectedIdx = selectedLoadStapelIdx;
		} else {
			if (stapel.size() == limitMaxStapel) {
				saveGameAndExit();
			} else {
				gp.showMsg("zuWenigKartenStapel");
			}
		}
	}

	private void handle_upPressed() {
		if (state == truheState) {
			removeHoloEffekt();
			if (selectedIdx < limitCardsInRowTruhe + currentPage * limitCardsPerPageTruhe) {
				if (currentPage > 0) {
					currentPage--;
					selectedIdx = selectedIdx - limitCardsInRowTruhe;
				} else {
					switchState(filterState);
				}
			} else {
				selectedIdx = selectedIdx - limitCardsInRowTruhe;
			} 
		} else if (state == stapelState) {
			if (selectedIdx >= limitCardsInRowStapel) {
				selectedIdx = selectedIdx - limitCardsInRowStapel;
			} else {
				switchState(saveLoadState);
			}
		} else if (state == loadStapelState) {
			if (selectedIdx > 0) {
				selectedIdx--;
			}
		} else if (state == askLoadOrDeleteState) {
			selectedIdx = 0;
		} 
		gp.playSE(1);
	}

	private void handle_downPressed() {
		if (state == truheState) {
			removeHoloEffekt();
			if (selectedIdx >= (limitCardsInRowTruhe * (limitCardRowsTruhe - 1) + currentPage * limitCardsPerPageTruhe)) {
			
				if (currentPage < (totalPages - 1)) {
					currentPage++;
					selectedIdx = selectedIdx + limitCardsInRowTruhe < truhe.size()? selectedIdx + limitCardsInRowTruhe: truhe.size() - 1;
				}
			} else {
				selectedIdx = selectedIdx + limitCardsInRowTruhe < truhe.size()? selectedIdx + limitCardsInRowTruhe : truhe.size() - 1;
			} 
		} else if (state == stapelState) {
			selectedIdx = (selectedIdx + limitCardsInRowStapel) < stapel.size()? selectedIdx + limitCardsInRowStapel : stapel.size() - 1;
		} else if (state == filterState) {
			switchState(truheState);
		} else if (state == saveLoadState) {
			switchState(stapelState);
		} else if (state == loadStapelState) {
			if (selectedIdx < savedStapel.size() - 1) {
				selectedIdx++;
			}
		} else if (state == askLoadOrDeleteState) {
			selectedIdx = 1;
		} 
		gp.playSE(1);
	}
	
	private void handle_leftPressed() {
		if (state == truheState) {
			removeHoloEffekt();
			if (selectedIdx % limitCardsInRowTruhe != 0) {
				selectedIdx = selectedIdx - 1;
			}
		} else if (state == stapelState) {
			if (selectedIdx % limitCardsInRowStapel != 0) {
				selectedIdx = selectedIdx - 1;
			}
		} else if (state == filterState) {
			if (selectedIdx > 0) {
				selectedIdx--;
			}
		}  else if (state == saveLoadState) {
			selectedIdx = 0;
		}
		gp.playSE(1);
	}

	private void hanlde_rightPressed() {
		if (state == truheState) {
			removeHoloEffekt();
			if (((selectedIdx + 1) < truhe.size())) {
				if ((selectedIdx + 1) % limitCardsInRowTruhe != 0) {
					selectedIdx = selectedIdx + 1;
				}
			}
		} else if (state == stapelState) {
			if (((selectedIdx + 1) < stapel.size())) {
				if ((selectedIdx + 1) % limitCardsInRowStapel != 0) {
					selectedIdx = selectedIdx + 1;
				}
			}
		} else if (state == filterState) {
			if (selectedIdx < 6) {
				selectedIdx++;
			}
		} else if (state == saveLoadState) {
			selectedIdx = 1;
		}
		gp.playSE(1);
	}
	
	private void handle_fPressed() {
		if (state == truheState) {
			removeHoloEffekt();
			if (truhe.size() > 0 && stapel.size() < limitMaxStapel) {
				stapel.add(truhe.get(selectedIdx));
				truheAllCards.remove(truhe.get(selectedIdx));
				filterTruhe();

				if (truhe.size() == 0) {
					currentPage = 0;
					switchState(stapelState);
				} else {
					if (currentPage == totalPages) {
						currentPage--;
					} if (selectedIdx == truhe.size()) {
					selectedIdx--;
					}	
				}
				gp.playSE(2);
			}
		} else if (state == stapelState) {
			if (stapel.size() > 0) {
				truheAllCards.add(stapel.get(selectedIdx));
				stapel.remove(selectedIdx);
				filterTruhe();

				if (stapel.size() == 0) {
					switchState(truheState);
				} else if (selectedIdx == stapel.size()) {
					selectedIdx--;
				}

				gp.playSE(2);
			}
		} else if (state == filterState) {
			filterValues.set(selectedIdx, !filterValues.get(selectedIdx));
			filterTruhe();
			gp.playSE(1);
		} else if (state == saveLoadState) {
			if (selectedIdx == 0) {
				if (savedStapel.size() >= limitSaves) {
					gp.showMsg("saveFailSpeicher");
				} else if (stapel.size() < limitMaxStapel) {
					gp.showMsg("saveFailMaxStapel");
				} else {
					savedStapel.add(new ArrayList<>(stapel));
					gp.showMsg("saveSuccess");
				}
			} else if (selectedIdx == 1) {
				if (savedStapel.size() > 0) {
					switchState(loadStapelState);
				} else {
					gp.showMsg("keineStapelGespeichert");								
				}
			}
			gp.playSE(1);
		} else if (state == loadStapelState) {
			selectedLoadStapelIdx = selectedIdx;
			switchState(askLoadOrDeleteState);
			gp.playSE(1);
		} else if (state == askLoadOrDeleteState) {
			if (selectedIdx == 0) {
				truheAllCards.addAll(stapel);
				stapel.clear();
				
				List<Integer> savedIds = savedStapel.get(selectedLoadStapelIdx);
				if (savedIds != null) {
					truheAllCards.removeAll(savedIds);
					stapel.addAll(savedIds);
				}
				
				filterTruhe();
				switchState(saveLoadState);
				gp.playSE(2);
			} else if (selectedIdx == 1) {
				savedStapel.remove(selectedLoadStapelIdx);
				if (savedStapel.size() > 0) {
					switchState(loadStapelState);
				} else {
					switchState(saveLoadState);
				}
			} 	
			gp.playSE(1);						
		}
	}

	private void handle_gPressed() {
		if (state == truheState) {
			removeHoloEffekt();
			switchState(stapelState);
		} else if (state == stapelState || state == saveLoadState) {
			switchState(truheState);
		}
		gp.playSE(1);
	}

	@Override
	public void update() {
		if (gp.keyH.qPressed) {
			handle_qPressed();
		} else if (gp.keyH.upPressed) {		
			handle_upPressed();
		} else if (gp.keyH.downPressed) {
			handle_downPressed();
		} else if (gp.keyH.leftPressed) {
			handle_leftPressed();
		} else if (gp.keyH.rightPressed) {
			hanlde_rightPressed();
		} else if (gp.keyH.fPressed) {
			handle_fPressed();
		} else if (gp.keyH.gPressed == true) {
			handle_gPressed();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		Card card = null;
		int x = 0;
		int y = 0;
		//BGs
		g2.drawImage(gp.imageLoader.animCardEditorBG.get(), 0, 0, gp.screenWidth, gp.screenHeight, null); //background
		g2.drawImage(gp.imageLoader.paper02, gp.p(1), gp.p(0.7), gp.p(13), gp.p(2.5), null); //FILTER
		g2.drawImage(gp.imageLoader.paper05, gp.p(11.4), gp.p(3.17), gp.p(2.8), gp.p(1.4), null); //SEITENANZAHL
		g2.drawImage(gp.imageLoader.paper08, gp.p(29.6), gp.p(8.7), gp.p(2.8), gp.p(1.3), null); //STAPELANZAHL
		g2.drawImage(gp.imageLoader.paper07, gp.p(14.55), 0, gp.p(11), gp.p(8), null); //INSTRACTION STATUS PAPER
		g2.drawImage(gp.imageLoader.status, gp.p(15.5), gp.p(1), gp.p(1.6), gp.p(6), null); //INSTRACTION STATUS BILD
		g2.drawImage(gp.imageLoader.paper01, gp.p(14.8), gp.p(7.6), gp.p(1.4), gp.p(1.2), null); //SAVE
		g2.drawImage(gp.imageLoader.iconSave, gp.p(15), gp.p(7.6), gp.p(1), gp.p(1), null); //SAVE ICON
		g2.drawImage(gp.imageLoader.paper01, gp.p(16.4), gp.p(7.6), gp.p(1.4), gp.p(1.2), null); //LOAD
		g2.drawImage(gp.imageLoader.iconLoad, gp.p(16.6), gp.p(7.6), gp.p(1), gp.p(1), null); //ICON LOAD
		g2.drawImage(gp.imageLoader.paper06, gp.p(1.18), gp.p(2.9), gp.p(3.8), gp.p(1.55), null); //TRUHE
		g2.drawImage(gp.imageLoader.paper06, gp.p(14.55), gp.p(9), gp.p(3.8), gp.p(1.4), null); //STAPEL

		//FILTER
		for (int i = 0; i < filterArten.size(); i++) {
			g2.drawImage(gp.imageLoader.getArtIconForArt(filterArten.get(i), state == filterState && selectedIdx == i), xPositionFilterArten.get(i), gp.p(1.2), gp.p(1.4), gp.p(1.4), null);
			if (filterValues.get(i)) {
				g2.drawImage(gp.imageLoader.iconCheck, xPositionFilterArten.get(i), 0, gp.p(1.4), gp.p(1), null);
			}
		}

		//ARROWS
		gp.drawLib.drawArrowOnState(g2, 0, gp.p(1), state == filterState, true);
		gp.drawLib.drawArrowOnState(g2, 0, gp.p(2.8), state == truheState, true);
		gp.drawLib.drawArrowOnState(g2, gp.p(13.4), gp.p(7.2), state == saveLoadState, true);
		gp.drawLib.drawArrowOnState(g2, gp.p(13.3), gp.p(8.8), state == stapelState, true);

		//STRINGS
		g2.setColor(Colors.getColorSelectionDark(state, truheState));
		g2.setFont(gp.fontSelection(25, 34, state, truheState));
		g2.drawString(gp.t("truhe"), gp.p(1.6), gp.p(4));        

		g2.setColor(Colors.getColorSelectionDark(state, stapelState));
		g2.setFont(gp.fontSelection(25, 34, state, stapelState));
		g2.drawString(gp.t("stapel"), gp.p(15), gp.p(10)); 

		g2.setFont(gp.font(15));
		g2.setColor(Color.BLACK); 
		g2.drawString(gp.t("statusSchildB"),  gp.p(17.5), gp.p(1.7));
		g2.drawString(gp.t("statusFluegelB"), gp.p(17.5), gp.p(2.8));
		g2.drawString(gp.t("statusGiftB"),    gp.p(17.5), gp.p(4));
		g2.drawString(gp.t("statusFeuerB"), 	 gp.p(17.5), gp.p(5.4));
		g2.drawString(gp.t("statusBlitzB"),	 gp.p(17.5), gp.p(6.5));

		g2.setFont(gp.font(20));
		g2.drawString(currentPage + 1 + " " + gp.t("von") + " " + totalPages, gp.p(12.2), gp.p(4.1)); 

		g2.setFont(gp.font(20));
		g2.setColor(Colors.getColorSelection(stapel.size(), limitMaxStapel));
		g2.drawString(stapel.size() + "/" + limitMaxStapel, gp.p(31), gp.p(9.5)); 
		
		if (state == filterState) {
			g2.setColor(Color.WHITE);
			g2.setFont(gp.font(36));
			Art selectedArt = filterArten.get(selectedIdx);
			g2.drawString(gp.t(selectedArt.getTextbaustein()), gp.p(30), gp.p(0.8));
			
			g2.setFont(gp.font(15));
			if (selectedArt == Art.Fabelwesen) {
				g2.drawString(gp.t("fabelwesenHinweis"), gp.p(24), gp.p(1.8));
			} else if (selectedArt == Art.Nachtgestalt) {
				g2.drawString(gp.t("nachtgestalenHinweis"), gp.p(24), gp.p(1.8));
			}
		}
		
		//TRUHE Karten
		int startIndex = currentPage * limitCardsPerPageTruhe;
		int endIndex = (startIndex + limitCardsPerPageTruhe) <= truhe.size()? (startIndex + limitCardsPerPageTruhe) : truhe.size();

		x = gp.p(1); 
		y = gp.p(4.68); 

		for (int i = startIndex; i < endIndex; i++) {
			// falls endindex den falschen wert hat, timing problem manchmal
			if (i < truhe.size()) {
				card = gp.cardLoader.getCard(truhe.get(i));
				gp.drawLib.drawCardStandardSize(g2, card, x, y, state == truheState && selectedIdx == i, newCardIds.contains(card.getId()));
				
				x += gp.p(1.9) + gp.p(0.5);
				if (i % limitCardsInRowTruhe == limitCardsInRowTruhe - 1) {
					x = gp.p(1);
					y += gp.p(2.9) + gp.p(0.5);
				}	
			}
		}

		x = gp.p(15);
		y = gp.p(10.5); 
		
		for (int i = 0; i < stapel.size(); i++) {
			card = gp.cardLoader.getCard(stapel.get(i));
			gp.drawLib.drawCardStandardSize(g2, card, x, y, state == stapelState && selectedIdx == i, false);

			x += gp.p(1.9) + gp.p(0.5);
			if (i % limitCardsInRowStapel == limitCardsInRowStapel- 1) {
				x = gp.p(15);
				y += gp.p(2.9) + gp.p(0.5);
			}
		}

		//OnSaveLoad
		if (state == saveLoadState) {
			gp.drawLib.drawHover(g2, gp.p(15)  , gp.p(7.6), gp.p(1), gp.p(1), selectedIdx == 0);
			gp.drawLib.drawHover(g2, gp.p(16.6), gp.p(7.6), gp.p(1), gp.p(1), selectedIdx == 1);
		}

		//SELECTED
		if (state == truheState && truhe.size() > 0) {
			card = gp.cardLoader.getCard(truhe.get(selectedIdx));
		} else if (state == stapelState && stapel.size() > 0) {
			card = gp.cardLoader.getCard(stapel.get(selectedIdx));
		}

		if (card != null) {
			selectedCard.drawCard(g2, card);
		} 


		//LOAD SCREEN
		if (state == loadStapelState || state == askLoadOrDeleteState) {
			int selectedAbstandX = gp.p(1);

			g2.setColor(Colors.transparentDarkBlack); 
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			
			int select;
			if (state == loadStapelState) {
				select = selectedIdx;
			} else {
				select = selectedLoadStapelIdx;
			}
			for (int i = 0; i < savedStapel.size(); i++) {
				g2.drawImage(gp.imageLoader.paper01, gp.p(2.6) + selectedAbstandX, gp.p(1.7) + gp.p(3) * i, gp.p(1), gp.p(1), null);

				gp.drawLib.drawArrowOnState(g2, gp.p(1) + selectedAbstandX, gp.p(1.2) + gp.p(3) * i, state == loadStapelState, select == i);
				gp.drawLib.drawHover(g2, gp.p(2), gp.p(0.92) + gp.p(3) * i, gp.p(32), gp.p(2.7), select == i);

				g2.setColor(Colors.getColorSelection(i, select));
				g2.setFont(gp.font(30));
				g2.drawString(i + "", gp.p(3) + selectedAbstandX, gp.p(2.3) + gp.p(3) * i);

				for( int j = 0; j < savedStapel.get(i).size(); j++) {
					double angle = (j % 2 == 0) ? Math.toRadians(-5) : Math.toRadians(5); // Neigung abhängig von Parität

					int centerX = gp.p(4) + gp.p(1.2) * j + gp.p(4) / 2;
					int centerY = gp.p(1.2) + gp.p(3) * i;
	
					g2.rotate(angle, centerX, centerY);
	
					g2.drawImage(gp.cardLoader.getCard(savedStapel.get(i).get(j)).getImage(), gp.p(4) + gp.p(1.2) * j + selectedAbstandX, centerY, gp.p(1.2), gp.p(2), null);

					if (select != i) {
						g2.setPaint(Colors.gardianSelectFromGrave);
						g2.fillRect(gp.p(4) + gp.p(1.2) * j + selectedAbstandX, centerY, gp.p(1.2), gp.p(2));
					}
	
					g2.rotate(-angle, centerX, centerY);
				}
			}

			if (state == askLoadOrDeleteState) {
				gp.drawLib.drawDialog(g2, gp.p(15), gp.p(10), gp.p(3.5), gp.p(3.2));
				gp.drawLib.drawArrowOnState(g2, gp.p(14.55), gp.p(10), true, selectedIdx == 0);
				gp.drawLib.drawArrowOnState(g2, gp.p(14.55), gp.p(11), true, selectedIdx == 1);

				g2.setFont(gp.font(20));
				
				g2.setColor(Colors.getColorSelection(0, selectedIdx));
				g2.drawString(gp.t("laden"), gp.p(16.3), gp.p(11));
				
				g2.setColor(Colors.getColorSelection(1, selectedIdx));
				g2.drawString(gp.t("loeschen"), gp.p(16.3), gp.p(12));
			}


    	}
	}
}
