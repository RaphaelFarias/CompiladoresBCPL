package encoder;

public class Instruction {
	byte opCode;
	Object operando1 = null;
	Object operando2 = null;
	
	public interface OpCode{
		public final static byte ADD = 0, SUB = 1, MULT = 2, DIV = 3, JUMP = 4, PUSH = 5, POP = 6, CALL = 7,
				MOV = 8, CMP = 9, JNE = 10, JE = 11, JGE = 12, JG = 13, JLE = 14, JL = 15, RET = 16;
	}
	
}