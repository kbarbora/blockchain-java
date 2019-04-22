package accessControl;

import blockchain.GenerateKeys;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

public class DataKeeper extends Entity {

    private PrivateKey prk;
    private PublicKey puk;

    public DataKeeper( String name){
        super(name);
        GenerateKeys gen = null;
        try {
            gen = new GenerateKeys(256);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        prk = gen.getPrivateKey();
        puk = gen.getPublicKey();
    }

    public PrivateKey getPrivateKey(){
        return prk;
    }

    public PublicKey getPublicKey(){
        return puk;
    }
}
