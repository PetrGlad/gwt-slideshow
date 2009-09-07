package greenlight.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GreenLight implements EntryPoint {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		Window.enableScrolling(false);

		final Button goButton = new Button("Go");
		goButton.addStyleName("goButton");

		final TextBox urlField = new TextBox();
		urlField
				.setText("http://farm3.static.flickr.com/2672/3858770113_831a33c36c_o.jpg");
		urlField.setStyleName("urlField");

		RootPanel.get("urlBar").add(urlField);
		RootPanel.get("urlBar").add(goButton);

		final Image image = new Image();
		image.setStyleName("slideImage");
		image.setVisible(false);
		RootPanel.get("content").add(image);

		final Label dimsLabel = new Label();

		RootPanel.get("urlBar").add(dimsLabel);

		// Focus the cursor on the name field when the app loads
		urlField.setFocus(true);
		urlField.selectAll();

		// Create a handler for the sendButton and nameField
		class GoHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				showShow();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					showShow();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void showShow() {
				image.setUrl(urlField.getText());
				image.setVisible(true);
				image.addLoadHandler(new LoadHandler() {
					@Override
					public void onLoad(LoadEvent event) {
						fixupImageSizes(image, dimsLabel);						
					}
				});
				dimsLabel.setText("Loading...");
			}
		}

		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				fixupImageSizes(image, dimsLabel);
			}
		});

		// Add a handler to send the name to the server
		GoHandler handler = new GoHandler();
		goButton.addClickHandler(handler);
		urlField.addKeyUpHandler(handler);
	}

	private void fixupImageSizes(Image image, Label dimsLabel) {
		dimsLabel.setText("al=" + image.getAbsoluteLeft() + ",at="
				+ image.getAbsoluteTop() + ",w=" + image.getWidth() + ",h="
				+ image.getHeight() + ",oh=" + image.getOffsetHeight() + ",ow="
				+ image.getOffsetWidth() + ",orl=" + image.getOriginLeft()
				+ ",ort=" + image.getOriginTop() + ",ww="
				+ Window.getClientWidth() + ",wh=" + Window.getClientHeight());
		
		// TODO: retain original aspect ratio
		// XXX hardcode: need to get image padding somehow
		final int padding = 3;
		image
				.setHeight(""
						+ (Window.getClientHeight() - image.getAbsoluteTop() - padding * 2)
						+ "px");
		// XXX formatting: perhaps style's max-width should be sufficient
		image
				.setWidth(""
						+ (Window.getClientWidth() - image.getAbsoluteLeft() - padding * 2)
						+ "px");
	}
}
