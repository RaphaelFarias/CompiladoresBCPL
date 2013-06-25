package parser;

import java.util.ArrayList;

import scanner.LexicalException;
import scanner.Scanner;
import scanner.Token;
import scanner.Token.TokenType;
import util.AST.*;

/**
 * Parser class
 * 
 * @version 2010-august-29
 * @discipline Projeto de Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Parser {

	// The current token
	private Token currentToken = null;
	// The scanner
	private Scanner scanner = null;

	/**
	 * Parser constructor
	 * 
	 * @throws LexicalException
	 */
	public Parser() throws LexicalException {
		// Initializes the scanner object
		this.scanner = new Scanner();
		this.currentToken = this.scanner.getNextToken();
	}

	/**
	 * Veririfes if the current token kind is the expected one
	 * 
	 * @param kind
	 * @throws SyntacticException
	 * @throws LexicalException
	 */
	// TODO
	private void accept(int kind) throws SyntacticException, LexicalException {
		if (this.currentToken.getKind() == kind) {
			this.currentToken = this.scanner.getNextToken();
		} else {
			throw new SyntacticException("Erro sintatico no accept!",
					this.currentToken);
		}
	}

	/**
	 * Gets next token
	 * 
	 * @throws LexicalException
	 */
	// TODO
	private void acceptIt() throws LexicalException {
		this.currentToken = this.scanner.getNextToken();
	}

	/**
	 * Verifies if the source program is syntactically correct
	 * 
	 * @throws SyntacticException
	 * @throws LexicalException
	 */
	// TODO
	public AST parse() throws SyntacticException, LexicalException {
		AST program = parseProgram();
		return program;
	}

	public Program parseProgram() throws LexicalException, SyntacticException {
		Program prog = null;
		ArrayList<Procedure> proc = new ArrayList<Procedure>();
		ArrayList<Function> func = new ArrayList<Function>();
		ArrayList<Command> cmd = new ArrayList<Command>();
		if (this.currentToken.getKind() != TokenType.PROC
				&& this.currentToken.getKind() != TokenType.FUNC) {
			while (this.currentToken.getKind() != TokenType.EOT
					&& this.currentToken.getKind() != TokenType.FUNC
					&& this.currentToken.getKind() != TokenType.PROC) {
				cmd.add(parseCommand());
			}
		}  if (this.currentToken.getKind() == TokenType.PROC) {
			while (this.currentToken.getKind() != TokenType.EOT
					&& this.currentToken.getKind() != TokenType.FUNC) {
				proc.add(parseProcedure());
			}
		}  if (this.currentToken.getKind() == TokenType.FUNC) {
			while (this.currentToken.getKind() != TokenType.EOT) {
				func.add(parseFunction());
			}
		}if(this.currentToken.getKind() == TokenType.EOT){
		accept(TokenType.EOT);
		}
		prog = new Program(cmd, func, proc);
		return prog;
	}

	public Procedure parseProcedure() throws LexicalException,
			SyntacticException {
		String tipoRetorno;
		Identifier nomeProc = null;
		Parametro par = null;
		ArrayList<Parametro> parametros = new ArrayList<Parametro>();
		ArrayList<Command> cmd = new ArrayList<Command>();
		Identifier id = null;
		String tipoVar = null;
		accept(TokenType.PROC);
		if (this.currentToken.getKind() == TokenType.BOOL_TYPE) {
			tipoRetorno = this.currentToken.getSpelling();
			acceptIt();
		} else {
			accept(TokenType.INT_TYPE);
			tipoRetorno = this.currentToken.getSpelling();
		}
		nomeProc = new Identifier(this.currentToken.getSpelling());
		nomeProc.type = 2;
		accept(TokenType.IDENTIFIER);
		accept(TokenType.L_PAR);
		while (this.currentToken.getKind() != TokenType.R_PAR) {
			if (this.currentToken.getKind() == TokenType.INT_TYPE
					|| this.currentToken.getKind() == TokenType.BOOL_TYPE) {
				tipoVar = this.currentToken.getSpelling();
				acceptIt();
				id = new Identifier(this.currentToken.getSpelling());
				id.type = 2;
				if(this.currentToken.getKind() == TokenType.INT_TYPE){
				id.tipo = "INT";
				} else if(this.currentToken.getKind() == TokenType.BOOL_TYPE){
					id.tipo = "BOOL";
					}
				accept(TokenType.IDENTIFIER);
				par = new Parametro(id, tipoVar);
				parametros.add(par);
				if (this.currentToken.getKind() == TokenType.COMMA) {
					acceptIt();
					if (this.currentToken.getKind() == TokenType.R_PAR) {
						throw new SyntacticException(
								"Invalid Token no parse Procedure!",
								this.currentToken);
					} else {
						continue;
					}
				} else {
					break;
				}
			} else {
				break;
			}
		}
		accept(TokenType.R_PAR);
		accept(TokenType.ASG);
		accept(TokenType.L_BRACE);
		do {
			cmd.add(parseCommand());
		} while (this.currentToken.getKind() != TokenType.R_BRACE);
		accept(TokenType.R_BRACE);

		return new Procedure(tipoRetorno, id, parametros, cmd);

	}

	public Function parseFunction() throws LexicalException, SyntacticException {
		String tipoRetorno;
		Identifier nomeFunc = null;
		Parametro par = null;
		ArrayList<Parametro> parametros = new ArrayList<Parametro>();
		ArrayList<Command> cmd = new ArrayList<Command>();
		Identifier id = null;
		String tipoVar = null;
		accept(TokenType.FUNC);
		if (this.currentToken.getKind() == TokenType.BOOL_TYPE
				|| this.currentToken.getKind() == TokenType.INT_TYPE) {
			tipoRetorno = this.currentToken.getSpelling();
			acceptIt();
		} else {
			tipoRetorno = this.currentToken.getSpelling();
			accept(TokenType.VOID_TYPE);
		}
		nomeFunc = new Identifier(this.currentToken.getSpelling());
		nomeFunc.type = 2;
		accept(TokenType.IDENTIFIER);
		accept(TokenType.L_PAR);
		while (this.currentToken.getKind() != TokenType.R_PAR) {
			if (this.currentToken.getKind() == TokenType.INT_TYPE
					|| this.currentToken.getKind() == TokenType.BOOL_TYPE) {
				tipoVar = this.currentToken.getSpelling();
				acceptIt();
				id = new Identifier(this.currentToken.getSpelling());
				id.type = 2;
				if(this.currentToken.getKind() == TokenType.INT_TYPE){
					id.tipo = "INT";
					} else if(this.currentToken.getKind() == TokenType.BOOL_TYPE){
						id.tipo = "BOOL";
						}
				accept(TokenType.IDENTIFIER);
				par = new Parametro(id, tipoVar);
				parametros.add(par);
				if (this.currentToken.getKind() == TokenType.COMMA) {
					acceptIt();
					if (this.currentToken.getKind() == TokenType.R_PAR) {
						throw new SyntacticException(
								"Invalid Token no parse Procedure!",
								this.currentToken);
					} else {
						continue;
					}
				} else {
					break;
				}
			} else {
				break;
			}
		}
		accept(TokenType.R_PAR);
		accept(TokenType.BE);
		accept(TokenType.L_BRACE);
		do {
			cmd.add(parseCommand());
		} while (this.currentToken.getKind() != TokenType.R_BRACE);
		accept(TokenType.R_BRACE);
		return new Function(tipoRetorno, nomeFunc, parametros, cmd);
	}

	public Command parseCommand() throws LexicalException, SyntacticException {
		Command cmd = null;
		if (this.currentToken.getKind() == TokenType.LET
				|| this.currentToken.getKind() == TokenType.GLOBAL) {
			cmd = parseVarDeclaration();
		} else if (this.currentToken.getKind() == TokenType.IF) {
			cmd = parseIfStatement();
		} else if (this.currentToken.getKind() == TokenType.TEST) {
			cmd = parseTestStatement();
		} else if (this.currentToken.getKind() == TokenType.WHILE) {
			cmd = parseWhile();
		} else if (this.currentToken.getKind() == TokenType.WRITEF) {
			cmd = parseWritef();
		} else if (this.currentToken.getKind() == TokenType.RETURN) {
			cmd = parseReturnCommand();
		} else if (this.currentToken.getKind() == TokenType.FUNC) {
			cmd = parseFunctionCall();
		} else if (this.currentToken.getKind() == TokenType.PROC) {
			cmd = parseProcedureCall();
		} else if (this.currentToken.getKind() == TokenType.BREAK) {
			cmd = parseBreakCommand();
		} else if (this.currentToken.getKind() == TokenType.CONTINUE) {
			cmd = parseContinueCommand();
		} else {
			cmd = parseASGcommand();
		}
		return cmd;
	}

	public VarDeclaration parseVarDeclaration() throws SyntacticException,
			LexicalException {
		boolean isGlobal = false;
		String type = "";
		Identifier id = null;
		ArrayList<Identifier> ids = new ArrayList<Identifier>();
		ArrayList<AssignCommand> asgs = new ArrayList<AssignCommand>();
		if (this.currentToken.getKind() == TokenType.LET
				|| this.currentToken.getKind() == TokenType.GLOBAL) {
			if (this.currentToken.getKind() == TokenType.GLOBAL) {
				isGlobal = true;
				acceptIt();
			} else {
				isGlobal = false;
				acceptIt();
			}
			if (this.currentToken.getKind() == TokenType.BOOL_TYPE
					|| this.currentToken.getKind() == TokenType.INT_TYPE) {
				type = currentToken.getSpelling();
				acceptIt();
				if (this.currentToken.getKind() == TokenType.L_PAR) {
					acceptIt();
					asgs.add(parseASGcommand());
					accept(TokenType.R_PAR);
				} else {
					id = new Identifier(this.currentToken.getSpelling());
					id.type = 0;
					if(this.currentToken.getKind() == TokenType.INT_TYPE){
						id.tipo = "INT";
						} else if(this.currentToken.getKind() == TokenType.BOOL_TYPE){
							id.tipo = "BOOL";
							}
					accept(TokenType.IDENTIFIER);
					ids.add(id);
				}
			}else {
				throw new SyntacticException("Invalid Token no parse Declaracao de variavel!", this.currentToken);
			}
			}
			
		return new VarDeclaration(isGlobal, type, ids, asgs);
	}

	public AssignCommand parseASGcommand() throws SyntacticException,
			LexicalException {
		Identifier id = null;
		Expressao exp = null;
		FunctionCall func = null;
		id = new Identifier(this.currentToken.getSpelling());
		id.type = 0;
		accept(TokenType.IDENTIFIER);
		accept(TokenType.ASG);
		if (this.currentToken.getKind() == TokenType.FUNC) {
			func = parseFunctionCall();
			accept(TokenType.SEMICOLON);
			return new AssignCommand(id, func);
		} else {
			exp = parseExpression();
			accept(TokenType.SEMICOLON);
			return new AssignCommand(id, exp);
		}

	}

	public IfStatement parseIfStatement() throws SyntacticException,
			LexicalException {
		Expressao exp = null;
		ArrayList<Command> doCmd = new ArrayList<Command>();
		accept(TokenType.IF);
		accept(TokenType.L_PAR);
		exp = parseBooleanExpression();
		accept(TokenType.R_PAR);
		accept(TokenType.DO);
		accept(TokenType.L_BRACE);
		do {
			doCmd.add(parseCommand());
		} while (this.currentToken.getKind() != TokenType.R_BRACE);
		accept(TokenType.R_BRACE);
		return new IfStatement(exp, doCmd);
	}

	public TestStatement parseTestStatement() throws SyntacticException,
			LexicalException {
		Expressao exp = null;
		ArrayList<Command> ThenCmd = new ArrayList<Command>();
		ArrayList<Command> ElseCmd = new ArrayList<Command>();
		accept(TokenType.TEST);
		accept(TokenType.L_PAR);
		exp = parseBooleanExpression();
		accept(TokenType.R_PAR);
		accept(TokenType.THEN);
		accept(TokenType.L_BRACE);
		do {
			ThenCmd.add(parseCommand());
		} while (this.currentToken.getKind() != TokenType.R_BRACE);
		accept(TokenType.R_BRACE);
		accept(TokenType.ELSE);
		accept(TokenType.L_BRACE);
		do {
			ElseCmd.add(parseCommand());
		} while (this.currentToken.getKind() != TokenType.R_BRACE);
		accept(TokenType.R_BRACE);
		return new TestStatement(exp, ThenCmd, ElseCmd);
	}

	public WhileCommand parseWhile() throws SyntacticException,
			LexicalException {
		Expressao exp = null;
		ArrayList<Command> cmds = new ArrayList<Command>();
		accept(TokenType.WHILE);
		accept(TokenType.L_PAR);
		exp = parseBooleanExpression();
		accept(TokenType.R_PAR);
		accept(TokenType.DO);
		accept(TokenType.L_BRACE);
		do {
			cmds.add(parseCommand());
		} while (this.currentToken.getKind() != TokenType.R_BRACE);
		accept(TokenType.R_BRACE);
		return new WhileCommand(exp, cmds);
	}

	public Writef parseWritef() throws SyntacticException, LexicalException {
		
		Expressao exp;
		accept(TokenType.WRITEF);
		accept(TokenType.L_PAR);
		exp = parseExpression();
		accept(TokenType.R_PAR);
		accept(TokenType.SEMICOLON);
		
		return new Writef(exp);
	}

	public ReturnCommand parseReturnCommand() throws SyntacticException,
			LexicalException {
		Expressao exp;
		accept(TokenType.RETURN);
		exp = parseExpression();
		accept(TokenType.SEMICOLON);
		return new ReturnCommand(exp);

	}

	public FunctionCall parseFunctionCall() throws SyntacticException,
			LexicalException {
		ArrayList<Expressao> exps = new ArrayList<Expressao>();
		Identifier nome;
		accept(TokenType.FUNC);
		nome = new Identifier(this.currentToken.getSpelling());
		nome.type = 1;
		accept(TokenType.IDENTIFIER);
		accept(TokenType.L_PAR);
		if(this.currentToken.getKind() != TokenType.R_PAR){
		exps.add(parseExpression());
		while (this.currentToken.getKind() != TokenType.R_PAR && this.currentToken.getSpelling() != "\000") {
			if (this.currentToken.getKind() == TokenType.COMMA) {
				acceptIt();
				exps.add(parseExpression());
			} else{
				break;
			}
		}
		}
		accept(TokenType.R_PAR);
		return new FunctionCall(nome, exps);
	}

	public ProcedureCall parseProcedureCall() throws SyntacticException,
			LexicalException {
		ArrayList<Expressao> exps = new ArrayList<Expressao>();
		Identifier nome;
		accept(TokenType.PROC);
		nome = new Identifier(this.currentToken.getSpelling());
		nome.type = 1;
		accept(TokenType.IDENTIFIER);
		accept(TokenType.L_PAR);
		if(this.currentToken.getKind() != TokenType.R_PAR){
		exps.add(parseExpression());
		while (this.currentToken.getKind() != TokenType.R_PAR && this.currentToken.getSpelling() != "\000") {
			if (this.currentToken.getKind() == TokenType.COMMA) {
				acceptIt();
				exps.add(parseExpression());
			} else{
				break;
			}
		}
		}
		accept(TokenType.R_PAR);
		return new ProcedureCall(nome, exps);
	}

	public BreakCommand parseBreakCommand() throws SyntacticException,
			LexicalException {
		accept(TokenType.BREAK);
		accept(TokenType.SEMICOLON);
		return new BreakCommand();
	}

	public ContinueCommand parseContinueCommand() throws SyntacticException,
			LexicalException {
		accept(TokenType.CONTINUE);
		accept(TokenType.SEMICOLON);
		return new ContinueCommand();
	}

	public Expressao parseExpression() throws SyntacticException,
			LexicalException {
		Expressao exp;
		if (this.currentToken.getKind() == TokenType.BOOL_VALUE
				|| this.currentToken.getKind() == TokenType.L_PAR
				|| this.currentToken.getKind() == TokenType.L_BRACKET) {
			exp = parseBooleanExpression();
		} else {
			exp = parseArithmeticExpression();
		}
		return exp;
	}

	public ExpressaoBooleana parseBooleanExpression() throws LexicalException,
			SyntacticException {
		ExpressaoBooleana exp = null;
		if(this.currentToken.getKind() == TokenType.L_PAR){
			exp = new ExpressaoBooleana(parseComparacaoNumero());
		}else{
			exp = new ExpressaoBooleana(parseComparacaoBooleana());
		}
		return exp;
	}

	public ComparacaoBooleana parseComparacaoBooleana()
			throws LexicalException, SyntacticException {
		BoolValue boolValDir = null;
		BoolValue boolValEsq = null;
		ComparativeOperator op = null;
		ArithmeticExpression expDir = null;
		ArithmeticExpression expEsq = null;
		ComparacaoBooleana comp =null;
		
		if(this.currentToken.getKind() == TokenType.L_BRACKET){
			acceptIt();
		if (this.currentToken.getKind() == TokenType.BOOL_VALUE) {
			boolValEsq = new BoolValue(this.currentToken.getSpelling());
			acceptIt();
			if(isComparativoOperator(this.currentToken.getKind())){
				op = new ComparativeOperator(this.currentToken.getSpelling());
				acceptIt();
				if(this.currentToken.getKind() == TokenType.BOOL_VALUE){
					boolValDir = new BoolValue(this.currentToken.getSpelling());
					acceptIt();
					comp = new ComparacaoBooleana(boolValEsq,op,boolValDir);
				}else {
					acceptIt();
					expDir = parseArithmeticExpression();
					accept(TokenType.R_BRACKET);
					comp = new ComparacaoBooleana(boolValEsq,op,expDir);
				}
			}else{
				comp = new ComparacaoBooleana (boolValEsq);
			}
			
		}else{
			expEsq = parseArithmeticExpression();
			if(isComparativoOperator(this.currentToken.getKind())){
				op = new ComparativeOperator(this.currentToken.getSpelling());
				acceptIt();
				if(this.currentToken.getKind() == TokenType.BOOL_VALUE){
					boolValDir = new BoolValue(this.currentToken.getSpelling());
					acceptIt();
					comp = new ComparacaoBooleana(expEsq,op,boolValDir);
				}else {
					expDir = parseArithmeticExpression();
					comp = new ComparacaoBooleana(expEsq,op,expDir);
				}
			}else{
				throw new SyntacticException("Invalid Token no parse Comparacao Booleana!", this.currentToken);
			}
			
		}
		accept(TokenType.R_BRACKET);
	}else if(this.currentToken.getKind() == TokenType.BOOL_VALUE){
		boolValEsq = new BoolValue(this.currentToken.getSpelling());
		acceptIt();
		comp = new ComparacaoBooleana(boolValEsq);
	}
		return comp;

	}

	public ComparacaoNumero parseComparacaoNumero() throws SyntacticException,
			LexicalException {
		ArithmeticExpression expDir = null;
		RelationalOperator op = null;
		Identifier id = null;
		Numero num = null;
		ArithmeticExpression expEsq = null;
		if (this.currentToken.getKind() == TokenType.L_PAR) {
			accept(TokenType.L_PAR);
			expEsq = parseArithmeticExpression();
			accept(TokenType.R_PAR);
			if (isRelationalOperator(this.currentToken.getKind())) {
				op = new RelationalOperator(this.currentToken.getSpelling());
				acceptIt();
				if (this.currentToken.getKind() == TokenType.L_PAR) {
					accept(TokenType.L_PAR);
					expDir = parseArithmeticExpression();
					accept(TokenType.R_PAR);
				} else if (this.currentToken.getKind() == TokenType.IDENTIFIER) {
					id = new Identifier(this.currentToken.getSpelling());
					expDir = new ArithmeticExpression(new Term(new Factor(id)));
					acceptIt();
				} else if (this.currentToken.getKind() == TokenType.NUMBER) {
					num = new Numero(this.currentToken.getSpelling());
					expDir = new ArithmeticExpression(new Term(new Factor(num)));
					acceptIt();
				}
			}else throw new SyntacticException("Falta operador relacional", this.currentToken);
		} else if (this.currentToken.getKind() == TokenType.IDENTIFIER) {
			id = new Identifier(this.currentToken.getSpelling());
			expEsq = new ArithmeticExpression(new Term(new Factor(id)));
			acceptIt();
			if (isRelationalOperator(this.currentToken.getKind())) {
				op = new RelationalOperator(this.currentToken.getSpelling());
				acceptIt();
				if (this.currentToken.getKind() == TokenType.L_PAR) {
					accept(TokenType.L_PAR);
					expDir = parseArithmeticExpression();
					accept(TokenType.R_PAR);
				} else if (this.currentToken.getKind() == TokenType.IDENTIFIER) {
					id = new Identifier(this.currentToken.getSpelling());
					expDir = new ArithmeticExpression(new Term(new Factor(id)));
					acceptIt();
				} else if (this.currentToken.getKind() == TokenType.NUMBER) {
					num = new Numero(this.currentToken.getSpelling());
					expDir = new ArithmeticExpression(new Term(new Factor(num)));
					acceptIt();
				}
			}
		} else if (this.currentToken.getKind() == TokenType.NUMBER) {
			num = new Numero(this.currentToken.getSpelling());
			expEsq = new ArithmeticExpression(new Term(new Factor(num)));
			acceptIt();
			if (isRelationalOperator(this.currentToken.getKind())) {
				op = new RelationalOperator(this.currentToken.getSpelling());
				acceptIt();
				if (this.currentToken.getKind() == TokenType.L_PAR) {
					accept(TokenType.L_PAR);
					expDir = parseArithmeticExpression();
					accept(TokenType.R_PAR);
				} else if (this.currentToken.getKind() == TokenType.IDENTIFIER) {
					id = new Identifier(this.currentToken.getSpelling());
					expDir = new ArithmeticExpression(new Term(new Factor(id)));
					acceptIt();
				} else if (this.currentToken.getKind() == TokenType.NUMBER) {
					num = new Numero(this.currentToken.getSpelling());
					expDir = new ArithmeticExpression(new Term(new Factor(num)));
					acceptIt();
				}
			}
		}else throw new SyntacticException("Falta operador relacional", this.currentToken);
		//accept(TokenType.R_PAR);
		return new ComparacaoNumero(expDir, op, expEsq);
	}

	public ArithmeticExpression parseArithmeticExpression()
			throws SyntacticException, LexicalException {
		ArithmeticExpression exp;
		ArithmeticOperator op;
		Term termo;
		termo = parseTerm();
		if (this.currentToken.getKind() == TokenType.OP_ADD) {
			op = new ArithmeticOperator(this.currentToken.getSpelling());
			accept(TokenType.OP_ADD);
			exp = parseArithmeticExpression();
			return new ArithmeticExpression(termo, exp, op);
		} else
			return new ArithmeticExpression(termo);

	}

	public Term parseTerm() throws SyntacticException, LexicalException {
		Factor fator;
		ArithmeticOperator op;
		Term termo;
		fator = parseFactor();
		if (this.currentToken.getKind() == TokenType.OP_MULT) {
			op = new ArithmeticOperator(this.currentToken.getSpelling());
			accept(TokenType.OP_MULT);
			termo = parseTerm();
			return new Term(fator, op, termo);
		} else
			return new Term(fator);
	}
		
	public Factor parseFactor() throws SyntacticException, LexicalException {
		Factor fator = null;
		Numero num = null;
		Identifier id =null;
		if (this.currentToken.getKind() == TokenType.IDENTIFIER){
			id = new Identifier(this.currentToken.getSpelling());
			id.type = 0;
			fator = new Factor(id);
			acceptIt();
		}
		if (this.currentToken.getKind() == TokenType.NUMBER) {
			num = new Numero(this.currentToken.getSpelling());
			fator = new Factor(num);
			acceptIt();
		} else if (this.currentToken.getKind() == TokenType.L_PAR) {
			acceptIt();
			fator = new Factor(parseArithmeticExpression());
			accept(TokenType.R_PAR);
		} 
		
		return fator;
	}

	public String parseExpressaoUnaria() throws SyntacticException,
			LexicalException {
		String s;
		accept(TokenType.OP_ADD);
		s = this.currentToken.getSpelling();
		accept(TokenType.NUMBER);
		return s;
	}

	public boolean isRelationalOperator(int kind) {
		if (kind == TokenType.MAJOREQ || kind == TokenType.MINOREQ
				|| kind == TokenType.MAJOR || kind == TokenType.MINOR) {
			return true;
		} else
			return false;
	}
	
	public boolean isComparativoOperator(int kind) {
		if (kind == TokenType.EQUALS || kind == TokenType.DIFFERENT) {
			return true;
		} else
			return false;
	}
}
