import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public class Block {
    private final int index;
    private final String nonce;
    private String previousHash;
    private final String hash;
    private final String timeStamp;
    private final String data;

    public int getIndex() {
        return index;
    }

    public String getNonce() {
        return nonce;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getData() {
        return data;
    }

    public Block(int index, String nonce, String previousHash, String hash, String data) {
        this.index = index;
        this.nonce = nonce;
        this.previousHash = previousHash;
        this.hash = hash;
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.data = data;
    }

    public Block(int index, String nonce, String hash, String data) {
        this.index = index;
        this.nonce = nonce;
        this.hash = hash;
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.data = data;
    }

     public static String calculateBlockHash(int index, int nonce, String previousHash, String data, String timeStamp) throws NoSuchAlgorithmException {
        String dataToHash = index + nonce + previousHash + data + timeStamp;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                dataToHash.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    public static Block genesisBlock(){
        return new Block(0, "0", "Genesis block hash", "Genesis block");
    }

    public static Block mineBlock(Block lastBlock, String data, int difficulty) throws NoSuchAlgorithmException {
        int index = lastBlock.index + 1;
        String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        String previousHash = lastBlock.hash;
        int nonce = 0;

        while (true){
            String hash = calculateBlockHash(index, nonce, previousHash, data, time);
            if (hash.substring(0, difficulty).equals("0".repeat(difficulty))){
                return new Block(index, String.valueOf(nonce), previousHash, hash, data);
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
