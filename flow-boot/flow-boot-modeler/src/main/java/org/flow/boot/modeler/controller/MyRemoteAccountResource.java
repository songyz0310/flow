package org.flow.boot.modeler.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.security.FlowableAppUser;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class MyRemoteAccountResource {

    /**
     * GET /rest/account -> get the current user.
     */
    @GetMapping(value = "/rest/account", produces = "application/json")
    public UserRepresentation getAccount(HttpServletRequest request) {
        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId("1");
        userRepresentation.setFirstName("songyz");
        userRepresentation.setFullName("宋印赠");
        userRepresentation.setLastName("宋印赠");
        userRepresentation.setPrivileges(roles);

        List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        RemoteUser user = new RemoteUser();
        BeanUtils.copyProperties(userRepresentation, user);

        // 根据用户名username加载userDetails
        UserDetails userDetails = new FlowableAppUser(user, "1", authorities);

        // 根据userDetails构建新的Authentication,这里使用了
        // PreAuthenticatedAuthenticationToken当然可以用其他token,如UsernamePasswordAuthenticationToken
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails,
                userDetails.getPassword(), userDetails.getAuthorities());

        // 设置authentication中details
        authentication.setDetails(new WebAuthenticationDetails(request));

        // 存放authentication到SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        // 在session中存放security context,方便同一个session中控制用户的其他操作
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return userRepresentation;

    }
}
