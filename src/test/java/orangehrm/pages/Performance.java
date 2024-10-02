package orangehrm.pages;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Performance {

    public static ChainBuilder addKpi(String randomNumber) {
        return exec(
                http("Performance - Add KPI")
                        .post("/web/index.php/api/v2/performance/kpis")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody("""
                        {
                          "title": "My KPI #{randomNumber}",
                          "jobTitleId": 5,
                          "minRating": 0,
                          "maxRating": 100,
                          "isDefault": false
                        }
                        """))
                        .disableFollowRedirect()
                        .check(status().is(200))
                        .check(jsonPath("$.data.id").saveAs("kpiId"))
        ).exec(session -> {
            String kpiId = session.getString("kpiId");
            System.out.println("The KPI ID: " + kpiId);
            return session;
        });
    }

    public static ChainBuilder deleteKpi(String id) {
        return exec(
                http("Performance - Delete Kpi")
                        .delete("/web/index.php/api/v2/performance/kpis")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody("{ \"ids\": [" + id + "] }"))
                        .check(status().is(200))
        ).exec(session -> {
            String kpiId = session.getString("kpiId");
            System.out.println("KPI ID deleted: " + kpiId);
            return session;
        });
    }

}
