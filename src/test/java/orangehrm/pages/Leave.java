package orangehrm.pages;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Leave {

    public static ChainBuilder addLeaveType(String randomNumber) {
        return exec(
                http("Leave - Add Leave Type")
                        .post("/web/index.php/api/v2/leave/leave-types")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody("""
                        {
                          "name": "New Leave #{randomNumber}",
                          "situational": false
                        }
                        """))
                        .disableFollowRedirect()
                        .check(status().is(200))
                        .check(jsonPath("$.data.id").saveAs("leaveId"))
        ).exec(session -> {
            String leaveId = session.getString("leaveId");
            System.out.println("The Leave ID: " + leaveId);
            return session;
        });
    }

    public static ChainBuilder deleteLeaveType(String id) {
        return exec(
                http("Leave - Delete Leave Type")
                        .delete("/web/index.php/api/v2/leave/leave-types")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody("{ \"ids\": [" + id + "] }"))
                        .check(status().is(200))
        ).exec(session -> {
            String employeeId = session.getString("employeeId");
            System.out.println("Leave Type ID Deleted: " + employeeId);
            return session;
        });
    }

}
