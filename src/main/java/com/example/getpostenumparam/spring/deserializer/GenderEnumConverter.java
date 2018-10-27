package com.example.getpostenumparam.spring.deserializer;

import com.example.getpostenumparam.type.GenderEnum;
import org.springframework.core.convert.converter.Converter;

/**
 * @author zhixiao.mzx
 * @date 2018/10/27
 */
public class GenderEnumConverter implements Converter<String, GenderEnum> {
    @Override
    public GenderEnum convert(String value) {
        return GenderEnum.of(Integer.valueOf(value));
    }
}
