package orangehrm.pages;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Recruitment {

    public static ChainBuilder getJobTitles =
            exec(
                    http("Recruitment - Get Job Titles")
                            .get("/web/index.php/api/v2/admin/job-titles?limit=0")
                            .header("Cookies", "#{setCookieHeader}")
                            .check(status().is(200))
            );

}
