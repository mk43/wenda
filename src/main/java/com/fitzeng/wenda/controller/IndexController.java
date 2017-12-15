package com.fitzeng.wenda.controller;

import com.fitzeng.wenda.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class IndexController {

    //    @RequestMapping("/")
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index() {
        return "Hello Fitzeng";
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam("type") int type,
                          @RequestParam("key") String key) {
        return String.format("Profile Page of %s / %d, type : %d key : %s", groupId, userId, type, key);
    }

    @RequestMapping(path = {"/vm"}, method = {RequestMethod.GET})
    public String template(Model model) {
        model.addAttribute("value1", "vvvc 1");
        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
        model.addAttribute("colors", colors);

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 4; ++i) {
            map.put(String.valueOf(i), String.valueOf(i * i));
        }
        model.addAttribute("map", map);
        model.addAttribute("user", new User("Tony"));
        return "home";
    }

    @RequestMapping(path = {"/request"}, method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletResponse response,
                           HttpServletRequest request,
                           HttpSession httpSession) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headeNames = request.getHeaderNames();
        sb.append(request.getMethod() + "<br/>");
        sb.append(request.getQueryString() + "<br/>");
        sb.append(request.getPathInfo() + "<br/>");
        sb.append(request.getRequestURL() + "<br/>");
        sb.append(request.getRequestURI()  + "<br/>");
        return sb.toString();
    }
}
