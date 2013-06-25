package util.AST;

import checker.SemanticException;

public class Parametro extends AST {
	Identifier id = null;
	String type = null;
	
	public String getType() {
		return type;
	}

	public Parametro (Identifier id, String type){
		this.type = type;
		this.id = id;
	}
	
	public Identifier getId() {
		return id;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitParametro(this, args);
	}

}
