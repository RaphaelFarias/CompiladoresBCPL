package util.AST;

import checker.SemanticException;

public class ReturnCommand extends Command {
	Expressao exp = null;
		
	public ReturnCommand(Expressao id){
		this.exp = id;
	}
	
	public Expressao getExp(){
		return this.exp;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitReturnCommand(this, args);
	}
}
