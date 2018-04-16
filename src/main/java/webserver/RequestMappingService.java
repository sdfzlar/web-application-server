package webserver;

import controller.Controller;
import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;

import java.util.HashMap;
import java.util.Map;

public class RequestMappingService {
	private static Map<String, Controller> controllerMap = new HashMap<String, Controller>();

	static {
		controllerMap.put("/user/create", new UserCreateController());
		controllerMap.put("/user/login", new UserLoginController());
		controllerMap.put("/user/list", new UserListController());
	}

	public static Controller getController(String url) {
		return controllerMap.get(url);
	}

}
