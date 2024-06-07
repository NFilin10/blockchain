import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private final int difficulty;

    public List<Block> getChain() {
        return chain;
    }

    private final List<Block> chain = new ArrayList<>();

    public Blockchain(int difficulty) throws NoSuchAlgorithmException {
        this.difficulty = difficulty;
        genesisBlock();
    }

    private void genesisBlock() throws NoSuchAlgorithmException {
        addBlock(0);
    }

    public void addBlock(int blockHeight) throws NoSuchAlgorithmException {
        BlockHeader blockHeader;
        String transaction;

        if (!chain.isEmpty()){
            Block lastBlock = chain.getLast();
            transaction = "t" + blockHeight + " " + (lastBlock.getBlockHeight() + 1);
            blockHeader = new BlockHeader(lastBlock.getBlockHeader().getHash());
            //Genesis block
        } else {
            transaction = "t0";
            blockHeader = new BlockHeader();
        }

        blockHeader.mineBlock(blockHeight, difficulty);
        chain.add(new Block(blockHeight, blockHeader, transaction, 1));


        boolean chainValidity = checkChainValidity();
        if (chainValidity){
            System.out.println("VALID");
        } else {
            System.out.println("ERROR");
            chain.remove(chain.getLast());
        }
    }


    private boolean checkChainValidity() throws NoSuchAlgorithmException {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            BlockHeader currentBlockHeader = current.getBlockHeader();
            Block previous = chain.get(i - 1);
            BlockHeader previousBlockHeader = previous.getBlockHeader();

            String hash = currentBlockHeader.calculateBlockHash(current.getBlockHeight(), currentBlockHeader.getNonce(), currentBlockHeader.getPreviousHash(), currentBlockHeader.getTimeStamp());

            if (!currentBlockHeader.getHash().equals(hash)){
                return false;
            }
            else if (!previousBlockHeader.getHash().equals(currentBlockHeader.getPreviousHash())) {
                return false;
            }
        }
        return true;
    }

}

