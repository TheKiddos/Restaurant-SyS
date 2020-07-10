package org.thekiddos.manager.models;

/**
 * This is used to classify and filter {@link Item}
 */
public enum Type {
    FOOD, STARTER, HOT, COLD, SNACK, MAIN, DESSERT, DRINK, ALCOHOL;

    private static final Type[] types = Type.values();

    public static Type fromOrdinal( Integer ordinal ) {
        return types[ ordinal ];
    }
}
