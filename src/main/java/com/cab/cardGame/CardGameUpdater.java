package com.cab.cardGame;

import com.cab.KeyHandler;
import com.cab.card.Status;

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
                    if (cg.isState(cg.handCardState) || cg.isState(cg.boardState) || cg.isState(cg.boardOponentState) || cg.isState(cg.graveState) || cg.isState(cg.graveOponentState)) {
                        cg.endTurn();
                    }
                    else if (cg.isState(cg.handCardSelectedState) || cg.isState(cg.effektQuestionStateHand)) {
                        cg.switchState(cg.handCardState);
                        cg.selectedIdx = cg.selectedHandCardIdx;
                    }
                    else if (cg.isState(cg.boardCardSelectedState) || cg.isState(cg.effektQuestionStateBoard) || cg.isState(cg.selectCardToAttackState)) {
                        cg.switchState(cg.boardState);
                        cg.selectedIdx = cg.selectedBoardCardIdx;
                    }
                    else if (cg.isState(cg.graveSelectedOponentState)) {
                        cg.switchState(cg.graveOponentState);
                    }
                    else if (cg.isState(cg.graveSelectedState)) {
                        cg.switchState(cg.graveState);
                    } else if (cg.isState(cg.effektQuestionStateGrave)) {
                        cg.switchState(cg.graveSelectedState);
                    } else if (cg.isState(cg.askAufgebenState)) {
                        cg.switchState(cg.onAufgbenState);
                    }
                }
                else if(keyH.enterPressed) {
                    if (!cg.isOnline) {} //TODO DEBUG
                }
                else if (keyH.rightPressed) {
                    if (cg.isState(cg.graveState) || cg.isState(cg.graveOponentState) || 
                    cg.isState(cg.handCardState) || cg.isState(cg.boardState) || 
                    cg.isState(cg.boardOponentState) || cg.isState(cg.selectCardToAttackState) || 
                    cg.isState(cg.effektSelectOponentBoardState) || cg.isState(cg.effektSelectOwnBoardState) || 
                    cg.isState(cg.graveSelectedOponentState) || cg.isState(cg.graveSelectedState) || 
                    cg.isState(cg.effektSelectOwnGraveState)  || cg.isState(cg.effektSelectOponentGraveState) ||
                    cg.isState(cg.selectOptionCardListState)) {
                        
                        
                        if (cg.isState(cg.boardOponentState)) {
                            if (cg.oponent.boardCards.size() == 0 || cg.selectedIdx == cg.oponent.boardCards.size() - 1) {
                                cg.switchState(cg.graveOponentState);
                            }
                        }else if (cg.isState(cg.boardState)) {
                            if (cg.player.boardCards.size() == 0 || cg.selectedIdx == cg.player.boardCards.size() - 1) {
                                cg.switchState(cg.graveState);
                            }
                        } else if (cg.isState(cg.graveState)) {
                            cg.switchState(cg.spellGraveState);
                        } else if (cg.isState(cg.graveOponentState)) {
                            cg.switchState(cg.spellGraveOponentState);
                        } else if (cg.isState(cg.handCardState)) {
                            if (cg.player.handCards.size() == 0 || cg.selectedIdx == cg.player.handCards.size() - 1) {
                                cg.switchState(cg.onAufgbenState);
                            }
                        }

                        int size = 0;

                        if (cg.isState(cg.handCardState)) {
                            size = cg.player.handCards.size() - 1;
                        } else if (cg.isState(cg.boardState) || cg.isState(cg.effektSelectOwnBoardState)) {
                            size = cg.player.boardCards.size() - 1;
                        } else if (cg.isState(cg.boardOponentState) || cg.isState(cg.selectCardToAttackState) || cg.isState(cg.effektSelectOponentBoardState)) {
                            size = cg.oponent.boardCards.size() - 1;
                        } else if (cg.isState(cg.graveSelectedState) || cg.isState(cg.effektSelectOwnGraveState)) {
                            size = cg.player.graveCards.size() - 1;
                        } else if (cg.isState(cg.graveSelectedOponentState) || cg.isState(cg.effektSelectOponentGraveState)) {
                            size = cg.oponent.graveCards.size() - 1; 
                        }  else if (cg.isState(cg.selectOptionCardListState)) {
                            size = cg.optionsCardsToSelect.size() - 1;
                        }

                        if (cg.selectedIdx < size) {
                            cg.selectedIdx++;
                        }
                    } 
                }
                
                else if (keyH.leftPressed) {
                    if (cg.isState(cg.handCardState) || cg.isState(cg.boardState) || 
                    cg.isState(cg.boardOponentState) || cg.isState(cg.selectCardToAttackState) || 
                    cg.isState(cg.effektSelectOponentBoardState) || cg.isState(cg.effektSelectOwnBoardState) || 
                    cg.isState(cg.graveSelectedOponentState) || cg.isState(cg.graveSelectedState) || 
                    cg.isState(cg.effektSelectOwnGraveState)  || cg.isState(cg.effektSelectOponentGraveState) || cg.isState(cg.selectOptionCardListState)) {
                        if (cg.selectedIdx > 0) {
                            cg.selectedIdx--;
                        } else {
                            if (cg.isState(cg.boardState)) {
                                cg.switchState(cg.handCardState);
                            }
                        }
                    } else if (cg.isState(cg.spellGraveState)) {
                        cg.switchState(cg.graveState);
                    } else if (cg.isState(cg.spellGraveOponentState)) {
                        cg.switchState(cg.graveOponentState);
                    } else if (cg.isState(cg.graveState)) {
                        cg.switchState(cg.boardState);
                        if (cg.player.boardCards.size() > 0) {
                            cg.selectedIdx = cg.player.boardCards.size() - 1;
                        }
                    } else if (cg.isState(cg.graveOponentState)) {
                        cg.switchState(cg.boardOponentState);
                    } else if (cg.isState(cg.onAufgbenState)) {
                        cg.switchState(cg.handCardState);
                    }
                }

                else if (keyH.downPressed) {
                    if (cg.isState(cg.handCardSelectedState) || cg.isState(cg.askAufgebenState)) {
                        if (cg.selectedIdx < 1) {
                            cg.selectedIdx++;
                        }
                    } else if (cg.isState(cg.boardState) || cg.isState(cg.graveState) || cg.isState(cg.spellGraveState)) {
                        cg.switchState(cg.handCardState);
                    } else if (cg.isState(cg.boardOponentState)) {
                        cg.switchState(cg.boardState);
                    } else if (cg.isState(cg.graveOponentState)) {
                        cg.switchState(cg.graveState);
                    } else if (cg.isState(cg.spellGraveOponentState)) {
                        cg.switchState(cg.spellGraveState);
                    } else if (cg.isState(cg.selectOptionState)) {
                        if (cg.selectedIdx < cg.optionsToSelect.size() - 1) {
                            cg.selectedIdx++;
                        }
                    }
                }
                else if (keyH.upPressed) {
                    if  (cg.isState(cg.handCardState)) {
                        cg.switchState(cg.boardState);
                    }              
                    else if (cg.isState(cg.selectOptionState) || cg.isState(cg.handCardSelectedState) || cg.isState(cg.askAufgebenState)) {
                        if (cg.selectedIdx > 0) {
                            cg.selectedIdx--;
                        }
                    }
                    else if (cg.isState(cg.spellGraveState)) {
                        cg.switchState(cg.spellGraveOponentState);
                    }
                    else if (cg.isState(cg.boardState)) {
                        cg.switchState(cg.boardOponentState);
                    }
                    else if (cg.isState(cg.graveState)) {
                        cg.switchState(cg.graveOponentState);
                    }
                    else if (cg.isState(cg.onAufgbenState)) {
                        cg.switchState(cg.graveState);
                    }
                } 

                else if (keyH.fPressed) {
                    if (cg.isState(cg.gameFinishedState)) {
                        cg.gp.connection.closeGame();
                        cg.gp.gameState = cg.gp.mainMenuState;
                        if (cg.player.lifeCounter == 0) {
                            cg.gp.mainMenu.currentState = cg.gp.mainMenu.looseState;
                            cg.gp.player.punkte = cg.gp.player.punkte + 10;
                        } else {
                            cg.gp.mainMenu.currentState = cg.gp.mainMenu.winState;
                            cg.gp.player.punkte = cg.gp.player.punkte + 50;
                            if (cg.gp.mainMenu.getBonus) {
                                cg.gp.player.punkte = cg.gp.player.punkte + 20;
                            }
                            cg.gp.save();
                        }
                    } else if (!cg.inactiveMode) {
                        if (cg.isState(cg.handCardState)) {
                            if (cg.player.handCards.size() > 0) {
                                if (cg.player.handCards.get(cg.selectedIdx).defaultCard.isSpell()) {
                                    if (cg.isPlaySpellAllowed(cg.player, cg.player.handCards.get(cg.selectedIdx))) {
                                        cg.selectedHandCardIdx = cg.selectedIdx;
                                        cg.switchState(cg.effektQuestionStateHand);
                                    }
                                } else {
                                    if (cg.isPlayCreatureAllowed(cg.player, cg.player.handCards.get(cg.selectedIdx))) {
                                        cg.selectedHandCardIdx = cg.selectedIdx;
                                        cg.switchState(cg.handCardSelectedState);
                                    } 
                                }
                            }
                        }
                        else if (cg.isState(cg.handCardSelectedState)) {
                            if (!cg.player.handCards.get(cg.selectedHandCardIdx).defaultCard.isSpell()) {
                                if (cg.selectedIdx == 0) {
                                    cg.kreaturAufrufen(cg.player, cg.player.handCards.get(cg.selectedHandCardIdx).id, false, false, true);
                                } else if (cg.selectedIdx == 1) {
                                    cg.kreaturAufrufen(cg.player, cg.player.handCards.get(cg.selectedHandCardIdx).id, true, false, true);
                                }
                                if (cg.isState(cg.handCardSelectedState)) {
                                    cg.switchState(cg.boardState);
                                }
                            }
                        }
                        else if (cg.isState(cg.boardState)) {
                            if (cg.player.boardCards.size() > 0) {
                                cg.selectedBoardCardIdx = cg.selectedIdx;
                                if ((cg.player.boardCards.get(cg.selectedBoardCardIdx).isHide && !cg.player.boardCards.get(cg.selectedBoardCardIdx).wasPlayedInTurn) || cg.checkIsAttackAlowed(cg.player, cg.selectedBoardCardIdx)) {
                                    cg.switchState(cg.boardCardSelectedState);
                                }
                            }					
                        }
                        else if (cg.isState(cg.boardCardSelectedState)) {
                            if (cg.player.boardCards.get(cg.selectedBoardCardIdx).isHide) {
                                cg.karteDrehen(cg.player.boardCards.get(cg.selectedBoardCardIdx).id, false, true);
                                cg.switchState(cg.boardState);
                            } else if (cg.oponent.boardCards.size() == 0 || cg.player.boardCards.get(cg.selectedBoardCardIdx).statusSet.contains(Status.Fluegel)) {
                                cg.setUpDirectAttack(cg.player, cg.selectedBoardCardIdx, true);
                            } else  {
                                cg.switchState(cg.selectCardToAttackState);
                            } 
                        }
    
                        else if (cg.isState(cg.effektQuestionStateBoard)) {
                            cg.manualEffekt(cg.player.boardCards.get(cg.selectedBoardCardIdx).id,  true);
                        } else if (cg.isState(cg.effektQuestionStateHand)) {
                            cg.manualEffekt(cg.player.handCards.get(cg.selectedHandCardIdx).id, true);
                        } else if (cg.isState(cg.effektQuestionStateGrave)) {
                            cg.manualEffekt(cg.player.graveCards.get(cg.selectGraveCardIdx).id, true);
                        } 
                        
                        else if (cg.isState(cg.selectCardToAttackState)) {
                            CardState angreifer = cg.player.boardCards.get(cg.selectedBoardCardIdx);
                            CardState verteidiger = cg.oponent.boardCards.get(cg.selectedIdx);
                            cg.attackPhaseOne(cg.player, angreifer.id, verteidiger.id, true);
                        } 
    
                        else if (cg.isState(cg.effektSelectOponentBoardState)) {
                            if (cg.activeEffektCard.isCardValidForSelection(cg.oponent.boardCards.get(cg.selectedIdx))) {
                                cg.selectTargetCard(cg.oponent, cg.oponent.boardCards.get(cg.selectedIdx).id, true);
                                cg.handleEffekt(cg.activeEffektCard.id, cg.oponent.boardCards.get(cg.selectedIdx).id, true);
                            }
                        } else if (cg.isState(cg.effektSelectOwnBoardState)) {
                            if (cg.activeEffektCard.isCardValidForSelection(cg.player.boardCards.get(cg.selectedIdx))) {
                                cg.selectTargetCard(cg.player, cg.player.boardCards.get(cg.selectedIdx).id, true);
                                cg.handleEffekt(cg.activeEffektCard.id, cg.player.boardCards.get(cg.selectedIdx).id, true);
                            }
                        } else if (cg.isState(cg.effektSelectOwnGraveState)) {
                            if (cg.activeEffektCard.isCardValidForSelection(cg.player.graveCards.get(cg.selectedIdx))) {
                                cg.selectTargetCard(cg.player, cg.player.graveCards.get(cg.selectedIdx).id, true);
                                cg.handleEffekt(cg.activeEffektCard.id, cg.player.graveCards.get(cg.selectedIdx).id, true);
                            }				
                        } else if (cg.isState(cg.effektSelectOponentGraveState)) {
                            if (cg.activeEffektCard.isCardValidForSelection(cg.oponent.graveCards.get(cg.selectedIdx))) {
                                cg.selectTargetCard(cg.oponent, cg.oponent.graveCards.get(cg.selectedIdx).id, true);
                                cg.handleEffekt(cg.activeEffektCard.id, cg.oponent.graveCards.get(cg.selectedIdx).id, true);
                            }
                        } else if (cg.isState(cg.selectOptionState)) {
                            String value = cg.optionsToSelect.values().toArray(new String[0])[cg.selectedIdx];
                            cg.selectOptionFromList(value, true);
                            cg.handleEffekt(cg.activeEffektCard.id, cg.selectedIdx, true);
                        }  else if (cg.isState(cg.selectOptionCardListState)) {
                            cg.selectTargetCard(cg.oponent, cg.optionsCardsToSelect.get(cg.selectedIdx).id, true);
                            cg.handleEffekt(cg.activeEffektCard.id, cg.optionsCardsToSelect.get(cg.selectedIdx).id, true);
                        } else if (cg.isState(cg.graveState)) {
                            if (cg.player.graveCards.size() > 0) {
                                cg.switchState(cg.graveSelectedState);
                            }
                        } else if (cg.isState(cg.graveOponentState)) {
                            if (cg.oponent.graveCards.size() > 0) {
                                cg.switchState(cg.graveSelectedOponentState);
                            }
                        } else if (cg.isState(cg.onAufgbenState)) {
                            cg.switchState(cg.askAufgebenState);
                        } else if (cg.isState(cg.askAufgebenState)) {
                            if (cg.selectedIdx == 0) {
                                cg.spielerPunkteAendern(cg.player, -cg.player.lifeCounter, PunkteArt.Leben, true);
                            } else if (cg.selectedIdx == 1) {
                                cg.switchState(cg.onAufgbenState);
                            }
                        }
                    } 
                } 
                else if (keyH.gPressed && !cg.inactiveMode) {
                    if (cg.isState(cg.boardState)) {
                        if (cg.player.boardCards.size() > 0) {
                            if (cg.isEffektManualActivatable(cg.player, cg.player.boardCards.get(cg.selectedIdx), cg.effekteMangaer.triggerManualFromBoard)) {
                                cg.selectedBoardCardIdx = cg.selectedIdx;
                                cg.switchState(cg.effektQuestionStateBoard);
                            }
                        }
                    } else if (cg.isState(cg.handCardState)) {
                        if (cg.player.handCards.size() > 0) {
                            if (cg.isEffektManualActivatable(cg.player, cg.player.handCards.get(cg.selectedIdx), cg.effekteMangaer.triggerManualFromHand)) {
                                cg.selectedHandCardIdx = cg.selectedIdx;
                                cg.switchState(cg.effektQuestionStateHand);
                            }	
                        }	
                    } else if (cg.isState(cg.graveSelectedState)) {
                        if (cg.isEffektManualActivatable(cg.player, cg.player.graveCards.get(cg.selectedIdx), cg.effekteMangaer.triggerManualFromGrave)) {
                            cg.selectGraveCardIdx = cg.selectedIdx;
                            cg.switchState(cg.effektQuestionStateGrave);
                        }		
                    } 
                }
                cg.gp.playSE(1);
            }
        } 
    }
    
}
