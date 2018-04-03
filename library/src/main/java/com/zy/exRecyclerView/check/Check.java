package com.zy.exRecyclerView.check;

/**
 * Created by yunzou on 18-3-30.
 */

public class Check {

    public static <T> void checkNotNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> void checkNotNull(T value) {
        if (value == null) {
            throw new IllegalArgumentException("the param must not null");
        }
    }
}
