package test.com.example.demo.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class IntegrationTests extends AbstractResourceTest {

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void test() throws Exception {
        String url = "http://localhost:8080/pregled/predefinisaniPregledi";
        MvcResult  result = (MvcResult) mvc.perform(MockMvcRequestBuilders.get(url));
        int status = result.getResponse().getStatus();
        Assertions.assertEquals(200, status);

    }

}



