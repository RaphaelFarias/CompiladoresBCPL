package principal;

import checker.Checker;
import checker.SemanticException;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;
import scanner.Token;
import scanner.Token.TokenType;
import util.AST.Program;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Analisador lexico
		try {
			Scanner scan = new Scanner();
			Parser parser = new Parser();
			

			Token token = scan.getNextToken();
			while (token.getKind() != TokenType.EOT){
				System.out.println(token.getSpelling() + " -> " + getType(token.getKind()));
				token = scan.getNextToken();
			}
//			Analisador sintatico
			try {
				//parser.parse();
				Program prog = (Program) (parser.parse());
				Checker cheker = new Checker();
				try {
					cheker.check(prog);
				} catch (SemanticException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SyntacticException e) {
				e.printStackTrace();
			}
		} catch (LexicalException e) {
			e.printStackTrace();
		}
	}


	private static String getType(int kind){
		switch (kind){

			case TokenType.IDENTIFIER:
				return "IDENTIFIER";
	
			case TokenType.NUMBER:
				return "NUMBER";
	
			case TokenType.COLON:
				return "COLON";
	
			case TokenType.ASG:
				return "ASG";
	
			case TokenType.EQUAL:
				return "EQUAL";
	
			case TokenType.EQUALS:
				return "EQUALS";
	
			case TokenType.SEMICOLON:
				return "SEMICOLON";
	
			case TokenType.OP_ADD:
				return "OP_ADD";
				
			case TokenType.OP_MULT:
				return "OP_MULT";
	
			case TokenType.MAJOR:
				return "MAJOR";
	
			case TokenType.MAJOREQ:
				return "MAJOREQ";
	
			case TokenType.MINOR:
				return "MINOR";
	
			case TokenType.MINOREQ:
				return "MINOREQ";
	
			case TokenType.NOT:
				return "NOT";
	
			case TokenType.DIFFERENT:
				return "DIFFERENT";
	
			case TokenType.L_PAR:
				return "L_PAR";
	
			case TokenType.R_PAR:
				return "R_PAR";
	
			case TokenType.L_BRACE:
				return "L_BRACE";
	
			case TokenType.R_BRACE:
				return "R_BRACE";
	
			case TokenType.COMMA:
				return "COMMA";
	
			case TokenType.IF:
				return "IF";
	
			case TokenType.TEST:
				return "TEST";
	
			case TokenType.THEN:
				return "THEN";
	
			case TokenType.ELSE:
				return "ELSE";
	
			case TokenType.DO:
				return "DO";
	
			case TokenType.WHILE:
				return "WHILE";
	
			case TokenType.RETURN:
				return "RETURN";
	
			case TokenType.BREAK:
				return "BREAK";
	
			case TokenType.CONTINUE:
				return "CONTINUE";
	
			case TokenType.LET:
				return "LET";
	
			case TokenType.BE:
				return "BE";
	
			case TokenType.MANIFEST:
				return "MANIFEST";
	
			case TokenType.GLOBAL:			
				return "GLOBAL";
	
			case TokenType.BOOL_VALUE:
				return "BOOL_VALUE";
	
			case TokenType.INT_TYPE:
				return "INT_TYPE";
	
			case TokenType.BOOL_TYPE:
				return "BOOL_TYPE";
	
			case TokenType.WRITEF:
				return "WRITEF";
	
			case TokenType.SYMBOL:
				return "SYMBOL";
	
			case TokenType.VOID_TYPE:
				return "VOID_TYPE";
	
			case TokenType.PROC:
				return "PROC";
	
			case TokenType.FUNC:
				return "FUNC";
				
			case TokenType.R_BRACKET:
				return "R_BRACKET";
				
			case TokenType.L_BRACKET:
				return "L_BRACKET";
	
			default:
				return "EOT";
		}
	}
}