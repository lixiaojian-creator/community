package com.lxj.controller;

import com.lxj.dto.PaginationDTO;
import com.lxj.mapper.UserMapper;
import com.lxj.model.User;
import com.lxj.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/profile/{section}")
    public String profile(@PathVariable(name = "section") String section,
                          HttpServletRequest request,
                          @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
                          Model model) {
        Cookie[] cookies = request.getCookies();
        User user = null;
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                user = userMapper.findOneByToken(cookie.getValue());
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        if (user == null) {
            return "redirect:/";
        }


        if ("question".equals(section)) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的问题");
        }
        if ("reversion".equals(section)) {
            model.addAttribute("section", "reversion");
            model.addAttribute("sectionName", "最新回复");
        }
        PaginationDTO paginationDTO = questionService.getPaginationDTO(user.getId(), currentPage, pageSize);
        model.addAttribute("pagination", paginationDTO);
        return "profile";
    }
}
