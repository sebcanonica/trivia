package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class EventPublisher implements IPublishEvent {

    private HashMap<Class, List<Consumer>> handlers;

    public EventPublisher() {
        handlers = new HashMap<>();
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
            List<Consumer> handlers = this.handlers.getOrDefault(event.getClass(), Collections.emptyList());
            for (Consumer handler : handlers) {
                handler.accept(event);
            }
        }
    }

    public <T> void registerHandler(Class<T> clazz, Consumer<T> handler) {
        List<Consumer> consumerList = handlers.computeIfAbsent(clazz, k -> new ArrayList<>());
        consumerList.add(handler);
    }
}
