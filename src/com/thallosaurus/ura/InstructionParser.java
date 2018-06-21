package com.thallosaurus.ura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructionParser {
	Map <String, Integer> markers;
	ArrayList<Integer> parsedProgram;

	public InstructionParser() {
		markers = new HashMap<String, Integer>();
		parsedProgram = new ArrayList<Integer>();
	}
	
	public void parseProgram(String s) {
		boolean done = false;
		int ip = 0;
		boolean insideProgram = false;
		String[] sliced = s.split(" ");
		Map<Integer, String> p = new HashMap<Integer, String>();
		
		for (int i = 0; i < sliced.length; i++) {
			p.put(i, sliced[i]);
		}
	}
}
