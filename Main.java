import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Blockchain blockchain = new Blockchain(4);
        blockchain.addBlock(1);
        blockchain.addBlock(2);
        blockchain.addBlock(3);
        blockchain.addBlock(4);

        for (Block block : blockchain.getChain()) {
            System.out.println("##############################");
            System.out.println("Index: " + block.getBlockHeight());
            System.out.println("Nonce: " + block.getBlockHeader().getNonce());
            System.out.println("Transaction: " + block.getTransaction());
            System.out.println("##############################");
            System.out.println();

        }

    }
}
