package com.inHisPresence;

import com.inHisPresence.model.Tag;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Betsy on 4/19/2016.
 */
public class TagTests {

    private Tag tag;

    public TagTests(){
        tag = new Tag();
    }

    @Test
    public void testTagId() {
        tag.setId(22);
        assertEquals(22, tag.getId());
    }

    @Test
    public void testTagName() {
        tag.setTagName("Joy");
        assertEquals("Joy", tag.getTagName());
    }
}
