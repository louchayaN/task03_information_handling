package com.corporation.task03parser.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class DefaultReader extends Reader {

	private static final String DEFAULT_ENCORDING = "UTF-8";	
	private static final StringBuilder NODE_BUILDER = new StringBuilder();
	private static final String MULTY_GAPS = "[\\s]{2,}";
	
	private BufferedReader reader;

	public DefaultReader(InputStream stream) throws UnsupportedEncodingException {
		this.reader = new BufferedReader(new InputStreamReader(stream, DEFAULT_ENCORDING));
	}

	@Override
	public String readNextNode() throws IOException {
		int nextCharacter;
		while ((nextCharacter = reader.read()) != -1) {
			char character = (char) nextCharacter;	
			
			if (character == '<' && isNotEmptyNodeBuilder()) {
				String node = formNodeFromBuilder();
				cleanBuilder();
				NODE_BUILDER.append(character);
				return node;
			}
			
			if (character == '>') {
				NODE_BUILDER.append(character);
				String node = formNodeFromBuilder();
				cleanBuilder();
				return node;
			}		
			NODE_BUILDER.append(character);
		}
		
		return null;
	}

	private boolean isNotEmptyNodeBuilder() {
		return (! NODE_BUILDER.toString().trim().isEmpty());
	}
	
	private String formNodeFromBuilder() {
		String nodeLine = NODE_BUILDER.toString().trim().replaceAll(MULTY_GAPS, " ");
		return nodeLine;
	}

	private void cleanBuilder() {
		NODE_BUILDER.setLength(0);
	}
	
	@Override
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
