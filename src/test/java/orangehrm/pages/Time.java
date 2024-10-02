package orangehrm.pages;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Time {

    public static ChainBuilder getDatetime =
            exec(
                    http("Time - Get Datetime")
                            .get("/web/index.php/api/v2/attendance/current-datetime")
                            .header("Cookies", "#{setCookieHeader}")
                            .check(status().is(200))
                            .check(jsonPath("$.data.utcDate").saveAs("utcDate"))
            ).exec(session -> {
                String utcDate = session.getString("utcDate");
                System.out.println("UTC Date: " + utcDate);
                return session;
            });

    public static ChainBuilder getAllPunchInAndOutInDate(String date) {
        return exec(
                http("Time - Get All Punch IN/OUT")
                        .get("/web/index.php/api/v2/attendance/records?limit=50&offset=0&date=" + date)
                        .header("Cookies", "#{setCookieHeader}")
                        .check(status().is(200))
        );
    }
}
