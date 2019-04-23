import accessControl.DataKeeper;
import accessControl.Policy;
import accessControl.Record;
import accessControl.ThirdEntity;
import blockchain.Blockchain;
import blockchain.ConsensusLevel;
import blockchain.PermissionLevel;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        DataKeeper master = null;
        DataKeeper hospital = null;
        DataKeeper athlete = null;
        try {
            master = new DataKeeper("Master");
            hospital = new DataKeeper("Hospital");
            athlete = new DataKeeper("High tech athlete");
        } catch (Exception e) {e.printStackTrace();}
        List<DataKeeper> dk = new ArrayList<DataKeeper>();
        dk.add(master);
        dk.add(hospital);
        dk.add(athlete);

        List<Policy> policies = new ArrayList<Policy>();
        List<Record> records = new ArrayList<Record>();
        Record[] r = {null, null, null, null, null};
        Policy[] p = {null, null, null, null, null};

        try {
            r[0] = new Record(dk, ConsensusLevel.ALL, "ems-solutions.com",
                    "blog/your-medical-record-is-worth-more-to-hackers-than-your-credit-card/",
                    "SSN:123-456-7890",master.getPrivateKey());
            p[0] = new Policy(new ThirdEntity("Webmaster"), r[0], PermissionLevel.READ);
            r[1] = new Record(dk, ConsensusLevel.ALL, "University Hospital",
                    "Brain transplant", "/health/patient",
                    master.getPrivateKey());
            p[1] = new Policy(new ThirdEntity("Webmaster"), r[1], PermissionLevel.READWRITE);
            r[2] = new Record(dk, ConsensusLevel.ALL, "students.utsa.edu",
                    "/student/health/allergies",
                    "Allergy to pollen", master.getPrivateKey());
            p[2] = new Policy(new ThirdEntity("Nurse"), r[2], PermissionLevel.WRITE);
            r[3] = new Record(dk, ConsensusLevel.ALL, "health.gov",
                    "/health/patient/treatment",
                    "Surgery in knee" , master.getPrivateKey());
            p[3] = new Policy(new ThirdEntity("Webmaster"), r[3], PermissionLevel.WRITE);
            r[4] = new Record(dk, ConsensusLevel.ALL, "Apple watch",
                    "sportdata.com","80 beats per/minute",
                    master.getPrivateKey());
            p[4] = new Policy(new ThirdEntity("Webmaster"), r[4], PermissionLevel.READWRITE);
        }catch (Exception e) {e.printStackTrace();}
        for(int i=0;i<r.length;i++) {
            records.add(r[i]);
            policies.add(p[i]);
        }
        List<Policy> p1 = new ArrayList<>();
        p1.add(p[1]);
        Blockchain blockchain = new Blockchain(3, ConsensusLevel.ALL);
        blockchain.addBlock(blockchain.newBlock(hospital.getPrivateKey(), records, policies));
        try {
//            System.out.println(policies.get(2).getRecord().getData());  //Bad permission to read data
            policies.get(0).getRecord().setData("Trying to write a read-only data");
            System.out.println(policies.get(0).getRecord().getData());
        } catch (Policy.NotEnoughPermission notEnoughPermission) {
            notEnoughPermission.printStackTrace();}
        blockchain.addBlock(blockchain.newBlock(master.getPrivateKey(), null, null));
        blockchain.addBlock(blockchain.newBlock(master.getPrivateKey(), null, p1));
//        blockchain.addBlock(blockchain.newBlock("Kevin Barba"));

        System.out.println("is Blockchain valid? " + blockchain.isBlockChainValid());
        System.out.println(blockchain);
    }
}
