package main;
import blockchain.Block;
import blockchain.Blockchain;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        Blockchain blockchain = new Blockchain(4);
        blockchain.addBlock(1);
        blockchain.addBlock(2);
        blockchain.addBlock(3);
        System.out.println(blockchain.addBlock(4));

        for (Block block : blockchain.getChain()) {
            System.out.println("##############################");
            System.out.println("Index: " + block.getBlockHeight());
            System.out.println("Nonce: " + block.getBlockHeader().getNonce());
            System.out.println("Transaction: " + block.getTransaction());
            System.out.println("MerkleRoot: " + block.getBlockHeader().getMerkleRoot());
            System.out.println("##############################");
            System.out.println();

        }

    }
}
