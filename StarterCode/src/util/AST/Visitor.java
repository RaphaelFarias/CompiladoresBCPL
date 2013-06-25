package util.AST;

import checker.SemanticException;

public interface Visitor {
	public Object visitArithmeticExpression  (ArithmeticExpression exp, Object args) throws SemanticException; //ok
	public Object visitArithmeticOperator(ArithmeticOperator op, Object args) throws SemanticException; //ok
	public Object visitAssignCommand(AssignCommand cmd, Object args) throws SemanticException; //ok
	public Object visitBoolValue(BoolValue bool, Object args) throws SemanticException; //ok
	public Object visitBreakCommand(BreakCommand cmd, Object args) throws SemanticException; //ok
	public Object visitCommand(Command cmd, Object args) throws SemanticException; //ok
	public Object visitComparacaoBooleana(ComparacaoBooleana compBool, Object args) throws SemanticException; //ok
	public Object visitComparacaoNumero(ComparacaoNumero compNum, Object args) throws SemanticException;  //ok
	public Object visitComparativeOperator(ComparativeOperator op, Object args) throws SemanticException; //ok
	public Object visitContinueCommand(ContinueCommand cmd, Object args) throws SemanticException;  //ok
	public Object visitExpressaoBooleana(ExpressaoBooleana boolexp, Object args) throws SemanticException; //ok
	public Object visitFactor(Factor fator, Object args) throws SemanticException; //ok
	public Object visitFunction(Function func, Object args) throws SemanticException; //ok
	public Object visitFunctionCall(FunctionCall func, Object args) throws SemanticException; //ok
	public Object visitIdentifier(Identifier id, Object args) throws SemanticException; //ok
	public Object visitIfStatement(IfStatement ifCmd, Object args) throws SemanticException; //ok 
	public Object visitNumero(Numero num, Object args) throws SemanticException; //ok
	public Object visitParametro(Parametro par, Object args) throws SemanticException; //ok
	public Object visitProcedure(Procedure proc, Object args) throws SemanticException; //ok
	public Object visitProcedureCall(ProcedureCall proc, Object args) throws SemanticException; //ok
	public Object visitProgram(Program prog, Object args) throws SemanticException; //ok
	public Object visitRelationalOperator(RelationalOperator op, Object args) throws SemanticException; //ok
	public Object visitReturnCommand(ReturnCommand cmd, Object args) throws SemanticException; //ok
	public Object visitTerm(Term termo, Object args) throws SemanticException; //ok
	public Object visitTestStatement(TestStatement testCmd, Object args) throws SemanticException; //ok
	public Object visitVarDeclaration(VarDeclaration var, Object args) throws SemanticException; //ok
	public Object visitWhileCommand(WhileCommand whileCmd, Object args) throws SemanticException; //ok 
	public Object visitWritef(Writef write, Object args) throws SemanticException;	//ok
}
