package com.credit.utils;

import java.util.Comparator;
import java.util.Map;

class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {

    @Override
    public int compare(Map.Entry<String, Integer> me1, Map.Entry<String, Integer> me2) {

        return me1.getValue().compareTo(me2.getValue());
    }
}