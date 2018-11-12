![project-language](https://img.shields.io/badge/language-java-orange.svg) [![Travis CI status](https://travis-ci.com/test-project-demos/spring-boot-get-post-enum-param.svg?branch=master)](https://travis-ci.com/test-project-demos/spring-boot-get-post-enum-param) [![Coverage Status](https://coveralls.io/repos/github/test-project-demos/spring-boot-get-post-enum-param/badge.svg?branch=master)](https://coveralls.io/github/test-project-demos/spring-boot-get-post-enum-param?branch=master)

# Spring Boot: 如何处理 GET 或 POST 请求中枚举类型的参数?

Spring 框架中对枚举类型的反序列化支持是有限的, 如果类似下面这种枚举类型的参数, 在 GET & POST 请求中都可以正常的反序列化:

```java
public enum GenderEnum {
    MALE,
    FEMALE;
}
```

但如果像下面这样, 带有映射值的话, 但原生 Spring 框架在某些情况下就不能正常工作啦:

```java
public enum GenderEnum {
    MALE(1),
    FEMALE(2);

    private int value;
    
    GenderEnum(int value) {
        this.value = value;
    }
}
```

比如对于具有 `GenderEnum` 类型的参数的 HTTP 接口来说:

```java
@RequestMapping(value = "/get_or_post_enum", method = {RequestMethod.GET, RequestMethod.POST})
public GenderEnum getOrPostEnum(GenderEnum gender) {
    return gender;
}
```

如果 URL 形式是 `/get_or_post_enum?gender=MALE`, 那没问题. 但如果是 `/get_or_post_enum?gender=1`, 那会抛出 `MethodArgumentTypeMismatchException` 异常:

>Resolved [org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'com.example.getpostenumparam.type.GenderEnum'; nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [com.example.getpostenumparam.type.GenderEnum] for value '1'; nested exception is java.lang.IllegalArgumentException: No enum constant com.example.getpostenumparam.type.GenderEnum.1]



---

综合分析 HTTP 接口中可能出现枚举的几种情况:

1. `GET` 方法, 参数列表中有枚举类型;
2. `GET` 方法, 有个自定义类型参数, 该自定义类型包含枚举字段;
3. `POST` 方法(以`form`格式提交参数), 参数列表中有枚举类型;
4. `POST` 方法(以 `form` 格式提交参数), 有个自定义类型参数, 该自定义类型包含枚举字段;
5. `POST` 方法(以 `json` 格式提交参数), 是一个自定义类型参数;

其中, 前四种情况基本一致, 是同一种形式的不同变种, 解决方案一致: 

1. 实现 Spring 提供转换器接口 `org.springframework.core.convert.converter.Converter`;
2. 为其添加注解 `@Component`;

搞定! Spring Boot 在启动时就会自动获取并加载 `Converter` 类型的 bean, [点此查看相关代码](https://github.com/spring-projects/spring-boot/blob/v2.1.0.RELEASE/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/servlet/WebMvcAutoConfiguration.java#L310).

示例代码:

```java
import com.example.getpostenumparam.type.GenderEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenderEnumConverter implements Converter<String, GenderEnum> {
    @Override
    public GenderEnum convert(String value) {
        return GenderEnum.of(Integer.valueOf(value));
    }
}
```



针对第五种情况, 大家应该用的比较多了: Spring Boot 默认集成的 HTTP 序列化和反序列化工具 jackson 就提供了注解 `@JsonCreator` 来设定默认的反序列化方法. 示例:

```java
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
```

至此, 已完成各种情况下枚举类型参数的支持.

[示例代码](https://github.com/test-project-demos/spring-boot-get-post-enum-param)

[示例代码的测试用例](https://github.com/test-project-demos/spring-boot-get-post-enum-param/blob/master/src/test/java/com/example/getpostenumparam/MainControllerHttpTest.java)