package com.cab.cardGame;

import com.cab.KeyHandler;
import com.cab.card.Status;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class CardGameUpdater {
    CardGame cg;
    CardGameState cardGameState;
    KeyHandler keyH;
    Player player;
    Player oponent;

    
    public CardGameUpdater(CardGame cg) {
        this.cg = cg;
        this.player = cg.player;
        this.oponent = cg.oponent;
        this.cardGameState = cg.cardGameState;

        this.keyH = cg.gp.keyH;
    }

    public void update() {
        if (cg.gp.keyH.qPressed && !player.inactiveMode) {
            if (cardGameState.isState(State.handCardState) || cardGameState.isState(State.boardState) || cardGameState.isState(State.boardOponentState) || cardGameState.isState(State.graveState) || cardGameState.isState(State.graveOponentState)) {
                cg.endTurn();
            }
            else if (cardGameState.isState(State.handCardSelectedState) || cardGameState.isState(State.effektQuestionStateHand)) {
                cg.switchState(State.handCardState);
                cg.selectedIdx = cg.selectedHandCardIdx;
            }
            else if (cardGameState.isState(State.boardCardSelectedState) || cardGameState.isState(State.effektQuestionStateBoard) || cardGameState.isState(State.selectCardToAttackState)) {
                cg.switchState(State.boardState);
                cg.selectedIdx = cg.selectedBoardCardIdx;
            }
            else if (cardGameState.isState(State.graveSelectedOponentState)) {
                cg.switchState(State.graveOponentState);
            }
            else if (cardGameState.isState(State.graveSelectedState)) {
                cg.switchState(State.graveState);
            } else if (cardGameState.isState(State.effektQuestionStateGrave)) {
                cg.switchState(State.graveSelectedState);
            } else if (cardGameState.isState(State.askAufgebenState)) {
                cg.switchState(State.onAufgbenState);
            }
        }
        else if(keyH.enterPressed) {
            for (CardState card : cg.player.boardCards) {
                cg.karteHeilen(card.id, 1, true);
            }
        }
        else if (keyH.rightPressed) {
            if (cardGameState.isState(State.graveState) || cardGameState.isState(State.graveOponentState) || 
            cardGameState.isState(State.handCardState) || cardGameState.isState(State.boardState) || 
            cardGameState.isState(State.boardOponentState) || cardGameState.isState(State.selectCardToAttackState) || 
            cardGameState.isState(State.effektSelectOponentBoardState) || cardGameState.isState(State.effektSelectOwnBoardState) || 
            cardGameState.isState(State.graveSelectedOponentState) || cardGameState.isState(State.graveSelectedState) || 
            cardGameState.isState(State.effektSelectOwnGraveState)  || cardGameState.isState(State.effektSelectOponentGraveState) ||
            cardGameState.isState(State.selectOptionCardListState)) {
                
                
                if (cardGameState.isState(State.boardOponentState)) {
                    if (oponent.boardCards.size() == 0 || cg.selectedIdx == oponent.boardCards.size() - 1) {
                        cg.switchState(State.graveOponentState);
                    }
                }else if (cardGameState.isState(State.boardState)) {
                    if (player.boardCards.size() == 0 || cg.selectedIdx == player.boardCards.size() - 1) {
                        cg.switchState(State.graveState);
                    }
                } else if (cardGameState.isState(State.graveState)) {
                    cg.switchState(State.spellGraveState);
                } else if (cardGameState.isState(State.graveOponentState)) {
                    cg.switchState(State.spellGraveOponentState);
                } else if (cardGameState.isState(State.handCardState)) {
                    if (player.handCards.size() == 0 || cg.selectedIdx == player.handCards.size() - 1) {
                        cg.switchState(State.onAufgbenState);
                    }
                }

                int size = 0;

                if (cardGameState.isState(State.handCardState)) {
                    size = player.handCards.size() - 1;
                } else if (cardGameState.isState(State.boardState) || cardGameState.isState(State.effektSelectOwnBoardState)) {
                    size = player.boardCards.size() - 1;
                } else if (cardGameState.isState(State.boardOponentState) || cardGameState.isState(State.selectCardToAttackState) || cardGameState.isState(State.effektSelectOponentBoardState)) {
                    size = oponent.boardCards.size() - 1;
                } else if (cardGameState.isState(State.graveSelectedState) || cardGameState.isState(State.effektSelectOwnGraveState)) {
                    size = player.graveCards.size() - 1;
                } else if (cardGameState.isState(State.graveSelectedOponentState) || cardGameState.isState(State.effektSelectOponentGraveState)) {
                    size = oponent.graveCards.size() - 1; 
                }  else if (cardGameState.isState(State.selectOptionCardListState)) {
                    size = cg.optionsCardsToSelect.size() - 1;
                }

                if (cg.selectedIdx < size) {
                    cg.selectedIdx++;
                }
            } 
        }
        
        else if (keyH.leftPressed) {
            if (cardGameState.isState(State.handCardState) || cardGameState.isState(State.boardState) || 
            cardGameState.isState(State.boardOponentState) || cardGameState.isState(State.selectCardToAttackState) || 
            cardGameState.isState(State.effektSelectOponentBoardState) || cardGameState.isState(State.effektSelectOwnBoardState) || 
            cardGameState.isState(State.graveSelectedOponentState) || cardGameState.isState(State.graveSelectedState) || 
            cardGameState.isState(State.effektSelectOwnGraveState)  || cardGameState.isState(State.effektSelectOponentGraveState) || cardGameState.isState(State.selectOptionCardListState)) {
                if (cg.selectedIdx > 0) {
                    cg.selectedIdx--;
                } else {
                    if (cardGameState.isState(State.boardState)) {
                        cg.switchState(State.handCardState);
                    }
                }
            } else if (cardGameState.isState(State.spellGraveState)) {
                cg.switchState(State.graveState);
            } else if (cardGameState.isState(State.spellGraveOponentState)) {
                cg.switchState(State.graveOponentState);
            } else if (cardGameState.isState(State.graveState)) {
                cg.switchState(State.boardState);
                if (player.boardCards.size() > 0) {
                    cg.selectedIdx = player.boardCards.size() - 1;
                }
            } else if (cardGameState.isState(State.graveOponentState)) {
                cg.switchState(State.boardOponentState);
            } else if (cardGameState.isState(State.onAufgbenState)) {
                cg.switchState(State.handCardState);
            }
        }

        else if (keyH.downPressed) {
            if (cardGameState.isState(State.handCardSelectedState) || cardGameState.isState(State.askAufgebenState)) {
                if (cg.selectedIdx < 1) {
                    cg.selectedIdx++;
                }
            } else if (cardGameState.isState(State.boardState) || cardGameState.isState(State.graveState) || cardGameState.isState(State.spellGraveState)) {
                cg.switchState(State.handCardState);
            } else if (cardGameState.isState(State.boardOponentState)) {
                cg.switchState(State.boardState);
            } else if (cardGameState.isState(State.graveOponentState)) {
                cg.switchState(State.graveState);
            } else if (cardGameState.isState(State.spellGraveOponentState)) {
                cg.switchState(State.spellGraveState);
            } else if (cardGameState.isState(State.selectOptionState)) {
                if (cg.selectedIdx < cg.optionsToSelect.size() - 1) {
                    cg.selectedIdx++;
                }
            }
        }
        else if (keyH.upPressed) {
            if  (cardGameState.isState(State.handCardState)) {
                cg.switchState(State.boardState);
            }              
            else if (cardGameState.isState(State.selectOptionState) || cardGameState.isState(State.handCardSelectedState) || cardGameState.isState(State.askAufgebenState)) {
                if (cg.selectedIdx > 0) {
                    cg.selectedIdx--;
                }
            }
            else if (cardGameState.isState(State.spellGraveState)) {
                cg.switchState(State.spellGraveOponentState);
            }
            else if (cardGameState.isState(State.boardState)) {
                cg.switchState(State.boardOponentState);
            }
            else if (cardGameState.isState(State.graveState)) {
                cg.switchState(State.graveOponentState);
            }
            else if (cardGameState.isState(State.onAufgbenState)) {
                cg.switchState(State.graveState);
            }
        } 

        else if (keyH.fPressed) {
            if (cardGameState.isState(State.gameFinishedState)) {
                cg.connection.closeGame();
                cg.gp.switchState(cg.gp.mainMenuState);
            } else if (!player.inactiveMode) {
                if (cardGameState.isState(State.handCardState)) {
                    if (player.handCards.size() > 0) {
                        if (player.handCards.get(cg.selectedIdx).defaultCard.isSpell()) {
                            if (player.isPlaySpellAllowed(oponent, player.handCards.get(cg.selectedIdx))) {
                                cg.selectedHandCardIdx = cg.selectedIdx;
                                cg.switchState(State.effektQuestionStateHand);
                            }
                        } else {
                            if (player.isPlayCreatureAllowed(player.handCards.get(cg.selectedIdx))) {
                                cg.selectedHandCardIdx = cg.selectedIdx;
                                cg.switchState(State.handCardSelectedState);
                            } 
                        }
                    }
                }
                else if (cardGameState.isState(State.handCardSelectedState)) {
                    if (!player.handCards.get(cg.selectedHandCardIdx).defaultCard.isSpell()) {
                        if (cg.selectedIdx == 0) {
                            cg.karteVonHandAufBoard(player, player.handCards.get(cg.selectedHandCardIdx).id, false, false, true);
                        } else if (cg.selectedIdx == 1) {
                            cg.karteVonHandAufBoard(player, player.handCards.get(cg.selectedHandCardIdx).id, true, false, true);
                        }
                        if (cardGameState.isState(State.handCardSelectedState)) {
                            cg.switchState(State.boardState);
                        }
                    }
                }
                else if (cardGameState.isState(State.boardState)) {
                    if (player.boardCards.size() > 0) {
                        cg.selectedBoardCardIdx = cg.selectedIdx;
                        CardState card = player.boardCards.get(cg.selectedBoardCardIdx);
                        if ((card.isHide && !player.boardCards.get(cg.selectedBoardCardIdx).wasPlayedInTurn) || player.isAttackAlowed(card)) {
                            cg.switchState(State.boardCardSelectedState);
                        }
                    }					
                }
                else if (cardGameState.isState(State.boardCardSelectedState)) {
                    if (player.boardCards.get(cg.selectedBoardCardIdx).isHide) {
                        cg.karteDrehen(player.boardCards.get(cg.selectedBoardCardIdx).id, false, true);
                        cg.switchState(State.boardState);
                    } else if (oponent.boardCards.size() == 0 || player.boardCards.get(cg.selectedBoardCardIdx).statusSet.contains(Status.Fluegel)) {
                        cg.setUpDirekterAngriff(player, cg.selectedBoardCardIdx, true);
                    } else  {
                        cg.switchState(State.selectCardToAttackState);
                    } 
                }

                else if (cardGameState.isState(State.effektQuestionStateBoard)) {
                    cg.manualEffekt(player.boardCards.get(cg.selectedBoardCardIdx).id,  true);
                } else if (cardGameState.isState(State.effektQuestionStateHand)) {
                    cg.manualEffekt(player.handCards.get(cg.selectedHandCardIdx).id, true);
                } else if (cardGameState.isState(State.effektQuestionStateGrave)) {
                    cg.manualEffekt(player.graveCards.get(cg.selectGraveCardIdx).id, true);
                } 
                
                else if (cardGameState.isState(State.selectCardToAttackState)) {
                    CardState angreifer = player.boardCards.get(cg.selectedBoardCardIdx);
                    CardState verteidiger = oponent.boardCards.get(cg.selectedIdx);
                    cg.attackPhaseOne(player, angreifer.id, verteidiger.id, true);
                } 

                else if (cardGameState.isState(State.effektSelectOponentBoardState)) {
                    if (cg.activeEffektCard.isCardValidForSelection(oponent.boardCards.get(cg.selectedIdx))) {
                        cg.selectTargetCard(oponent, oponent.boardCards.get(cg.selectedIdx).id, true);
                        cg.handleEffekt(cg.activeEffektCard.id, oponent.boardCards.get(cg.selectedIdx).id, true);
                    }
                } else if (cardGameState.isState(State.effektSelectOwnBoardState)) {
                    if (cg.activeEffektCard.isCardValidForSelection(player.boardCards.get(cg.selectedIdx))) {
                        cg.selectTargetCard(player, player.boardCards.get(cg.selectedIdx).id, true);
                        cg.handleEffekt(cg.activeEffektCard.id, player.boardCards.get(cg.selectedIdx).id, true);
                    }
                } else if (cardGameState.isState(State.effektSelectOwnGraveState)) {
                    if (cg.activeEffektCard.isCardValidForSelection(player.graveCards.get(cg.selectedIdx))) {
                        cg.selectTargetCard(player, player.graveCards.get(cg.selectedIdx).id, true);
                        cg.handleEffekt(cg.activeEffektCard.id, player.graveCards.get(cg.selectedIdx).id, true);
                    }				
                } else if (cardGameState.isState(State.effektSelectOponentGraveState)) {
                    if (cg.activeEffektCard.isCardValidForSelection(oponent.graveCards.get(cg.selectedIdx))) {
                        cg.selectTargetCard(oponent, oponent.graveCards.get(cg.selectedIdx).id, true);
                        cg.handleEffekt(cg.activeEffektCard.id, oponent.graveCards.get(cg.selectedIdx).id, true);
                    }
                } else if (cardGameState.isState(State.selectOptionState)) {
                    String value = cg.optionsToSelect.values().toArray(new String[0])[cg.selectedIdx];
                    cg.selectOptionFromList(value, true);
                    cg.handleEffekt(cg.activeEffektCard.id, cg.selectedIdx, true);
                }  else if (cardGameState.isState(State.selectOptionCardListState)) {
                    cg.selectTargetCard(oponent, cg.optionsCardsToSelect.get(cg.selectedIdx).id, true);
                    cg.handleEffekt(cg.activeEffektCard.id, cg.optionsCardsToSelect.get(cg.selectedIdx).id, true);
                } else if (cardGameState.isState(State.graveState)) {
                    if (player.graveCards.size() > 0) {
                        cg.switchState(State.graveSelectedState);
                    }
                } else if (cardGameState.isState(State.graveOponentState)) {
                    if (oponent.graveCards.size() > 0) {
                        cg.switchState(State.graveSelectedOponentState);
                    }
                } else if (cardGameState.isState(State.onAufgbenState)) {
                    cg.switchState(State.askAufgebenState);
                } else if (cardGameState.isState(State.askAufgebenState)) {
                    if (cg.selectedIdx == 0) {
                        cg.spielerPunkteAendern(player, -player.lifeCounter, PunkteArt.Leben, true);
                    } else if (cg.selectedIdx == 1) {
                        cg.switchState(State.onAufgbenState);
                    }
                }
            } 
        } 
        else if (keyH.gPressed && !player.inactiveMode) {
            if (cardGameState.isState(State.boardState)) {
                if (player.boardCards.size() > 0) {
                    if (cg.isEffektManualActivatable(player, player.boardCards.get(cg.selectedIdx), Trigger.triggerManualFromBoard)) {
                        cg.selectedBoardCardIdx = cg.selectedIdx;
                        cg.switchState(State.effektQuestionStateBoard);
                    }
                }
            } else if (cardGameState.isState(State.handCardState)) {
                if (player.handCards.size() > 0) {
                    if (cg.isEffektManualActivatable(player, player.handCards.get(cg.selectedIdx), Trigger.triggerManualFromHand)) {
                        cg.selectedHandCardIdx = cg.selectedIdx;
                        cg.switchState(State.effektQuestionStateHand);
                    }	
                }	
            } else if (cardGameState.isState(State.graveSelectedState)) {
                if (cg.isEffektManualActivatable(player, player.graveCards.get(cg.selectedIdx), Trigger.triggerManualFromGrave)) {
                    cg.selectGraveCardIdx = cg.selectedIdx;
                    cg.switchState(State.effektQuestionStateGrave);
                }		
            } 
        }
        cg.gp.playSE(1);
    }
    
}
