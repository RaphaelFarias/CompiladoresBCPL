package util.AST;

import checker.SemanticException;

public class AssignCommand extends Command {
	Identifier id = null;
	Expressao exp = null;
	FunctionCall func = null;
			
	public AssignCommand(Identifier id, FunctionCall func) {
		super();
		this.id = id;
		this.func = func;
	}

	public AssignCommand(Identifier id, Expressao exp) {
		this.id = id;
		this.exp = exp;
	}
	
	public Identifier getId(){
		return this.id;
	}
	
	public Expressao getExpressao(){
		return this.exp;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitAssignCommand(this,args);
	}
}
