package spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import reflection.Student;

import java.util.Arrays;
import java.util.Map;

public class JacksonTest {
    @Test
    void object_to_map() {
        ObjectMapper oMapper = new ObjectMapper();

        Student obj = new Student();
        obj.setName("mkyong");
        obj.setAge(34);
        obj.setSkills(Arrays.asList("java","node"));

        // object -> Map
        Map<String, Object> map = oMapper.convertValue(obj, Map.class);
        System.out.println(map);

    }
}

