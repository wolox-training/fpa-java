package wolox.training.config;

import java.net.ServerSocket;
import java.util.List;
import javax.net.ServerSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebServerFactoryCustomizerConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

	@Value("#{'${port.list}'.split(',')}")
	private List<Integer> listOfPorts;

	private static int selectedStartPort = 0;

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		Integer port = listOfPorts
				.stream()
					.filter(portSelected -> isPortAvailable(portSelected.intValue()))
							.findFirst()
									.orElseThrow(() -> new RuntimeException("No port available: " + listOfPorts));
		setPortSelected(port, factory);
		selectedStartPort = port;
		log.info("Port selected : " + port);
	}

	private void setPortSelected(Integer port, ConfigurableWebServerFactory factory) {
		factory.setPort(port);
		System.getProperties().put("server.port", String.valueOf(port));
		System.getProperties().put("eureka.instance.secure-port", String.valueOf(port));
	}

	private boolean isPortAvailable(int port) {
		try {
			ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(port, 1);
			serverSocket.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static int getSelectedStartPort() {
		return selectedStartPort;
	}
}
