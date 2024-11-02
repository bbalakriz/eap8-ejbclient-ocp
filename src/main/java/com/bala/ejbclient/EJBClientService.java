package com.bala.ejbclient;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.stereotype.Service;

import com.bala.eap.ejb.RemoteCalculator;

@Service
public class EJBClientService {

    public String invokeRemoteEJB() {
        try {
            // Configure JNDI properties
            final Hashtable<String, String> jndiProperties = new Hashtable<>();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.wildfly.naming.client.WildFlyInitialContextFactory");
            // use HTTP upgrade, an initial upgrade requests is sent to upgrade to the
            // remoting protocol
            String OPENSHIFT_JNDI_PROVIDER_URL = System.getenv("JAVA_NAMING_PROVIDER_URL");

            jndiProperties.put(Context.PROVIDER_URL,
                    OPENSHIFT_JNDI_PROVIDER_URL != null ? OPENSHIFT_JNDI_PROVIDER_URL : getProviderURl());
            jndiProperties.put(Context.SECURITY_PRINCIPAL, "quickstartUser");
            jndiProperties.put(Context.SECURITY_CREDENTIALS, "quickstartPwd1!");

            // Properties jndiProperties = new Properties();
            // jndiProperties.load(this.getClass().getClassLoader().getResourceAsStream("jndi.properties"));

            final Context context = new InitialContext(jndiProperties);

            // Perform JNDI lookup
            final RemoteCalculator statelessRemoteCalculator = (RemoteCalculator) context
                    .lookup(getEJBBaseJndiName() + "/CalculatorBean!" + RemoteCalculator.class.getName());
            System.out.println("Obtained a remote stateless calculator for invocation");

            // invoke on the remote calculator
            int a = 204;
            int b = 340;
            System.out.println(
                    "Adding " + a + " and " + b + " via the remote stateless calculator deployed on the server");

            int sum = statelessRemoteCalculator.add(a, b);
            System.out.println("Remote calculator returned sum = " + sum);

            // try one more invocation, this time for subtraction
            int num1 = 3434;
            int num2 = 2332;
            System.out.println("Subtracting " + num2 + " from " + num1
                    + " via the remote stateless calculator deployed on the server");

            int difference = statelessRemoteCalculator.subtract(num1, num2);
            System.out.println("Remote calculator returned difference = " + difference);
            return "All remoteEJB operations completed";

        } catch (NamingException e) {
            e.printStackTrace();
            return "Error invoking EJB";
        }
    }

    private String getEJBBaseJndiName() {
        return "ejb:/ROOT";
    }

    private static final String DEFAULT_SERVER_HOST = "http://localhost:8080";

    private String getProviderURl() {
        final String serverHost = System.getenv("SERVER_HOST");
        return "remote+" + (serverHost != null ? serverHost : DEFAULT_SERVER_HOST);
    }
}
