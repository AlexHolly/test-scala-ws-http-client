/**
 * Created by alex on 11.11.15.
 */

import de.alexholly.util.JettyServer
import de.alexholly.util.http.HttpAsync
import de.alexholly.util.http.HttpSync
import de.alexholly.util.app.{Numbers, AsyncTestServlet}

import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._
import org.scalatest._
import scala.concurrent.duration._
import play.api.libs.ws.WSResponse
import scala.concurrent.ExecutionContext.Implicits.global

class AsyncAndSpeed extends FunSuite with BeforeAndAfter {

  //JSON stuff
  implicit val jsonFormats = DefaultFormats

  //Debugging stuff
  val BODY_MESSAGE = " BODY EMPTY?"
  val JSON_MESSAGE = " JSON ERROR"
  val EMPTY_MESSAGE = " SHOULD BE EMPTY"
  val TIMEOUT = 10 seconds

  var server = JettyServer().startOnFreePort()
  var default_url = "http://localhost:" + server.port

  after {
    AsyncTestServlet.reset()
  }

  //@TODO clean description
  // server speicher reihenfolge ab und ich frage sie zum schluss ab
  // um zu schauen ob sie asynchon abgerarbeitet werden
  //@TODO More tests

  test("1000 Sync Requests - Responses are ordered") {
    val range = 0 until 1000
    var res = List[Int]()
    var response: WSResponse = null

    for (i <- range) {
      response = HttpSync.put(default_url + "/test/sync?number=" + i, TIMEOUT)
      assert(response.status == 200)
      res :+= i
    }

    response = HttpSync.get(default_url + "/test/numbers", TIMEOUT)
    assert(response.status == 200)
    val numbers = parse(response.body).extract[Numbers].numbers
    assert(numbers == range.toList)
    assert(numbers == res)
  }

  test("1000 Async Requests - Responses have no order") {
    val range = 0 to 1000
    var response: WSResponse = null

    for (i <- range) {
      HttpAsync.put(default_url + "/test/async?number=" + i) map { response =>
        assert(response.status == 200)
      }
    }

    response = HttpSync.get(default_url + "/test/numbers", TIMEOUT)
    assert(response.status == 200)
    val numbers = parse(response.body).extract[Numbers].numbers
    //@INFO Koennte in manchen faellen fehlschlagen ~ Zufall
    assert(numbers != range.toList)
  }
}
