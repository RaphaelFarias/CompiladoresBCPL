package scanner;

/**
 * Token class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Token {

	// The token kind
	private int kind;
	// The token spelling
	private String spelling;
	// The line and column that the token was found
	private int line, column;
	
	/**
	 * Default constructor
	 * @param kind
	 * @param spelling
	 * @param line
	 * @param column
	 */
	public Token(int kind, String spelling, int line, int column) {
		this.kind = kind;
		this.spelling = spelling;
		this.line = line;
		this.column = column;
	}

	/**
	 * Returns token kind
	 * @return
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * Returns token spelling
	 * @return
	 */
	public String getSpelling() {
		return spelling;
	}

	/**
	 * Returns the line where the token was found
	 * @return
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Returns the column where the token was found
	 * @return
	 */	
	public int getColumn() {
		return column;
	}
	
	public interface TokenType {
		public final static byte EOT = 0, IDENTIFIER = 1, NUMBER = 2, COLON = 3, ASG = 4, EQUAL = 5,
				EQUALS = 6, SEMICOLON = 7, OP_ADD = 8, MAJOR = 9, MAJOREQ = 10, MINOR = 11, MINOREQ = 12, 
				NOT = 13, DIFFERENT = 14, L_PAR = 15, R_PAR = 16, L_BRACE = 17, R_BRACE = 18, COMMA = 19,
				IF = 20, TEST = 21, THEN = 22, ELSE = 23, DO = 24, WHILE = 25, RETURN = 26, BREAK = 27, 
				CONTINUE = 28, LET = 29, BE = 30, MANIFEST = 31, GLOBAL = 32, BOOL_VALUE = 33, INT_TYPE = 34,
				BOOL_TYPE = 35, WRITEF = 36, SYMBOL = 37, VOID_TYPE = 38, PROC = 39, FUNC = 40,
				OP_COMPARATIVO = 41, OP_MULT = 42, L_BRACKET = 43, R_BRACKET = 44;
		
	}
}