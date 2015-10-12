package ast.stm;

public class AssignArray extends T {
	public String id;
	public ast.exp.T index;
	public ast.exp.T exp;
	public ast.type.T type; // type of the array
	
	public AssignArray(String id, ast.exp.T index, ast.exp.T exp) {
		this.id = id;
		this.index = index;
		this.exp = exp;
		type = null;
	}
	public AssignArray(String id, ast.exp.T index, ast.exp.T exp , int line) {
		this.id = id;
		this.index = index;
		this.exp = exp;
		type = null;
		this.lineNo = line;
	}

	@Override
	public void accept(ast.Visitor v) {
		v.visit(this);
	}
}
