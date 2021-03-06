package com.thallosaurus.ura;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

public class Main extends JPanel {
    //Initialize GUI here
	//and other stuff like the cpu, the memory etc...
	Memory mem;
	CPU cpu;
	
	JFrame frame;
	JTextArea area;
	JScrollPane scroll;
	JLabel registers;
	JTextField input;
	JButton submit;
	
	JTree tree;
	public static void main(String[] args) {
		Main m = new Main();
		m.run(args);
	}
	
	public Main() {
		mem = new Memory(64000); //this is size
		cpu = new CPU(mem);	
		initGUI();
	}
	
	private void initGUI() {
		frame = new JFrame("URA Emulator by thallosaurus");
		registers = new JLabel("0");
		input = new JTextField(20);
		submit = new JButton("Submit");
		frame.setSize(800, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		add(registers);
		add(input);
		add(submit);
		frame.setVisible(true);
		
	}
	
	public void run(String[] args) {
		/* int[] stub = {
				4, 0, 10, //cnst r0 10
				4, 1, 26, //cnst r1 26
				4, 2, 1,  //cnst r2 1
				4, 3, 17, //cnst r3 17
				4, 4, 0,  //cnst r4 0
				2, 3,     //call r3
				27, 0, 0, 2, //sub r0, r0, r2
				11, 0, 1, //jmpeq r0, r1
				2, 3,     //call r3
				8   	  //hlt
				}; */
		/* int[] stub = {4, 0, 100,
				4, 1, 42,
				26, 0, 1,
				15, 2, 0,
				8}; */
		int[] stub = {4, 0, 0, 	//cnst r0 0
				4, 1, 14,		//cnst r1 14
				4, 2, 24,		//cnst r2 24
				4, 3, 29,		//cnst r3 29
				2, 1,			//call r1
				4, 0, 1,		//cnst r0 1
				2, 2,			//call r2
				4, 0, 2,		//cnst r0 2
				2, 3,			//call r3
				4, 0, 30,		//cnst r0 30
				25, 1,			//ret r1
				8};				//hlt

		mem.loadProgram(stub);
		//System.out.println(mem.getMemoryMap().toString());
		
		cpu.execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("IP after execution: " + cpu.getRegisterValue(SPECIALREGISTER.IP));
				registers.setText(cpu.getRegisterOverview().toString() + "\n" + "CR: " + cpu.getRegisterValue(SPECIALREGISTER.CR) + 
						"stack Pointer: " + cpu.getRegisterValue(SPECIALREGISTER.SP)
						);
				System.out.println(cpu.getRegisterOverview().toString());
			}
		},
				
				new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("Finished!");
						
					}
			
		});
		//System.out.println(cpu.getRegisterOverview().toString());
		
		//System.out.println(mem.readMemory((byte) 63)); //maximum is size-1
	}
}
