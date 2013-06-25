package scanner;

import compiler.Properties;
import scanner.Token.TokenType;
import util.Arquivo;

/**
 * Scanner class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Scanner {

	// The file object that will be used to read the source code
	private Arquivo file;
	// The last char read from the source code
	private char currentChar;
	// The kind of the current token
	private int currentKind;
	// Buffer to append characters read from file
	private StringBuffer currentSpelling;
	// Current line and column in the source file
	private int line, column;

	/**
	 * Default constructor
	 */
	public Scanner() {
		this.file = new Arquivo(Properties.sourceCodeLocation);		
		this.line = 0;
		this.column = 0;
		this.currentChar = this.file.readChar();
	}

	/**
	 * Returns the next token
	 * @return
	 * @throws LexicalException 
	 */ //TODO
	public Token getNextToken() throws LexicalException {

		this.currentSpelling = new StringBuffer("");

		while (isSeparator(currentChar) == true ) { 
			this.scanSeparator();
		}

		this.currentSpelling.delete(0, this.currentSpelling.length()); // ou apagar o conteudo do buffer

		this.currentKind = this.scanToken();

		return new Token(this.currentKind, this.currentSpelling.toString(), this.line, this.column);
	}

	/**
	 * Returns if a character is a separator
	 * @param c
	 * @return
	 */
	private boolean isSeparator(char c) {
		if ( c == '#' || c == ' ' || c == '\n' || c == '\t' ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Reads (and ignores) a separator
	 * @throws LexicalException
	 */ //TODO
	private void scanSeparator() throws LexicalException {
		// If it is a comment line
		// Gets next char
		// Reads characters while they are graphics or '\t'
		// A command line should finish with a \n
		if ( this.currentChar == '#' ) { 
			this.getNextChar(); 
			while ( this.isGraphic(this.currentChar) || this.currentChar == '\t') { 
				this.getNextChar(); 
			} if(this.currentChar == '\n'){ //verifica se o caractere corrente e '\n' 
				this.getNextToken();  
			}else{
				throw new LexicalException("Erro lexico no scan separator! Token esperado e '\\n'", this.currentChar, this.line, this.column);
			}
		} 
		else { 
			this.getNextChar(); 
		} 
	}

	/**
	 * Gets the next char
	 */
	private void getNextChar() {
		// Appends the current char to the string buffer
		this.currentSpelling.append(this.currentChar);
		// Reads the next one
		this.currentChar = this.file.readChar();
		// Increments the line and column
		this.incrementLineColumn();
	}

	/**
	 * Increments line and column
	 */
	private void incrementLineColumn() {
		// If the char read is a '\n', increments the line variable and assigns 0 to the column
		if ( this.currentChar == '\n' ) {
			this.line++;
			this.column = 0;
			// If the char read is not a '\n' 
		} else {
			// If it is a '\t', increments the column by 4
			if ( this.currentChar == '\t' ) {
				this.column = this.column + 4;
				// If it is not a '\t', increments the column by 1
			} else {
				this.column++;
			}
		}
	}

	/**
	 * Returns if a char is a digit (between 0 and 9)
	 * @param c
	 * @return
	 */
	private boolean isDigit(char c) {
		if ( c >= '0' && c <= '9' ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns if a char is a letter (between a and z or between A and Z)
	 * @param c
	 * @return
	 */
	private boolean isLetter(char c) {
		if ( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns if a char is a graphic (any ASCII visible character)
	 * @param c
	 * @return
	 */
	private boolean isGraphic(char c) {
		if ( c >= ' ' && c <= '~' ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Scans the next token
	 * Simulates the DFA that recognizes the language described by the lexical grammar
	 * @return
	 * @throws LexicalException
	 */ //TODO
	private int scanToken() throws LexicalException {
		int s = 0;
		
		while (this.currentChar != '\000'){ // verifica se chegou ao fim do arquivo
			switch (s){
			case 0:
				if (this.isLetter(this.currentChar)){
					s = 1;
					this.getNextChar();
					break;
				} else if (this.isDigit(this.currentChar)){
					s = 2;
					this.getNextChar();
					break;
				} else if (this.isGraphic(this.currentChar)){
					if (this.currentChar == ','){
						this.getNextChar();
						return TokenType.COMMA;
					} else if (this.currentChar == ';'){
						this.getNextChar();
						return TokenType.SEMICOLON;
					} else if (this.currentChar == '='){ 
						s = TokenType.EQUAL;
						this.getNextChar();
						break;						
					} else if (this.currentChar == '~'){
						s = TokenType.NOT; 
						this.getNextChar();
						break;
					} else if (this.currentChar == ':'){
						s = TokenType.COLON;
						this.getNextChar();
						break;
					} else if (this.currentChar == '+' || this.currentChar == '-'){
						this.getNextChar();
						return TokenType.OP_ADD;
					}else if (this.currentChar == '*' || this.currentChar == '/'){
						this.getNextChar();
						return TokenType.OP_MULT;
					}
					else if (this.currentChar == '>'){
						s = TokenType.MAJOR; 
						this.getNextChar();
						break;
					} else if (this.currentChar == '<'){
						s = TokenType.MINOR; 
						this.getNextChar();
						break;
					} else if (this.currentChar == '('){
						this.getNextChar();
						return TokenType.L_PAR;
					} else if (this.currentChar == ')'){
						this.getNextChar();
						return TokenType.R_PAR;
					}else if (this.currentChar == '{'){
						this.getNextChar();
						return TokenType.L_BRACE;
					} else if (this.currentChar == '}'){
						this.getNextChar();
						return TokenType.R_BRACE;
					} else if (this.currentChar == '['){
						this.getNextChar();
						return TokenType.L_BRACKET;
					} else if (this.currentChar == ']'){
						this.getNextChar();
						return TokenType.R_BRACKET;
					} else {
						this.getNextChar();
						return TokenType.SYMBOL;
					}
				} else
					throw new LexicalException("Erro lexico caso 0!", this.currentChar, this.line, this.column);
			case 1:
				if (this.isLetter(this.currentChar) || this.isDigit(this.currentChar)){
					s = TokenType.IDENTIFIER;
					this.getNextChar();
					break;
				} else {
					if (this.currentSpelling.toString().equals("IF")){
						return TokenType.IF;
					} else if (this.currentSpelling.toString().equals("TEST")){
						return TokenType.TEST;
					} else if (this.currentSpelling.toString().equals("THEN")){
						return TokenType.THEN;
					} else if (this.currentSpelling.toString().equals("ELSE")){
						return TokenType.ELSE;
					} else if (this.currentSpelling.toString().equals("DO")){
						return TokenType.DO;
					} else if (this.currentSpelling.toString().equals("WHILE")){
						return TokenType.WHILE;
					} else if (this.currentSpelling.toString().equals("RETURN")){
						return TokenType.RETURN;
					} else if (this.currentSpelling.toString().equals("BREAK")){
						return TokenType.BREAK;
					} else if (this.currentSpelling.toString().equals("TRUE") || this.currentSpelling.toString().equals("FALSE")){
						return TokenType.BOOL_VALUE;
					} else if (this.currentSpelling.toString().equals("CONTINUE")){
						return TokenType.CONTINUE;
					} else if (this.currentSpelling.toString().equals("LET")){
						return TokenType.LET;
					} else if (this.currentSpelling.toString().equals("BE")){
						return TokenType.BE;
					} else if (this.currentSpelling.toString().equals("MANIFEST")){
						return TokenType.MANIFEST;
					} else if (this.currentSpelling.toString().equals("GLOBAL")){
						return TokenType.GLOBAL;
					} else if (this.currentSpelling.toString().equals("INT")){
						return TokenType.INT_TYPE;
					} else if (this.currentSpelling.toString().equals("BOOL")){
						return TokenType.BOOL_TYPE;
					}else if (this.currentSpelling.toString().equals("writef")){
						return TokenType.WRITEF;
					}else if (this.currentSpelling.toString().equals("VOID")){
						return TokenType.VOID_TYPE;
					}else if (this.currentSpelling.toString().equals("PROC")){
						return TokenType.PROC;
					}else if (this.currentSpelling.toString().equals("FUNC")){
						return TokenType.FUNC;
					} 
						else 
							return TokenType.IDENTIFIER;
				}
			case 2:
				if (this.isDigit(this.currentChar)){
					s = TokenType.NUMBER;
					this.getNextChar();
					break;
				} else 
					return TokenType.NUMBER;
					
			case 3:
				if (this.currentChar == '='){
					this.getNextChar();
					return TokenType.ASG;
				} else{
					throw new LexicalException("Erro lexico caso 3!", this.currentChar, this.line, this.column);
				}
			case 5:
				if (this.currentChar == '='){
					this.getNextChar();
					return TokenType.EQUALS;
				} else{
					throw new LexicalException("Erro lexico caso 5!", this.currentChar, this.line, this.column);
				}
			case 9:
				if (this.currentChar == '='){
					this.getNextChar();
					return TokenType.MAJOREQ;
				} else
					return TokenType.MAJOR;
			case 11:
				if (this.currentChar == '='){
					this.getNextChar();
					return TokenType.MINOREQ;
				} else
					return TokenType.MINOR;
			case 13:
				if (this.currentChar == '='){
					this.getNextChar();
					return TokenType.DIFFERENT;
				} else
					return TokenType.NOT;

			}
		}
		return s;
	}
	
}