package util.AST;

import checker.SemanticException;

public class ContinueCommand extends Command{
	String id ;
	public ContinueCommand(){
		String id = "CONTINUE";		
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
		return v.visitContinueCommand(this, args);
	}
}
