package src.main.java.org.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public class BlockHeader {
    private String merkleRoot;
    private Integer nonce = 0;
    private String previousHash;
    private String hash;
    private final String timeStamp;

    public String getHash() {
        return hash;
    }



    public BlockHeader(String previousHash, String merkleRoot) {
        this.previousHash = previousHash;
        this.merkleRoot = merkleRoot;
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }

    public BlockHeader(String merkleRoot) {
        this.merkleRoot = merkleRoot;
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public  static  String calculateBlockHash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }


    public void mineBlock(int blockHeight, int difficulty) throws NoSuchAlgorithmException {

        while (true){
            String hash = calculateBlockHash(blockHeight + nonce + previousHash + timeStamp);
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

    public Integer getNonce() {
        return nonce;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
