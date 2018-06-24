package com.thallosaurus.ura;

import java.util.HashMap;
import java.util.Map;

public class Memory {
  public int MAXADDRESS;
  Map<Integer, Integer> memory;
  int programLength = 0;
  
  public Memory(int size) {
	  memory = new HashMap<>() {{
	    for (int i = 0; i < size; i++) {
		  put(i, (int) 0x0000); //initialize memory with zeros by looping through the whole HashMap, lul
	    }
	  }};
	  MAXADDRESS = size;
  }
  
  public Map<Integer, Integer> getMemoryMap() {
	  return memory;
  }
  
  public int readMemory(int address) {
	  return memory.get(address);
  }
  
  public void writeMemory(int address, int value) {
	  memory.replace(address, value);
  }
  
  public void loadProgram(int[] program) {
	  if (program.length > MAXADDRESS) {
		  System.out.println("Your program is too big.");
	  } else {
		  programLength = program.length;
		  for (int i = 0; i < program.length; i++) {
			  writeMemory(i, program[i]);
		  }
	  }
  }
}