package com.mk;

import amf.apicontract.client.platform.AMFBaseUnitClient;
import amf.apicontract.client.platform.RAMLConfiguration;
import amf.apicontract.client.platform.OASConfiguration;
import amf.core.client.common.transform.PipelineId;
import amf.core.client.platform.config.RenderOptions;
import amf.core.client.platform.model.document.BaseUnit;

import net.lingala.zip4j.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.concurrent.ExecutionException;

public class RamlToOas {

    private static final String RAML_PROJECT_NAME = "poc-stockmanagement-api";
    private static final String OAS3_OUTPUT_FILE = "output.json";
    private static final Logger logger = LoggerFactory.getLogger(RamlToOas.class);

    public static void main(String[] args) {
        try {
            // Initialize AMF
            RenderOptions options = new RenderOptions().withCompactUris()
                .withSourceMaps()
                .withPrettyPrint()
                .withDocumentation();
            final AMFBaseUnitClient oas30Client = OASConfiguration.OAS30().withRenderOptions(options).baseUnitClient();
            final AMFBaseUnitClient raml10Client = RAMLConfiguration.RAML10().baseUnitClient();

            // Path to temporary directory
            File tempDir = new File("tempDir");
            if (tempDir.exists()) {
                FileUtils.cleanDirectory(tempDir);
                logger.info("Temporary directory cleaned: " + tempDir.getAbsolutePath());
            } else {
                tempDir.mkdir();
                logger.info("Temporary directory created: " + tempDir.getAbsolutePath());
            }

            new ZipFile(RAML_PROJECT_NAME + ".zip").extractAll(tempDir.getPath());
            logger.info("Zip file extraction completed");

            // Parse RAML file to model
            String ramlFilePath = tempDir + File.separator + RAML_PROJECT_NAME + ".raml";
            BaseUnit model = raml10Client.parse("file://" + ramlFilePath).get().baseUnit();
            logger.info("RAML file parsed");

            // Generate OAS 3.0 from model
            final BaseUnit convertedOas = oas30Client.transform(model, PipelineId.Compatibility()).baseUnit();

            String oas = oas30Client.render(convertedOas).trim();
            logger.info("OAS 3.0 file generated");

            // Write OAS 3.0 to file
            try (PrintWriter out = new PrintWriter(OAS3_OUTPUT_FILE)) {
                out.println(oas);
            }
            logger.info("OAS 3.0 file saved to " + OAS3_OUTPUT_FILE);
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
