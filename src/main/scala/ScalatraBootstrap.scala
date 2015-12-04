
import de.alexholly.util.http.HttpSync
import de.alexholly.util.IPManager
import de.alexholly.util.app.AsyncTestServlet

import org.json4s._
import org.json4s.native.Serialization.write

import org.scalatra._
import javax.servlet.ServletContext
import scala.concurrent.duration._

class ScalatraBootstrap extends LifeCycle {

  protected implicit val jsonFormats: Formats = DefaultFormats

  override def init(context: ServletContext) {
    context.mount(new AsyncTestServlet(), "/test/*")
  }
}