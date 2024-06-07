import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public class BlockHeader {
    public Integer getNonce() {
        return nonce;
    }

    private Integer nonce = 0;

    public String getPreviousHash() {
        return previousHash;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    private String previousHash;
    private String hash;
    private final String timeStamp;

    public String getHash() {
        return hash;
    }



    public BlockHeader(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }

    public BlockHeader() {
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }

    public String calculateBlockHash(int index, int nonce, String previousHash, String timeStamp) throws NoSuchAlgorithmException {
        String dataToHash = index + nonce + previousHash + timeStamp;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                dataToHash.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }


    public void mineBlock(int blockHeight, int difficulty) throws NoSuchAlgorithmException {

        while (true){
            String hash = calculateBlockHash(blockHeight, nonce, previousHash, timeStamp);
            if (hash.substring(0, difficulty).equals("0".repeat(difficulty))){
                this.hash = hash;
                return ;
            }
            nonce++;
        }
    }


    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
