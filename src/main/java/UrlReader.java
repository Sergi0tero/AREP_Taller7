import jdk.nashorn.api.scripting.URLReader;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.*;

//webapp123
public class UrlReader {
    public static void main(String[] args) {
        secure(getKeyStore(), getPwdKeyStore(), null, null);
        port(getPort());
        try {

            // Create a file and a password representation
            File trustStoreFile = new File("target/certificate/myTrustStore");
            char[] trustStorePassword = "webapp123".toCharArray();

            // Load the trust store, the default type is "pkcs12", the alternative is "jks"
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

            // Get the singleton instance of the TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());

            // Itit the TrustManagerFactory using the truststore object
            tmf.init(trustStore);

            //Print the trustManagers returned by the TMF
            //only for debugging
            for(TrustManager t: tmf.getTrustManagers()){
                System.out.println(t);
            }

            //Set the default global SSLContext so all the connections will use it
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);

            // We can now read this URL
            get("/hello", (req, res) -> {
                return readURL("https://ec2-18-233-67-132.compute-1.amazonaws.com:5000/hello");
            });


            // This one can't be read because the Java default truststore has been
            // changed.
//            readURL("https://www.google.com");

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException |
                 KeyManagementException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ArrayList<String> readURL(String sitetoread) {
        try {
            // Crea el objeto que representa una URL2
            URL siteURL = new URL(sitetoread);
            // Crea el objeto que URLConnection
            URLConnection urlConnection = siteURL.openConnection();
            // Obtiene los campos del encabezado y los almacena en un estructura Map
            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            // Obtiene una vista del mapa como conjunto de pares <K,V>
            // para poder navegarlo
            Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
            // Recorre la lista de campos e imprime los valores
            for (Map.Entry<String, List<String>> entry : entrySet) {
                String headerName = entry.getKey();

                //Si el nombre es nulo, significa que es la linea de estado
                if (headerName != null) {
                    System.out.print(headerName + ":");
                }
                List<String> headerValues = entry.getValue();
                for (String value : headerValues) {
                    System.out.print(value);
                }
                System.out.println("");
            }

            System.out.println("-------message-body------");
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine = null;
            ArrayList<String> inputList = new ArrayList<>();
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                inputList.add(inputLine);
            }
            return inputList;
        } catch (IOException x) {
            System.err.println(x);
        }
        return null;
    }

    private static String getPwdKeyStore() {
        if (System.getenv("PWDKEYSTORE") != null) {
            return (System.getenv("PWDKEYSTORE")).toString();
        }
        return "webapp123";
    }

    private static String getKeyStore() {
        if (System.getenv("KEYSTORE") != null) {
            return (System.getenv("KEYSTORE")).toString();
        }
        return "target/certificate/webappkeystore.p12";
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5001; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
