package jdev.novid.component.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.jasypt.properties.PropertyValueEncryptionUtils;

public class JasyptCrypto {

    private static final Logger LOG = LogManager.getLogger(JasyptCrypto.class);

    private StandardPBEStringEncryptor encryptor;

    public JasyptCrypto(String envName) {

        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        config.setAlgorithm("PBEWithMD5AndDES");

        String password = System.getenv(envName);

        if (password == null)
            password = System.getProperty(envName);

        LOG.debug("Jasypt password : {}", password);

        config.setPassword(password);

        this.encryptor = new StandardPBEStringEncryptor();
        this.encryptor.setConfig(config);

    }

    public String decrypt(String encrypted) {

        return PropertyValueEncryptionUtils.decrypt(encrypted, this.encryptor);

    }

    public String encrypt(String plain) {

        return PropertyValueEncryptionUtils.encrypt(plain, this.encryptor);

    }

}
