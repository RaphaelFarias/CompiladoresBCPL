package checker;

import java.util.ArrayList;
import util.AST.*;
import util.symbolsTable.IdentificationTable;

public final class Checker implements Visitor {
	private int contadorParametros = 0;
	private IdentificationTable idTable;

	public void check(Program prog) throws SemanticException {
		idTable = new IdentificationTable();
		prog.visit(this, null);
	}

	public Object visitProgram(Program prog, Object args)// ok
			throws SemanticException {
		// TODO Auto-generated method stub

		for (Command cmd : prog.c) {
			cmd.visit(this, null);
		}
		for (Procedure proc : prog.proc) {
			proc.visit(this, null);
		}
		for (Function func : prog.func) {
			func.visit(this, null);
		}
		return null;
	}

	public Object visitVarDeclaration(VarDeclaration var, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub

		for (Identifier id : var.getIds()) {
			if (idTable.retrieve(id.spelling) != null)
				throw new SemanticException("Variavel " + id.spelling
						+ " ja foi declarada!");
			else
				id.visit(this, var);
		}
		for (AssignCommand asg : var.getAsgs()) {
			asg.visit(this, args);
		}

		return null;
	}

	public Object visitWhileCommand(WhileCommand whileCmd, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub

		if (whileCmd.getExpressao().visit(this, args).equals("INT")) {
			throw new SemanticException(
					"Expressao invalida. Expressao deve ser booleana!");
		}
		idTable.openScope();
		for (Command cmd : whileCmd.getWhlCmd()) {
			if(cmd instanceof BreakCommand){
				visitBreakCommand((BreakCommand)cmd, args);
			}else if(cmd instanceof ContinueCommand){
				visitContinueCommand((ContinueCommand)cmd, args);
			}else
			cmd.visit(this, whileCmd);
		}
		idTable.closeScope();
		return null;
	}

	public Object visitCommand(Command cmd, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub

		if (cmd instanceof VarDeclaration) {
			return ((VarDeclaration) cmd).visit(this, args);
		} else if (cmd instanceof IfStatement) {
			return ((IfStatement) cmd).visit(this, args);
		} else if (cmd instanceof TestStatement) {
			return ((TestStatement) cmd).visit(this, args);
		} else if (cmd instanceof WhileCommand) {
			return ((WhileCommand) cmd).visit(this, args);
		} else if (cmd instanceof Writef) {
			return ((Writef) cmd).visit(this, args);
		} else if (cmd instanceof ReturnCommand) {
			return ((ReturnCommand) cmd).visit(this, args);
		} else if (cmd instanceof AssignCommand) {
			return ((AssignCommand) cmd).visit(this, args);
		} else if (cmd instanceof FunctionCall) {
			return ((FunctionCall) cmd).visit(this, args);
		} else if (cmd instanceof ProcedureCall) {
			return ((ProcedureCall) cmd).visit(this, args);
		} else if (cmd instanceof BreakCommand) {
			return ((BreakCommand) cmd).visit(this, args);
		} else if (cmd instanceof ContinueCommand) {
			return ((ContinueCommand) cmd).visit(this, args);
		}

		return null;
	}

	public Object visitTestStatement(TestStatement testCmd, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub

		Object temp = null;

		if (testCmd.getExp() instanceof ExpressaoBooleana) {
			((ExpressaoBooleana) testCmd.getExp()).visit(this, args);
			idTable.openScope();
			for (Command thenCmd : testCmd.getThenCmd()) {
				if (thenCmd instanceof BreakCommand) {
					break;
				} else
					temp = thenCmd.visit(this, args);
			}
			idTable.closeScope();
			idTable.openScope();
			for (Command elseCmd : testCmd.getElseCmd()) {
				if (elseCmd instanceof BreakCommand) {
					break;
				} else {
					elseCmd.visit(this, args);
				}
			}
			idTable.closeScope();

		} else
			throw new SemanticException(
					"Expressao da condicao do TEST nao e booleana!");

		return temp;
	}

	public Object visitIfStatement(IfStatement ifCmd, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub

		Object temp = null;

		if (!(ifCmd.getExp() instanceof ArithmeticExpression)) {
			((ExpressaoBooleana) ifCmd.getExp()).visit(this, args);
			idTable.openScope();
			for (Command doCmd : ifCmd.getDoCmd()) {
				if (doCmd instanceof BreakCommand) {
					break;
				} else
					temp = doCmd.visit(this, args);
			}
			idTable.closeScope();
		} else
			throw new SemanticException(
					"Expressao da condicao do IF nao e booleana!");

		return temp;
	}

	public Object visitIdentifier(Identifier id, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub
		if (args instanceof VarDeclaration) {
			id.type = 0;
			id.tipo = ((VarDeclaration) args).getType();
			id.declaration = args;
			idTable.enter(id.spelling, (AST) args);
		} else if (args instanceof Function) {
			id.type = 1;
			id.tipo = ((Function) args).getTipoRetorno();
			id.declaration = args;
			id.memoryPosition = this.contadorParametros;
			this.contadorParametros += 1;
			idTable.enter(id.spelling, (AST) args);
		} else if (args instanceof Procedure) {
			id.type = 2;
			id.tipo = ((Procedure) args).getTipo();
			id.declaration = args;
			id.memoryPosition = this.contadorParametros;
			this.contadorParametros += 1;
			idTable.enter(id.spelling, (AST) args);
		} else if (args instanceof Factor) {
			if (idTable.retrieve(((Factor) args).getId().spelling) == null
					&& ((Factor) args).getId() != null) {
				throw new SemanticException("A variavel "
						+ ((Factor) args).getId().spelling
						+ " nao foi declarada!");
			} else {
				return ((Factor) args).getId().tipo;
			}
		} else {
			id.type = 3;
			id.declaration = args;
			id.tipo = ((Parametro) args).getType();
			idTable.enter(id.spelling, (AST) args);

		}
		return id;
	} 

	public Object visitWritef(Writef writef, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub
		
			writef.getExp().visit(this, writef);
		

		return null;
	}

	public Object visitNumero(Numero num, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub
		return "INT";
	}

	public Object visitBoolValue(BoolValue bool, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub
		return "BOOL";
	}

	public Object visitComparativeOperator(ComparativeOperator op, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitArithmeticOperator(ArithmeticOperator op, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitRelationalOperator(RelationalOperator op, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitBreakCommand(BreakCommand cmd, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		if(!(args instanceof WhileCommand)){
			throw new SemanticException("O comando BREAK só pode ser utilisado em loops");
		}
		return null;
	}

	public Object visitContinueCommand(ContinueCommand cmd, Object args)
			throws SemanticException {
		if(!(args instanceof WhileCommand)){
			throw new SemanticException("O comando CONTINUE só pode ser utilisado em loops");
		}
		return null;
	}

	public Object visitParametro(Parametro par, Object args) // ok
			throws SemanticException {
		return par.getType();
	}

	public Object visitArithmeticExpression(ArithmeticExpression exp,
			Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub
		Term term;
		ArithmeticExpression aExp;
		term = exp.getTerm();
		aExp = exp.getExp();
		Object temp = term.visit(this, args);
		if (aExp == null) {
			return term.visit(this, args);
		} else if (aExp.visit(this, args).equals(temp)) {
			return temp;
		} else
			throw new SemanticException("Tipos incompativeis. Termo eh"
					+ term.visit(this, args) + "e Expressao eh"
					+ aExp.visit(this, args));
	}

	public Object visitTerm(Term termo, Object args) throws SemanticException {
		Term term;
		Factor fac;
		fac = termo.getFator();
		term = termo.getTermo();
		Object temp = fac.visit(this, args);
		if (term == null) {
			return fac.visit(this, args);
		} else if (term.visit(this, args).equals(temp)) {
			return temp;
		} else {
			throw new SemanticException("Tipos incompativeis. Fator eh"
					+ fac.visit(this, args) + "e Termo eh"
					+ term.visit(this, args));
		}
	}

	public Object visitFactor(Factor fator, Object args)
			throws SemanticException {
		if (fator.getId() != null) {
			return fator.getId().visit(this, fator);
		} else if (fator.getNumero() != null) {
			return "INT";
		} else if (fator.getAritExp() != null) {
			return fator.getAritExp().visit(this, args);
		} else {
			throw new SemanticException("Erro no visitFactor");
		}
	}

	public Object visitExpressaoBooleana(ExpressaoBooleana boolexp, Object args)
			throws SemanticException {
		if (boolexp.getCompBool() != null) {
			return boolexp.getCompBool().visit(this, args);
		} else if (boolexp.getCompNum() != null) {
			return boolexp.getCompNum().visit(this, args);
		} else {
			throw new SemanticException("Erro no visitExpressaoBooleana");
		}
	}

	public Object visitComparacaoBooleana(ComparacaoBooleana compBool,
			Object args) throws SemanticException {
		BoolValue boolValDir = compBool.getBoolValDir();
		BoolValue boolValEsq = compBool.getBoolValEsq();
		ArithmeticExpression expDir = compBool.getExpDir();
		ArithmeticExpression expEsq = compBool.getExpEsq();
		if (boolValEsq != null) {
			if (boolValDir != null) {
				return "BOOL";
			} else if (expDir != null &&  expDir.visit(this, args).equals("INT")) {
				throw new SemanticException(
						"Erro! Nao se pode comparar um valor Booleano com um numero");
			} else {
				return "BOOL";
			}
		} else if (expEsq != null) {
			if (expDir != null
					&& expDir.visit(this, args)
							.equals(expEsq.visit(this, args))) {
				return "BOOL";
			} else if (boolValDir != null
					&& expDir.visit(this, args).equals("BOOL")) {
				return "BOOL";
			} else {
				throw new SemanticException(
						"Erro! Nao se pode comparar um valor Booleano com um Inteiro");
			}
		} else {
			throw new SemanticException("Erro no visitComparacaoBooleana");
		}
	}

	public Object visitProcedureCall(ProcedureCall proc, Object args)
			throws SemanticException {
		if (idTable.retrieve(proc.getNome().spelling) == null) {
			throw new SemanticException("O procedimento "
					+ proc.getNome().spelling + " nao foi declarado!");
		} else {
			AST temp = idTable.retrieve(proc.getNome().spelling);
			if (!(temp instanceof Procedure)) {
				throw new SemanticException("Identificador "
						+ proc.getNome().spelling
						+ " nao representa um procedimento!");
			} else {
				ArrayList<Parametro> params = ((Procedure) temp)
						.getParametros();
				if (((Procedure) temp).getParametros().size() != proc
						.getParams().size()) {
					throw new SemanticException(
							"Quantidade de parametros passada diferente da quantidade de parametros requeridas pelo procedimento!");
				} else {
					for (int i = 0; i < params.size(); i++) {
						if (params.get(i).visit(this, args) != proc.getParams()
								.get(i).visit(this, args)) {
							throw new SemanticException(
									"Tipo dos parametros informados não correspondem ao tipo esperado");
						}
					}
				}
			}
		}
		return ((Procedure) idTable.retrieve(proc.getNome().spelling))
				.getTipo();
	}

	public Object visitFunctionCall(FunctionCall func, Object args)
			throws SemanticException {
		if (idTable.retrieve(func.getNome().spelling) == null) {
			throw new SemanticException("A funcao " + func.getNome().spelling
					+ " nao foi declarada!");
		} else {
			AST temp = idTable.retrieve(func.getNome().spelling);
			if (!(temp instanceof Function)) {
				throw new SemanticException("Identificador "
						+ func.getNome().spelling
						+ " nao representa uma Funcao!");
			} else {
				ArrayList<Parametro> params = ((Function) temp).getParametros();
				if (((Function) temp).getParametros().size() != func
						.getParams().size()) {
					throw new SemanticException(
							"Quantidade de parametros passada diferente da quantidade de parametros requeridas pela funcao!");
				} else {
					for (int i = 0; i < params.size(); i++) {
						if (params.get(i).visit(this, args) != func.getParams()
								.get(i).visit(this, args)) {
							throw new SemanticException(
									"Tipo dos parametros informados não correspondem ao tipo esperado");
						}
					}
				}
			}
		}
		return ((Function) idTable.retrieve(func.getNome().spelling))
				.getTipoRetorno();
	}

	public Object visitComparacaoNumero(ComparacaoNumero compNum, Object args)
			throws SemanticException {
		ArithmeticExpression expDir = compNum.getExpDir();
		ArithmeticExpression expEsq = compNum.getExpEsq();
		if (expDir != null && expEsq != null && expDir.visit(this, args).equals(expEsq.visit(this, args))
				&& !(expEsq.visit(this, args).equals("BOOL"))) {
			return "BOOL";
		} else {
			throw new SemanticException("Erro no visitComparacaoNumero");
		}
	}

	public Object visitAssignCommand(AssignCommand cmd, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub
		AST teste = idTable.retrieve(cmd.getId().spelling);
		if (teste != null) {
			if (teste instanceof VarDeclaration) {
				cmd.getId().tipo = ((VarDeclaration) teste).getType();
				if (cmd.getExpressao().visit(this, args).equals("BOOL")) {
					if (!cmd.getId().tipo.equals("BOOL")) {
						throw new SemanticException(
								"Tipos incompativeis. A expressao nao eh do tipo da variavel!");
					}
				} else {
					if (!(cmd.getId().tipo.equals("INT"))) {
						throw new SemanticException(
								"Tipos incompativeis. A expressao nao eh do tipo da variavel!");
					}
				}
			} else {
				if (args instanceof Procedure) {
					if (teste instanceof VarDeclaration) {
						cmd.getId().tipo = ((VarDeclaration) teste).getType();
						if (cmd.getExpressao().visit(this, args).equals("BOOL")) {
							if (!cmd.getId().tipo.equals("BOOL")) {
								throw new SemanticException(
										"Tipos incompativeis. A expressao nao eh do tipo da variavel!");
							}
						} else {
							if (!cmd.getId().tipo.equals("INT")) {
								throw new SemanticException(
										"Tipos incompativeis. A expressao nao eh do tipo da variavel!");
							}
						}
					} else if (teste instanceof Parametro) {
						cmd.getId().tipo = ((Parametro) teste).getType();
						if (cmd.getExpressao().visit(this, args).equals("BOOL")) {
							if (!cmd.getId().tipo.equals("BOOL")) {
								throw new SemanticException(
										"Tipos incompativeis. A expressao nao eh do tipo da variavel!");
							}
						} else {
							if (!cmd.getId().tipo.equals("INT")) {
								throw new SemanticException(
										"Tipos incompativeis. A expressao nao eh do tipo da variavel!");
							}
						}
					} else
						throw new SemanticException(
								"A variavel nao foi declarada nem eh um parametro!");
				} else
					throw new SemanticException("O identificador "
							+ cmd.getId().spelling
							+ " nao representa uma variavel!");
			}
		} else
			throw new SemanticException("Variavel " + cmd.getId().spelling
					+ " nao declarada!");

		return null;
	}

	public Object visitReturnCommand(ReturnCommand cmd, Object args)
			throws SemanticException {
		if (args instanceof Function
				&& ((Function) args).getTipoRetorno().equals("VOID")) {
			throw new SemanticException("Funcao VOID nao tem retorno");
		} else if (args instanceof Function
				&& ((ArithmeticExpression) args).getExp() == null) {
			if (!(cmd.getExp().visit(this, args).equals(((Function) args)
					.getTipoRetorno()))) {
				throw new SemanticException(
						"Valor retornado incompativel com o tipo de retorno da funcao!");
			} else {
				Identifier id = ((ArithmeticExpression) args).getTerm()
						.getFator().getId();
				AST temp = idTable.retrieve(id.spelling);
				if (temp == null) {
					throw new SemanticException("A variavel " + id.spelling
							+ " nao foi declarada!");
				}
			}
		} else if (args instanceof Procedure
				&& ((ArithmeticExpression) args).getExp() == null) {
			if (!(cmd.getExp().visit(this, args).equals(((Procedure) args)
					.getTipo()))) {
				throw new SemanticException(
						"Valor retornado incompativel com o tipo de retorno do procedimento!");
			} else {
				Identifier id = ((ArithmeticExpression) args).getTerm()
						.getFator().getId();
				AST temp = idTable.retrieve(id.spelling);
				if (temp == null) {
					throw new SemanticException("A variavel " + id.spelling
							+ " nao foi declarada!");
				}
			}
		} else if ((args instanceof Function)
				&& !(cmd.getExp().visit(this, args).equals(((Function) args)
						.getTipoRetorno()))) {
			throw new SemanticException(
					"Valor retornado incompativel com o tipo de retorno da funcao!");
		} else if ((args instanceof Procedure)
				&& !(cmd.getExp().visit(this, args).equals(((Procedure) args)
						.getTipo()))) {
			throw new SemanticException(
					"Valor retornado incompativel com o tipo de retorno do procedimento!");
		} else {
			throw new SemanticException(
					"Comando de retorno deve estar dentro de um procedimento ou funcao!");
		}
		return null;
	}

	public Object visitProcedure(Procedure proc, Object args)
			throws SemanticException {
		proc.getId().visit(this, proc);
		idTable.openScope();
		for (Parametro params : proc.getParametros()) {
			params.visit(this, args);
		}
		Object temp = null;
		ArrayList<Command> cmds = new ArrayList<Command>();
		for (Command cmd : proc.getCmd()) {
			if (cmd instanceof ReturnCommand) {
				temp = cmd;
				cmds.add(cmd);
				break;
			} else
				cmds.add(cmd);
		}

		if (cmds.size() != proc.getCmd().size())
			throw new SemanticException(
					"Regra extra! Nao deve haver comandos apos o retorno do procedimentos ou funcoes!");
		for (Command cmd : proc.getCmd()) {
			if (temp != null) {
				if (temp instanceof ReturnCommand)
					cmd.visit(this, proc);
				else
					temp = cmd.visit(this, proc);
			} else
				temp = cmd.visit(this, proc);
		}

		if (temp == null) {
			throw new SemanticException("Procedimento " + proc.getId().spelling
					+ " precisa retornar um valor do tipo " + proc.getTipo());
		}
		idTable.closeScope();

		return null;
	}

	public Object visitFunction(Function func, Object args)
			throws SemanticException {
		func.getId().visit(this, func);
		idTable.openScope();
		for (Parametro params : func.getParametros()) {
			params.visit(this, args);
		}
		Object temp = null;
		ArrayList<Command> cmds = new ArrayList<Command>();
		for (Command cmd : func.getCmd()) {
			if (cmd instanceof ReturnCommand) {
				temp = cmd;
				cmds.add(cmd);
				break;
			} else
				cmds.add(cmd);
		}

		if (cmds.size() != func.getCmd().size())
			throw new SemanticException(
					"Regra extra! Nao deve haver comandos apos o retorno do procedimentos ou funcoes!");
		for (Command cmd : func.getCmd()) {
			if (temp != null) {
				if (temp instanceof ReturnCommand)
					cmd.visit(this, func);
				else
					temp = cmd.visit(this, func);
			} else
				temp = cmd.visit(this, func);
		}

		if (temp == null && ((!func.getTipoRetorno().equals("VOID")))) {
			throw new SemanticException("A Funcao " + func.getId().spelling
					+ " precisa retornar um valor do tipo "
					+ func.getTipoRetorno());
		}
		idTable.closeScope();

		return null;
	}
}
