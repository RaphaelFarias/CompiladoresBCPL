package util.AST;

import checker.SemanticException;

public class BreakCommand extends Command{
	String id ;
	public int whileCont;
	public BreakCommand(){
		String id = "BREAK";		
	}
	public String getId() {
		return id;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitBreakCommand(this, args);
	}
}
