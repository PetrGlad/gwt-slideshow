package greenlight.server
import greenlight.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

class GreetingServiceImpl extends RemoteServiceServlet with GreetingService {
	
	/**
	 * The server side implementation of the RPC service.
	 */
	def greetServer(input : String) : String = {
			new greenlight.server.ScalaData().greeting + ", " + input + "!"
	}
}
