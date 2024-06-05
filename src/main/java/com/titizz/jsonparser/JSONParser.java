package com.titizz.jsonparser;

import com.titizz.jsonparser.parser.Parser;
import com.titizz.jsonparser.tokenizer.CharReader;
import com.titizz.jsonparser.tokenizer.TokenList;
import com.titizz.jsonparser.tokenizer.Tokenizer;

import java.io.IOException;
import java.io.StringReader;


public class JSONParser {

    private Tokenizer tokenizer = new Tokenizer();

    private Parser parser = new Parser();

    public Object fromJSON(String json) throws IOException {
        CharReader charReader = new CharReader(new StringReader(json));
        TokenList tokens = tokenizer.tokenize(charReader);
        return parser.parse(tokens);
    }
}
