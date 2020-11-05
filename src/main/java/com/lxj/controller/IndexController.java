package com.lxj.controller;

import com.lxj.dto.PaginationDTO;
import com.lxj.dto.QuestionDTO;
import com.lxj.mapper.UserMapper;
import com.lxj.model.User;
import com.lxj.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping({"/"})
    public String index(HttpServletRequest request,
                        @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                User user = userMapper.findOneByToken(cookie.getValue());
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        //获取List<questionDTO>
//        List<QuestionDTO> questionDTOList = questionService.getQuestionDTOList();
//        model.addAttribute("questions",questionDTOList);

        //分页获取paginationDTO
        PaginationDTO paginationDTO = questionService.getPaginationDTO(currentPage, pageSize);
        model.addAttribute("pagination",paginationDTO);
        return "index";
    }
}
