package codegen.bytecode.stm;

import codegen.bytecode.Visitor;

public class PutField extends T{

	public String classid;
	public String id;
	public String type;
	public PutField(String classid, String id , String type) {
		this.classid = classid;
		this.id = id;
		this.type = type;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
