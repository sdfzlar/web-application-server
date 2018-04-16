package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.HttpRequest;
import http.HttpResponse;


/**
 * Created by coupang on 2018. 4. 15..
 */
public class UserCreateController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(UserCreateController.class);

	@Override
	protected void doPost(HttpRequest request, HttpResponse response) {
		User user = new User(
			request.getParameter("userId"),
			request.getParameter("password"),
			request.getParameter("name"),
			request.getParameter("email"));

		log.debug("User : {}", user);

		DataBase.addUser(user);
		response.response302Header("/index.html");
	}
}
