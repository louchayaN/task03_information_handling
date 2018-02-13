package com.corporation.task03parser.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ReaderBySymbols extends Reader {

	private static final StringBuilder BUFFER = new StringBuilder();

	private static final String MULTY_GAPS = "[\\s]{2,}";

	private BufferedReader reader;

	public ReaderBySymbols(InputStream stream) throws UnsupportedEncodingException {
		this.reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
	}

	@Override
	public String readNextNode() throws IOException {

		int nextCharacter;
		while ((nextCharacter = reader.read()) != -1) {
			char character = (char) nextCharacter;
			BUFFER.append(character);

			if (character == '<' && (BUFFER.toString().trim() != "<")) {
				BUFFER.deleteCharAt(BUFFER.length() - 1);
				String node = formNodeFromBuffer();
				cleanBuffer();
				BUFFER.append('<');
				return node;
			}
			if (character == '>') {
				String node = formNodeFromBuffer();
				cleanBuffer();
				return node;
			}
		}
		return null;
	}

	@Override
	public void close() throws IOException {
		this.reader.close();
	}

	private String formNodeFromBuffer() {
		String nodeLine = BUFFER.toString().trim().replaceAll(MULTY_GAPS, " ");
		return nodeLine;
	}

	private void cleanBuffer() {
		BUFFER.setLength(0);
	}

}
