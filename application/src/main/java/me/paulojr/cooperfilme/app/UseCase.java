package me.paulojr.cooperfilme.app;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIn);

}
