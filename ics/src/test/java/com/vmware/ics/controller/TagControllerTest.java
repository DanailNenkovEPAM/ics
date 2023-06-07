package com.vmware.ics.controller;

import com.vmware.ics.dto.TagDTO;
import com.vmware.ics.model.Tag;
import com.vmware.ics.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TagControllerTest {

    @InjectMocks
    private TagController tagController;

    @Mock
    private TagService tagService;

    @Test
    public void testGetAllTagsController() {
        when(tagService.getAllTags()).thenReturn(Arrays.asList(new Tag(), new Tag()));
        List<Tag> tags = tagController.getAllTagsController();
        verify(tagService, times(1)).getAllTags();
        assertEquals(2, tags.size());
    }

}

