package utils;

import java.net.URI;

import org.springframework.web.util.UriComponentsBuilder;

public class RequestsUtils {

	private RequestsUtils() {
		throw new IllegalStateException("Utility class");
	}

	private static final String TEMPLATE_URL = "http://localhost:%s/v1/pedidos";
	
	public static String getURL(int port) {
		return String.format(TEMPLATE_URL, port);
	}

	public static URI getDefaultURI(String url) {
		return UriComponentsBuilder.fromUriString(url).build().toUri();
	}

}
