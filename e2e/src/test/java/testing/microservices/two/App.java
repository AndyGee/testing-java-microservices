package testing.microservices.two;

import org.springframework.boot.test.context.SpringBootTest;
import testing.microservices.DemoApp;

@SpringBootTest(classes = {DemoApp.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class App {
}
