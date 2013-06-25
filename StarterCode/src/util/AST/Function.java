package util.AST;

import java.util.ArrayList;

import checker.SemanticException;

public class Function extends AST {

	public String getTipoRetorno() {
		return tipoRetorno;
	}
	String tipoRetorno;
	Identifier id;
	ArrayList<Parametro> parametros;
	ArrayList<Command> cmd;
	
	public Function(String tipoRetorno, Identifier id,
			ArrayList<Parametro> parametros, ArrayList<Command> cmd) {
		super();
		this.tipoRetorno = tipoRetorno;
		this.id = id;
		this.parametros = parametros;
		this.cmd = cmd;
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
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitFunction(this, args);
	}
}
