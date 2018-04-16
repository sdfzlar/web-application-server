package http;

/**
 * Created by coupang on 2018. 4. 11..
 */
public enum HttpMethod {
	GET,
	POST;

	public boolean isGetMethod() {
		return this.equals(GET);
	}

	public boolean isPostMethod() {
		return this.equals(POST);
	}
}
