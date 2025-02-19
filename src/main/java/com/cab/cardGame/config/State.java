package com.cab.cardGame.config;

public class State {
	public static final int ignoreState = -1;
    public static final int handCardState = 0;
	public static final int handCardSelectedState = 1;	
	public static final int boardState = 2;
	public static final int boardCardSelectedState = 3;	
	public static final int boardOponentState = 4;
	public static final int selectCardToAttackState = 5;
	public static final int graveState = 6;
	public static final int graveSelectedState = 7;
	public static final int graveOponentState = 8;
	public static final int graveSelectedOponentState = 9;

	public static final int effektQuestionState = 14;

	public static final int selectOptionState = 20;
	public static final int selectOptionCardListState = 21;


	public static final int spellGraveState = 30;
	public static final int spellGraveOponentState = 31;

	public static final int onAufgbenState = 90;
	public static final int askAufgebenState = 91;
	public static final int gameFinishedState = 100;
}
