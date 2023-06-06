package com.livongo.webserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.auth.htdigest.HtdigestAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BasicAuthHandler;
import io.vertx.ext.web.handler.DigestAuthHandler;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    HttpServer server = vertx.createHttpServer();
    HtdigestAuth authProvider = HtdigestAuth.create(vertx, ".htdigest");
    DigestAuthHandler authHandler = DigestAuthHandler.create(vertx, authProvider);

    Router router = Router.router(vertx);

    router.route("/private/*").handler(authHandler);

    router.route("/private/incentive").handler(ctx -> {

      // This will require a login

      // This will have the value true
      boolean isAuthenticated = ctx.user() != null;

      ctx.response().end();

    });

    server.requestHandler(router).listen(8080);

  }
}
