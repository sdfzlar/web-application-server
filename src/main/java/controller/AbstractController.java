package controller;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

/**
 * Created by coupang on 2018. 4. 15..
 */
public abstract class AbstractController implements Controller {
	@Override
	public void service(HttpRequest request, HttpResponse response) {
		HttpMethod method = request.getMethod();

		if (method.isGetMethod()) {
			doGet(request, response);
		} else {
			doPost(request, response);
		}
	}

	protected void doGet(HttpRequest request, HttpResponse response){}
	protected void doPost(HttpRequest request, HttpResponse response){}
}
