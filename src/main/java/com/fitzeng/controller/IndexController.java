package com.fitzeng.controller;

import com.fitzeng.model.User;
import com.fitzeng.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

//@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpSession httpSession) {
        logger.info("VISIT HOME PAGE");
        return wendaService.getMessage(2) + "Hello Fitzeng" + httpSession.getAttribute("msg");
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
                          HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionId) {
        StringBuilder sb = new StringBuilder();
        sb.append("COOKIEVALUE : " + sessionId + "<br/>");
        Enumeration<String> headeNames = request.getHeaderNames();
        while (headeNames.hasMoreElements()) {
            String name = headeNames.nextElement();
            sb.append(name + "--->" + request.getHeader(name) + "<br/>");
        }
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                sb.append("Cookie : " + cookie.getName() + "value : " + cookie.getValue());
            }
        }
        sb.append(request.getMethod() + "<br/>");
        sb.append(request.getQueryString() + "<br/>");
        sb.append(request.getPathInfo() + "<br/>");
        sb.append(request.getRequestURL() + "<br/>");
        sb.append(request.getRequestURI()  + "<br/>");

        response.addHeader("Fitzeng", "Aim");
        response.addCookie(new Cookie("username", "fitzeng.org"));
        return sb.toString();
    }


    @RequestMapping(path = {"/redirect/{code}"}, method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession httpSession) {
        httpSession.setAttribute("msg", "jump from redirect ");
        RedirectView redirectView = new RedirectView("/");
        if (code == 301) {
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return redirectView;
    }

    @RequestMapping(path = {"/admin"}, method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        if ("admin".equals(key)) {
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e) {
        return "error : " + e.getMessage();
    }
}
