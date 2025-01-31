package com.cab.states;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.cab.GamePanel;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.configs.Colors;
import com.cab.draw.SelectedCard;
import com.cab.draw.ShakingKoordinaten;


public class CardMenu extends GameState {
	GamePanel gp;

	List<Integer> truheAllCards = new ArrayList<>();
	List<Integer> truhe = new ArrayList<Integer>();
	List<Integer> stapel = new ArrayList<Integer>();
    List<List<Integer>> savedStapel = new ArrayList<List<Integer>>();

	int state;
	final int filterState = 0;
	final int truheState = 1;
	final int stapelState = 2;
	final int showMsgState = 3;
	final int saveLoadState = 4;
	final int loadStapelState = 5;
	final int askLoadOrDeleteState = 7;
	
	String msg = "";
	int stateBeforeMsg;

	int selectedIdx = 0;
	int selectedLoadStapelIdx;
	
	int limitCardsInRowTruhe = 5;
	int limitCardRowsTruhe = 4;
	int limitCardsPerPageTruhe = limitCardsInRowTruhe * limitCardRowsTruhe; 
	int totalPages;
	int currentPage = 0;

	final int limitCardsInRowStapel = 7;
	final int limitSaves = 7;
	public final int limitMaxStapel = 20;

	List<Art> filterArten = new ArrayList<>();
	List<Boolean> filterValues = new ArrayList<>();
	List<Integer> xPositionFilterArten = new ArrayList<>();

	//Draw
	BufferedImage instactionKeyboard;
	ShakingKoordinaten koordinatenTruhePaper;
	ShakingKoordinaten koordinatenTruheString;
	ShakingKoordinaten koordinatenStapelPaper;
	ShakingKoordinaten koordinatenStapelString;

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

		try {
			instactionKeyboard = ImageIO.read(getClass().getResourceAsStream("/instractions/keyboard/cardEditor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		koordinatenTruhePaper = new ShakingKoordinaten(gp.p(1.15), gp.p(2.9));
		koordinatenStapelPaper = new ShakingKoordinaten(gp.p(14.55), gp.p(9));
		koordinatenTruheString = new ShakingKoordinaten(gp.p(1.6), gp.p(4));
		koordinatenStapelString = new ShakingKoordinaten(gp.p(15), gp.p(10));
		selectedCard = new SelectedCard(gp, gp.p(1), gp.p(1.2));
	}

	public void start() {
		truheAllCards = gp.player.truhe;
		truhe = gp.player.truhe;
		stapel = gp.player.stapel;
		savedStapel = gp.player.savedStapel;
		
		selectedIdx = 0;
		currentPage = 0;
		totalPages = (int) Math.ceil((double) truhe.size() / limitCardsPerPageTruhe);
		state = truheState;
		gp.switchState(gp.cardMenuState);
		gp.playMusic(3);
	}

	private void filterTruhe() {
		List<Integer> filterList = new ArrayList<Integer>();

		for (int i = 0; i < filterArten.size(); i++) {
			if (filterValues.get(i)) {
				for (Integer id : truheAllCards) {
					Card card = gp.cardLoader.getCard(id);
					if (card.getArt() == filterArten.get(i)) {
						filterList.add(id);
					}

				}
			}
		}

		truhe = filterList;
		totalPages = (int) Math.ceil((double) truhe.size() / limitCardsPerPageTruhe);
	}

	private void switchState(int newState) {
		selectedIdx = 0;
		currentPage = 0;
		state = newState;
	}

	private void showMsg(String msg) {
		stateBeforeMsg = state;
		this.msg = msg;
		switchState(showMsgState);
	}

	private void removeHoloEffekt() {
		if (truhe.size() > 0 && gp.player.newCardIds.contains(truhe.get(selectedIdx))) {
			gp.player.newCardIds.remove(truhe.get(selectedIdx));
		}
	}

	@Override
	public void update() {
		if(gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed || gp.keyH.qPressed || gp.keyH.fPressed || gp.keyH.gPressed) {
			if (!gp.keyH.blockBtn) {
				gp.keyH.blockBtn = true;

				if (gp.keyH.qPressed) {
					if (state == showMsgState) {
						switchState(stateBeforeMsg);
					} else if (state == loadStapelState) {
						switchState(stapelState);
					} else if (state == askLoadOrDeleteState) {
						switchState(loadStapelState);
						selectedIdx = selectedLoadStapelIdx;
					} else {
						if (stapel.size() == limitMaxStapel) {
							gp.player.truhe = truheAllCards;
							gp.player.stapel = stapel;
							gp.player.savedStapel = savedStapel;
							gp.save();
							gp.mainMenu.start();
						} else {
							showMsg("zuWenigKartenStapel");
						}
					}
				}

				else if (gp.keyH.upPressed) {		
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
				
				else if (gp.keyH.downPressed) {
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
				
				else if (gp.keyH.leftPressed) {
					if (state == truheState) {
						removeHoloEffekt();
						if (selectedIdx % limitCardsInRowTruhe != 0) {
							selectedIdx = selectedIdx - 1;
						}
					} else if (state == stapelState) {
						if (selectedIdx % limitCardsInRowStapel != 0) {
							selectedIdx = selectedIdx - 1;
						}
					}
					
					else if (state == filterState) {
						if (selectedIdx > 0) {
							selectedIdx--;
						}
					}  else if (state == saveLoadState) {
						selectedIdx = 0;
					}
					gp.playSE(1);
				}
				else if (gp.keyH.rightPressed) {
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
				
				else if (gp.keyH.fPressed) {
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
					} else if (state == showMsgState) {
						switchState(stateBeforeMsg);
						gp.playSE(1);
					} else if (state == saveLoadState) {
						if (selectedIdx == 0) {
							if (savedStapel.size() >= limitSaves) {
								showMsg("saveFailSpeicher");
							} else if (stapel.size() < limitMaxStapel) {
								showMsg("saveFailMaxStapel");
							} else {
								savedStapel.add(new ArrayList<>(stapel));
								showMsg("saveSuccess");
							}
						} else if (selectedIdx == 1) {
							if (savedStapel.size() > 0) {
								switchState(loadStapelState);
							} else {
								showMsg("keineStapelGespeichert");								
							}
						}
						gp.playSE(1);
					} else if (state == showMsgState) {
						switchState(stateBeforeMsg);
						gp.playSE(1);
					} else if (state == loadStapelState) {
						selectedLoadStapelIdx = selectedIdx;
						switchState(askLoadOrDeleteState);
						gp.playSE(1);
					} else if (state == askLoadOrDeleteState) {
						if (selectedIdx == 0) {
							List<Integer> ids = new ArrayList<Integer>();
							for (Integer id : stapel) {
								ids.add(id);
							}
							for (Integer id : ids) {
								truheAllCards.add(id);
								stapel.remove(id);
							}

							ids = new ArrayList<Integer>();
							for (Integer id : savedStapel.get(selectedLoadStapelIdx)) {
								ids.add(id);
							}
							for (Integer id : ids) {
								truheAllCards.remove(id);
								stapel.add(id);
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
				
				else if (gp.keyH.gPressed == true) {
					if (state == truheState) {
						removeHoloEffekt();
						switchState(stapelState);
					} else if (state == stapelState || state == saveLoadState) {
						switchState(truheState);
					}
					gp.playSE(1);
				}
			}
		} 
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(gp.imageLoader.animCardEditorBG.get(), 0, 0, gp.screenWidth, gp.screenHeight, null); //background
		g2.drawImage(gp.imageLoader.paper02, gp.p(1), gp.p(0.7), gp.p(13), gp.p(2.5), null); //FILTER
		g2.drawImage(gp.imageLoader.paper05, gp.p(11.4), gp.p(3.17), gp.p(2.8), gp.p(1.4), null); //SEITENANZAHL
		g2.drawImage(gp.imageLoader.paper08, gp.p(29.6), gp.p(8.7), gp.p(2.8), gp.p(1.3), null); //STAPELANZAHL
		g2.drawImage(instactionKeyboard, gp.p(1), gp.p(18.5), gp.p(9), gp.p(3.6), null); //INSTRACTION KEYBOARD
		
		g2.drawImage(gp.imageLoader.paper07, gp.p(14.55), 0, gp.p(11), gp.p(8), null); //INSTRACTION STATUS PAPER
		g2.drawImage(gp.imageLoader.status, gp.p(15.5), gp.p(1), gp.p(1.6), gp.p(6), null); //INSTRACTION STATUS BILD

		g2.setFont(gp.font(15));
		g2.setColor(Color.BLACK); 
		g2.drawString(gp.t("statusSchildB"),  gp.p(17.5), gp.p(1.7));
		g2.drawString(gp.t("statusFluegelB"), gp.p(17.5), gp.p(2.8));
		g2.drawString(gp.t("statusGiftB"),    gp.p(17.5), gp.p(4));
		g2.drawString(gp.t("statusFeuerB"), 	 gp.p(17.5), gp.p(5.4));
		g2.drawString(gp.t("statusBlitzB"),	 gp.p(17.5), gp.p(6.5));

		for (int i = 0; i < filterArten.size(); i++) {
			g2.drawImage(gp.imageLoader.getArtIconForArt(filterArten.get(i), state == filterState && selectedIdx == i), xPositionFilterArten.get(i), gp.p(1.2), gp.p(1.4), gp.p(1.4), null);
			if (filterValues.get(i)) {
				g2.drawImage(gp.imageLoader.iconCheck, xPositionFilterArten.get(i), 0, gp.p(1.4), gp.p(1), null);
			}
		}

		if (state == filterState) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, 0, gp.p(1), gp.p(2), gp.p(2), null);
		}

		if (state == truheState) {
			g2.drawImage(gp.imageLoader.paper06, koordinatenTruhePaper.getX(), koordinatenTruhePaper.getY(), gp.p(3.8), gp.p(1.55), null); //TRUHE
			g2.setFont(gp.font(36));
			g2.setColor(Colors.orangeYellow); 
			g2.drawImage(gp.imageLoader.iconArrowMarker, 0, gp.p(2.8), gp.p(2), gp.p(2), null);
			g2.drawString(gp.t("truhe"), koordinatenTruheString.getX(), koordinatenTruheString.getY());        

		} else {
			g2.drawImage(gp.imageLoader.paper06, gp.p(1.15), gp.p(2.9), gp.p(3.8), gp.p(1.55), null); //TRUHE
			g2.setFont(gp.font(25));
			g2.setColor(Color.BLACK); 
			g2.drawString(gp.t("truhe"), gp.p(1.6), gp.p(4));        
		}

		g2.setFont(gp.font(20));
		g2.setColor(Color.BLACK); 
		g2.drawString(currentPage + 1 + " " + gp.t("von") + " " + totalPages, gp.p(12.2), gp.p(4));   

		int startIndex = currentPage * limitCardsPerPageTruhe;
		int endIndex = (startIndex + limitCardsPerPageTruhe) <= truhe.size()? (startIndex + limitCardsPerPageTruhe) : truhe.size();

		int x = gp.p(1); 
		int y = gp.p(4.68); 

		for (int i = startIndex; i < endIndex; i++) {
			// falls endindex den falschen wert hat, timing problem manchmal
			if (i < truhe.size()) {

				Card card = gp.cardLoader.getCard(truhe.get(i));

				if (state == truheState && selectedIdx == i) {            		
					g2.drawImage(card.getImage(), x, y, gp.p(1.9) + 10, gp.p(2.9) + 10, null); 
			
					if (gp.player.newCardIds.contains(card.getId())) {
						g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).getHoloEffekt().get(), x, y, gp.p(1.9) + 10, gp.p(2.9) + 10, null); 
					}
					g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, y, gp.p(1.9) + 10, gp.p(2.9) + 10, null); 
				} else {
					g2.setColor(Colors.transparent); 
					g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).getImage(), x, y, gp.p(1.9), gp.p(2.9), null); 
					if (gp.player.newCardIds.contains(card.getId())) {
						g2.drawImage(gp.cardLoader.getCard(truhe.get(i)).getHoloEffekt().get(), x, y, gp.p(1.9), gp.p(2.9), null); 
					}
				}


				x += gp.p(1.9) + gp.p(0.5);
				if (i % limitCardsInRowTruhe == limitCardsInRowTruhe - 1) {
					x = gp.p(1);
					y += gp.p(2.9) + gp.p(0.5);
				}	
			}
		}

		g2.setFont(gp.font(20));
		g2.setColor(Color.BLACK); 
		g2.drawString(gp.t("karteSchieben"), gp.p(4), gp.p(19.5));
		g2.drawString(gp.t("wechselnTruheStapel"), gp.p(4), gp.p(20.2));
		g2.drawString(gp.t("verlassen"), gp.p(4), gp.p(21));

		x = gp.p(15);
		y = gp.p(10.5); 
		
		g2.drawImage(gp.imageLoader.paper01, gp.p(14.8), gp.p(7.6), gp.p(1.4), gp.p(1.2), null);
		g2.drawImage(gp.imageLoader.iconSave, gp.p(15), gp.p(7.6), gp.p(1), gp.p(1), null);
		g2.drawImage(gp.imageLoader.paper01, gp.p(16.4), gp.p(7.6), gp.p(1.4), gp.p(1.2), null);
		g2.drawImage(gp.imageLoader.iconLoad, gp.p(16.6), gp.p(7.6), gp.p(1), gp.p(1), null);

		if (state == stapelState) {
			g2.drawImage(gp.imageLoader.paper06, koordinatenStapelPaper.getX(), koordinatenStapelPaper.getY(), gp.p(3.8), gp.p(1.4), null); //STAPEL

			g2.setFont(gp.font(36));
			g2.setColor(Colors.orangeYellow); 
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(13.4), gp.p(8.7), gp.p(2), gp.p(2), null);
			g2.drawString(gp.t("stapel"), koordinatenStapelString.getX(), koordinatenStapelString.getY()); 

		} else {
			g2.drawImage(gp.imageLoader.paper06, gp.p(14.55), gp.p(9), gp.p(3.8), gp.p(1.4), null); //STAPEL
			g2.setFont(gp.font(25));
			g2.setColor(Color.BLACK); 
			g2.drawString(gp.t("stapel"), gp.p(15), gp.p(10)); 
		}



		if (state == saveLoadState) {
			g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(13.4), gp.p(7.2), gp.p(2), gp.p(2), null);
			if (selectedIdx == 0) {
				g2.drawImage(gp.imageLoader.boosterHover, gp.p(15), gp.p(7.6), gp.p(1), gp.p(1), null);
			} else if (selectedIdx == 1) {
				g2.drawImage(gp.imageLoader.boosterHover, gp.p(16.6), gp.p(7.6), gp.p(1), gp.p(1), null);
			}
		}

		g2.setFont(gp.font(25));
		g2.setColor(Color.BLACK); 
		g2.drawString(stapel.size() + "/" + limitMaxStapel, gp.p(30.7), gp.p(9.5));   
		
		for (int i = 0; i < stapel.size(); i++) {
			g2.setColor(Colors.transparent); 
			
			if (state == stapelState && selectedIdx == i) {
				g2.drawImage(gp.cardLoader.getCard(stapel.get(i)).getImage(), x, y, gp.p(1.9) + 10, gp.p(2.9) + 10, null); 
				g2.drawImage(gp.imageLoader.selectedCardHover.get(), x, y, gp.p(1.9) + 10, gp.p(2.9) + 10, null); 
			} else {
				g2.drawImage(gp.cardLoader.getCard(stapel.get(i)).getImage(), x, y, gp.p(1.9), gp.p(2.9), null);
			}

			g2.fillRect(x, y, gp.p(1.9), gp.p(2.9));
			x += gp.p(1.9) + gp.p(0.5);
			if (i % limitCardsInRowStapel == limitCardsInRowStapel- 1) {
				x = gp.p(15);
				y += gp.p(2.9) + gp.p(0.5);
			}
		}

		Card card = null;
		if (state == truheState && truhe.size() > 0) {
			card = gp.cardLoader.getCard(truhe.get(selectedIdx));
		} else if (state == stapelState && stapel.size() > 0) {
			card = gp.cardLoader.getCard(stapel.get(selectedIdx));
		}
		if (card != null) {
			selectedCard.drawCard(g2, card);
		} else if (state == filterState) {
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

		if (state == loadStapelState || state == askLoadOrDeleteState) {
			g2.setColor(Colors.transparentDarkBlack); 
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			g2.setFont(gp.font(30));
			int select;
			if (state == loadStapelState) {
				select = selectedIdx;
			} else {
				select = selectedLoadStapelIdx;
			}
			for (int i = 0; i < savedStapel.size(); i++) {
				int selectedAbstandX = gp.p(1);

				if (select == i) {
					if (state == loadStapelState) {
						g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(1) + selectedAbstandX, gp.p(1.2) + gp.p(3) * i, gp.p(2), gp.p(2), null);
					}
					g2.drawImage(gp.imageLoader.boosterHover, gp.p(2), gp.p(0.924) + gp.p(3) * i, gp.p(32), gp.p(2.7), null);
				}

				g2.setColor(Colors.getColorSelection(i, select));
				g2.drawImage(gp.imageLoader.paper01, gp.p(2.6) + selectedAbstandX, gp.p(1.7) + gp.p(3) * i, gp.p(1), gp.p(1), null);

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
				g2.setColor(Colors.transparentBlack);
				g2.fillRoundRect(gp.p(15), gp.p(10), gp.p(4), gp.p(3), 35, 35);
				g2.setColor(Color.white);
				g2.setStroke(new BasicStroke(5)); 
				g2.drawRoundRect(gp.p(15), gp.p(10), gp.p(4), gp.p(3), 25, 25);
				g2.setColor(Color.RED);
				g2.setFont(gp.font(20));
				int yArrowMarker = selectedIdx == 0? gp.p(10) : gp.p(11);
				g2.drawImage(gp.imageLoader.iconArrowMarker, gp.p(14.55), yArrowMarker, gp.p(2), gp.p(2), null);

				g2.setColor(Colors.getColorSelection(0, selectedIdx));
				g2.drawString(gp.t("laden"), gp.p(16.3), gp.p(11));
				g2.setColor(Colors.getColorSelection(1, selectedIdx));
				g2.drawString(gp.t("loeschen"), gp.p(16.3), gp.p(12));
			}
    	}

		if (state == showMsgState) {
			g2.setColor(Colors.transparentBlack);
			g2.fillRoundRect(gp.p(14), 0, gp.p(10), gp.p(2), 35, 35);
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(5)); 
			g2.drawRoundRect(gp.p(14), 0, gp.p(10), gp.p(2), 25, 25);
			g2.setColor(Color.RED);
			g2.setFont(gp.font(20));
			g2.drawString(gp.t(msg), gp.p(15), gp.p(12));
			g2.setColor(Color.YELLOW);
			g2.drawString("Ok", gp.p(19), gp.p(12.8));
		}
		
	}
}
