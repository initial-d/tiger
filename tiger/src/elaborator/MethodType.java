package elaborator;

public class MethodType
{
  public ast.type.T retType;
  public java.util.LinkedList<ast.dec.T> argsType;
  public int lineNo;
  
  public MethodType(ast.type.T retType, java.util.LinkedList<ast.dec.T> decs , int line)
  {
    this.retType = retType;
    this.argsType = decs;
    this.lineNo = line;
  }
  public MethodType(ast.type.T retType, java.util.LinkedList<ast.dec.T> decs )
  {
    this.retType = retType;
    this.argsType = decs;
  }
  @Override
  public String toString()
  {
    String s = "";
    for (ast.dec.T dec : this.argsType) {
      ast.dec.Dec decc = (ast.dec.Dec) dec;
      s = decc.type.toString() + "*" + s;
    }
    s = s + " -> " + this.retType.toString();
    return s;
  }
}
