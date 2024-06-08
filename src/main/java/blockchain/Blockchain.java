package blockchain;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.Database;

public class Blockchain {

    private final int difficulty;

    public List<Block> getChain() {
        return chain;
    }

    private final List<Block> chain = new ArrayList<>();

    public Blockchain(int difficulty) throws NoSuchAlgorithmException, IOException {
        this.difficulty = difficulty;
        genesisBlock();
    }

    private void genesisBlock() throws NoSuchAlgorithmException, IOException {
        addBlock(0);
    }

    public String addBlock(int blockHeight) throws NoSuchAlgorithmException, IOException {
        BlockHeader blockHeader;
        String transaction;
        String merkleRoot;

        if (!chain.isEmpty()){
            Block lastBlock = chain.getLast();
            transaction = "t" + blockHeight + " " + (lastBlock.getBlockHeight() + 1);
            merkleRoot = BlockHeader.calculateBlockHash(transaction);
            blockHeader = new BlockHeader(lastBlock.getBlockHeader().getHash(), merkleRoot);
            //Genesis block
        } else {
            transaction = "t0";
            merkleRoot = BlockHeader.calculateBlockHash(transaction);
            blockHeader = new BlockHeader(merkleRoot);
        }

        blockHeader.mineBlock(blockHeight, difficulty);
        chain.add(new Block(blockHeight, blockHeader, transaction, 1));

        boolean chainValidity = checkChainValidity();
        if (chainValidity){
            System.out.println("VALID");
            ObjectMapper mapper = new ObjectMapper();
            Database.writeToFile(chain, mapper);
            return mapper.writeValueAsString(chain);
        } else {
            System.out.println("ERROR");
            chain.remove(chain.getLast());
        }
        return null;
    }


    private boolean checkChainValidity() throws NoSuchAlgorithmException {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            BlockHeader currentBlockHeader = current.getBlockHeader();
            Block previous = chain.get(i - 1);
            BlockHeader previousBlockHeader = previous.getBlockHeader();

            String hash = BlockHeader.calculateBlockHash(current.getBlockHeight() + currentBlockHeader.getNonce() + currentBlockHeader.getPreviousHash() + currentBlockHeader.getTimeStamp());

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

