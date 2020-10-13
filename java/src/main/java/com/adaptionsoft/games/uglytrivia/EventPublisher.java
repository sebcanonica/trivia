package com.adaptionsoft.games.uglytrivia;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class EventPublisher implements IPublishEvent {
    public EventPublisher() {
    }

    @Override
    public void applyEvents(List<Object> events) {
        HashMap<Class, Consumer> handlers = new HashMap<Class, Consumer>();
        registerHandler(handlers, PlayerMoved.class, GameHandler::handlePlayerMoved);
        registerHandler(handlers, QuestionAsked.class, GameHandler::handleQuestionAsked);
        registerHandler(handlers, PlayerSentToPenaltyBox.class, GameHandler::handlePlayerSentToPenaltyBox);
        registerHandler(handlers, DiceRolled.class, GameHandler::handle);
        registerHandler(handlers, GetOutOfPenaltyBox.class, GameHandler::handle);
        registerHandler(handlers, NotGettingOutOfPenaltyBox.class, GameHandler::handle);
        registerHandler(handlers, GoldCoinWon.class, GameHandler::handle);
        registerHandler(handlers, PlayerAdded.class, GameHandler::handle);

        for (Object event : events) {
            Consumer handler = handlers.getOrDefault(event.getClass(), o -> {
            });
            handler.accept(event);
        }
    }

    <T> void registerHandler(HashMap<Class, Consumer> handlers, Class<T> clazz, Consumer<T> handler) {
        handlers.put(clazz, handler);
    }
}
