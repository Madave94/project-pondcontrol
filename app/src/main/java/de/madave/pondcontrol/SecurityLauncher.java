package de.madave.pondcontrol;

import android.app.Application;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class SecurityLauncher extends Application {

    public SecurityLauncher() {
        super();
        trustEveryone();
    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");

            X509TrustManager trustManager = new X509TrustManager()
            {
                public void checkClientTrusted(X509Certificate[] chain, String authType) { }
                public void checkServerTrusted(X509Certificate[] chain, String authType) { }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }};

            context.init(null, new X509TrustManager[]{trustManager}, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}
