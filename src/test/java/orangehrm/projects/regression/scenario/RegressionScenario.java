package orangehrm.projects.regression.scenario;

import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;
import orangehrm.projects.regression.journey.RegressionJourney;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;

public class RegressionScenario {

    private static final Duration TEST_DURATION = Duration.ofSeconds(Integer.parseInt(System.getProperty("DURATION", "60")));

    public static ScenarioBuilder defaultLoadTest =
            scenario("Default Load Test")
                    .during(TEST_DURATION)
                    .on(
                            randomSwitch()
                                    .on(
                                            Choice.withWeight(34, exec(RegressionJourney.adminDailyOperations)),
                                            Choice.withWeight(33, exec(RegressionJourney.adminDailyOperations)),
                                            Choice.withWeight(33, exec(RegressionJourney.userOperations))
                                    )
                    );

    public static ScenarioBuilder highAdminLoadTest =
            scenario("Admin Most Used Operations Load Test")
                    .during(TEST_DURATION)
                    .on(
                            randomSwitch()
                                    .on(
                                            Choice.withWeight(80, exec(RegressionJourney.adminDailyOperations)),
                                            Choice.withWeight(10, exec(RegressionJourney.adminDailyOperations)),
                                            Choice.withWeight(10, exec(RegressionJourney.userOperations))
                                    )
                    );
}