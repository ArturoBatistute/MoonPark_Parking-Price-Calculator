package com.giantleap.moonpark.utils;

public final class PriceUtils {

    private PriceUtils() {}

    private static final String NORWAY_CURRENCY_NAME = " NOK";

    public static String setNorwayCurrency(long amountToPay){
       return String.valueOf(amountToPay).concat(NORWAY_CURRENCY_NAME);
    }
}
