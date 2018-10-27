package com.example.getpostenumparam.model;

import com.example.getpostenumparam.type.GenderEnum;
import lombok.Data;

/**
 * @author zhixiao.mzx
 * @date 2018/10/27
 */
@Data
public class User {
    private String name;
    private GenderEnum gender;
}
