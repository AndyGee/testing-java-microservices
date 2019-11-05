package testing.microservices.one;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.ArquillianTest;
import org.jboss.arquillian.junit.ArquillianTestClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Cucumber.class) //<1>
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/test-one") //<2>
public class BDDExampleOneTest {

  @ClassRule //<3>
  public static final ArquillianTestClass arquillianTestClass = new ArquillianTestClass();
  @Drone //<5>
  public static WebDriver webDriver;

  static {
    //System.setProperty("DOCKER_HOST", "http://localhost:2375");
    System.setProperty("DOCKER_HOST", System.getProperty("os.name").toLowerCase()
      .contains("win") ? "http://localhost:2375" : "unix:///var/run/docker.sock");

    System.setProperty("DOCKER_TLS_VERIFY", "0");
  }

  @Rule //<4>
  public ArquillianTest arquillianTest = new ArquillianTest();
}
