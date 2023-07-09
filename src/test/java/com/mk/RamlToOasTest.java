package com.mk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.PrintWriter;

public class RamlToOasTest {

    @Mock
    File tempDir;

    @Mock
    PrintWriter printWriter;

    @InjectMocks
    RamlToOas ramlToOas;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMainFlow() {
        // Invoke the main method
        RamlToOas.main(new String[]{});

        // Verify the interactions
        verify(tempDir, times(1)).mkdir();
        verify(printWriter, times(1)).println(anyString());
    }
}

