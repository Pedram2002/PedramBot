package PedramK.PedramBot.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static PedramK.PedramBot.repository.SqlFunctions.*;


@RestController
public class MainRestController {
    public static class RestResponse {
        private String param1;
        private String param2;

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public String getParam2() {
            return param2;
        }

        public void setParam2(String param2) {
            this.param2 = param2;
        }
    }

    public static class Users {
        private String user;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }
    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse restMethodHello(String name) {
        RestResponse result = new RestResponse();
        result.setParam1("Hello");
        result.setParam2(name);
        return result;
    }

    @RequestMapping(value = "/userList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Users>  restMethodAllUsers() {
        List<Users> resultList = new ArrayList<>();

        for (String user : getAllUsers().split("\n") ) {
            Users users = new Users();
            users.setUser(user);
            resultList.add(users);
        }

        return resultList;
    }


    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    private String  restMethodUsersCount() {
        int i =getAllUsers().split("\n").length;

        return "We have " + i + " users. For more detailed information, log in as an admin and enter here <a href=\"userList\">List all users</a>";
    }
}
