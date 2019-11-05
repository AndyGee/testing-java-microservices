import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.specto.hoverfly.junit.core.HoverflyConfig;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import testing.microservices.DemoApp;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApp.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HoverflyTest {

  @ClassRule
  public static HoverflyRule hoverflyRule = HoverflyRule.inCaptureOrSimulationMode("Scenario.json",
    HoverflyConfig.localConfigs().proxyLocalHost());

  @Before
  public void setup() {
    RestAssured.baseURI = "http://localhost:8081";
  }

  @Test
  public void someTest1() {
    final Response response = get("/actuator/health");

    System.out.println("response = " + response.asString());

    response.then().assertThat().body("status", containsString("UP"));
  }

}
