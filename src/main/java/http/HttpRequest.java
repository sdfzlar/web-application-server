package http;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class HttpRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
	private HttpMethod method;
	private String path;
	private Map<String, String> headerMap = Maps.newHashMap();
	private Map<String, String> parameterMap = Maps.newHashMap();

	public HttpRequest(InputStream in) {
		String line = null;

		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			line = buffer.readLine();
			if (line == null) {
				return;
			}

			String[] tokens = line.split(" ");
			this.method = HttpMethod.valueOf(tokens[0]);

			if (this.method.isGetMethod()) {
				int index = tokens[1].indexOf("?");
				if (index == -1) {
					this.path = tokens[1];
				} else {
					this.path = tokens[1].substring(0, index);
				}
				this.parameterMap = HttpRequestUtils.parseQueryString(tokens[1].substring(index + 1));
			} else {
				this.path = tokens[1];
			}

			int contentLength = 0;

			while (!(line = buffer.readLine()).equals("")) {
				HttpRequestUtils.Pair headerInfo = HttpRequestUtils.parseHeader(line);
				log.info("line : {}", line);
				this.headerMap.put(headerInfo.getKey(), headerInfo.getValue());
			}

			if (this.method.isPostMethod()) {
				String body = IOUtils.readData(buffer, contentLength);
				Map<String, String> params = HttpRequestUtils.parseQueryString(body);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	public HttpMethod getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public String getHeader(String key) {
		return headerMap.get(key);
	}

	public String getParameter(String key) {
		return parameterMap.get(key);
	}
}
