import static spark.Spark.*;

public class Api {
    public static void main(String[] args) {
        //keytool -genkeypair -alias apikeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore apikeystore.p12 -validity 3650
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath,truststorePassword);
        //keytool -export -keystore certificate/apikeystore.p12 -alias apikeypair -storetype PKCS12 -file apicert.cer
        //keytool -import -storetype PKCS12 -file certificate/apicert.cer -alias apiCA -keystore myTrustStore
        secure(getKeyStore(), getPwdKeyStore(), null, null);
        port(getPort());
        get("/hello", (req, res) -> "Hello World");
    }

    private static String getPwdKeyStore() {
        if (System.getenv("PWDKEYSTORE") != null) {
            return (System.getenv("PWDKEYSTORE")).toString();
        }
        return "api123";
    }

    private static String getKeyStore() {
        if (System.getenv("KEYSTORE") != null) {
            return (System.getenv("KEYSTORE")).toString();
        }
        return "target/certificate/apikeystore.p12";
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
