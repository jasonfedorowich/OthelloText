package com.util;

public class Pair<T, V> {

    private final T t;
    private final V v;

    public Pair(T t, V v){
        this.t = t;
        this.v = v;
    }

    public T getValue0(){
        return t;
    }
    public V getValue1(){
        return v;
    }
}
