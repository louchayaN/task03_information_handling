package com.corporation.task03parser.analizator;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import com.corporation.task03parser.entity.Node;
import com.corporation.task03parser.entity.Type;
import com.corporation.task03parser.reader.Reader;
import com.corporation.task03parser.reader.ReaderBySymbols;

public class NodeAnalizator implements Analizator{

	private static final String START_TAG_REGEX = "<[^\\?!/].+[^/]>";
	private static final String END_TAG_REGEX = "</.+>";
	private static final String EMPTY_ELEMENT_TAG_REGEX = "<.+/>";
	private static final String CONTENT_REGEX = "[^<].+";
	
	private static final Logger lOG = Logger.getLogger(NodeAnalizator.class.getName());
	
	private Reader reader;
	
	public NodeAnalizator(InputStream stream) throws UnsupportedEncodingException {
		reader = new ReaderBySymbols(stream);
	}
	
	
	public NodeAnalizator(InputStream stream, Reader reader) {
		this.reader = reader;
		reader.setStream(stream);
	}
 
	@Override
	public Node getNextNode() throws IOException   {
		
			String nodeLine;
			while((nodeLine=reader.readNextNode()) != null ) {
				Node node = createNode(nodeLine);
				if(node == null) {
					continue;
				}
				return node;
			}
			try {
				return null;
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					lOG.warning("Resource haven't been closed");
				}
			}
				
	}  

	
	private Node createNode(String nodeLine) {
		Node node = new Node();
		if(nodeLine.matches(START_TAG_REGEX)) {
			node.setName(nodeLine);
			node.setType(Type.START_TAG);
			return node;
		}
		if(nodeLine.matches(END_TAG_REGEX)) {
			node.setName(nodeLine);
			node.setType(Type.END_TAG);
			return node;
		}
		if(nodeLine.matches(EMPTY_ELEMENT_TAG_REGEX)) {
			node.setName(nodeLine);
			node.setType(Type.EMPTY_ELEMENT_TAG);
			return node;
		}
		if(nodeLine.matches(CONTENT_REGEX)) {
			node.setName(nodeLine);
			node.setType(Type.CONTENT);
			return node;
		}		
		return null;
	}


}
