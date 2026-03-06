@Override
public Void visitFunctionStmt(Stmt.Function stmt) {
    String fnName = stmt.name.lexeme;
    environment.define(fnName, new LoxFunction(fnName, stmt.function, environment));
    return null;
}

@Override
public Object visitFunctionExpr(Expr.Function expr) {
    return new LoxFunction(null, expr, environment);
}
