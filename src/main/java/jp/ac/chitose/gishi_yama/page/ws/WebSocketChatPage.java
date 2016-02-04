package jp.ac.chitose.gishi_yama.page.ws;

import jp.ac.chitose.gishi_yama.page.BasePage;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.WebSocketRequestHandler;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;
import org.apache.wicket.protocol.ws.api.message.TextMessage;
import org.apache.wicket.protocol.ws.api.registry.IKey;
import org.apache.wicket.protocol.ws.api.registry.SimpleWebSocketConnectionRegistry;
import org.wicketstuff.annotation.mount.MountPath;

@Slf4j
@MountPath("wschat")
public class WebSocketChatPage extends BasePage {
  private static final long serialVersionUID = -7939091964203183432L;

  private String applicationName;
  private String sessionId;
  private IKey key;

  public WebSocketChatPage() {
    add(new WebSocketBehavior() {
      private static final long serialVersionUID = -6393777237087512361L;

      @Override
      protected void onConnect(ConnectedMessage message) {
        applicationName = message.getApplication().getName();
        sessionId = message.getSessionId();
        key = message.getKey();
        log.info("onOpen: " + sessionId);
      }

      @Override
      protected void onMessage(WebSocketRequestHandler handler, TextMessage message) {
        log.info("onOpen: " + message.getText());

        val registry = new SimpleWebSocketConnectionRegistry();
        val connections = registry.getConnections(Application.get(applicationName));

        connections.stream()
            .filter(con -> con != null)
            .filter(con -> con.isOpen())
            .forEach(con -> {
              try {
                con.sendMessage(message.getText());
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
      }
    });
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    response.render(JavaScriptHeaderItem.forUrl("js/websocket-chat.js"));
  }
}
