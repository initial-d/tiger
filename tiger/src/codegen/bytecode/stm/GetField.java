package codegen.bytecode.stm;

import codegen.bytecode.Visitor;

public class GetField extends T {

	public String classid;
	public String id;
	public String type;
	public GetField(String classid,String id , String type) {
		this.classid = classid;
		this.id = id;
		this.type = type;
	}
	
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
