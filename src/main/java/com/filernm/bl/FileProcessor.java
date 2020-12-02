package com.filernm.bl;

import com.drew.imaging.ImageProcessingException;
import com.filernm.model.ParsedFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class FileProcessor {
    @Autowired
    private MetadataFileParser metadataFileParser;
    @Autowired
    private FileNameGenerator fileNameGenerator;

    public void renameAllFiles(String folderPath){
        try (Stream<Path> walk = Files.walk(Paths.get(folderPath))) {
            List<String> result = walk
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());

            for(String s : result) {
                File f = new File(s);
                String mimeType = new MimetypesFileTypeMap().getContentType(f);
                if(mimeType != null && mimeType.split("/")[0].equals("image")) {
                    ParsedFile pf = metadataFileParser.parse(s);
                    processFile(pf);
//                    metadataFileParser.printAllMetadataAttrs(s);
                }
            }

        } catch (IOException | ImageProcessingException e) {
            log.error("Got exception: ", e);
        }
    }

    private void processFile(ParsedFile pf) {
        pf.setName(fileNameGenerator.generate(pf));

        try {
            Path path = pf.getFile().toPath();
            Files.move(path, path.resolveSibling(pf.getName()));
        } catch (IOException e) {
            log.error("Got exception, ex: ", e);
        }
    }
}

