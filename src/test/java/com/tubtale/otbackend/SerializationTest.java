package com.tubtale.otbackend;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.sql.Timestamp;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class SerializationTest extends CommonTest {
    @Test
    public void putV1ItemsIdShouldSaveNewTree() throws Exception {
        tree.setIp("ip10");
        tree.setLocation(30, 40);
        tree.setMetersToHide(20);
        tree.setText("hola mon");
        tree.setTimestamp(new Timestamp(0));
        tree.setId(30);
        String json = new ObjectMapper().writeValueAsString(tree);
        assertThat(json,is(equalTo("{\"id\":30,\"text\":\"hola mon\",\"ip\":\"ip10\",\"metersToHide\":20,\"timestamp\":0,\"x\":30.0,\"y\":40.0}")));
    }
}
