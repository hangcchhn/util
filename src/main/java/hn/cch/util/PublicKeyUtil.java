package hn.cch.util;

import hn.cch.util.BytesUtil;
import hn.cch.util.StreamUtil;
import hn.cch.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class PublicKeyUtil {


    private static String KeyStoreType = "PKCS12";

    private static String CertificateFactoryType = "X.509";

    private static Logger logger = LoggerFactory.getLogger(PublicKeyUtil.class);


    public static boolean verify(String filePath, String signData, String jsonText) {

        try {

            // CertificateFactory certificateFactory = CertificateFactory.getInstance(CertificateFactoryType);
            // InputStream inputStream = StreamUtil.toInput(new File(filePath));
            // X509Certificate x509Certificate = (X509Certificate)certificateFactory.generateCertificate(inputStream);
            // String sigAlg = x509Certificate.getSigAlgName();
            // Signature signature = Signature.getInstance(sigAlg);
            // signature.initVerify(x509Certificate);
            // signature.update(StringUtil.toBytes(jsonText));
            // return signature.verify(BytesUtil.fromHex(signData));


            CertificateFactory certificateFactory = CertificateFactory.getInstance(CertificateFactoryType);
            InputStream inputStream = StreamUtil.toInput(new File(filePath));
            X509Certificate x509Certificate = (X509Certificate)certificateFactory.generateCertificate(inputStream);

            PublicKey publicKey = x509Certificate.getPublicKey();

            String sigalg = x509Certificate.getSigAlgName();
            Signature signature = Signature.getInstance(sigalg);

            signature.initVerify(publicKey);
            signature.update(StringUtil.toBytes(jsonText));
            return signature.verify(BytesUtil.fromHex(signData));


        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean verify1(String filePath, String passWord, String jsonText){

        try {

            KeyStore keyStore = KeyStore.getInstance(KeyStoreType);
            InputStream inputStream = StreamUtil.toInput(new File(filePath));


            //
            //         KeyStore keyStore = KeyStore.getInstance(KeyStoreType);
            //
            //         CertificateFactory certificateFactory = CertificateFactory.getInstance(CertificateFactoryType);
            //         InputStream inputStream = StreamUtil.toInput(new File(filePath));
            //         X509Certificate x509Certificate = (X509Certificate)certificateFactory.generateCertificate(inputStream);
            //

            keyStore.load(inputStream, passWord.toCharArray());
            Enumeration<String> aliases = keyStore.aliases();

            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                Key key = keyStore.getKey(alias, passWord.toCharArray());

                if (key instanceof PublicKey) {

                    logger.info("PublicKey");

                    PublicKey publicKey = (PublicKey) key;
                    X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(alias);
                    String sigalg = x509Certificate.getSigAlgName();
                    Signature signature = Signature.getInstance(sigalg);
                    signature.initVerify(publicKey);
                    signature.update(BytesUtil.fromHex(jsonText));
                    return signature.verify(StringUtil.toBytes(jsonText));
                }

                if (key instanceof PrivateKey) {

                    logger.info("PrivateKey");

                    // PrivateKey privateKey = (PrivateKey) key;
                    // X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(alias);
                    // String sigalg = x509Certificate.getSigAlgName();
                    // Signature signature = Signature.getInstance(sigalg);
                    // signature.initSign(privateKey);
                    // signature.update(StringUtil.toBytes(jsonText));
                    // return BytesUtil.toHex(signature.sign());

                }
            }

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
