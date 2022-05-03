package com.avbravo.microcoregenesis.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
//@ApplicationPath("resources")
//@CustomFormAuthenticationMechanismDefinition(
//        loginToContinue = @LoginToContinue(
//                loginPage = "login.xhtml",
//                useForwardToLogin = false
//            )
//)
@ApplicationPath("/")
@ApplicationScoped
public class ApplicationRestConfiguration extends Application {
    
}
