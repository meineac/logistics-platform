package utils.export.decorators;

import models.LogisticsData;
import utils.export.format.DataExporter;

import javax.crypto.*;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptingDataExporter extends DataExporterDecorator {
    private final SecretKey key;

    public EncryptingDataExporter(DataExporter exporter, SecretKey key) {
        super(exporter);
        this.key = key;
    }

    @Override
    public void export(LogisticsData data, OutputStream out) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            CipherOutputStream cos = new CipherOutputStream(out, cipher);

            super.export(data, cos);

            cos.flush();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Fatal error: AES encryption algorithm not found.", e);
        } catch (NoSuchPaddingException e) {
            throw new IllegalStateException("Fatal error: Required padding mechanism for AES encryption is not supported.", e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("Configuration error: The provided secret key is invalid or incompatible with the AES cipher.", e);
        }
    }
}