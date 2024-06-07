import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public class Block {

    private final int blockHeight;
//    private final int blockSize;
    private final BlockHeader blockHeader;
    private final String transaction;
    private final int transactionCount;


    public int getBlockHeight() {
        return blockHeight;
    }

    public BlockHeader getBlockHeader() {
        return blockHeader;
    }

    public String getTransaction() {
        return transaction;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public Block(int blockHeight, BlockHeader blockHeader, String transaction, int transactionCount) {
        this.blockHeight = blockHeight;
        this.blockHeader = blockHeader;
        this.transaction = transaction;
        this.transactionCount = transactionCount;
    }
}
