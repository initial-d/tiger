package codegen.C;

import java.util.HashMap;
import java.util.Hashtable;

import ast.exp.Paren;
import control.Control;

public class PrettyPrintVisitor implements Visitor {
	private int indentLevel;
	private java.io.BufferedWriter writer;
	public Hashtable<String, String> htable;
	public Hashtable<String, String> htablev;
	public String cmap;

	public PrettyPrintVisitor() {
		this.indentLevel = 2;
	}

	private void indent() {
		this.indentLevel += 2;
	}

	private void unIndent() {
		this.indentLevel -= 2;
	}

	private void printSpaces() {
		int i = this.indentLevel;
		while (i-- != 0)
			this.say(" ");
	}

	private void sayln(String s) {
		say(s);
		try {
			this.writer.write("\n");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void say(String s) {
		try {
			this.writer.write(s);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// /////////////////////////////////////////////////////
	// expressions
	@Override
	public void visit(codegen.C.exp.Add e) {
		e.left.accept(this);
		this.say(" + ");
		e.right.accept(this);
		return;
	}

	@Override
	public void visit(codegen.C.exp.And e) {
		e.left.accept(this);
		this.say(" & ");
		e.right.accept(this);
		return;
	}

	@Override
	public void visit(codegen.C.exp.ArraySelect e) {
		e.array.accept(this);
		this.say("[ ");
		e.index.accept(this);
		this.say("+4");
		this.say(" ]");
		return;
	}

	@Override
	public void visit(codegen.C.exp.Call e) {
		String tmp = "";
		if (htable != null && htable.containsKey(e.assign))
		{
			tmp = htable.get(e.assign) + ".";
			tmp += e.assign;
			this.say("(" + tmp + "=");
			e.exp.accept(this);
			this.say(", ");
			this.say("((struct "+htablev.get(e.assign)+"_vtable*)("+tmp + "->vptr))->" + e.id + "(" + tmp);
			int size = e.args.size();
			if (size == 0) {
				this.say("))");
				return;
			}
			for (codegen.C.exp.T x : e.args) {
				this.say(", ");
				x.accept(this);
			}
			this.say("))");
		}
		else
		{
			tmp += e.assign;
			this.say("(" + tmp + "=");
			e.exp.accept(this);
			this.say(", ");
			if(htablev != null && htablev.containsKey(e.assign))
				this.say("((struct "+htablev.get(e.assign)+"_vtable*)("+tmp + "->vptr))->" + e.id + "(" + tmp);
			else
				this.say(tmp + "->vptr->" + e.id + "(" + tmp);
			int size = e.args.size();
			if (size == 0) {
				this.say("))");
				return;
			}
			for (codegen.C.exp.T x : e.args) {
				this.say(", ");
				x.accept(this);
			}
			this.say("))");
		}
		return;
	}

	@Override
	public void visit(codegen.C.exp.Id e) {
		if (e.isField)
			this.say("this->");
		else {
			if (htable != null && htable.containsKey(e.id))
				this.say(htable.get(e.id) + ".");
		}
		this.say(e.id);
	}

	@Override
	public void visit(codegen.C.exp.Length e) {
		e.array.accept(this);
		this.say("[2]");
		return;
	}

	@Override
	public void visit(codegen.C.exp.Lt e) {
		e.left.accept(this);
		this.say(" < ");
		e.right.accept(this);
		return;
	}

	@Override
	public void visit(codegen.C.exp.NewIntArray e) { // @_@
		this.say("Tiger_new_array(");
		e.exp.accept(this);
		this.say(")");
		return;
	}

	@Override
	public void visit(codegen.C.exp.NewObject e) {
		this.say("((struct " + e.id + "*)(Tiger_new (&" + e.id
				+ "_vtable_, sizeof(struct " + e.id + "))))");
		return;
	}

	@Override
	public void visit(codegen.C.exp.Not e) {
		this.say("!");
		e.exp.accept(this);
		return;
	}

	@Override
	public void visit(codegen.C.exp.Num e) {
		this.say(Integer.toString(e.num));
		return;
	}

	@Override
	public void visit(codegen.C.exp.Sub e) {
		e.left.accept(this);
		this.say(" - ");
		e.right.accept(this);
		return;
	}

	@Override
	public void visit(codegen.C.exp.This e) {
		this.say("this");
	}

	@Override
	public void visit(codegen.C.exp.Times e) {
		e.left.accept(this);
		this.say(" * ");
		e.right.accept(this);
		return;
	}

	// statements
	@Override
	public void visit(codegen.C.stm.Assign s) {
		this.printSpaces();
		if (s.isField)
			this.say("this->");
		else {
			if (htable != null && htable.containsKey(s.id))
				this.say(htable.get(s.id) + ".");
		}
		this.say(s.id + " = ");
		s.exp.accept(this);
		this.sayln(";");
		return;
	}

	@Override
	public void visit(codegen.C.stm.AssignArray s) {
		this.printSpaces();
		if (s.isField)
			this.say("this->");
		else {
			if (htable != null && htable.containsKey(s.id))
				this.say(htable.get(s.id) + ".");
		}
		this.say(s.id);
		this.say(" [ ");
		s.index.accept(this);
		this.say("+4");
		this.say(" ] ");
		this.say(" = ");
		s.exp.accept(this);
		this.sayln(";");
		return;
	}

	@Override
	public void visit(codegen.C.stm.Block s) {
		this.unIndent();
		this.printSpaces();
		this.sayln("{");
		this.indent();
		for (codegen.C.stm.T s1 : s.stms)
			s1.accept(this);
		this.unIndent();
		this.printSpaces();
		this.sayln("}");
		this.indent();
		return;
	}

	@Override
	public void visit(codegen.C.stm.If s) {
		this.printSpaces();
		this.say("if (");
		s.condition.accept(this);
		this.sayln(")");
		this.indent();
		s.thenn.accept(this);
		this.unIndent();
		this.sayln("");
		this.printSpaces();
		this.sayln("else");
		this.indent();
		s.elsee.accept(this);
		this.sayln("");
		this.unIndent();
		return;
	}

	@Override
	public void visit(codegen.C.stm.Print s) {
		this.printSpaces();
		this.say("System_out_println (");
		s.exp.accept(this);
		this.sayln(");");
		return;
	}

	@Override
	public void visit(codegen.C.stm.While s) {
		this.printSpaces();
		this.say("while (");
		s.condition.accept(this);
		this.sayln(")");
		this.indent();
		s.body.accept(this);
		this.unIndent();
		this.sayln("");
		return;
	}

	// type
	@Override
	public void visit(codegen.C.type.Class t) {
		this.say("struct " + t.id + " *");
	}

	@Override
	public void visit(codegen.C.type.Int t) {
		this.say("int");
	}

	@Override
	public void visit(codegen.C.type.IntArray t) { // @_@
		this.say("int *");
	}

	// dec
	@Override
	public void visit(codegen.C.dec.Dec d) {
		d.type.accept(this);
		this.say(" " + d.id + " ;");
	}

	// method
	@Override
	public void visit(codegen.C.method.Method m) {
		htable = new Hashtable<String, String>();
		htablev = new Hashtable<String, String>();
		String type1 = "";
		String type2 = "";
		for (codegen.C.dec.T d : m.formals) {
			codegen.C.dec.Dec dec = (codegen.C.dec.Dec) d;
			if (dec.type.toString().equals("@int")
					|| dec.type.toString().equals("@boolean"))
				type1 += '0';
			else
				type1 += '1';
		}
		for (codegen.C.dec.T d : m.locals) {
			codegen.C.dec.Dec dec = (codegen.C.dec.Dec) d;
			if (dec.type.toString().equals("@int")
					|| dec.type.toString().equals("@boolean"))
				type2 += '0';
			else {
				htable.put(dec.id, "frame");
				htablev.put(dec.id, dec.type.toString());
				type2 += '1';
			}
		}
		this.sayln("char  " + m.classId+ "_"+m.id + "_arguments_gc_map[] = \"" + type1 + "\";");
		this.sayln("char  " + m.classId+ "_"+m.id + "_locals_gc_map[] = \"" + type2 + "\";");

		this.sayln("struct " + m.classId+"_"+m.id + "_gc_frame{");
		this.sayln("  void *prev;");
		this.sayln("  char *arguments_gc_map;");
		this.sayln("  int *arguments_base_address;");
		this.sayln("  char *locals_gc_map;");
		for (codegen.C.dec.T d : m.locals) {
			codegen.C.dec.Dec dec = (codegen.C.dec.Dec) d;
			if (dec.type.toString().equals("@int")
					|| dec.type.toString().equals("@boolean"))
				;
			else {
				this.say("  ");
				dec.type.accept(this);
				this.say(" " + dec.id + ";\n");
			}
		}
		this.sayln("};");
		m.retType.accept(this);
		this.say(" " + m.classId + "_" + m.id + "(");
		int size = m.formals.size();
		for (codegen.C.dec.T d : m.formals) {
			codegen.C.dec.Dec dec = (codegen.C.dec.Dec) d;
			size--;
			dec.type.accept(this);
			this.say(" " + dec.id);
			if (size > 0)
				this.say(", ");
		}
		this.sayln(")");
		this.sayln("{");
		this.sayln("  struct " + m.classId+"_"+m.id + "_gc_frame frame;");
		this.sayln("  memset(&frame,0,sizeof(frame));");
		this.sayln("  frame.prev = prev;");
		this.sayln("  prev = &frame;");
		this.sayln("  frame.arguments_gc_map = " + m.classId+"_"+m.id + "_arguments_gc_map;");
		this.sayln("  frame.arguments_base_address = (int*)&this;");
		this.sayln("  frame.locals_gc_map = " +m.classId+"_"+ m.id + "_locals_gc_map;");

		for (codegen.C.dec.T d : m.locals) {
			codegen.C.dec.Dec dec = (codegen.C.dec.Dec) d;
			if (dec.type.toString().equals("@int")
					|| dec.type.toString().equals("@boolean")) {
				this.say("  ");
				dec.type.accept(this);
				this.say(" " + dec.id + ";\n");
			}
		}
		this.sayln("");
		for (codegen.C.stm.T s : m.stms)
			s.accept(this);

		this.sayln("  prev = frame.prev;");
		this.say("  return ");
		m.retExp.accept(this);
		this.sayln(";");
		this.sayln("}");
		return;
	}

	@Override
	public void visit(codegen.C.mainMethod.MainMethod m) {
		htable = new Hashtable<String, String>();
		htablev = new Hashtable<String, String>();
		String type1 = "";
		for (codegen.C.dec.T d : m.locals) {
			codegen.C.dec.Dec dec = (codegen.C.dec.Dec) d;
			if (dec.type.toString().equals("@int")
					|| dec.type.toString().equals("@boolean"))
				type1 += '0';
			else {
				type1 += '1';
				htable.put(dec.id, "frame");
				htablev.put(dec.id, dec.type.toString());
			}
		}
		this.sayln("char  " + "Main_arguments_gc_map[] = \"\";");
		this.sayln("char  " + "Main_locals_gc_map[] = " +"\"" +type1 + "\";");
		this.sayln("struct " +  "Main_gc_frame{");
		this.sayln("  void *prev;");
		this.sayln("  char *arguments_gc_map;");
		this.sayln("  int *arguments_base_address;");
		this.sayln("  char *locals_gc_map;");
		for (codegen.C.dec.T d : m.locals) {
			codegen.C.dec.Dec dec = (codegen.C.dec.Dec) d;
			if (dec.type.toString().equals("@int")
					|| dec.type.toString().equals("@boolean"))
				;
			else {
				this.say("  ");
				//System.out.println(dec.type.getClass());
				dec.type.accept(this);
				this.say(" " + dec.id + ";\n");
			}
		}
		this.sayln("};");
		this.sayln("int Tiger_main ()");
		this.sayln("{");
		this.sayln("  struct " + "Main_gc_frame frame;");
		this.sayln("  memset(&frame,0,sizeof(frame));");
		this.sayln("  frame.prev = prev;");
		this.sayln("  prev = &frame;");
		this.sayln("  frame.arguments_gc_map = " + "Main_arguments_gc_map;");
		this.sayln("  frame.locals_gc_map = " +"Main_locals_gc_map;");		
		
		for (codegen.C.dec.T d : m.locals) {
			codegen.C.dec.Dec dec = (codegen.C.dec.Dec) d;
			if (dec.type.toString().equals("@int")
					|| dec.type.toString().equals("@boolean")){
				this.say("  ");
				//System.out.println(dec.type.getClass());
				dec.type.accept(this);
				this.say(" " + dec.id + ";\n");
			}
		}
		
		
		m.stm.accept(this);
		this.sayln("  prev = frame.prev;");
		this.sayln("}\n");
		return;
	}

	// vtables
	@Override
	public void visit(codegen.C.vtable.Vtable v) {
		this.sayln("struct " + v.id + "_vtable");
		this.sayln("{");
		this.sayln("  char *" + v.id + "_gc_map;");
		for (codegen.C.Ftuple t : v.ms) {
			this.say("  ");
			t.ret.accept(this);
			this.sayln(" (*" + t.id + ")();");
		}
		this.sayln("};\n");
		this.sayln("extern struct " + v.id + "_vtable " + v.id + "_vtable_ ; ");
		return;
	}

	private void outputVtable(codegen.C.vtable.Vtable v) {
		this.sayln("struct " + v.id + "_vtable " + v.id + "_vtable_ = ");
		this.sayln("{");
		this.sayln("  "+v.id+"_gc_map,");
		for (codegen.C.Ftuple t : v.ms) {
			this.say("  ");
			this.sayln(t.classs + "_" + t.id + ",");
		}
		this.sayln("};\n");
		return;
	}

	// class
	@Override
	public void visit(codegen.C.classs.Class c) {
		this.sayln("struct " + c.id);
		this.sayln("{");
		this.sayln("  struct "+c.id+"_vtable  *vptr;");
		this.sayln("  int isObjOrArray;");
		this.sayln("  unsigned int length;");
		this.sayln("  void *forwarding;");
		cmap = "";
		for (codegen.C.Tuple t : c.decs) {
			this.say("  ");
			if (t.type.toString().equals("@int")
					|| t.type.toString().equals("@boolean"))
				cmap += '0';
			else
				cmap += '1';
			t.type.accept(this);
			this.say(" ");
			this.sayln(t.id + ";");
		}
		this.sayln("};");
		this.sayln("char " + c.id + "_gc_map[] = \"" +cmap +"\";");
		return;
	}

	

	// program
	@Override
	public void visit(codegen.C.program.Program p) {
		// we'd like to output to a file, rather than the "stdout".
		try {
			String outputName = null;
			if (Control.outputName != null)
				outputName = Control.outputName;
			else if (Control.fileName != null)
				outputName = Control.fileName + ".c";
			else
				outputName = "a.c";

			this.writer = new java.io.BufferedWriter(
					new java.io.OutputStreamWriter(
							new java.io.FileOutputStream(outputName)));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		this.sayln("// This is automatically generated by the Tiger compiler.");
		this.sayln("// Do NOT modify!\n");
		this.sayln("// a global pointer");
		this.sayln("#include <memory.h>");
		this.sayln("extern void *prev;");
		this.sayln("// structures");
		for (codegen.C.classs.T c : p.classes) {
			c.accept(this);
		}

		this.sayln("// vtables structures");
		for (codegen.C.vtable.T v : p.vtables) {
			v.accept(this);
		}
		this.sayln("");

		this.sayln("// methods");
		for (codegen.C.method.T m : p.methods) {
			m.accept(this);
		}
		this.sayln("");

		this.sayln("// vtables");
		for (codegen.C.vtable.T v : p.vtables) {
			outputVtable((codegen.C.vtable.Vtable) v);
		}
		this.sayln("");

		this.sayln("// main method");
		p.mainMethod.accept(this);
		this.sayln("");

		this.say("\n\n");

		try {
			this.writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	@Override
	public void visit(codegen.C.exp.Paren paren) {
		this.say("(");
		paren.exp.accept(this);
		this.say(")");
		return;
		// TODO Auto-generated method stub

	}

}
