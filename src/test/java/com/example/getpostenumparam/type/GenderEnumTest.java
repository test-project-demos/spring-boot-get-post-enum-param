package com.example.getpostenumparam.type;

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
}