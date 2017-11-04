package org.openehr.odin.loader;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class BmmParserErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int charPositionInLine,
                            String msg,
                            RecognitionException e)
        throws ParseCancellationException{
        throw new ParseCancellationException(
            "parser failed at line: " +
            line +
            " char: " +
            charPositionInLine + " " +
            msg);
    }
}
