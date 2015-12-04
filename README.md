## Dependencies ##

https://github.com/AlexHolly/util-scala-ws-http-client
https://github.com/AlexHolly/ip-manager

## Test, Run & Build ##

Test

    sbt test
    
Run

    sbt run
    
Live Debug (set custom port in build.sbt "port in container.Configuration := 4567")

    sbt ~container:start

Build stand alone

    sbt assembly

## Logging settings ##

    /resources/logback.xml -> root level


[http://localhost:4567/](http://localhost:4567/)

# Quellen #

[Responce code objects](https://github.com/scalatra/scalatra/blob/develop/core/src/main/scala/org/scalatra/ActionResult.scala)

[Parameters example](http://www.scalatra.org/2.4/guides/http/actions.html)

[Random Parameters stuff](http://www.scalatra.org/2.4/guides/http/routes.html)



