package com.gj.miclrn.entityBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LambdaEntityBuilder<T> {
    private final Supplier<T> instantiator;
    private List<Consumer<T>> modifiers = new ArrayList<>();

    public LambdaEntityBuilder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    public static <T> LambdaEntityBuilder<T> of(Supplier<T> instantiator) {
        return new LambdaEntityBuilder<>(instantiator);
    }

    public <U> LambdaEntityBuilder<T> with(BiConsumer<T, U> consumer, U value) {
        modifiers.add(instance -> consumer.accept(instance, value));
        return this;
    }

    public T build() {
        T instance = instantiator.get();
        modifiers.forEach(modifier -> modifier.accept(instance));
        modifiers.clear();
        return instance;
    }
}
