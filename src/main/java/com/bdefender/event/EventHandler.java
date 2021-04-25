package com.bdefender.event;

@FunctionalInterface
public interface EventHandler<T extends Event> {

    void handle(T event);
}
