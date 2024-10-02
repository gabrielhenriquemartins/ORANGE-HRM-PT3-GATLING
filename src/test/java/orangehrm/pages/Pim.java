package orangehrm.pages;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Pim {

    public static ChainBuilder addEmployee(String randomNumber) {
        return exec(
                http("PIM - Add Employee")
                        .post("/web/index.php/api/v2/pim/employees")
                        .header("Cookies", "#{setCookieHeader}")
                        .formParam("employeeId", session -> session.getString("randomNumber"))
                        .formParam("firstName", session -> "Gabriel " + session.getString("randomNumber"))
                        .formParam("lastName", "Henrique")
                        .formParam("middleName", "Martins")
                        .disableFollowRedirect()
                        .check(status().is(200))
                        .check(jsonPath("$.data.empNumber").saveAs("employeeId"))
        ).exec(session -> {
            String employeeId = session.getString("employeeId");
            System.out.println("The employee ID: " + employeeId);
            return session;
        });
    }

    public static ChainBuilder deleteEmployee(String id) {
        return exec(
                http("PIM - Delete Employee")
                        .delete("/web/index.php/api/v2/pim/employees")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody("{ \"ids\": [\"" + id + "\"] }"))
                        .check(status().is(200))
        ).exec(session -> {
            String employeeId = session.getString("employeeId");
            System.out.println("Employee ID Deleted: " + employeeId);
            return session;
        });
    }

}
