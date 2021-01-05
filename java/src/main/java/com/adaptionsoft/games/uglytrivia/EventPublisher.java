package com.adaptionsoft.games.uglytrivia;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class EventPublisher implements IPublishEvent {

    //TODO remplacer Consumer par List<Consumer> pour gérer plusieurs conumers par type d'evt
    private HashMap<Class, Consumer> handlers;

    public EventPublisher() {
        handlers = new HashMap<Class, Consumer>();
        registerHandler(PlayerMoved.class, GameHandler::handlePlayerMoved);
        registerHandler(QuestionAsked.class, GameHandler::handleQuestionAsked);
        registerHandler(PlayerSentToPenaltyBox.class, GameHandler::handlePlayerSentToPenaltyBox);
        registerHandler(DiceRolled.class, GameHandler::handle);
        registerHandler(GetOutOfPenaltyBox.class, GameHandler::handle);
        registerHandler(NotGettingOutOfPenaltyBox.class, GameHandler::handle);
        registerHandler(GoldCoinWon.class, GameHandler::handle);
        registerHandler(PlayerAdded.class, GameHandler::handle);
    }

    @Override
    public void applyEvents(List<Object> events) {
        for (Object event : events) {
            Consumer handler = handlers.getOrDefault(event.getClass(), o -> {
            });
            handler.accept(event);
        }
    }

    public <T> void registerHandler(Class<T> clazz, Consumer<T> handler) {
        handlers.put(clazz, handler);
    }
}
