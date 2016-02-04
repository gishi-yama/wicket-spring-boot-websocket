package jp.ac.chitose.gishi_yama;

import jp.ac.chitose.gishi_yama.page.HomePage;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.ws.javax.WicketServerEndpointConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.giffing.wicket.spring.boot.starter.app.WicketBootWebApplication;
import com.giffing.wicket.spring.boot.starter.context.WicketSpringBootApplication;

@EnableWebSocket
@WicketSpringBootApplication
public class WicketApplication extends WicketBootWebApplication implements WebSocketConfigurer {

  public static void main(String[] args) {
    new SpringApplicationBuilder().sources(WicketApplication.class).run(args);
  }

  @Override
  public Class<? extends Page> getHomePage() {
    return HomePage.class;
  }

  @Override
  protected Class<? extends WebPage> getSignInPageClass() {
    return null;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    // TODO Auto-generated method stub
  }
  
  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }

  @Bean
  public WicketServerEndpointConfig wicketServerEndpointConfig() {
    return new WicketServerEndpointConfig();
  }


}
