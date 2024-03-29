package ast.type;

import ast.Visitor;

public class Boolean extends T
{
  public Boolean()
  {
  }
  public Boolean(int line)
  {
	  this.lineNo = line;
  }

  @Override
  public String toString()
  {
    return "@boolean";
  }

  @Override
  public int getNum()
  {
    return -1;
  }

  @Override
  public void accept(Visitor v)
  {
    v.visit(this);
  }
}
