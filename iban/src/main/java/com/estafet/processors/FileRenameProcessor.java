package com.estafet.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by DRamadan on 30-Nov-16.
 */
public class FileRenameProcessor implements Processor {

    private static final int LENGTH_FILE_EXTENSION = ".txt".length();

    /**
     * Processor that removes the file name extension from the header
     * @param exchange
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        Object fileNameObject = exchange.getIn().getHeader(Exchange.FILE_NAME);

        if (fileNameObject instanceof String) {
            String fileName = (String) fileNameObject;
            fileName = fileName.substring(0, fileName.length() - LENGTH_FILE_EXTENSION);
            exchange.getIn().setHeader(Exchange.FILE_NAME, fileName);
        }
    }
}
