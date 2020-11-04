package com.lxj.controller;

import com.lxj.dto.AccessTokenDTO;
import com.lxj.dto.GithubUser;
import com.lxj.mapper.UserMapper;
import com.lxj.model.User;
import com.lxj.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {

    private GithubProvider githubProvider;

    @Autowired
    public AuthorizeController(GithubProvider githubProvider) {
        this.githubProvider = githubProvider;
    }

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/oauth/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        try {
            String accessToken = githubProvider.getAccessToken(accessTokenDTO);
            GithubUser githubUser = githubProvider.getUser(accessToken);
            if(githubUser != null){
                //登入成功
                request.getSession().setAttribute("user",githubUser);
                User user = new User();
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setName(githubUser.getName());
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUri(githubUser.getAvatar_url());
                userMapper.insert(user);
                Cookie cookieToken = new Cookie("token", token);
                cookieToken.setPath("/community/");
                response.addCookie(cookieToken);
                return "redirect:/";
            }else {
                //登入失败
                return "redirect:/";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }
}
