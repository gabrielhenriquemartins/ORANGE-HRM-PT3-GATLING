package orangehrm.pages;

import io.gatling.javaapi.core.ChainBuilder;

import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class MyInfo {

    public static ChainBuilder getPersonalDetails =
            exec(
                    http("My Info - Get Personal Details")
                            .get("/api/v2/pim/employees/7/personal-details")
                            .header("Cookies", "#{setCookieHeader}")
                            .check(status().is(200))
            );

}
