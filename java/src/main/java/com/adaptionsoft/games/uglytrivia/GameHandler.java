package com.adaptionsoft.games.uglytrivia;

public class GameHandler {
    static void handle(PlayerAdded event) {
        System.out.println(event.playerName + " was added");
        System.out.println("They are player number " + event.totalNumberOfPlayers);
    }

    static void handle(GoldCoinWon goldCoinWon) {
        System.out.println("Answer was correct!!!!");
        System.out.println(goldCoinWon.name
                + " now has "
                + goldCoinWon.goldCoinsTotal
                + " Gold Coins.");
    }

    static void handle(NotGettingOutOfPenaltyBox event) {
        System.out.println(event.name + " is not getting out of the penalty box");
    }

    static void handle(GetOutOfPenaltyBox event) {
        System.out.println(event.name + " is getting out of the penalty box");
    }

    static void handle(DiceRolled event) {
        System.out.println(event.name + " is the current player");
        System.out.println("They have rolled a " + event.roll);
    }

    static void handlePlayerSentToPenaltyBox(PlayerSentToPenaltyBox playerSentToPenaltyBox) {
        System.out.println("Question was incorrectly answered");
        System.out.println(playerSentToPenaltyBox.name + " was sent to the penalty box");
    }

    static void handlePlayerMoved(PlayerMoved playerMoved) {
        System.out.println(playerMoved.name
                + "'s new location is "
                + playerMoved.newLocation);
    }

    static void handleQuestionAsked(QuestionAsked questionAsked) {
        System.out.println("The category is " + questionAsked.category);
        System.out.println(questionAsked.question);
    }
}
