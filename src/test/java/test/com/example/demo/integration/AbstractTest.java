package test.com.example.demo.integration;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.demo.DemoApplication;

@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public abstract class AbstractTest {

}