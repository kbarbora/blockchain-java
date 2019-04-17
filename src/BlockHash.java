import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BlockHash {

    public static String calculateHash(Block block) {
        if (block != null) {
            MessageDigest digest = null;

            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
            String txt = block.str();
            final byte bytes[] = digest.digest(txt.getBytes());
            final StringBuilder builder = new StringBuilder();
            for(final byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1){
                    builder.append('0');
                }
                builder.append(hex);
            }
            return builder.toString();
        }
        return null;
    }
}
