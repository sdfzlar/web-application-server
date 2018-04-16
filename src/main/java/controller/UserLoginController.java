package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class UserLoginController extends AbstractController {
	@Override
	public void doPost(HttpRequest request, HttpResponse response) {
		User user = DataBase.findUserById(request.getParameter("userId"));
		if (user != null) {
			if (user.login(request.getParameter("password"))) {
				response.addHeaderKeyValue("Set-Cookie", "logined=true");
				response.response302Header("/index.html");
			} else {
				response.response302Header("/user/login_failed.html");
			}
		} else {
			response.response302Header("/user/login_failed.html");
		}
	}
}
