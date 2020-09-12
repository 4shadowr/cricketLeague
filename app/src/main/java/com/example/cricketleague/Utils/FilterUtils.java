package com.example.cricketleague.Utils;

import android.widget.Filter;

import java.util.Arrays;
import java.util.function.Predicate;

public class FilterUtils {

    @SafeVarargs
    public static <T> Predicate<T> and(Predicate<T>... predicates) {
        // TODO Handle case when argument is null or empty or has only one element
        return Arrays.stream(predicates).reduce(Predicate::and).orElse(t -> true);
    }

    public enum COMP{
        GREATER,LESS,EQUALS
    }
}
