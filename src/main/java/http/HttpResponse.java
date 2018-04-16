package http;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;

public class HttpResponse {
	private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
	private DataOutputStream dos = null;
	private Map<String, String> headers = Maps.newHashMap();

	public HttpResponse(OutputStream out) {
		dos = new DataOutputStream(out);
	}

	public void forward(String url) {
		try {
			byte[] body = Files.readAllBytes(
				new File("./webapp" + url).toPath());

			if (url.endsWith(".css")) {
				headers.put("Content-Type", "text/css");
			} else if (url.endsWith(".js")) {
				headers.put("Content-Type", "application/javascript");
			} else {
				headers.put("Content-Type", "text/html;charset=utf-8");
			}

			headers.put("Content-Length", String.valueOf(body.length));

			response200Header(body.length);
			responseBody(body);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void forwardBody(byte[] contents) {
		headers.put("Content-Type", "text/html;charset=utf-8");
		headers.put("Content-Length", String.valueOf(contents.length));
		response200Header(contents.length);
		responseBody(contents);
	}

	public void addHeaderKeyValue(String key, String value) {
		headers.put(key, value);
	}

	private void addHeaderContents() {
		headers.keySet().stream()
			.distinct()
			.forEach(p -> {
				try {
					dos.writeBytes(p + ": " + headers.get(p) + " \r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
	}

	private void response200Header(int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			addHeaderContents();
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void response302Header(String url) {
		try {
			dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
			addHeaderContents();
			dos.writeBytes("Location: " + url + " \r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void responseBody(byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.writeBytes("\r\n");
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
