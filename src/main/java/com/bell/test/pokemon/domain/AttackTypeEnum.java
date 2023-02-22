package com.bell.test.pokemon.domain;

public enum AttackTypeEnum {
    NORMAL_ATTACK(1),
    SPECIAL_ATTACK(2);

    private final int value;

    AttackTypeEnum(int value) {
        this.value =  value;
    }

    public int getValue() {
        return value;
    }
}
