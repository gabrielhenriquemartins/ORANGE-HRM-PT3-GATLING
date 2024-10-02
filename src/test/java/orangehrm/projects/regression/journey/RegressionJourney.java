package orangehrm.projects.regression.journey;

import io.gatling.javaapi.core.ChainBuilder;
import orangehrm.pages.*;

import java.time.Duration;
import java.util.Random;

import static io.gatling.javaapi.core.CoreDsl.*;

public class RegressionJourney {

    private static final  Duration LOW_PAUSE = Duration.ofMillis(1000);
    private static final  Duration HIGH_PAUSE = Duration.ofMillis(3000);

    public static ChainBuilder adminDailyOperations =
            exec(Login.getCsrfToken)
                    .exec(Login.loginAsAdmin)
                    .exec(session -> {
                        int randomNumber = new Random().nextInt(9999999);
                        return session.set("randomNumber", randomNumber);
                    })
                    .exec(Admin.addJobTitle("#{randomNumber}"))
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Admin.deleteJobTitle("#{jobId}"))
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Pim.addEmployee("#{randomNumber}"))
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Pim.deleteEmployee("#{employeeId}"))
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Performance.addKpi("#{randomNumber}"))
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Performance.deleteKpi("#{kpiId}"))
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Claim.addExpenseType("#{randomNumber}"))
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Claim.deleteExpenseType("#{expenseId}"))
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Recruitment.getJobTitles);

    public static ChainBuilder adminRareOperations =
            exec(Login.getCsrfToken)
                    .exec(Login.loginAsAdmin)
                    .exec(session -> {
                        int randomNumber = new Random().nextInt(9999999);
                        return session.set("randomNumber", randomNumber);
                    })
                    .exec(Admin.emailConfiguration())
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Admin.changeLanguageToEnglish())
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Admin.changeCorpBrandToDefault())
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Admin.activateModules())
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Leave.addLeaveType("#{randomNumber}"))
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Leave.deleteLeaveType("#{leaveId}"));

    public static ChainBuilder userOperations =
            exec(Login.getCsrfToken)
                    .exec(Login.loginAsAdmin)
                    .exec(session -> {
                        int randomNumber = new Random().nextInt(9999999);
                        return session.set("randomNumber", randomNumber);
                    })
                    .exec(MyInfo.getPersonalDetails)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Time.getDatetime)
                    .pause(LOW_PAUSE, HIGH_PAUSE)
                    .exec(Time.getAllPunchInAndOutInDate("#utcDate"));
}
