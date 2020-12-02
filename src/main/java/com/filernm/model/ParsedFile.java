package com.filernm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParsedFile {
    private File file;
    private String name;
    private Date dateCreated;
    private String extension;

}
