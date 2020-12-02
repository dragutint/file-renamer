package com.filernm.bl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.file.FileTypeDirectory;
import com.drew.metadata.iptc.IptcDirectory;
import com.filernm.model.ParsedFile;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class MetadataFileParser {

    public ParsedFile parse(String pathToImg) throws ImageProcessingException, IOException {
        File file = new File(pathToImg);
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        ExifSubIFDDirectory exifDir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        FileTypeDirectory fileTypeDir = metadata.getFirstDirectoryOfType(FileTypeDirectory.class);

        ParsedFile pi = new ParsedFile();
        pi.setFile(file);
        pi.setDateCreated(exifDir.getDateOriginal());
        pi.setExtension(fileTypeDir.getString(FileTypeDirectory.TAG_EXPECTED_FILE_NAME_EXTENSION));

        return pi;
    }

    public void printAllMetadataAttrs(String pathToImg) throws ImageProcessingException, IOException {
        File file = new File(pathToImg);
        Metadata metadata = ImageMetadataReader.readMetadata(file);

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }
    }


}
