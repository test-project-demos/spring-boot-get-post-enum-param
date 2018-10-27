package com.example.getpostenumparam;

import com.example.getpostenumparam.model.User;
import com.example.getpostenumparam.type.GenderEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zhixiao.mzx
 * @date 2018/10/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainControllerHttpTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String host;

    @Before
    public void init() {
        host = "http://localhost:" + port;
    }

    @Test
    public void getOrPostEnum_get_with_no_param() {
        GenderEnum result = restTemplate.getForObject(host + "/get_or_post_enum", GenderEnum.class);
        assertThat(result).isNull();
    }

    @Test
    public void getOrPostEnum_get_with_param() {
        String result = restTemplate.getForObject(host + "/get_or_post_enum?gender=1", String.class);
        assertThat(result).isEqualTo(String.valueOf(GenderEnum.MALE.getValue()));
    }

    @Test
    public void getOrPostEnum_post_with_no_param() {
        Map<String, String> paramMap = new HashMap<>();
        GenderEnum result = restTemplate.postForObject(host + "/get_or_post_enum", null, GenderEnum.class, paramMap);
        assertThat(result).isNull();
    }

    @Test
    public void getOrPostEnum_post_with_param() {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("gender", String.valueOf(GenderEnum.FEMALE.getValue()));

        String result = restTemplate.postForObject(host + "/get_or_post_enum", paramMap, String.class);
        assertThat(result).isEqualTo(String.valueOf(GenderEnum.FEMALE.getValue()));
    }

    @Test
    public void getOrPostObjectContainEnum_get_with_no_param() {
        User result = restTemplate.getForObject(host + "/get_or_post_object_contain_enum", User.class);
        assertThat(result.getName()).isNull();
        assertThat(result.getGender()).isNull();
    }

    @Test
    public void getOrPostObjectContainEnum_get_with_param() throws JsonProcessingException {
        String result = restTemplate.getForObject(host + "/get_or_post_object_contain_enum?name=123&gender=2",
            String.class);

        User user = new User();
        user.setName("123");
        user.setGender(GenderEnum.FEMALE);
        assertThat(result).isEqualTo(objectMapper.writer().writeValueAsString(user));
    }

    @Test
    public void getOrPostObjectContainEnum_post_with_no_param() {
        User result = restTemplate.postForObject(host + "/get_or_post_object_contain_enum", Collections.emptyMap(),
            User.class);
        assertThat(result.getName()).isNull();
        assertThat(result.getGender()).isNull();
    }

    @Test
    public void getOrPostObjectContainEnum_post_with_param() throws IOException {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "123");
        paramMap.add("gender", "2");

        String result = restTemplate.postForObject(host + "/get_or_post_object_contain_enum", paramMap, String.class);

        User user = new User();
        user.setName("123");
        user.setGender(GenderEnum.FEMALE);
        assertThat(result).isEqualTo(objectMapper.writer().writeValueAsString(user));
    }

    @Test
    public void postJson_with_no_json() {
        User result = restTemplate.postForObject(host + "/post_json", null, User.class);
        assertThat(result.getName()).isNull();
        assertThat(result.getGender()).isNull();
    }

    @Test
    public void postJson_with_json() {
        //language=JSON
        String jsonBody = "{\n"
            + "  \"name\": \"123\",\n"
            + "  \"gender\": 2\n"
            + "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        User result = restTemplate.postForObject(host + "/post_json", entity, User.class);
        assertThat(result.getName()).isEqualTo("123");
        assertThat(result.getGender()).isEqualTo(GenderEnum.FEMALE);
    }
}