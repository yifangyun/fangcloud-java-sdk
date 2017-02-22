package com.fangcloud.sdk.example.web_demo;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyHost;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;

import java.io.File;

import static com.fangcloud.sdk.util.StringUtil.jq;

/**
 * An web demo that uses the Fangcloud API to let users get info about their files
 */
public class Main {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("");
            System.out.println("Usage: COMMAND <http-listen-port> <app-key> <app-secret> <database-file>");
            System.out.println("");
            System.out.println(" <http-listen-port>: The port to run the HTTP server on.  For example,");
            System.out.println("    \"8080\".");
            System.out.println("");
            System.out.println(" <app-key>: Fangcloud API app key");
            System.out.println("");
            System.out.println(" <app-secret>: Fangcloud API app secret");
            System.out.println("");
            System.out.println(" <database-file>: Where you want this program to store its database.  For");
            System.out.println("    example, \"web-demo.db\".");
            System.exit(1);
            return;
        }

        String argPort = args[0];
        int port;
        try {
            port = Integer.parseInt(argPort);
            if (port < 1 || port > 65535) {
                System.err.println("Expecting <http-listen-port> to be a number from 1 to 65535.  Got: " + port + ".");
                System.exit(1); return;
            }
        } catch (NumberFormatException ex) {
            System.err.println("Expecting <http-listen-port> to be a number from 1 to 65535.  Got: " + jq(argPort) + ".");
            System.exit(1); return;
        }

        String appKey = args[1];
        String appSecret = args[2];
        YfyHost testHost = new YfyHost("platform.fangcloud.net", "oauth-server.fangcloud.net");
        YfyAppInfo.initAppInfo(appKey, appSecret, testHost);

        File dbFile = new File(args[3]);

        try {
            WebHandler handler = new WebHandler(dbFile);
            Server server = new Server(port);
            SessionHandler sessionHandler = new SessionHandler();
            sessionHandler.setServer(server);
            sessionHandler.setHandler(handler);
            server.setHandler(sessionHandler);

            server.start();
            System.out.println("Server running: http://localhost:" + port + "/");

            server.join();
        } catch (Exception ex) {
            System.err.println("Error running server: " + ex.getMessage());
            System.exit(1);
        }
    }

}
