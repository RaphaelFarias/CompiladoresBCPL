package util.AST;
import java.util.ArrayList;

import checker.SemanticException;

public class VarDeclaration extends Command {
	
	boolean isGlobal = true; // true se for global e false se for local
 	String type;
	ArrayList<Identifier> ids = null;
	ArrayList<AssignCommand> asgs = null;
	
	public String toString(int level){
		String varDec = type;
		for (Identifier id : ids){
			varDec += " "+id.spelling;
		}
		return (toString(level+1) + varDec);
	}
	
	public VarDeclaration(boolean global, String type, ArrayList<Identifier> ids, ArrayList<AssignCommand> asgs) {
		this.isGlobal = global;
		this.type = type;
		this.ids = ids;
		this.asgs = asgs;
	}
	
	public String getType(){
		return this.type;
	}
	
	public ArrayList<Identifier> getIds(){
		return this.ids;
	}
	public ArrayList<AssignCommand> getAsgs() {
		return asgs;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitVarDeclaration(this, args);
	}
	
	public boolean getGlobal() {
		return this.isGlobal;
	}
}
