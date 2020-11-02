package com.lxj.controller;

import com.lxj.dto.AccessTokenDTO;
import com.lxj.dto.GithubUser;
import com.lxj.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

    @GetMapping("/oauth/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        try {
            String accessToken = githubProvider.getAccessToken(accessTokenDTO);
            GithubUser user = githubProvider.getUser(accessToken);
            if(user != null){
                //登入成功
                request.getSession().setAttribute("user",user);
                return "redirect:/index";
            }else {
                //登入失败
                return "redirect:/index";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }
}
