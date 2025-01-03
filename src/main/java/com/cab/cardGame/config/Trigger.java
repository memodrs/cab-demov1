package com.cab.cardGame.config;

public class Trigger {
    public static int triggerManualFromBoard                                             = 0;  
	public static int triggerManualFromGrave                                             = 1;  
	public static int triggerManualFromHand                                              = 2;  
	public static int triggerKreaturAufrufen                                             = 10;
	public static int triggerOnBoardOponentKreaturAufgerufen                             = 11;                        
	public static int triggerOnBoardPlayerKreaturAufgerufen                              = 12;                        
	public static int triggerDirekterAngriff                                             = 20; 
	public static int triggerBeforeDirekterAngriff                                       = 21; 
	public static int triggerOnHandBeforeDamageDirekterAngriff                           = 22;                        
	public static int triggerOnHandDamageDirekterAngriff                                 = 23;                        
	public static int triggerAngriffSetupAngreifer                                       = 30; 
	public static int triggerAngriffSetupVerteidiger                                     = 31; 
	public static int triggerBeforeKarteAngreift                                         = 40;                        
	public static int triggerBeforeKarteWirdAngegriffen                                  = 41;                   
	public static int triggerSchadenZugefuegtDurchAngriff                                = 50;                        
	public static int triggerKarteWurdeAngegriffenUndAngreiferIstNochAufDemBoard         = 51;                        
	public static int triggerKarteWurdeDurchKampfZerstoertUndAngreiferIstNochAufDemBoard = 52;                        
	public static int triggerAfterDoAttack                                               = 53;                        
	public static int triggerAfterDoAttackAngreiferNochAufBoard                          = 54; 
	public static int triggerKarteWurdeDurchKampfZerstoert                               = 55; 
	public static int triggerKarteHatDurchAngriffKarteZerstoert                          = 56; 
	public static int triggerKarteWurdeAngegriffen                                       = 57; 
	public static int triggerAfterDestroyed                                              = 60;                        
	public static int triggerOnZerstoertPlayerKreaturZerstoert                           = 61; 
	public static int triggerOnZerstoertOponentKreaturZerstoert                          = 62; 
	public static int triggerOnZerstoertKreaturZerstoert                                 = 63; 
	public static int triggerOnAddKreaturToGrave                                         = 70; 
	public static int triggerOnStartRunde                                                = 71; 
	public static int triggerPermanent                                                   = 190;
	public static int triggerTest                                                        = 200;
}
