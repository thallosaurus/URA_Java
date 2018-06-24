package com.thallosaurus.ura;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CPU {
	Map<REGISTERS, Integer> r;
	Map<SPECIALREGISTER, Integer> sr;
	Memory mem;
	
	public CPU(Memory m) {
		initRegisters();
		setRegisterValue(SPECIALREGISTER.CR, 1);
		mem = m;
		setRegisterValue(SPECIALREGISTER.SP, (mem.programLength + 1)); //next free address -> after program finished
	}
	
	void initRegisters() {
		//init registers
				r = new HashMap<REGISTERS, Integer>(){{
					for (int i = 0; i < 16; i++) {
						put(REGISTERS.values()[i], 0);
					}
				}};
				sr = new HashMap<SPECIALREGISTER, Integer>(){{
					for (int i = 0; i < 7; i++) {
						put(SPECIALREGISTER.values()[i], 0);
					}
				}};
				
	}
	
	int getRegisterValue(REGISTERS reg) {
		//System.out.println(getRegisterValue(SPECIALREGISTER.IP));
		return r.get(reg);
	}
	
	int getRegisterValue(SPECIALREGISTER reg) {
		return sr.get(reg);
	}
	
	public void setRegisterValue(REGISTERS reg, int value) {
		r.replace(reg, value);
	}
	
	private void setRegisterValue(SPECIALREGISTER reg, int value) {
		sr.replace(reg, value);
	}
	
	public Map<REGISTERS, Integer> getRegisterOverview() {
		return r;
	}
	
	private int[] getArguments(int l) {
		int arg[] = new int[l];
		for (int i = 0; i < l; i++) {
			arg[i] = mem.readMemory(getRegisterValue(SPECIALREGISTER.IP) + i + 1);
		}
		return arg;
	}
	
	private void systemInterrupt() {
		
	}
	
	void syscall(int opcode) {
		boolean useNextInstructionFunction = true;
		System.out.println("[syscall()] called " + OPCODE.values()[opcode]);
		int[] arg = getArguments(OPCODE.values()[opcode].getArgumentLength());
		
		switch (OPCODE.values()[opcode]) {
		case CNST:
			setRegisterValue(REGISTERS.values()[arg[0]], arg[1]);
			break;
		case MOV:
			setRegisterValue(REGISTERS.values()[arg[0]], getRegisterValue(REGISTERS.values()[arg[1]]));
			break;
		case HLT:
			setRegisterValue(SPECIALREGISTER.CR, 0); //bye!
			break;
		case ADD:
			setRegisterValue(REGISTERS.values()[arg[0]],
					(getRegisterValue(
							REGISTERS.values()[arg[1]]) + getRegisterValue(
									REGISTERS.values()[arg[2]])));
			break;
		case AND:
			if ((getRegisterValue(REGISTERS.values()[arg[1]]) == 1 && getRegisterValue(REGISTERS.values()[arg[2]]) == 1)) {
				setRegisterValue(REGISTERS.values()[arg[0]], (byte) 1);
			}
			break;
		case CALL:
			//TODO implement push
			setRegisterValue(SPECIALREGISTER.SP, getRegisterValue(SPECIALREGISTER.SP) + 1);
			mem.writeMemory(getRegisterValue(SPECIALREGISTER.SP), getRegisterValue(SPECIALREGISTER.IP));
			useNextInstructionFunction = false;
			setRegisterValue(SPECIALREGISTER.IP, (getRegisterValue(REGISTERS.values()[arg[0]])));
			break;
		case CMP:
			if (getRegisterValue(REGISTERS.values()[arg[1]]) > getRegisterValue(REGISTERS.values()[arg[2]])) {
				setRegisterValue(REGISTERS.values()[arg[0]], 1);
			} else if (getRegisterValue(REGISTERS.values()[arg[1]]) == getRegisterValue(REGISTERS.values()[arg[2]])) {
				setRegisterValue(REGISTERS.values()[arg[0]], 0);
			} else if (getRegisterValue(REGISTERS.values()[arg[1]]) < getRegisterValue(REGISTERS.values()[arg[2]])) {
				setRegisterValue(REGISTERS.values()[arg[0]], -1);
			}
			break;
		case CTRL:
			setRegisterValue(SPECIALREGISTER.values()[arg[0]], getRegisterValue(REGISTERS.values()[arg[1]]));
			break;
		case DIV:
			setRegisterValue(REGISTERS.values()[arg[0]],
					(getRegisterValue(REGISTERS.values()[arg[1]])/getRegisterValue(REGISTERS.values()[arg[2]]))
					);
			break;
		case EPM:
			//TODO implement SPECIALREGISTER.CR two bits
			//come back later, m8 :)
			setRegisterValue(SPECIALREGISTER.PM, (byte) 1);
			setRegisterValue(SPECIALREGISTER.IP, getRegisterValue(REGISTERS.values()[arg[0]]));
			break;
		case INSP:
			setRegisterValue(REGISTERS.values()[arg[0]], getRegisterValue(SPECIALREGISTER.values()[arg[1]]));
			break;
		case INT:
			//come back later, m8
			//holy fuck
			//TODO
			break;
		case JMPEQ:
			if (getRegisterValue(REGISTERS.values()[arg[0]]) == 0) {
				useNextInstructionFunction = false;
				setRegisterValue(SPECIALREGISTER.IP, getRegisterValue(REGISTERS.values()[arg[1]]));
			}
			break;
		case JMPGE:
			if (getRegisterValue(REGISTERS.values()[arg[0]]) == 1) {
				useNextInstructionFunction = false;
				setRegisterValue(SPECIALREGISTER.IP, getRegisterValue(REGISTERS.values()[arg[1]]));
			}
			break;
		case JMPNEQ:
			if (getRegisterValue(REGISTERS.values()[arg[0]]) != 0) {
				useNextInstructionFunction = false;
				setRegisterValue(SPECIALREGISTER.IP, getRegisterValue(REGISTERS.values()[arg[1]]));
			}
			break;
		case JMPSML:
			if (getRegisterValue(REGISTERS.values()[arg[0]]) == -1) {
				useNextInstructionFunction = false;
				setRegisterValue(SPECIALREGISTER.IP, getRegisterValue(REGISTERS.values()[arg[1]]));
			}
			break;
		case LOAD:
			setRegisterValue(
					REGISTERS.values()[arg[0]], mem.readMemory(getRegisterValue(REGISTERS.values()[arg[1]]))
					);
			break;
		case MUL:
			setRegisterValue(REGISTERS.values()[arg[0]],
					(getRegisterValue(REGISTERS.values()[arg[1]])*getRegisterValue(REGISTERS.values()[arg[2]]))
					);
			break;
		case NOP:
			//do nothing. wow
			break;
		case NOT:
			if (getRegisterValue(REGISTERS.values()[arg[1]]) == 0) {
				setRegisterValue(REGISTERS.values()[arg[0]], 1);
			}
			break;
		case OR: //TODO look. if this works correctly, please
			if ((getRegisterValue(REGISTERS.values()[arg[1]]) != 0 || getRegisterValue(REGISTERS.values()[arg[2]]) != 0)) {
				setRegisterValue(REGISTERS.values()[arg[0]], 1);
			}
			break;
		case PLOAD:
			//TODO fix
			//please just use load for now, thx
			break;
		case POP:
			//TODO
			setRegisterValue(SPECIALREGISTER.SP, (getRegisterValue(SPECIALREGISTER.SP) - 1));
			setRegisterValue(REGISTERS.values()[arg[0]], mem.readMemory(getRegisterValue(SPECIALREGISTER.SP)));
			break;
		case PSTORE:
			//TODO
			break;
		case PUSH:
			//TODO implement stack pointer
			mem.writeMemory(getRegisterValue(SPECIALREGISTER.SP), getRegisterValue(REGISTERS.values()[arg[0]]));
			setRegisterValue(SPECIALREGISTER.SP, (getRegisterValue(SPECIALREGISTER.SP) + 1));
			break;
		case RET:
			//TODO
			//use call for now
			useNextInstructionFunction = false;
			/* setRegisterValue(SPECIALREGISTER.IP, mem.readMemory(getRegisterValue(SPECIALREGISTER.SP)));
			//setRegisterValue(SPECIALREGISTER.SP, getRegisterValue(REGISTERS.values()[arg[0]]));
			setRegisterValue(SPECIALREGISTER.SP, getRegisterValue(SPECIALREGISTER.SP)-1); */
			
			setRegisterValue(SPECIALREGISTER.SP, getRegisterValue(SPECIALREGISTER.SP) - 1);
			//mem.writeMemory(getRegisterValue(SPECIALREGISTER.SP), getRegisterValue(SPECIALREGISTER.IP));
			setRegisterValue(SPECIALREGISTER.IP, mem.readMemory(getRegisterValue(SPECIALREGISTER.SP)));
			//useNextInstructionFunction = false;
			//setRegisterValue(SPECIALREGISTER.IP, (getRegisterValue(REGISTERS.values()[arg[0]])));
			break;
		case STORE:
			mem.writeMemory(getRegisterValue(REGISTERS.values()[arg[0]]), getRegisterValue(REGISTERS.values()[arg[1]]));
			break;
		case SUB:
			setRegisterValue(REGISTERS.values()[arg[0]],
					(getRegisterValue(
							REGISTERS.values()[arg[1]]) - getRegisterValue(
									REGISTERS.values()[arg[2]])));
			break;
		case XOR:
			if ((getRegisterValue(REGISTERS.values()[arg[1]]) == 1 || getRegisterValue(REGISTERS.values()[arg[2]]) == 1) && (getRegisterValue(REGISTERS.values()[arg[1]]) == 0 || getRegisterValue(REGISTERS.values()[arg[2]]) == 0)) {
				setRegisterValue(REGISTERS.values()[arg[0]], 1);
			}
			break;
		default:
			throw new Error("Opcode " + opcode + " not found");
		}
		if (useNextInstructionFunction) {
			nextInstruction((OPCODE.values()[opcode].getArgumentLength() + 1));
		}
		
		try {
			TimeUnit.SECONDS.sleep(1); // we have a 4hz CPU! *yay*
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void nextInstruction(int i) {
		setRegisterValue(SPECIALREGISTER.IP, (byte) (getRegisterValue(SPECIALREGISTER.IP) + i));
	}
	
	void execute(Runnable callbackWhileExecution, Runnable callbackAfterExecution) {
		while (getRegisterValue(SPECIALREGISTER.CR) == 1) {
			
			syscall(
					mem.readMemory( //read from memory
							getRegisterValue(SPECIALREGISTER.IP) //...and pass memory at the current IP back to execute (syscall)
							)
					);
			callbackWhileExecution.run();
		}
		callbackAfterExecution.run();
	}
}
