package com.inHisPresence;

import com.inHisPresence.model.File;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Betsy on 4/19/2016.
 */
public class FileTests {

    private File file;

    public FileTests() {
        this.file = new File();
    }

    @Test
    public void testId() {
        file.setId(22);
        assertEquals(22, file.getId());
    }

    @Test
    public void testFileName() {
        file.setFileName("File01.txt");
        assertTrue(file.getFileName().equals("File01.txt"));
    }

    @Test
    public void testFileTitle() {
        file.setFileTitle("This is the title.");
        assertTrue(file.getFileTitle().equals("This is the title."));
    }

    @Test
    public void testFileIsFavorite() {
        file.setIsFavorite(false);
        assertEquals(false, file.getIsFavorite());
        file.setIsFavorite(true);
        assertEquals(true, file.getIsFavorite());
    }

    @Test
    public void testFileTag() {
        file.setFileTagId(200);
        assertEquals(200, file.getFileTagId());
    }

}
