package pippin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JOptionPane;

public class Assembler {
	public static Set<String> noArgument = new TreeSet<String>();
	public static Map<String, Integer> opcode = new TreeMap<>();
	public static Map<Integer, String> mnemonics = new TreeMap<>();

	static {
		noArgument.add("HALT");
		noArgument.add("NOP");
		noArgument.add("NOT");
		noArgument.add("DATA");

		opcode.put("LOD", 0x1);
		opcode.put("STO", 0x2);
		opcode.put("JUMP", 0xB);
		opcode.put("JMPZ", 0xC);
		opcode.put("NOP", 0x0);
		opcode.put("HALT", 0xF);
		opcode.put("ADD", 0x3);
		opcode.put("SUB", 0x4);
		opcode.put("MUL", 0x5);
		opcode.put("DIV", 0x6);
		opcode.put("AND", 0x7);
		opcode.put("NOT", 0x8);
		opcode.put("CMPZ", 0x9);
		opcode.put("CMPL", 0xA);
		// fill in the remaining opcodes for all the missing instructions--the numeric hex values come from the handout.

		opcode.put("LODI", 0x101);
		opcode.put("ADDI", 0x103);
		opcode.put("SUBI", 0x104);
		opcode.put("MULI", 0x105);
		opcode.put("DIVI", 0x106);
		opcode.put("ANDI", 0x107);

		// fill in similar opcodes for the remaining instructions that allow immediate addressing
		// --"I" is added to the mnemonic and 0x100 to the opcode value

		opcode.put("LODN", 0x201);
		opcode.put("STON", 0x202);
		opcode.put("JUMPN", 0x20B);
		opcode.put("JMPZN", 0x20C);
		opcode.put("ADDN", 0x203);
		opcode.put("SUBN", 0x204);
		opcode.put("MULN", 0x205);
		opcode.put("DIVN", 0x206);

		// fill in similar opcodes for the remaining instructions that allow indirect addressing
		// --"N" is added to the mnemonic and 0x200 to the opcode value

		for(String str : opcode.keySet()) {
			mnemonics.put(opcode.get(str), str);
		}
	}

	public static boolean allUpper(String str) {
		boolean retVal = true;
		for(int i = 0; i < str.length(); i++) {
			Character c = str.charAt(i);
			if(Character.isLetter(c) && !(Character.isUpperCase(c))) retVal = false;
			else if(!(Character.isLetter(c))) retVal = false;
		}
		return retVal;
	}

	public static boolean assemble(File input, File output) {
		boolean goodProgram = false; // will be used at end of method
		try {
			goodProgram = true;
			Scanner inp = new Scanner(input);
			PrintWriter outp = new PrintWriter(output);
			boolean blankLineHit = false; //keep track of when we hit a blank line
			boolean inCode = true; //keep track that we are in the code, not in data
			int lineCounter = 0;
			while(inp.hasNextLine() && goodProgram) {
				if(!blankLineHit) lineCounter++;
				String line = inp.nextLine();
				if(line.trim().length() == 0) blankLineHit = true;
				else {
					if(blankLineHit && line.trim().length() > 0) {
						// call this the ERROR BLOCK
						goodProgram = false;
						JOptionPane.showMessageDialog(null,
								"Blank line in the source file on line " + lineCounter,
								"Source Error", JOptionPane.WARNING_MESSAGE);
					}
					if(goodProgram && Character.isWhitespace(line.charAt(0))) { 
						goodProgram = false;
						JOptionPane.showMessageDialog(null,
								"Blank character at the start of line " + lineCounter,
								"Source Error", JOptionPane.WARNING_MESSAGE);
					}
					if(goodProgram && line.trim().equals("DATA")) {
						inCode = false;
						outp.println(-1);
					}
					else if(goodProgram) {
						String[] parts = line.trim().split("\\s+");
						if(parts.length > 2) {
							goodProgram = false;
							JOptionPane.showMessageDialog(null,
									"Too many items on line " + lineCounter,
									"Source Error", JOptionPane.WARNING_MESSAGE);
						}
						if(goodProgram && inCode) {
							if(!allUpper(parts[0]))	{
								goodProgram = false;
								JOptionPane.showMessageDialog(null,
										"The mneumonic is not in upper case ",
										"Source Error", JOptionPane.WARNING_MESSAGE);
							}
							if(goodProgram && (parts.length == 1)) {
								if(!(noArgument.contains(parts[0]))) {
									goodProgram = false;
									JOptionPane.showMessageDialog(null,
											"Illegal mneumonic or missing argument on line " + lineCounter,
											"Source Error", JOptionPane.WARNING_MESSAGE);
								}
								else {
									outp.println(Integer.toHexString(opcode.get(parts[0])));
									outp.println(0);
								}
							}

							if(noArgument.contains(parts[0])) {
								goodProgram = false;
								JOptionPane.showMessageDialog(null,
										"Illegal argument on line " + lineCounter,
										"Source Error", JOptionPane.WARNING_MESSAGE);
							}
							if(goodProgram && !(opcode.keySet().contains(parts[0]))) {
								goodProgram = false;
								JOptionPane.showMessageDialog(null,
										"Illegal mneumonic on line " + lineCounter,
										"Source Error", JOptionPane.WARNING_MESSAGE);
							}
							if(goodProgram) {
								try {
									Integer.parseInt(parts[1], 16);
									outp.println(Integer.toHexString(opcode.get(parts[0])));
									outp.println(parts[1]);
								}
								catch (NumberFormatException e) {
									goodProgram = false;
									JOptionPane.showMessageDialog(null,
											"Argument is not an int on line " + lineCounter,
											"Source Error", JOptionPane.WARNING_MESSAGE);
								}
							}
						}
						else if(goodProgram) {
							if(parts.length != 2) {
								goodProgram = false;
								JOptionPane.showMessageDialog(null,
										"No address/data pair on line " + lineCounter,
										"Source Error", JOptionPane.WARNING_MESSAGE);
							}
							else {
								try {
									int addr = Integer.parseInt(parts[0], 16);
									if(addr < 0) {
										goodProgram = false;
										JOptionPane.showMessageDialog(null,
												"Address cannot be negative on line " + lineCounter,
												"Source Error", JOptionPane.WARNING_MESSAGE);
									}
								}
								catch (NumberFormatException e) {
									goodProgram = false;
									JOptionPane.showMessageDialog(null,
											"Memory address is not an int on line " + lineCounter,
											"Source Error", JOptionPane.WARNING_MESSAGE);
								}
							}
							if(goodProgram) {
								try {
									Integer.parseInt(parts[1]);
									outp.println(parts[0]);
			                        outp.println(parts[1]);
								}
								catch (NumberFormatException e) {
									goodProgram = false;
									JOptionPane.showMessageDialog(null,
											"Memory value is not an int on line " + lineCounter,
											"Source Error", JOptionPane.WARNING_MESSAGE);
								}
							}
						}
					}
				}
			}
			inp.close();
			outp.close();
		} catch (IOException e){
			System.out.println("Unable to open the necessary files");
		}
		if(!goodProgram && output != null && output.exists()) {
			output.delete();
		}
		return goodProgram;
	}
	public static void main(String[] args) {
		// print a message to ask for a file name, such as factorial8, merge, qsort
		// use a Scanner to read the name
		System.out.println("Please enter a file name");
		Scanner keyboard = new Scanner(System.in);
		String name = keyboard.nextLine();
		assemble(new File(name + ".pasm"), new File(name + ".pexe"));
		keyboard.close();
	}
}