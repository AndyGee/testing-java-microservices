package testing.microservices.two;

import io.cucumber.java8.En;

public class StepsTwo extends App implements En {

  public StepsTwo() {
    When("^this is the step two$", () -> {
      System.out.println("This is step two");
    });
  }
}
