
private final Stack<Map<String, Variable>> scopes = new Stack<>();

private static class Variable {
    final Token name;
    VariableState state;

    private Variable(Token name, VariableState state) {
      this.name = name;
      this.state = state;
    }
  }

  private enum VariableState {
    DECLARED,
    DEFINED,
    READ
  }

  private void resolveLocal(Expr expr, Token name, boolean isRead) {
    for (int i = scopes.size() - 1; i >= 0; i--) {
      if (scopes.get(i).containsKey(name.lexeme)) {
        interpreter.resolve(expr, scopes.size() - 1 - i);

        if (isRead) {
          scopes.get(i).get(name.lexeme).state = VariableState.READ;
        }
        return;
      }
    }
  }
  private void beginScope() {
    scopes.push(new HashMap<String, Variable>());
  }

  private void declare(Token name) {
    if (scopes.isEmpty()) return;

    Map<String, Variable> scope = scopes.peek();
    if (scope.containsKey(name.lexeme)) {
      Lox.error(name,
          "Already variable with this name in this scope.");
    }

    scope.put(name.lexeme, new Variable(name, VariableState.DECLARED));
  }

  private void define(Token name) {
    if (scopes.isEmpty()) return;
    scopes.peek().get(name.lexeme).state = VariableState.DEFINED;
  }
  private void endScope() {
    Map<String, Variable> scope = scopes.pop();

    for (Map.Entry<String, Variable> entry : scope.entrySet()) {
      if (entry.getValue().state == VariableState.DEFINED) {
        Lox.error(entry.getValue().name, "Local variable is not used.");
      }
    }
  }
