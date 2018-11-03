package com.example.getpostenumparam.type;

import com.example.getpostenumparam.exception.UnknownEnumValueException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zhixiao.mzx
 * @date 2018/11/03
 */
public class GenderEnumTest {

    @Test
    public void of_null() {
        GenderEnum of = GenderEnum.of(null);
        assertThat(of).isNull();
    }

    @Test(expected = UnknownEnumValueException.class)
    public void of_exception() {
        GenderEnum of = GenderEnum.of(99999999);
    }
}