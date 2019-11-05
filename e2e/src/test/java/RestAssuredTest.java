import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import testing.microservices.DemoApp;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApp.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) //<1>
public class RestAssuredTest {

  @Before
  public void setup() {
    RestAssured.baseURI = "http://localhost:8081";
  }

  @Test
  public void test() {
    final Response getResponse = get("/actuator/health");

    System.out.println("response = " + getResponse.asString());

    getResponse.then().assertThat().body("status", containsString("UP"));
  }
}
