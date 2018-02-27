package com.corporation.task03parser.analizator;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.corporation.task03parser.entity.Node;
import com.corporation.task03parser.entity.NodeType;
import com.corporation.task03parser.reader.DefaultReader;
import com.corporation.task03parser.reader.Reader;

public class NodeAnalizator implements Analizator{

	private static final Map<String, NodeType> NODE_TYPE_MATCHER = new HashMap<>();
	
	static {
		NODE_TYPE_MATCHER.put("<[^\\?!/].+[^/]>", NodeType.START_TAG);
		NODE_TYPE_MATCHER.put("</.+>>", NodeType.END_TAG);
		NODE_TYPE_MATCHER.put("<.+/>", NodeType.EMPTY_ELEMENT_TAG);
		NODE_TYPE_MATCHER.put("[^<].+", NodeType.CHARACTERS);
	}
	
	private Reader reader;//представь себе, для чего ты создаешь этот объект - я собираюсь читать данные из... - и потом переименовывай переменную
	
	public NodeAnalizator(InputStream stream) throws UnsupportedEncodingException {
		reader = new DefaultReader(stream);
	}
		
	public NodeAnalizator(InputStream stream, Reader reader) {
		this.reader = reader;
		this.reader.setStream(stream);
	}
 
	@Override
	public Node getNextNode() throws IOException   {
		
		String nodeLine;
		while((nodeLine = reader.readNextNode()) != null ) {
			Node node = createNode(nodeLine);
			if(node != null) {
				return node;
			}			
		}
		
		try {// придумано хорошо, но не надо закрытие ресурсов прятать, иначе можно просто неправильно использовать метод
			return null;			
		} finally {			
			reader.close();
		}				
	}  
	
	private Node createNode(String nodeLine) {
		Node node = new Node();
		
		Set<String> typeRegexes = NODE_TYPE_MATCHER.keySet();		
		for(String typeRegex : typeRegexes) {
			if(nodeLine.matches(typeRegex)) {
				node.setName(nodeLine);
				node.setType(NODE_TYPE_MATCHER.get(typeRegex));
				return node;
			}
		}			
		return null;
	}


}
