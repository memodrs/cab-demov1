package com.cab.cardGame;

import com.cab.KeyHandler;
import com.cab.card.Status;
import com.cab.cardGame.actions.ActivateSpell;
import com.cab.cardGame.actions.AttackPhaseOne;
import com.cab.cardGame.actions.EndTurn;
import com.cab.cardGame.actions.KarteDrehen;
import com.cab.cardGame.actions.KarteVonHandAufBoard;
import com.cab.cardGame.actions.ManualEffekt;
import com.cab.cardGame.actions.SelectOptionFromList;
import com.cab.cardGame.actions.SelectTargetCard;
import com.cab.cardGame.actions.SetUpDirekterAngriff;
import com.cab.cardGame.actions.SpielerPunkteAendern;
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
                new EndTurn().execute(cg, player, cg.isOnline);
            }
            else if (cardGameState.isState(State.effektQuestionState)) {
                cg.switchState(State.handCardState);
            }
            else if (cardGameState.isState(State.boardCardSelectedState) || cardGameState.isState(State.selectCardToAttackState)) {
                cg.switchState(State.boardState);
                cg.selectedIdx = cg.lastSelectedIdx;
            }
            else if (cardGameState.isState(State.graveSelectedOponentState)) {
                cg.switchState(State.graveOponentState);
            }
            else if (cardGameState.isState(State.graveSelectedState)) {
                cg.switchState(State.graveState);
            } else if (cardGameState.isState(State.askAufgebenState)) {
                cg.switchState(State.onAufgbenState);
            }
        }
        else if (keyH.rightPressed) {
            if (cardGameState.isState(State.graveState) || cardGameState.isState(State.graveOponentState) || 
            cardGameState.isState(State.handCardState) || cardGameState.isState(State.boardState) || 
            cardGameState.isState(State.boardOponentState) || cardGameState.isState(State.selectCardToAttackState) || 
            cardGameState.isState(State.graveSelectedOponentState) || cardGameState.isState(State.graveSelectedState) || 
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
                } else if (cardGameState.isState(State.boardState)) {
                    size = player.boardCards.size() - 1;
                } else if (cardGameState.isState(State.boardOponentState) || cardGameState.isState(State.selectCardToAttackState)) {
                    size = oponent.boardCards.size() - 1;
                } else if (cardGameState.isState(State.graveSelectedState)) {
                    size = player.graveCards.size() - 1;
                } else if (cardGameState.isState(State.graveSelectedOponentState)) {
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
            if (cardGameState.isState(State.handCardState)  || cardGameState.isState(State.boardState) || 
            cardGameState.isState(State.boardOponentState)  || cardGameState.isState(State.selectCardToAttackState) || 
            cardGameState.isState(State.graveSelectedState) || cardGameState.isState(State.selectOptionCardListState)) {
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
                        CardState card = player.handCards.get(cg.selectedIdx);
                        if (card.defaultCard.isSpell()) {
                            if (cg.isSpellPossible(player, card)) {
                                cg.selectedCard = card;
                                cg.switchState(State.effektQuestionState);
                            }
                        } else {
                            if (player.isPlayCreatureAllowed(card)) {
                                cg.selectedCard = card;
                                cg.switchState(State.handCardSelectedState);
                            } 
                        }
                    }
                }
                else if (cardGameState.isState(State.handCardSelectedState)) {
                    if (!cg.selectedCard.defaultCard.isSpell()) {
                        boolean isHide = cg.selectedIdx == 1;
                        new KarteVonHandAufBoard().execute(cg, player, cg.selectedCard.id, isHide, false, true);
                        if (cardGameState.isState(State.handCardSelectedState)) {
                            cg.switchState(State.boardState);
                        }
                    }
                }
                else if (cardGameState.isState(State.boardState)) {
                    if (player.boardCards.size() > 0) {
                        cg.selectedCard = player.boardCards.get(cg.selectedIdx);
                        if ((cg.selectedCard.isHide && !cg.selectedCard.wasPlayedInTurn) || player.isAttackAlowed(cg.selectedCard)) {
                            cg.switchState(State.boardCardSelectedState);
                        }
                    }					
                }
                else if (cardGameState.isState(State.boardCardSelectedState)) {
                    if (cg.selectedCard.isHide) {
                        new KarteDrehen().execute(cg, cg.selectedCard.id, false, true);
                        cg.switchState(State.boardState);
                    } else if (oponent.boardCards.size() == 0 || cg.selectedCard.statusSet.contains(Status.Fluegel)) {
                        new SetUpDirekterAngriff().execute(cg, player, cg.selectedCard.id, true);
                    } else  {
                        cg.switchState(State.selectCardToAttackState);
                    } 
                }

                else if (cardGameState.isState(State.effektQuestionState)) {
                    if (cg.selectedCard.defaultCard.isSpell()) {
                        new ActivateSpell().execute(cg, player, cg.selectedCard.id, true);
                    } else {
                        new ManualEffekt().execute(cg, cg.selectedCard.id,  true);
                    }
                } 

                else if (cardGameState.isState(State.selectCardToAttackState)) {
                    CardState verteidiger = oponent.boardCards.get(cg.selectedIdx);
                    new AttackPhaseOne().execute(cg, player, cg.selectedCard.id, verteidiger.id, true);
                } 
                else if (cardGameState.isState(State.selectOptionState)) {
                    String value = cg.optionsToSelect.values().toArray(new String[0])[cg.selectedIdx];
                    new SelectOptionFromList().execute(cg, value, true);
                    cg.handleEffekt(cg.activeEffektCard.id, cg.selectedIdx, true);
                }  
                else if (cardGameState.isState(State.selectOptionCardListState)) {
                    new SelectTargetCard().execute(cg, cg.optionsCardsToSelect.get(cg.selectedIdx).id, true);
                    cg.handleEffekt(cg.activeEffektCard.id, cg.optionsCardsToSelect.get(cg.selectedIdx).id, true);
                } 
                else if (cardGameState.isState(State.graveState)) {
                    if (player.graveCards.size() > 0) {
                        cg.switchState(State.graveSelectedState);
                    }
                } 
                else if (cardGameState.isState(State.graveOponentState)) {
                    if (oponent.graveCards.size() > 0) {
                        cg.switchState(State.graveSelectedOponentState);
                    }
                } 
                else if (cardGameState.isState(State.onAufgbenState)) {
                    cg.switchState(State.askAufgebenState);
                } 
                else if (cardGameState.isState(State.askAufgebenState)) {
                    if (cg.selectedIdx == 0) {
                        new SpielerPunkteAendern().execute(cg, player, -player.lifeCounter, PunkteArt.Leben, true);
                    } else if (cg.selectedIdx == 1) {
                        cg.switchState(State.onAufgbenState);
                    }
                }
            } 
        } 
        else if (keyH.gPressed && !player.inactiveMode) {
            if (cardGameState.isState(State.boardState) && player.boardCards.size() > 0 && cg.isEffektPossible(player, Trigger.triggerManualFromBoard, player.boardCards.get(cg.selectedIdx))) {
                cg.selectedCard = player.boardCards.get(cg.selectedIdx);
                cg.switchState(State.effektQuestionState);
            } else if (cardGameState.isState(State.handCardState) && player.handCards.size() > 0 && cg.isEffektPossible(player, Trigger.triggerManualFromHand, player.handCards.get(cg.selectedIdx))) {
                cg.selectedCard = player.handCards.get(cg.selectedIdx);
                cg.switchState(State.effektQuestionState);
            } else if (cardGameState.isState(State.graveSelectedState) && player.graveCards.size() > 0 && cg.isEffektPossible(player, Trigger.triggerManualFromGrave, player.graveCards.get(cg.selectedIdx))) {
                cg.selectedCard = player.graveCards.get(cg.selectedIdx);
                cg.switchState(State.effektQuestionState);		
            } 
        }
        cg.gp.playSE(1);
    }
    
}
