package org.example;


import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import src.main.java.org.example.BlockHeader;

public class Blockchain {

    private final int difficulty;

    public List<Block> getChain() {
        return chain;
    }

    private final List<Block> chain = new ArrayList<>();

    public Blockchain(int difficulty) throws NoSuchAlgorithmException, JsonProcessingException {
        this.difficulty = difficulty;
        genesisBlock();
    }

    private void genesisBlock() throws NoSuchAlgorithmException, JsonProcessingException {
        addBlock(0);
    }

    public String addBlock(int blockHeight) throws NoSuchAlgorithmException, JsonProcessingException {
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

