package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class Procedure extends AST {
	
	public String getTipo() {
		return tipo;
	}
	String tipo;
	Identifier id;
	ArrayList<Parametro> parametros;
	ArrayList<Command> cmd;
		
	public Procedure(String tipo, Identifier id,
			ArrayList<Parametro> parametros, ArrayList<Command> cmd) {
		this.tipo = tipo;
		this.id = id;
		this.parametros = parametros;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Identifier getId() {
		return id;
	}

	public ArrayList<Parametro> getParametros() {
		return parametros;
	}

	public ArrayList<Command> getCmd() {
		return cmd;
	}

	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitProcedure(this, args);
	}
}
