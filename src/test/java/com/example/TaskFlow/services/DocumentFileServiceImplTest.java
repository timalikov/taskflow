package com.example.TaskFlow.services;

import com.example.TaskFlow.entities.DocumentFile;
import com.example.TaskFlow.entities.Task;
import com.example.TaskFlow.exceptions.BadRequestException;
import com.example.TaskFlow.properties.UploadProperties;
import com.example.TaskFlow.repositories.DocumentFileRepository;
import com.example.TaskFlow.repositories.TaskRepository;
import com.example.TaskFlow.services.impl.DocumentFileServiceImpl;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentFileServiceImplTest {

    @Mock
    private GridFsTemplate gridFsTemplate;

    @Mock
    private DocumentFileRepository documentFileRepository;

    @Mock
    private UploadProperties uploadProperties;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private DocumentFileServiceImpl documentFileService;

    @Captor
    ArgumentCaptor<DocumentFile> captor;

    private String fileName;
    private Long taskId;
    private String contentType;
    private byte[] fileContent;
    private MultipartFile mockFile;
    private ObjectId mockObjectId;

    @BeforeEach
    void setUp() {
        taskId = 10L;
        fileName = "test.pdf";
        contentType = "application/pdf";
        fileContent = "Hello".getBytes();

        mockFile = new MockMultipartFile(fileName, fileName, contentType, fileContent);

        mockObjectId = new ObjectId();
    }

    @Test
    void shouldSaveDocument() throws IOException {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(new Task()));
        when(uploadProperties.getAllowedFileTypes()).thenReturn(List.of("application/pdf", "image/png"));
        when(gridFsTemplate.store(any(InputStream.class), eq(fileName), eq(contentType))).thenReturn(mockObjectId);

        when(documentFileRepository.save(captor.capture())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        var saved = documentFileService.saveDocument(taskId, mockFile);
        verify(documentFileRepository).save(captor.capture());
        var captured = captor.getValue();

        assertNotNull(saved);
        assertEquals(fileName, captured.getFileName());
        assertEquals(contentType, captured.getFileType());
        assertEquals(taskId, captured.getTaskId());
        assertEquals(fileContent.length, captured.getSize());

        verify(gridFsTemplate).store(any(InputStream.class), eq(fileName), eq(contentType));
        verify(documentFileRepository).save(any(DocumentFile.class));
    }

    @Test
    void shouldThrowWhenFileIsNullOrEmpty() {
        var emptyFile = new MockMultipartFile("file", new byte[0]);

        var exception = assertThrows(BadRequestException.class,
                () -> documentFileService.saveDocument(1L, emptyFile));

        assertEquals("File is empty or null", exception.getMessage());
        verifyNoInteractions(gridFsTemplate, documentFileRepository);
    }

    @Test
    void shouldThrowWhenContentTypeNotAllowed() {
        var file = new MockMultipartFile("test.exe", "test.exe", "application/x-msdownload", new byte[]{1, 2, 3});

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(new Task()));
        when(uploadProperties.getAllowedFileTypes()).thenReturn(List.of("application/pdf"));

        var exception = assertThrows(BadRequestException.class,
                () -> documentFileService.saveDocument(taskId, file));

        assertEquals("File type is not supported", exception.getMessage());
        verifyNoInteractions(gridFsTemplate, documentFileRepository);
    }


    @Test
    void shouldDeleteDocumentById() {
        var fileId = mockObjectId.toHexString();
        var mockGridFsFile = mock(GridFSFile.class);

        when(gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(mockObjectId))))
                .thenReturn(mockGridFsFile);

        documentFileService.deleteDocument(fileId);

        verify(gridFsTemplate).findOne(Query.query(Criteria.where("_id").is(mockObjectId)));
        verify(gridFsTemplate).delete(Query.query(Criteria.where("_id").is(mockObjectId)));
        verify(documentFileRepository).deleteById(mockObjectId);
    }
}
