package parser;


/**
 * This class contains codes for each grammar terminal
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class GrammarSymbols {

	// Language terminals (starts from 0)
	public static final int EOT = 0, IDENTIFIER = 1, NUMBER = 2, COLON = 3, ASG = 4, EQUAL = 5,
			EQUALS = 6, SEMICOLON = 7, OP_ARIT = 8, MAJOR = 9, MAJOREQ = 10, MINOR = 11, MINOREQ = 12, 
			NOT = 13, DIFFERENT = 14, L_PAR = 15, R_PAR = 16, L_BRACE = 17, R_BRACE = 18, COMMA = 19,
			IF = 20, TEST = 21, THEN = 22, ELSE = 23, DO = 24, WHILE = 25, RETURN = 26, BREAK = 27, 
			CONTINUE = 28, LET = 29, BE = 30, MANIFEST = 31, GLOBAL = 32, BOOL_VALUE = 33, INT_TYPE = 34,
			BOOL_TYPE = 35, WRITEF = 36, SYMBOL = 37;
	
}
