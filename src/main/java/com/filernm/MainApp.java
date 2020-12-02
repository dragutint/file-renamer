package com.filernm;

import com.filernm.bl.FileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainApp implements CommandLineRunner {
    @Autowired
    private FileProcessor fileProcessor;

    @Override
    public void run(String... args) throws Exception {
        String pathForTesting = "/Users/dragutintodorovic/Guta/test";

        fileProcessor.renameAllFiles(pathForTesting);
    }
}
