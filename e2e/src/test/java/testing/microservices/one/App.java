package testing.microservices.one;

import org.springframework.boot.test.context.SpringBootTest;
import testing.microservices.DemoApp;

@SpringBootTest(classes = {DemoApp.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) //<1>
public class App {
}
