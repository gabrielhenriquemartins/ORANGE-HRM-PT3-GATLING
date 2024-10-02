package orangehrm.projects.regression.population;

import io.gatling.javaapi.core.PopulationBuilder;
import orangehrm.projects.regression.scenario.RegressionScenario;

import static io.gatling.javaapi.core.CoreDsl.*;

public class RegressionPopulation {

    private static final int USER = Integer.parseInt(System.getProperty("USERS", "5"));
    private static final int RAMP_DURATION = Integer.parseInt(System.getProperty("RAMP_DURATION", "30"));

    public static PopulationBuilder instantUsers =
            RegressionScenario.defaultLoadTest
                    .injectOpen(
                            nothingFor(5),
                            atOnceUsers(USER));

    public static PopulationBuilder rampUsers =
            RegressionScenario.defaultLoadTest
                    .injectOpen(
                            nothingFor(5),
                            rampUsers(USER).during(RAMP_DURATION));

    public static PopulationBuilder stressLoadUsers =
            RegressionScenario.defaultLoadTest
                    .injectOpen(
                            nothingFor(5),
                            atOnceUsers(USER),
                            constantUsersPerSec(5).during(RAMP_DURATION).randomized()
                    );
}
