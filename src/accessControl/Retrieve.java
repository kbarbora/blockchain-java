package accessControl;

import blockchain.PermissionLevel;

import java.security.PrivateKey;

public class Retrieve {
    private String filePath;
    private Policy policy;
    private PrivateKey dataKeeperKey;
    private String data;

    public Retrieve(String filePath, Record record, String data, Policy policy, PrivateKey prikey){
        this.filePath = filePath;
        this.data = data;
        this.policy = policy;
        this.dataKeeperKey = prikey;
    }

    public String getData(){

        if(policy.getLevel() == PermissionLevel.READ){
            return data;
        }
        return "";
    }
}
