package com.example.demo.Persistance;

import java.util.Vector;

public interface Repo<T> {
    public void add(T elem);
    public int get(String name);
    public void remove(String name);
    public Vector<T> getData();
}