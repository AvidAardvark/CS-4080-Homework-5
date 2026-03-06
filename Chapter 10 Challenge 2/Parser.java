private Stmt.Function function(String kind) {
  Token name = consume(IDENTIFIER, "Expect " + kind + " name.");
  return new Stmt.Function(name, functionBody(kind));
}

private Expr.Function functionBody(String kind) {
  consume(LEFT_PAREN, "Expect '(' after " + kind + " name.");
  List<Token> parameters = new ArrayList<>();
  if (!check(RIGHT_PAREN)) {
    do {
      if (parameters.size() >= 8) {
        error(peek(), "Can't have more than 8 parameters.");
      }

      parameters.add(consume(IDENTIFIER, "Expect parameter name."));
    } while (match(COMMA));
  }
  consume(RIGHT_PAREN, "Expect ')' after parameters.");

  consume(LEFT_BRACE, "Expect '{' before " + kind + " body.");
  List<Stmt> body = block();
  return new Expr.Function(parameters, body);
}
private Expr primary() {
    if (match(FUN)) return functionBody("function");
}

private Stmt declaration() {
  try {
    if (check(FUN) && checkNext(IDENTIFIER)) {
      consume(FUN, null);
      return function("function");
    }
}

private boolean checkNext(TokenType tokenType) {
  if (isAtEnd()) return false;
  if (tokens.get(current + 1).type == EOF) return false;
  return tokens.get(current + 1).type == tokenType;
}
