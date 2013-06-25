package encoder;

import java.io.File;
import java.util.ArrayList;

import checker.SemanticException;

import util.Arquivo;
import util.AST.AST;
import util.AST.ArithmeticExpression;
import util.AST.ArithmeticOperator;
import util.AST.AssignCommand;
import util.AST.BoolValue;
import util.AST.BreakCommand;
import util.AST.Command;
import util.AST.ComparacaoBooleana;
import util.AST.ComparacaoNumero;
import util.AST.ComparativeOperator;
import util.AST.ContinueCommand;
import util.AST.Expressao;
import util.AST.ExpressaoBooleana;
import util.AST.Factor;
import util.AST.Function;
import util.AST.FunctionCall;
import util.AST.Identifier;
import util.AST.IfStatement;
import util.AST.Numero;
import util.AST.Parametro;
import util.AST.Procedure;
import util.AST.ProcedureCall;
import util.AST.Program;
import util.AST.RelationalOperator;
import util.AST.ReturnCommand;
import util.AST.Term;
import util.AST.TestStatement;
import util.AST.VarDeclaration;
import util.AST.Visitor;
import util.AST.WhileCommand;
import util.AST.Writef;

public class Encoder implements Visitor {

	// o código anterior n foi deletado, tá comentado e o novo código tem um comentário em cima 
	// explicando o que foi feito
	// nao esquecer de apagar esses comentários (sobre updates) do código
	
	ArrayList<Instruction> listInstruction = new ArrayList<Instruction>();
	int contadorIf = 1, contadorElse = 1, contadorTest = 1, contadorWhile = 1;
	int contadorDesvioCondicional = 1;
	int nextInstr = 0;

	File arquivo = new File(
			"\\home\\clodomir\\Dropbox\\POLI\\Compiladores\\Projeto BCPL\\Entrega 3\\outPut.asm");
	Arquivo out = new Arquivo(arquivo.toString(), arquivo.toString());

	public void encode(AST program) throws SemanticException {
		this.out.println("extern  _printf");
		program.visit(this, null);
		this.out.close();
	}

	public Object visitProgram(Program prog, Object args)// ok
			throws SemanticException {
		// TODO Auto-generated method stub
		int contVarDec = 0;

		if (contVarDec == 0)
			this.out.println("SECTION .data");
		// globais
		for (Command cmd : prog.c) {
			if (cmd instanceof VarDeclaration)
				cmd.visit(this, null);
			contVarDec++;
		}

		this.out.println("intFormat: db \"%d\", 10, 0");
		this.out.println("SECTION .text");
		this.out.println("global _WinMain@16");

		this.out.println("_WinMain@16:");
		this.out.println("push ebp");
		this.out.println("mov ebp, esp");

		for (Command cmd : prog.c) {
			if (!(cmd instanceof VarDeclaration))
				cmd.visit(this, null);
			contVarDec++;
		}
		for (Procedure proc : prog.proc) {
			proc.visit(this, null);
		}
		for (Function func : prog.func) {
			func.visit(this, null);
		}

		this.out.println("mov esp, ebp");
		this.out.println("pop ebp");
		this.out.println("mov eax, 0");
		this.out.println("hlt");

		return null;
	}

	public Object visitVarDeclaration(VarDeclaration var, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub
		
		// var.getGlobal retorna se ela é global ou local, então só entra no if se for global
		// tem que ver como fazer para variáveis locais, provavelmente um ELSE resolveria movendo
	    // o ESP em 4 pra cada
		if ((args == null) && var.getGlobal()) {
			for (int i = 0; i < var.getIds().size(); i++) {
				var.getIds().get(i).memoryPosition = this.nextInstr;
				this.out.println(var.getIds().get(i).spelling + ": dd 0");
				this.nextInstr += 4;
			}
			
			for (AssignCommand asg : var.getAsgs()) {
				asg.visit(this, args);
			}
			
		}
		return null;
	}

	public Object visitWhileCommand(WhileCommand whileCmd, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub

		if (args instanceof Procedure) {
			this.out.println("_" + ((Procedure) args).getId().spelling
					+ "_while_" + this.contadorWhile + "_begin:");
		} else if (args instanceof Function) {
			this.out.println("_" + ((Function) args).getId().spelling
					+ "_while_" + this.contadorWhile + "_begin:");
		} else if (args instanceof WhileCommand) {
			this.out.println("_while_" + this.contadorWhile + "_begin:");
		} else if (args instanceof IfStatement) {
			this.out.println("_while_" + this.contadorWhile + "_if_"
					+ this.contadorIf + "_begin:");
		} else if (args instanceof TestStatement) {
			this.out.println("_while_" + this.contadorWhile + "_test_"
					+ this.contadorTest + "_begin:");
		} else {
			this.out.println("_WinMain@16_while_" + this.contadorWhile
					+ "_begin:");
		}

		whileCmd.getExpressao().visit(this, whileCmd);

		if (args instanceof Procedure) {
			this.out.println(((Procedure) args).getId().spelling + "_while_"
					+ this.contadorWhile + "_end");
		} else if (args instanceof Function) {
			this.out.println(((Function) args).getId().spelling + "_while_"
					+ this.contadorWhile + "_end");
		} else if (args instanceof WhileCommand) {
			this.out.println("while_" + this.contadorWhile + "_end");
		} else if (args instanceof IfStatement) {
			this.out.println("while_" + this.contadorWhile + "_if_"
					+ this.contadorIf + "_end");
		} else if (args instanceof TestStatement) {
			this.out.println("while_" + this.contadorWhile + "_test_"
					+ this.contadorTest + "_end");
		} else {
			this.out.println("_WinMain@16_while_" + this.contadorWhile + "_end");
		}

		for (Command cmd : whileCmd.getWhlCmd()) {
			cmd.visit(this, whileCmd);
		}

		if (args instanceof Procedure) {
			this.out.println("jmp _" + ((Procedure) args).getId().spelling
					+ "_while_" + this.contadorWhile + "_begin");
			this.out.println("_" + ((Procedure) args).getId().spelling
					+ "_while_" + this.contadorWhile + "_end:");
		} else if (args instanceof Function) {
			this.out.println("jmp _" + ((Function) args).getId().spelling
					+ "_while_" + this.contadorWhile + "_begin");
			this.out.println("_" + ((Function) args).getId().spelling
					+ "_while_" + this.contadorWhile + "_end:");
		} else if (args instanceof WhileCommand) {
			this.out.println("jmp _" + "_while_" + this.contadorWhile
					+ "_begin");
			this.out.println("_" + "_while_" + this.contadorWhile + "_end:");
		} else if (args instanceof IfStatement) {
			this.out.println("jmp _while_" + this.contadorWhile + "_if_"
					+ this.contadorIf + "_begin");
			this.out.println("_while_" + this.contadorWhile + "_if_"
					+ this.contadorIf + "_end:");
		} else if (args instanceof TestStatement) {
			this.out.println("jmp _while_" + this.contadorWhile + "_test_"
					+ this.contadorTest + "_begin");
			this.out.println("_while_" + this.contadorWhile + "_test_"
					+ this.contadorTest + "_end:");
		} else {
			this.out.println("jmp _WinMain@16_while_" + this.contadorWhile	+ "_begin");
			this.out.println("_WinMain@16_while_" + this.contadorWhile
					+ "_end:");
		}

		this.contadorWhile += 1;

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

	public Object visitTestStatement(TestStatement testCmd, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub

		testCmd.getExp().visit(this, args);

//		if (args instanceof Procedure) {
//			this.out.println(((Procedure) args).getId().spelling + "_test_"
//					+ this.contadorTest + "_end");
//		} else if (args instanceof Function) {
//			this.out.println(((Function) args).getId().spelling + "_test_"
//					+ this.contadorTest + "_end");
//		} else if (args instanceof WhileCommand) {
//			this.out.println("while_" + ((WhileCommand) args).contadorWhile
//					+ "_test_" + this.contadorTest + "_end");
//		} else {
//			this.out.println("WinMain@16_" + "_test_" + this.contadorTest
//					+ "_end");
//		}
		
		
		// completa o desvio condicional com o label para o jump
		if (args instanceof Procedure)
			this.out.println(((Procedure) args).getId().spelling + "_else_"
					+ this.contadorTest + "_begin");
		else if (args instanceof Function)
			this.out.println(((Function) args).getId().spelling + "_else_"
					+ this.contadorTest + "_begin");
		else 
			this.out.println("WinMain@16_" + "_else_" + this.contadorTest
					+ "_begin");
		
		// visita os comandos no corpo do if		
		for (Command TestCmd : testCmd.getThenCmd()) {
			TestCmd.visit(this, args);
		}

		
//		if (args instanceof Procedure) {
//			this.out.println("jmp _" + ((Procedure) args).getId().spelling
//					+ "_desvioCondicional_" + this.contadorDesvioCondicional
//					+ "_end");
//			this.out.println("_" + ((Procedure) args).getId().spelling
//					+ "_test_" + this.contadorTest + "_end:");
//		} else if (args instanceof Function) {
//			this.out.println("jmp _" + ((Function) args).getId().spelling
//					+ "_desvioCondicional_" + this.contadorDesvioCondicional
//					+ "_end");
//			this.out.println("_" + ((Function) args).getId().spelling
//					+ "_test_" + this.contadorTest + "_end:");
//		} else if (args instanceof WhileCommand) {
//			visit while
//			this.out.println("jmp _while_"
//					+ ((WhileCommand) args).contadorWhile
//					+ "_desvioCondicional_" + contadorDesvioCondicional
//					+ "_end");
//			this.out.println("_while_" + ((WhileCommand) args).contadorWhile
//					+ "_test_" + this.contadorTest + "_end:");
//		} else {
//			this.out.println("jmp _WinMain@16_" + "_desvioCondicional_"
//					+ contadorDesvioCondicional + "_end");
//			this.out.println("_WinMain@16_" + "_test_" + this.contadorTest
//					+ "_end:");
//		}
		
		
		// pula para o final do test, de forma que não entra no else
		if (args instanceof Procedure) 
			this.out.println("jmp _" + ((Procedure) args).getId().spelling
					+ "_test_" + this.contadorTest + "_end");
		else if (args instanceof Function) 
			this.out.println("jmp _" + ((Function) args).getId().spelling
					+ "_test_" + this.contadorTest + "_end");
		else if (args instanceof WhileCommand)
			this.out.println("jmp _WinMain@16_" + "_test_" + this.contadorTest
					+ "_end");
		
		this.contadorTest += 1;


		// cria o label de inicio do else
		if (args instanceof Procedure)
			this.out.println("_" + ((Procedure) args).getId().spelling + "_else_"
					+ this.contadorTest + "_begin:");
		else if (args instanceof Function)
			this.out.println("_" + ((Function) args).getId().spelling + "_else_"
					+ this.contadorTest + "_begin:");
		else 
			this.out.println("_WinMain@16_" + "_else_" + this.contadorTest
					+ "_begin:");
		
		
		// visita os comandos do else
		for (Command elseCmd : testCmd.getElseCmd()) {
			elseCmd.visit(this, args);
		}		
		
		// define o label do fim do TEST 
		if (args instanceof Procedure) 
//			this.out.println("_" + ((Procedure) args).getId().spelling+ "_else_" + this.contadorElse + "_end:");
			this.out.println("_" + ((Procedure) args).getId().spelling+ "_test_" + this.contadorElse + "_end:");
		else if (args instanceof Function) 
//			this.out.println("_" + ((Function) args).getId().spelling+ "_else_" + this.contadorElse + "_end:");
			this.out.println("_" + ((Function) args).getId().spelling+ "_test_" + this.contadorElse + "_end:");

//		else if (args instanceof WhileCommand) {
//			this.out.println("_while_"
//					+ ((WhileCommand) args).contadorWhile + "_else_"
//					+ this.contadorElse + "_end:");
//			((WhileCommand) args).contadorWhile += 1;
//		} 
		else 
//			this.out.println("_WinMain@16_" + "_else_" + this.contadorElse + "_end:");
			this.out.println("_WinMain@16_" + "_test_" + this.contadorElse + "_end:");

		this.contadorElse += 1;

		
//		if (args instanceof Procedure) {
//			this.out.println("_" + ((Procedure) args).getId().spelling
//					+ "_desvioCondicional_" + this.contadorDesvioCondicional
//					+ "_end:");
//		} else if (args instanceof Function) {
//			this.out.println("_" + ((Function) args).getId().spelling
//					+ "_desvioCondicional_" + this.contadorDesvioCondicional
//					+ "_end:");
//		} else if (args instanceof WhileCommand) {
//			this.out.println("_while_" + ((WhileCommand) args).contadorWhile
//					+ "_desvioCondicional_" + this.contadorDesvioCondicional
//					+ "_end:");
//		} else {
//			this.out.println("_WinMain@16_" + "_desvioCondicional_"
//					+ this.contadorDesvioCondicional + "_end:");
//		}
//
//		this.contadorDesvioCondicional += 1;

		return null;
	}

	public Object visitIfStatement(IfStatement ifCmd, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub

		ifCmd.getExp().visit(this, args);
		
		// completa o desvio condicional
		if (args instanceof Procedure) 
			this.out.println(((Procedure) args).getId().spelling + "_if_"
					+ this.contadorIf + "_end");
		else if (args instanceof Function) 
			this.out.println(((Function) args).getId().spelling + "_if_"
					+ this.contadorIf + "_end");
//		else if (args instanceof WhileCommand) 
//			this.out.println("while_" + ((WhileCommand) args).contadorWhile
//					+ "_if_" + this.contadorIf + "_end");
		else 
			this.out.println("WinMain@16_" + "_if_" + this.contadorIf + "_end");
		
		
		// visita comandos do IF
		for (Command TestCmd : ifCmd.getDoCmd())
			TestCmd.visit(this, args);
		

//		if (args instanceof Procedure) {
//			this.out.println("jmp _" + ((Procedure) args).getId().spelling
//					+ "_desvioCondicional_" + this.contadorDesvioCondicional
//					+ "_end");
//			this.out.println("_" + ((Procedure) args).getId().spelling + "_if_"
//					+ this.contadorIf + "_end:");
//		} else if (args instanceof Function) {
//			this.out.println("jmp _" + ((Function) args).getId().spelling
//					+ "_desvioCondicional_" + this.contadorDesvioCondicional
//					+ "_end");
//			this.out.println("_" + ((Function) args).getId().spelling + "_if_"
//					+ this.contadorIf + "_end:");
//		} else if (args instanceof WhileCommand) {
//			this.out.println("jmp _while_"
//					+ ((WhileCommand) args).contadorWhile
//					+ "_desvioCondicional_" + contadorDesvioCondicional
//					+ "_end");
//			this.out.println("_while_" + ((WhileCommand) args).contadorWhile
//					+ "_if_" + this.contadorIf + "_end:");
//		} else {
//			this.out.println("jmp _WinMain@16_" + "_desvioCondicional_"
//					+ contadorDesvioCondicional + "_end");
//			this.out.println("_WinMain@16_" + "_if_" + this.contadorTest
//					+ "_end:");
//		}
		
		
		// define label do final do IF
		if (args instanceof Procedure) 
			this.out.println("_" + ((Procedure) args).getId().spelling + "_if_"
					+ this.contadorIf + "_end:");
		else if (args instanceof Function) 
			this.out.println("_" + ((Function) args).getId().spelling + "_if_"
					+ this.contadorIf + "_end:");
		else 
			this.out.println("_WinMain@16_" + "_if_" + this.contadorIf + "_end:");
				
		this.contadorIf += 1;

		return null;
	}

	public Object visitIdentifier(Identifier id, Object args) // ok
			throws SemanticException {
		return null;
	}

	public Object visitWritef(Writef writef, Object args) // ok
			throws SemanticException {
		// TODO Auto-generated method stub

		writef.getExp().visit(this, writef);
		this.out.println("push dword intFormat");
		this.out.println("call _printf");
		this.out.println("add esp, 8");

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

	public Object visitParametro(Parametro par, Object args) // ok
			throws SemanticException {

		return null;
	}

	public Object visitProcedureCall(ProcedureCall proc, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		for (Expressao argumentos : proc.getParams()) {
			argumentos.visit(this, args);
		}

		this.out.println("call _" + proc.getNome().spelling);
		this.out.println("add esp, " + (proc.getParams().size() * 4));
		this.out.println("push eax");

		return null;
	}

	public Object visitFunctionCall(FunctionCall func, Object args)
			throws SemanticException {
		for (Expressao arguments : func.getParams()) {
			arguments.visit(this, args);
		}

		this.out.println("call _" + func.getNome().spelling);
		this.out.println("add esp, " + (func.getParams().size() * 4));
		if ((func.getId().tipo != null)) {
			if (!(func.getId().tipo.equals("VOID")))
				this.out.println("push eax");
		} else if (func.getId().declaration != null) {
			if (!(((Function) (func.getId().declaration)).getTipoRetorno()
					.equals("VOID")))
				this.out.println("push eax");
		}

		return null;
	}

	public Object visitAssignCommand(AssignCommand cmd, Object args) // ok
			throws SemanticException {

		cmd.getExpressao().visit(this, args);

		if (args instanceof Procedure) {
			if (cmd.getId().local) {
				int y;
				for (y = 0; y < ((Procedure) args).getParametros().size(); y++) {
					if (cmd.getId().spelling.equals(((Procedure) args)
							.getParametros().get(y).getId().spelling)) {
						break;
					}
				}
				this.out.println("pop dword [ ebp + "
						+ (4 * (((Procedure) args).getParametros().size() - y + 1))
						+ " ]");
			} else
				this.out.println("pop dword [" + cmd.getId().spelling + "]");
		} else if (args instanceof Function) {
			if (cmd.getId().local) {
				int y;
				for (y = 0; y < ((Function) args).getParametros().size(); y++) {
					if (cmd.getId().spelling.equals(((Function) args)
							.getParametros().get(y).getId().spelling)) {
						break;
					}
				}
				this.out.println("pop dword [ ebp + "
						+ (4 * (((Function) args).getParametros().size() - y + 1))
						+ " ]");
			} else
				this.out.println("pop dword [" + cmd.getId().spelling + "]");
		} else {
			this.out.println("pop dword [" + cmd.getId().spelling + "]");
		}

		return null;
	}

	public Object visitReturnCommand(ReturnCommand cmd, Object args)
			throws SemanticException {

		cmd.getExp().visit(this, args);

		return null;
	}

	public Object visitProcedure(Procedure proc, Object args)
			throws SemanticException {
		this.out.println("_" + proc.getId().spelling + ":");
		this.out.println("push ebp");
		this.out.println("mov ebp, esp");

		for (Command cmd : proc.getCmd()) {
			cmd.visit(this, proc);
		}

		this.out.println("mov esp, ebp");
		this.out.println("pop ebp");
		this.out.println("ret");

		return null;
	}

	public Object visitFunction(Function func, Object args)
			throws SemanticException {
		this.out.println("_" + func.getId().spelling + ":");
		this.out.println("push ebp");
		this.out.println("mov ebp, esp");

		for (Command cmd : func.getCmd()) {
			cmd.visit(this, func);
		}

		this.out.println("mov esp, ebp");
		this.out.println("pop ebp");
		this.out.println("ret");

		return null;
	}

	public Object visitExpressaoBooleana(ExpressaoBooleana boolExp, Object args)
			throws SemanticException {
		if (boolExp.getCompBool() != null) {
			boolExp.getCompBool().visit(this, args);
		} else
			boolExp.getCompNum().visit(this, args);

		return null;

	}

	public Object visitComparacaoBooleana(ComparacaoBooleana compBool,
			Object args) throws SemanticException {

		if (compBool.getBoolValEsq() != null) {
			compBool.getBoolValEsq().visit(this, args);
			if (compBool.getBoolValDir() != null)
				compBool.getBoolValDir().visit(this, args);
			else if (compBool.getExpDir() != null)
				compBool.getExpDir().visit(this, args);
		} else if (compBool.getExpEsq() != null) {
			compBool.getExpEsq().visit(this, args);
			if (compBool.getBoolValDir() != null)
				compBool.getBoolValDir().visit(this, args);
			else if (compBool.getExpDir() != null)
				compBool.getExpDir().visit(this, args);
		}

		if (compBool.op.spelling.equals("==")) {
			this.out.print("jne _");
		} else if (compBool.op.spelling.equals("~=")) {
			this.out.print("jeq _");
		}

		return null;
	}

	public Object visitComparacaoNumero(ComparacaoNumero compNum, Object args)
			throws SemanticException {

		compNum.getExpEsq().visit(this, args);
		compNum.getExpDir().visit(this, args);

		if (compNum.relacionalOp.spelling.equals("<="))
			this.out.print("jg _");
		else if (compNum.relacionalOp.spelling.equals(">="))
			this.out.print("jl _");
		else if (compNum.relacionalOp.spelling.equals(">"))
			this.out.print("jle _");
		else if (compNum.relacionalOp.spelling.equals("<"))
			this.out.print("jge _");

		return null;
	}

	public Object visitArithmeticExpression(ArithmeticExpression exp,
			Object args) // ok
			throws SemanticException {
		exp.getTerm().visit(this, args);
		if (exp.getExp() != null) {
			exp.getExp().visit(this, args);
			this.out.println("pop ebx");
			this.out.println("pop eax");

			if (exp.getOpAritmetico().spelling.equals("+")) {
				this.out.println("add eax, ebx");
			} else if (exp.getOpAritmetico().equals("-")) {
				this.out.println("sub eax, ebx");
			}

			this.out.println("push eax");
		}

		return null;
	}

	public Object visitTerm(Term termo, Object args)
			throws SemanticException {
		termo.getFator().visit(this, args);
		if (termo.getTermo() != null) {
			termo.getTermo().visit(this, args);
			this.out.println("pop ebx");
			this.out.println("pop eax");

			if (termo.op.spelling.equals("*")) {
				this.out.println("imul eax, ebx");
			} else if (termo.op.spelling.equals("/")) {
				this.out.println("div eax, ebx");
			}

			this.out.println("push eax");
		}

		return null;
	}

	public Object visitFactor(Factor fator, Object args)
			throws SemanticException {
		if (fator.getNumero() != null)
			this.out.println("push dword [" + fator.getNumero().spelling + "]");
		else if (fator.getId() != null)
			this.out.println("push dword [" + fator.getId().spelling + "]");
		else
			fator.getAritExp().visit(this, args);

		return null;
	}
	
	public Object visitContinueCommand(ContinueCommand cmd, Object args)
			throws SemanticException {
			this.out.println("jmp _WinMain@16_while_" + this.contadorWhile + "_end");
			return null;
	}

	public Object visitBreakCommand(BreakCommand cmd, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
			this.out.println("jmp _WinMain@16_while_" + this.contadorWhile	+ "_begin");
			return null;
	}
}
