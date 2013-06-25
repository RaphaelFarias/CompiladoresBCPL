package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class WhileCommand extends Command {
	Expressao exp = null;
	ArrayList<Command> cmds = null;
	public int contadorWhile = 1;
		
	public WhileCommand (Expressao e, ArrayList<Command> cmds){
		this.exp = e;
		this.cmds = cmds;
	}
	
	public Expressao getExpressao(){
		return this.exp;
	}
	
	public ArrayList<Command> getWhlCmd(){
		return this.cmds;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitWhileCommand(this, args);
	}
}

