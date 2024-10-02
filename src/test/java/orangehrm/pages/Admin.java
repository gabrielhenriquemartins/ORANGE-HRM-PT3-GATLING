package orangehrm.pages;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
public class Admin {

    public static ChainBuilder addJobTitle(String randomNumber) {
        return exec(
                http("Admin - Add Job Title")
                        .post("/web/index.php/api/v2/admin/job-titles")
                        .header("Cookies", "#{setCookieHeader}")
                        .formParam("title", "My Job Title " + randomNumber)
                        .formParam("description", "My description")
                        .formParam("note", "My note")
                        .disableFollowRedirect()
                        .check(status().is(200))
                        .check(jsonPath("$.data.id").saveAs("jobId"))
        ).exec(session -> {
            String jobId = session.getString("jobId");
            System.out.println("The job ID: " + jobId);
            return session;
        });
    }

    public static ChainBuilder deleteJobTitle(String id) {
        return exec(
                http("Admin - Delete Job Title")
                        .delete("/web/index.php/api/v2/admin/job-titles")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody("{ \"ids\": [\"" + id + "\"] }"))
                        .check(status().is(200))
        ).exec(session -> {
            String jobId = session.getString("jobId");
            System.out.println("Job ID Deleted: " + jobId);
            return session;
        });
    }

    public static ChainBuilder emailConfiguration() {
        return exec(
                http("Admin - Email Configuration")
                        .put("/web/index.php/api/v2/admin/email-configuration")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody(
                                """
                                {
                                  "mailType": "sendmail",
                                  "sentAs": "send@mail.com",
                                  "smtpHost": null,
                                  "smtpPort": null,
                                  "smtpUsername": "",
                                  "smtpPassword": null,
                                  "smtpAuthType": "none",
                                  "smtpSecurityType": "none",
                                  "testEmailAddress": "destination@mail.com"
                                }
                                """
                        ))
                        .disableFollowRedirect()
                        .check(status().is(200))
        );
    }

    public static ChainBuilder changeLanguageToEnglish() {
        return exec(
                http("Admin - Change Language to English")
                        .put("/web/index.php/api/v2/admin/localization")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody(
                                """
                                {
                                  "language": "en_US",
                                  "dateFormat": "Y-d-m"
                                }
                                """
                        ))
                        .disableFollowRedirect()
                        .check(status().is(200))
        );
    }


    public static ChainBuilder changeCorpBrandToDefault() {
        return exec(
                http("Admin - Change Corp Brand to Default")
                        .put("/web/index.php/api/v2/admin/theme")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody(
                                """
                                {
                                  "variables": {
                                    "primaryColor": "#FF7B1D",
                                    "primaryFontColor": "#FFFFFF",
                                    "secondaryColor": "#76BC21",
                                    "secondaryFontColor": "#FFFFFF",
                                    "primaryGradientStartColor": "#FF920B",
                                    "primaryGradientEndColor": "#F35C17"
                                  },
                                  "showSocialMediaImages": true,
                                  "currentClientLogo": null,
                                  "clientLogo": null,
                                  "currentClientBanner": null,
                                  "clientBanner": null,
                                  "currentLoginBanner": null,
                                  "loginBanner": null
                                }
                                """
                        ))
                        .disableFollowRedirect()
                        .check(status().is(200))
        );
    }

    public static ChainBuilder activateModules() {
        return exec(
                http("Admin - Activate Modules")
                        .put("/web/index.php/api/v2/admin/modules")
                        .header("Cookies", "#{setCookieHeader}")
                        .header("Content-Type", "application/json")
                        .body(StringBody(
                                """
                                {
                                  "admin": true,
                                  "pim": true,
                                  "leave": true,
                                  "time": true,
                                  "recruitment": true,
                                  "performance": true,
                                  "maintenance": true,
                                  "mobile": true,
                                  "directory": true,
                                  "claim": true,
                                  "buzz": true
                                }
                                """
                        ))
                        .disableFollowRedirect()
                        .check(status().is(200))
        );
    }

}
