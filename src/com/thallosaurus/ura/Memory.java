package com.thallosaurus.ura;

import java.util.HashMap;
import java.util.Map;

public class Memory {
  public int MAXADDRESS;
  Map<Byte, Byte> memory;
  int programLength = 0;
  
  public Memory(byte size) {
	  memory = new HashMap<>() {{
	    for (byte i = 0; i < size; i++) {
		  put(i, (byte) 0x00); //initialize memory with zeros by looping through the whole HashMap, lul
	    }
	  }};
	  MAXADDRESS = size;
  }
  
  public Map<Byte, Byte> getMemoryMap() {
	  return memory;
  }
  
  public byte readMemory(byte address) {
	  return memory.get(address);
  }
  
  public void writeMemory(byte address, byte value) {
	  memory.replace(address, value);
  }
  
  public void loadProgram(int[] program) {
	  if (program.length > MAXADDRESS) {
		  System.out.println("Your program is too big.");
	  } else {
		  programLength = program.length;
		  for (int i = 0; i < program.length; i++) {
			  writeMemory((byte) i, (byte) program[i]);
		  }
	  }
  }
}