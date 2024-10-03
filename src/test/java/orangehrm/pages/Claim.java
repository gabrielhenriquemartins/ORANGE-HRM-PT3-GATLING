package orangehrm.pages;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Claim {

    public static ChainBuilder addExpenseType(String randomNumber) {
        return exec(
                http("Claim - Add Expense Type")
                        .post("/api/v2/claim/expenses/types")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody("""
                        {
                          "name": "My Expense #{randomNumber}",
                          "description": "My Description!",
                          "status": true
                        }
                        """))
                        .disableFollowRedirect()
                        .check(status().is(200))
                        .check(jsonPath("$.data.id").saveAs("expenseId"))
        ).exec(session -> {
            String expenseId = session.getString("expenseId");
            System.out.println("The Expense Type ID: " + expenseId);
            return session;
        });
    }

    public static ChainBuilder deleteExpenseType(String id) {
        return exec(
                http("Claim - Delete Expense Type")
                        .delete("/api/v2/claim/expenses/types")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody("{ \"ids\": [" + id + "] }"))
                        .check(status().is(200))
        ).exec(session -> {
            String expenseId = session.getString("expenseId");
            System.out.println("Expense ID deleted: " + expenseId);
            return session;
        });
    }

}
