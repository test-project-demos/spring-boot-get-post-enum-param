package com.example.getpostenumparam.type;

import com.example.getpostenumparam.exception.UnknownEnumValueException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author zhixiao.mzx
 * @date 2018/10/27
 */
public enum GenderEnum {
    MALE(1),

    FEMALE(2);

    private int value;

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static GenderEnum of(Integer value) {
        if (null == value) {
            return null;
        }

        for (GenderEnum item : GenderEnum.values()) {
            if (value.equals(item.getValue())) {
                return item;
            }
        }

        throw new UnknownEnumValueException("GenderEnum: unknown value: " + value);
    }

    GenderEnum(int value) {
        this.value = value;
    }
}
