package com.giantleap.moonpark.utils;

public class PriceUtils {

    private PriceUtils() {}

    private static final String NORWAY_CURRENCY_NAME = "kr";

    public static String setNorwayCurrency(int amountToPay){
       return String.valueOf(amountToPay).concat(NORWAY_CURRENCY_NAME);
    }
}
