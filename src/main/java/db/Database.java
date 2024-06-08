package db;

import blockchain.Block;
import blockchain.Blockchain;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Database {
    public static void writeToFile(List<Block> chain, ObjectMapper mapper) throws IOException {
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File("blockchain.json"), chain);
    }

    public static void readFromFile(ObjectMapper mapper) throws IOException {
        Blockchain blockchain = mapper.readValue(new File("blockchain.json"), Blockchain.class);
        System.out.println(blockchain.getChain());

    }
}
