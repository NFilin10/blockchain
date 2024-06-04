import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    public List<Block> getChain() {
        return chain;
    }

    private final List<Block> chain = new ArrayList<>();
    private final int difficulty;

    public Blockchain(int difficulty) {
        chain.add(Block.genesisBlock());
        this.difficulty = difficulty;
    }

    public void addBlock(String data) throws NoSuchAlgorithmException {
        Block lastBlock = chain.getLast();
        Block newBlock = Block.mineBlock(lastBlock, data, difficulty);
        chain.add(newBlock);
    }


    private boolean checkChainValidity() throws NoSuchAlgorithmException {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            if (!current.getHash().equals(Block.calculateBlockHash(current.getIndex(), current.getIndex(), current.getPreviousHash(), current.getData(), current.getTimeStamp()))){
                return false;
            } else if (!previous.getHash().equals(current.getPreviousHash())) {
                return false;
            }
        }
        return true;
    }

}

