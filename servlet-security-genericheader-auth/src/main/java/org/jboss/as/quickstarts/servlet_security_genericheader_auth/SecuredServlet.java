/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.servlet_security_genericheader_auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

/**
 * A simple secured Servlet. Upon successful authentication and authorization
 * the Servlet will print details of the user and authentication. Servlet
 * security is implemented using annotations.
 *
 * NOTE: This simply exists as an example of a servlet secured by this code.
 *    This servlet would not be used in an actual production situation.
 * 
 * @author Sherif Makary
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/SecuredServlet")
@ServletSecurity(@HttpConstraint(rolesAllowed = { "guest" }))
public class SecuredServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(SecuredServlet.class.getSimpleName());
    
    private static final String PARAM_UNIT_TEST = "unitTest";
    
    private static String PAGE_HEADER = "<html><head /><body>";

    private static String PAGE_FOOTER = "</body></html>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        
        if ("true".equalsIgnoreCase(req.getParameter(PARAM_UNIT_TEST))) {
            log.info("Authenticated Request Received from User: " + req.getUserPrincipal().getName());
            resp.setContentType("text/plain");
            writer.write("AUTHENTICATED");
        } else {
            Principal principal = null;
            String authType = null;
            String remoteUser = null;
    
            // Get security principal
            principal = req.getUserPrincipal();
            // Get user name from login principal
            remoteUser = req.getRemoteUser();
            // Get authentication type
            authType = req.getAuthType();
    
            writer.println(PAGE_HEADER);
            writer.println("<h1>" + "Successfully called Secured Servlet "
                    + "</h1>");
            writer.println("<p>" + "Principal  : " + principal.getName() + "</p>");
            writer.println("<p>" + "Remote User : " + remoteUser + "</p>");
            writer.println("<p>" + "Authentication Type : " + authType + "</p>");
            writer.println(PAGE_FOOTER);
            writer.close();
        }
    }

}
