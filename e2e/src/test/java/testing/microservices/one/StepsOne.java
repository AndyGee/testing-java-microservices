package testing.microservices.one;

import io.cucumber.java.Before;
import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.net.ServerSocket;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsString;

public class StepsOne extends App implements En {

  /**
   * This is the globally available WebDriver
   */
  private static WebDriver browser = BDDExampleOneTest.webDriver;

  public StepsOne() {

    //This is defined in our feature file
    Given("^My simple SpringBoot app is running$", () -> {  //<2>

      final Response response = get("/actuator/health");

      System.out.println("\n\nresponse = " + response.asString() + "\n\n");

      response.then().assertThat().body("status", containsString("UP"));
    });

    When("^the browser is available$", () -> {

      System.out.println("\n\nUsing this WebDriver: " + browser + "\n\n");

      Assert.assertNotNull(browser);
    });

    Then("^we can remote control the browser$", () -> {

      maximize();

      browser.navigate().to("https://2018.jnation.pt/");

      if (null != System.getProperty("e2e.dev", null)) {  //<3>

        System.out.println("\n\nRun VNC to take a peek");
        System.out.println("Don't kill the environment, or you'll end up with dangling containers!\n\n");
        System.out.println("Use curl localhost:6666!\n\n");

        //It's just for the demo...but could be useful.
        new ServerSocket(6666).accept().close();

        System.out.println("The test evironment is cleaning up");
      }
    });
  }

  @Before //<1>
  public void before() {
    RestAssured.baseURI = "http://localhost:8081";
  }

  private void maximize() {
    try {
      browser.manage().window().maximize();
    } catch (Exception e) {
      System.out.println("Window is already maximized");
    }
  }
}
