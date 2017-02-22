package com.fangcloud.sdk.example.web_demo;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyHost;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import static com.fangcloud.sdk.util.StringUtil.jq;

/**
 * An web demo that uses the Fangcloud API to let users get info about their files
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Properties prop = loadProp();

        String argPort = prop.getProperty("port");
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

        String appKey = prop.getProperty("client_id");
        String appSecret = prop.getProperty("client_secret");
        YfyHost testHost = new YfyHost("platform.fangcloud.net", "oauth-server.fangcloud.net");
        YfyAppInfo.initAppInfo(appKey, appSecret, testHost);

        File dbFile = new File(prop.getProperty("db_file"));

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

    private static Properties loadProp() throws Exception {
        String configFileName = "/config.properties";
        Properties prop = new Properties();
        InputStream in = Main.class.getResourceAsStream(configFileName);
        prop.load(in);
        return prop;
    }

}
