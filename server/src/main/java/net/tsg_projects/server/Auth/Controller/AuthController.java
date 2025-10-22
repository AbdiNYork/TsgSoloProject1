package net.tsg_projects.server.Auth.Controller;

import lombok.Data;
import net.tsg_projects.server.Dto.MemberDto;
import net.tsg_projects.server.Dto.UserInfoDto;
import net.tsg_projects.server.Service.AuthService;
import net.tsg_projects.server.Service.ImplAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Data
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ImplAuthService authService;

    @GetMapping("/public")
    public String publicPage() {
        return "public";
    }

    @GetMapping("/me")
    public MemberDto getMe(@AuthenticationPrincipal Jwt jwt) {
//        System.out.println(tk.getPrincipal());
          //return jwt.getClaims();
        String email = jwt.getClaim("email");
        return authService.initUserIfNeeded(jwt);
    }



}
