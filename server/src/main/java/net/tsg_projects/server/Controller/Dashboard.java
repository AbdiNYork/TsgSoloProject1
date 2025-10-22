package net.tsg_projects.server.Controller;


import lombok.Data;
import net.tsg_projects.server.Dto.DashboardDto;
import net.tsg_projects.server.Dto.MemberDto;
import net.tsg_projects.server.Entity.Member.Member;
import net.tsg_projects.server.MockDataFactory.MockDataFactory;
import net.tsg_projects.server.Repository.MemberRepository;
import net.tsg_projects.server.Repository.UserRepository;
import net.tsg_projects.server.Service.DashboardService;
import net.tsg_projects.server.Service.ImplDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/api/dashboard")
public class Dashboard {


    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MockDataFactory mockDataFactory;
    @Autowired
    ImplDashboardService dashboardService;

    @GetMapping("")
    public DashboardDto getDashBoard(@AuthenticationPrincipal Jwt jwt) {

        String email = jwt.getClaim("email");
        return dashboardService.dashboard(email);

    }
}
