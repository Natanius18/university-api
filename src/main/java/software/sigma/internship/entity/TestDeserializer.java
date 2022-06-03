package software.sigma.internship.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.sigma.internship.service.TeacherService;

import java.io.IOException;

@Component
public class TestDeserializer extends StdDeserializer<Test> {
    @Autowired
    private TeacherService teacherService;

    public TestDeserializer() {
        this(null);
    }

    public TestDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Test deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Long id = null;
        if (node.has("id")) {
            id = node.get("id").longValue();
        }
        String itemName = node.get("name").asText();
        Long userId = node.get("createdBy").longValue();
        return new Test(id, itemName, teacherService.findById(userId), null);
    }
}