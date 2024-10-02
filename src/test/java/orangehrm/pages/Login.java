package orangehrm.pages;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Login {
    private static String extractAuthToken(String responseBody) {
        String regex = ":token=\"&quot;([^\"]+)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(responseBody);

        if (matcher.find()) {
            String token = matcher.group(1);
            token = token.replace("&quot;", "");
            return token;
        }
        return null;
    }

    public static ChainBuilder getCsrfToken =
        exec(flushCookieJar())
        .exec(
                    http("Get the CSRF token for Login")
                            .get("/web/index.php/auth/login")
                            .check(status().is(200))
                            .check(bodyString().saveAs("responseBody"))
            ).exec(session -> {
                String responseBody = session.getString("responseBody");
                String authToken = extractAuthToken(responseBody);
                System.out.println("The Auth Token is: " + authToken);
                return session.set("authToken", authToken);
            });

    public static ChainBuilder loginAsAdmin =
            exec(
                    http("Home - Login as Admin")
                            .post("/web/index.php/auth/validate")
                            .formParam("_token", "#{authToken}")
                            .formParam("username", "Admin")
                            .formParam("password", "admin123")
                            .disableFollowRedirect()
                            .check(status().is(302))
                            .check(header("Set-Cookie").saveAs("setCookieHeader"))
            )
            .exec(session -> {
                String sessionCookie = session.getString("setCookieHeader");
                System.out.println("The cookie: " + sessionCookie);
                return session;
            });
}
