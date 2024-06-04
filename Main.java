import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Blockchain blockchain = new Blockchain(4);
        blockchain.addBlock("Block 1 Data");
        blockchain.addBlock("Block 2 Data");
        blockchain.addBlock("Block 3 Data");
        blockchain.addBlock("Block 4 Data");

        for (Block block : blockchain.getChain()) {
            System.out.println("##############################");
            System.out.println("Index: " + block.getIndex());
            System.out.println("Nonce: " + block.getNonce());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Prev hash: " + block.getPreviousHash());
            System.out.println("Time: " + block.getTimeStamp());
            System.out.println("Data: " + block.getData());
            System.out.println("##############################");
            System.out.println();

        }

    }
}
