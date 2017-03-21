
//insert your package name

import android.annotation.TargetApi;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;
import android.widget.Toast;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import javax.security.auth.x500.X500Principal;
import javax.security.cert.CertificateException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class KeyStoreModule extends ReactContextBaseJavaModule{


    final protected static char[] hexArray = "0123456789abcdef".toCharArray();

    public TestModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "TestModule";
    }

	
	//Min SDK version = 18
    @TargetApi(Build.VERSION_CODES.M)
    @ReactMethod
    public void generateKeys(Callback errorCallback)  {
        KeyPairGenerator kpGenerator;
        try {
            Calendar start = GregorianCalendar.getInstance();
            Calendar end = GregorianCalendar.getInstance();
            end.add(Calendar.YEAR, 10);
            kpGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
            kpGenerator.initialize(new KeyPairGeneratorSpec.Builder(getReactApplicationContext())
                    .setEncryptionRequired()
                    .setAlias("pushe")
                    .setSerialNumber(BigInteger.valueOf(1))
                    .setStartDate(start.getTime())
                    .setEndDate(end.getTime())
                    .setSubject(new X500Principal("CN=Pushe_CA"))
                    .build());
            KeyPair keyPair = kpGenerator.generateKeyPair();

        } catch (NoSuchAlgorithmException |
                NoSuchProviderException | InvalidAlgorithmParameterException e) {

			errorCallback.invoke("pair key not created");
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
       
			errorCallback.invoke("pair key not created");
        }
    }

	//sign data with private key
    @ReactMethod
    private void signData(String msg, Callback errorCallback, Callback succedCallback) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyStore.Entry keyEntry = keyStore.getEntry("pushe", null);
            if (!(keyEntry instanceof KeyStore.PrivateKeyEntry)) {

            } else {
                Signature s = Signature.getInstance("SHA256withRSA");
                s.initSign(((KeyStore.PrivateKeyEntry) keyEntry).getPrivateKey());
                s.update(msg.getBytes("UTF-8"));
                byte[] signature = s.sign();
                String encrypted = bytesToHex(signature);

                succedCallback.invoke(encrypted);
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException | SignatureException |
                IOException | java.security.cert.CertificateException | InvalidKeyException e) {
            errorCallback.invoke("encrypt not created");

        }
    }

	//get public key (Base64) 
    @ReactMethod
    public void getPublicCerts(Callback succeed , Callback error) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyStore.Entry keyEntry;
            keyEntry = keyStore.getEntry("pushe", null);
            if (!(keyEntry instanceof KeyStore.PrivateKeyEntry)) {
                error.invoke("private key");
            } else {
                java.security.cert.Certificate certificate = ((KeyStore.PrivateKeyEntry) keyEntry).getCertificate();
                succeed.invoke(Base64.encodeToString(certificate.getPublicKey().getEncoded(), Base64.DEFAULT));
            }
        } catch (KeyStoreException | NoSuchAlgorithmException
                | UnrecoverableEntryException | IOException e) {
         
            error.invoke(e.getMessage());
        } catch (IllegalStateException e) {
            throw e;
        } catch (java.security.cert.CertificateException e) {        
            error.invoke(e.getMessage());
        }
    }

	
	//get public key finger print (MD5)
    @ReactMethod
    private  void getMD5(Callback succeed ){
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyStore.Entry keyEntry;
            keyEntry = keyStore.getEntry("pushe", null);
            if ((keyEntry instanceof KeyStore.PrivateKeyEntry)) {
                java.security.cert.Certificate certificate = ((KeyStore.PrivateKeyEntry) keyEntry).getCertificate();
                String pk = Base64.encodeToString(certificate.getPublicKey().getEncoded(), Base64.DEFAULT);
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(pk.getBytes());
                byte[] digest = md.digest();
                StringBuffer sb = new StringBuffer();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b & 0xff));
                }

                succeed.invoke(sb.toString());
            }
        } catch (KeyStoreException | NoSuchAlgorithmException
                | UnrecoverableEntryException | IOException e) {
            e.printStackTrace();

        } catch (java.security.cert.CertificateException e) {
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
