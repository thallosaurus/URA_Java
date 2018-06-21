package com.thallosaurus.ura;

import java.util.HashSet;

public enum OPCODE {
	  ADD,
	  AND,
	  CALL,
	  CMP,
	  CNST,
	  CTRL,
	  DIV,
	  EPM,
	  HLT,
	  INSP,
	  INT,
	  JMPEQ,
	  JMPGE,
	  JMPNEQ,
	  JMPSML,
	  LOAD,
	  MOV,
	  MUL,
	  NOP,
	  NOT,
	  OR,
	  PLOAD,
	  POP,
	  PSTORE,
	  PUSH,
	  RET,
	  STORE,
	  SUB,
	  XOR;
	  
	  private int arg;

	static {
		  CNST.arg = 2;
		  HLT.arg = 0;
		  MOV.arg = 2;
		  ADD.arg = 3;
		  AND.arg = 3;
		  CALL.arg = 1;
		  CMP.arg = 3;
		  CTRL.arg = 2;
		  DIV.arg = 3;
		  EPM.arg = 1;
		  INSP.arg = 2;
		  INT.arg = 1;
		  JMPEQ.arg = 2;
		  JMPGE.arg = 2;
		  JMPNEQ.arg = 2;
		  JMPSML.arg = 2;
		  LOAD.arg = 2;
		  MUL.arg = 3;
		  NOP.arg = 0;
		  NOT.arg = 2;
		  OR.arg = 3;
		  PLOAD.arg = 2;
		  POP.arg = 1;
		  PSTORE.arg = 2;
		  PUSH.arg = 1;
		  RET.arg = 1;
		  STORE.arg = 2;
		  SUB.arg = 3;
		  XOR.arg = 3;
	  }
	  
	  public int getArgumentLength() {
		  return arg;
	  }
	  
	  public HashSet<String> getEnums() {
		  HashSet<String> values = new HashSet<String>();
		  for (OPCODE o : this.values()) {
			  values.add(this.name());
		  }
		  return values;
	  }
}
