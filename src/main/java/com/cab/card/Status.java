package com.cab.card;

public enum Status {
    Feuer, Gift, Schild, Blitz, Fluegel, Default;

    @Override
    public String toString() {
        switch (this) {
            case Feuer:
                return "statusFeuer"; 
            case Gift:
                return "statusGift"; 
            case Schild:
                return "statusSchild"; 
            case Blitz:
                return "statusBlitz"; 
            case Fluegel:
                return "statusFluegel"; 
            case Default:
                return "statusDefault"; 
            default:
                return super.toString(); 
        }
    }
}