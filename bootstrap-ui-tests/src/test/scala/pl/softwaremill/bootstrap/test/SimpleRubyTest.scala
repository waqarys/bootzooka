package pl.softwaremill.bootstrap.test

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfter, FlatSpec}
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.jruby.RubyFixnum
import org.jruby.embed.{ScriptingContainer, PathType}

class SimpleRubyTest extends FlatSpec with ShouldMatchers with BeforeAndAfter with EmbeddedJetty {

  before {
    startJetty()
  }

  after {
    stopJetty()
  }

  "This test" should "start jruby" in {
    val container = new ScriptingContainer()
    container.put("@filepath", "bootstrap-ui-tests/src/test/ruby/register_spec.rb")
    val script = container.parse(PathType.RELATIVE, "bootstrap-ui-tests/src/test/ruby/simplescript.rb")
    val executionResult: RubyFixnum = script.run().asInstanceOf[RubyFixnum]
    executionResult.to_s.asJavaString() should be("0")
  }
}

trait EmbeddedJetty {
  private var jetty: Server = _

  def startJetty() {
    jetty = new Server(8080)
    jetty setHandler prepareContext
    jetty.start()
  }

  private def prepareContext() = {
    val context = new WebAppContext()
    context setContextPath "/"
    context setResourceBase "bootstrap-ui/src/main/webapp"
    context
  }


  def stopJetty() {
    jetty.stop()
  }
}