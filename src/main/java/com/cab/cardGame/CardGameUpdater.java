package com.cab.cardGame;

import com.cab.KeyHandler;
import com.cab.card.Status;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.PunkteArt;

public class CardGameUpdater {
    CardGame cg;
    KeyHandler keyH;

    
    public CardGameUpdater(CardGame cg, KeyHandler keyH) {
        this.cg = cg;
        this.keyH = keyH;
    }

    public void update() {
        if (keyH.rightPressed || keyH.leftPressed || keyH.downPressed || keyH.upPressed || keyH.fPressed  || keyH.qPressed || keyH.gPressed || keyH.enterPressed) {
            if (!cg.gp.keyH.blockBtn) {
                cg.gp.keyH.blockBtn = true;					
                if (cg.gp.keyH.qPressed && !cg.inactiveMode) {
                    if (cg.cardGameState.isState(State.handCardState) || cg.cardGameState.isState(State.boardState) || cg.cardGameState.isState(State.boardOponentState) || cg.cardGameState.isState(State.graveState) || cg.cardGameState.isState(State.graveOponentState)) {
                        cg.endTurn();
                    }
                    else if (cg.cardGameState.isState(State.handCardSelectedState) || cg.cardGameState.isState(State.effektQuestionStateHand)) {
                        cg.switchState(State.handCardState);
                        cg.selectedIdx = cg.selectedHandCardIdx;
                    }
                    else if (cg.cardGameState.isState(State.boardCardSelectedState) || cg.cardGameState.isState(State.effektQuestionStateBoard) || cg.cardGameState.isState(State.selectCardToAttackState)) {
                        cg.switchState(State.boardState);
                        cg.selectedIdx = cg.selectedBoardCardIdx;
                    }
                    else if (cg.cardGameState.isState(State.graveSelectedOponentState)) {
                        cg.switchState(State.graveOponentState);
                    }
                    else if (cg.cardGameState.isState(State.graveSelectedState)) {
                        cg.switchState(State.graveState);
                    } else if (cg.cardGameState.isState(State.effektQuestionStateGrave)) {
                        cg.switchState(State.graveSelectedState);
                    } else if (cg.cardGameState.isState(State.askAufgebenState)) {
                        cg.switchState(State.onAufgbenState);
                    }
                }
                else if(keyH.enterPressed) {
                    if (!cg.isOnline) {} //TODO DEBUG
                }
                else if (keyH.rightPressed) {
                    if (cg.cardGameState.isState(State.graveState) || cg.cardGameState.isState(State.graveOponentState) || 
                    cg.cardGameState.isState(State.handCardState) || cg.cardGameState.isState(State.boardState) || 
                    cg.cardGameState.isState(State.boardOponentState) || cg.cardGameState.isState(State.selectCardToAttackState) || 
                    cg.cardGameState.isState(State.effektSelectOponentBoardState) || cg.cardGameState.isState(State.effektSelectOwnBoardState) || 
                    cg.cardGameState.isState(State.graveSelectedOponentState) || cg.cardGameState.isState(State.graveSelectedState) || 
                    cg.cardGameState.isState(State.effektSelectOwnGraveState)  || cg.cardGameState.isState(State.effektSelectOponentGraveState) ||
                    cg.cardGameState.isState(State.selectOptionCardListState)) {
                        
                        
                        if (cg.cardGameState.isState(State.boardOponentState)) {
                            if (cg.oponent.boardCards.size() == 0 || cg.selectedIdx == cg.oponent.boardCards.size() - 1) {
                                cg.switchState(State.graveOponentState);
                            }
                        }else if (cg.cardGameState.isState(State.boardState)) {
                            if (cg.player.boardCards.size() == 0 || cg.selectedIdx == cg.player.boardCards.size() - 1) {
                                cg.switchState(State.graveState);
                            }
                        } else if (cg.cardGameState.isState(State.graveState)) {
                            cg.switchState(State.spellGraveState);
                        } else if (cg.cardGameState.isState(State.graveOponentState)) {
                            cg.switchState(State.spellGraveOponentState);
                        } else if (cg.cardGameState.isState(State.handCardState)) {
                            if (cg.player.handCards.size() == 0 || cg.selectedIdx == cg.player.handCards.size() - 1) {
                                cg.switchState(State.onAufgbenState);
                            }
                        }

                        int size = 0;

                        if (cg.cardGameState.isState(State.handCardState)) {
                            size = cg.player.handCards.size() - 1;
                        } else if (cg.cardGameState.isState(State.boardState) || cg.cardGameState.isState(State.effektSelectOwnBoardState)) {
                            size = cg.player.boardCards.size() - 1;
                        } else if (cg.cardGameState.isState(State.boardOponentState) || cg.cardGameState.isState(State.selectCardToAttackState) || cg.cardGameState.isState(State.effektSelectOponentBoardState)) {
                            size = cg.oponent.boardCards.size() - 1;
                        } else if (cg.cardGameState.isState(State.graveSelectedState) || cg.cardGameState.isState(State.effektSelectOwnGraveState)) {
                            size = cg.player.graveCards.size() - 1;
                        } else if (cg.cardGameState.isState(State.graveSelectedOponentState) || cg.cardGameState.isState(State.effektSelectOponentGraveState)) {
                            size = cg.oponent.graveCards.size() - 1; 
                        }  else if (cg.cardGameState.isState(State.selectOptionCardListState)) {
                            size = cg.optionsCardsToSelect.size() - 1;
                        }

                        if (cg.selectedIdx < size) {
                            cg.selectedIdx++;
                        }
                    } 
                }
                
                else if (keyH.leftPressed) {
                    if (cg.cardGameState.isState(State.handCardState) || cg.cardGameState.isState(State.boardState) || 
                    cg.cardGameState.isState(State.boardOponentState) || cg.cardGameState.isState(State.selectCardToAttackState) || 
                    cg.cardGameState.isState(State.effektSelectOponentBoardState) || cg.cardGameState.isState(State.effektSelectOwnBoardState) || 
                    cg.cardGameState.isState(State.graveSelectedOponentState) || cg.cardGameState.isState(State.graveSelectedState) || 
                    cg.cardGameState.isState(State.effektSelectOwnGraveState)  || cg.cardGameState.isState(State.effektSelectOponentGraveState) || cg.cardGameState.isState(State.selectOptionCardListState)) {
                        if (cg.selectedIdx > 0) {
                            cg.selectedIdx--;
                        } else {
                            if (cg.cardGameState.isState(State.boardState)) {
                                cg.switchState(State.handCardState);
                            }
                        }
                    } else if (cg.cardGameState.isState(State.spellGraveState)) {
                        cg.switchState(State.graveState);
                    } else if (cg.cardGameState.isState(State.spellGraveOponentState)) {
                        cg.switchState(State.graveOponentState);
                    } else if (cg.cardGameState.isState(State.graveState)) {
                        cg.switchState(State.boardState);
                        if (cg.player.boardCards.size() > 0) {
                            cg.selectedIdx = cg.player.boardCards.size() - 1;
                        }
                    } else if (cg.cardGameState.isState(State.graveOponentState)) {
                        cg.switchState(State.boardOponentState);
                    } else if (cg.cardGameState.isState(State.onAufgbenState)) {
                        cg.switchState(State.handCardState);
                    }
                }

                else if (keyH.downPressed) {
                    if (cg.cardGameState.isState(State.handCardSelectedState) || cg.cardGameState.isState(State.askAufgebenState)) {
                        if (cg.selectedIdx < 1) {
                            cg.selectedIdx++;
                        }
                    } else if (cg.cardGameState.isState(State.boardState) || cg.cardGameState.isState(State.graveState) || cg.cardGameState.isState(State.spellGraveState)) {
                        cg.switchState(State.handCardState);
                    } else if (cg.cardGameState.isState(State.boardOponentState)) {
                        cg.switchState(State.boardState);
                    } else if (cg.cardGameState.isState(State.graveOponentState)) {
                        cg.switchState(State.graveState);
                    } else if (cg.cardGameState.isState(State.spellGraveOponentState)) {
                        cg.switchState(State.spellGraveState);
                    } else if (cg.cardGameState.isState(State.selectOptionState)) {
                        if (cg.selectedIdx < cg.optionsToSelect.size() - 1) {
                            cg.selectedIdx++;
                        }
                    }
                }
                else if (keyH.upPressed) {
                    if  (cg.cardGameState.isState(State.handCardState)) {
                        cg.switchState(State.boardState);
                    }              
                    else if (cg.cardGameState.isState(State.selectOptionState) || cg.cardGameState.isState(State.handCardSelectedState) || cg.cardGameState.isState(State.askAufgebenState)) {
                        if (cg.selectedIdx > 0) {
                            cg.selectedIdx--;
                        }
                    }
                    else if (cg.cardGameState.isState(State.spellGraveState)) {
                        cg.switchState(State.spellGraveOponentState);
                    }
                    else if (cg.cardGameState.isState(State.boardState)) {
                        cg.switchState(State.boardOponentState);
                    }
                    else if (cg.cardGameState.isState(State.graveState)) {
                        cg.switchState(State.graveOponentState);
                    }
                    else if (cg.cardGameState.isState(State.onAufgbenState)) {
                        cg.switchState(State.graveState);
                    }
                } 

                else if (keyH.fPressed) {
                    if (cg.cardGameState.isState(State.gameFinishedState)) {
                        cg.gp.connection.closeGame();
                        cg.gp.gameState = cg.gp.mainMenuState;
                        if (cg.player.lifeCounter == 0) {
                            cg.gp.mainMenu.currentState = cg.gp.mainMenu.looseState;
                            cg.gp.player.punkte = cg.gp.player.punkte + 10;
                        } else {
                            cg.gp.mainMenu.currentState = cg.gp.mainMenu.winState;
                            cg.gp.player.punkte = cg.gp.player.punkte + 50;
                            cg.gp.save();
                        }
                    } else if (!cg.inactiveMode) {
                        if (cg.cardGameState.isState(State.handCardState)) {
                            if (cg.player.handCards.size() > 0) {
                                if (cg.player.handCards.get(cg.selectedIdx).defaultCard.isSpell()) {
                                    if (cg.isPlaySpellAllowed(cg.player, cg.player.handCards.get(cg.selectedIdx))) {
                                        cg.selectedHandCardIdx = cg.selectedIdx;
                                        cg.switchState(State.effektQuestionStateHand);
                                    }
                                } else {
                                    if (cg.isPlayCreatureAllowed(cg.player, cg.player.handCards.get(cg.selectedIdx))) {
                                        cg.selectedHandCardIdx = cg.selectedIdx;
                                        cg.switchState(State.handCardSelectedState);
                                    } 
                                }
                            }
                        }
                        else if (cg.cardGameState.isState(State.handCardSelectedState)) {
                            if (!cg.player.handCards.get(cg.selectedHandCardIdx).defaultCard.isSpell()) {
                                if (cg.selectedIdx == 0) {
                                    cg.karteVonHandAufBoard(cg.player, cg.player.handCards.get(cg.selectedHandCardIdx).id, false, false, true);
                                } else if (cg.selectedIdx == 1) {
                                    cg.karteVonHandAufBoard(cg.player, cg.player.handCards.get(cg.selectedHandCardIdx).id, true, false, true);
                                }
                                if (cg.cardGameState.isState(State.handCardSelectedState)) {
                                    cg.switchState(State.boardState);
                                }
                            }
                        }
                        else if (cg.cardGameState.isState(State.boardState)) {
                            if (cg.player.boardCards.size() > 0) {
                                cg.selectedBoardCardIdx = cg.selectedIdx;
                                if ((cg.player.boardCards.get(cg.selectedBoardCardIdx).isHide && !cg.player.boardCards.get(cg.selectedBoardCardIdx).wasPlayedInTurn) || cg.isAttackAlowed(cg.player, cg.selectedBoardCardIdx)) {
                                    cg.switchState(State.boardCardSelectedState);
                                }
                            }					
                        }
                        else if (cg.cardGameState.isState(State.boardCardSelectedState)) {
                            if (cg.player.boardCards.get(cg.selectedBoardCardIdx).isHide) {
                                cg.karteDrehen(cg.player.boardCards.get(cg.selectedBoardCardIdx).id, false, true);
                                cg.switchState(State.boardState);
                            } else if (cg.oponent.boardCards.size() == 0 || cg.player.boardCards.get(cg.selectedBoardCardIdx).statusSet.contains(Status.Fluegel)) {
                                cg.setUpDirekterAngriff(cg.player, cg.selectedBoardCardIdx, true);
                            } else  {
                                cg.switchState(State.selectCardToAttackState);
                            } 
                        }
    
                        else if (cg.cardGameState.isState(State.effektQuestionStateBoard)) {
                            cg.manualEffekt(cg.player.boardCards.get(cg.selectedBoardCardIdx).id,  true);
                        } else if (cg.cardGameState.isState(State.effektQuestionStateHand)) {
                            cg.manualEffekt(cg.player.handCards.get(cg.selectedHandCardIdx).id, true);
                        } else if (cg.cardGameState.isState(State.effektQuestionStateGrave)) {
                            cg.manualEffekt(cg.player.graveCards.get(cg.selectGraveCardIdx).id, true);
                        } 
                        
                        else if (cg.cardGameState.isState(State.selectCardToAttackState)) {
                            CardState angreifer = cg.player.boardCards.get(cg.selectedBoardCardIdx);
                            CardState verteidiger = cg.oponent.boardCards.get(cg.selectedIdx);
                            cg.attackPhaseOne(cg.player, angreifer.id, verteidiger.id, true);
                        } 
    
                        else if (cg.cardGameState.isState(State.effektSelectOponentBoardState)) {
                            if (cg.activeEffektCard.isCardValidForSelection(cg.oponent.boardCards.get(cg.selectedIdx))) {
                                cg.selectTargetCard(cg.oponent, cg.oponent.boardCards.get(cg.selectedIdx).id, true);
                                cg.handleEffekt(cg.activeEffektCard.id, cg.oponent.boardCards.get(cg.selectedIdx).id, true);
                            }
                        } else if (cg.cardGameState.isState(State.effektSelectOwnBoardState)) {
                            if (cg.activeEffektCard.isCardValidForSelection(cg.player.boardCards.get(cg.selectedIdx))) {
                                cg.selectTargetCard(cg.player, cg.player.boardCards.get(cg.selectedIdx).id, true);
                                cg.handleEffekt(cg.activeEffektCard.id, cg.player.boardCards.get(cg.selectedIdx).id, true);
                            }
                        } else if (cg.cardGameState.isState(State.effektSelectOwnGraveState)) {
                            if (cg.activeEffektCard.isCardValidForSelection(cg.player.graveCards.get(cg.selectedIdx))) {
                                cg.selectTargetCard(cg.player, cg.player.graveCards.get(cg.selectedIdx).id, true);
                                cg.handleEffekt(cg.activeEffektCard.id, cg.player.graveCards.get(cg.selectedIdx).id, true);
                            }				
                        } else if (cg.cardGameState.isState(State.effektSelectOponentGraveState)) {
                            if (cg.activeEffektCard.isCardValidForSelection(cg.oponent.graveCards.get(cg.selectedIdx))) {
                                cg.selectTargetCard(cg.oponent, cg.oponent.graveCards.get(cg.selectedIdx).id, true);
                                cg.handleEffekt(cg.activeEffektCard.id, cg.oponent.graveCards.get(cg.selectedIdx).id, true);
                            }
                        } else if (cg.cardGameState.isState(State.selectOptionState)) {
                            String value = cg.optionsToSelect.values().toArray(new String[0])[cg.selectedIdx];
                            cg.selectOptionFromList(value, true);
                            cg.handleEffekt(cg.activeEffektCard.id, cg.selectedIdx, true);
                        }  else if (cg.cardGameState.isState(State.selectOptionCardListState)) {
                            cg.selectTargetCard(cg.oponent, cg.optionsCardsToSelect.get(cg.selectedIdx).id, true);
                            cg.handleEffekt(cg.activeEffektCard.id, cg.optionsCardsToSelect.get(cg.selectedIdx).id, true);
                        } else if (cg.cardGameState.isState(State.graveState)) {
                            if (cg.player.graveCards.size() > 0) {
                                cg.switchState(State.graveSelectedState);
                            }
                        } else if (cg.cardGameState.isState(State.graveOponentState)) {
                            if (cg.oponent.graveCards.size() > 0) {
                                cg.switchState(State.graveSelectedOponentState);
                            }
                        } else if (cg.cardGameState.isState(State.onAufgbenState)) {
                            cg.switchState(State.askAufgebenState);
                        } else if (cg.cardGameState.isState(State.askAufgebenState)) {
                            if (cg.selectedIdx == 0) {
                                cg.spielerPunkteAendern(cg.player, -cg.player.lifeCounter, PunkteArt.Leben, true);
                            } else if (cg.selectedIdx == 1) {
                                cg.switchState(State.onAufgbenState);
                            }
                        }
                    } 
                } 
                else if (keyH.gPressed && !cg.inactiveMode) {
                    if (cg.cardGameState.isState(State.boardState)) {
                        if (cg.player.boardCards.size() > 0) {
                            if (cg.isEffektManualActivatable(cg.player, cg.player.boardCards.get(cg.selectedIdx), Trigger.triggerManualFromBoard)) {
                                cg.selectedBoardCardIdx = cg.selectedIdx;
                                cg.switchState(State.effektQuestionStateBoard);
                            }
                        }
                    } else if (cg.cardGameState.isState(State.handCardState)) {
                        if (cg.player.handCards.size() > 0) {
                            if (cg.isEffektManualActivatable(cg.player, cg.player.handCards.get(cg.selectedIdx), Trigger.triggerManualFromHand)) {
                                cg.selectedHandCardIdx = cg.selectedIdx;
                                cg.switchState(State.effektQuestionStateHand);
                            }	
                        }	
                    } else if (cg.cardGameState.isState(State.graveSelectedState)) {
                        if (cg.isEffektManualActivatable(cg.player, cg.player.graveCards.get(cg.selectedIdx), Trigger.triggerManualFromGrave)) {
                            cg.selectGraveCardIdx = cg.selectedIdx;
                            cg.switchState(State.effektQuestionStateGrave);
                        }		
                    } 
                }
                cg.gp.playSE(1);
            }
        } 
    }
    
}
