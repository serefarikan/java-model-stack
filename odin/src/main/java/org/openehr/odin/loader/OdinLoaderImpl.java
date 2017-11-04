package org.openehr.odin.loader;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017 Cognitive Medical Systems
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 * Author: Claude Nanjo
 */

import org.antlr.v4.runtime.BaseErrorListener;
import org.apache.commons.io.IOUtils;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.antlr.OdinVisitorImpl;
import org.openehr.odin.antlr.odinLexer;
import org.openehr.odin.antlr.odinParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

public class OdinLoaderImpl {

    private static Logger log = LogManager.getLogger(OdinLoaderImpl.class.getName());
    private ANTLRInputStream antlrInputStream;

    public OdinVisitorImpl loadOdinFile(String bmmFilePath) {
        File file = new File(bmmFilePath);
        OdinVisitorImpl visitor = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            visitor = loadOdinFile(fis);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            log.error("Error loading reference model", ioe);
            throw new RuntimeException("Error loading reference model", ioe);
        }
        return visitor;
    }

    public OdinVisitorImpl loadOdinFile(InputStream inputStream) {
        OdinVisitorImpl visitor = new OdinVisitorImpl<>();
        try {
            ANTLRInputStream input = new ANTLRInputStream(inputStream);

            //override default antlr error handling mechanism
            //we want exceptions to be thrown
            odinLexer lexer = new odinLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new BmmParserErrorListener());

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            //same override logic goes for parser
            odinParser parser = new odinParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new BmmParserErrorListener());

            ParseTree tree = parser.odin_text();
            visitor.visit(tree);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            log.error("Error loading odin file", ioe);
            throw new RuntimeException("Error loading odin file", ioe);
        }
        return visitor;
    }

    public OdinLoaderImpl initParser(InputStream inputStream) throws IOException {
        antlrInputStream = new ANTLRInputStream(inputStream);
        return this;
    }

    public CompositeOdinObject parse() throws InvalidParseResultException {
        OdinVisitorImpl visitor = new OdinVisitorImpl<>();
        odinLexer lexer = new odinLexer(antlrInputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        odinParser parser = new odinParser(tokens);
        ParseTree tree = parser.odin_text();
        visitor.visit(tree);
        Stack parseOutputStack = visitor.getStack();

        int stackSize = parseOutputStack.size();
        if ( stackSize != 1) {
            throw new InvalidParseResultException("Parse result stack has " +
                    "unexpected number of elements. Expected 1, found " +
                    stackSize);
        }
        return visitor.getAstRootNode();
    }

    public OdinVisitorImpl loadOdinFromString(String odinContent) {
        OdinVisitorImpl visitor = new OdinVisitorImpl<>();
        try {
            InputStream is = IOUtils.toInputStream(odinContent, "UTF-8");
            ANTLRInputStream input = new ANTLRInputStream(is);

            //override default antlr error handling mechanism
            //we want exceptions to be thrown
            odinLexer lexer = new odinLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new BmmParserErrorListener());

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            //same override logic goes for parser
            odinParser parser = new odinParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new BmmParserErrorListener());

            ParseTree tree = parser.odin_text();
            visitor.visit(tree);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            log.error("Error loading odin file", ioe);
            throw new RuntimeException("Error loading odin file", ioe);
        }
        return visitor;
    }
}

