package orangehrm.projects.regression;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import orangehrm.projects.regression.population.RegressionPopulation;

import static io.gatling.javaapi.http.HttpDsl.*;


public class PerformanceRunner extends Simulation {

    private static final String TEST_TYPE = System.getProperty("TEST_TYPE","INSTANT_USERS");
    private static final String DOMAIN = "https://opensource-demo.orangehrmlive.com";

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl(DOMAIN);

    {
        if (TEST_TYPE == "INSTANT_USERS") {
            setUp(RegressionPopulation.instantUsers).protocols(httpProtocol);
        }
        if (TEST_TYPE == "RAMP_USERS") {
            setUp(RegressionPopulation.rampUsers).protocols(httpProtocol);
        }
        if (TEST_TYPE == "STRESS") {
            setUp(RegressionPopulation.stressLoadUsers).protocols(httpProtocol);
        }
    }
}