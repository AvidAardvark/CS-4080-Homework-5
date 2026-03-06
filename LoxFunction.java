class LoxFunction implements Callable {
  private final String name;
  private final Expr.Function declaration;
  private final Environment closure;

  LoxFunction(String name, Expr.Function declaration, Environment closure) {
    this.name = name;
    this.closure = closure;
    this.declaration = declaration;
  }
  @Override
  public String toString() {
    if (name == null) return "<fn>";
    return "<fn " + name + ">";
  }
}
