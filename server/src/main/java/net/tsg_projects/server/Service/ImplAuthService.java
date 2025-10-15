package net.tsg_projects.server.Service;

import lombok.Data;
import net.tsg_projects.server.Dto.UserInfoDto;
import net.tsg_projects.server.Entity.Member.Member;
import net.tsg_projects.server.Entity.User.User;
import net.tsg_projects.server.Repository.MemberRepository;
import net.tsg_projects.server.Repository.UserRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Data
@Service
public class ImplAuthService implements AuthService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;


    public UserInfoDto InitUserIfNeeded(OAuth2AuthenticationToken tk) {
        String provider = "google.com";
        String sub = tk.getPrincipal().getAttributes().get("sub").toString();
        String email = tk.getPrincipal().getAttributes().get("email").toString();
        String name = tk.getPrincipal().getAttributes().get("name").toString();
        String lastName = tk.getPrincipal().getAttributes().get("family_name").toString();

        // Create / find user
        Optional<User> user = userRepository.findByAuthProviderAndAuthSub(provider, sub);
        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setAuthProvider(provider);
            newUser.setAuthSub(sub);
            newUser.setEmail(email);
            newUser.setCreatedAt(OffsetDateTime.now());
            newUser.setUpdatedAt(OffsetDateTime.now());
            userRepository.save(newUser);
        }

        Member member = memberRepository.findByEmail(email);
        if(member == null) {
            member = CreateMember(user.get().getId(), name, lastName, email);
        } else {
            member = LinkMember(member, user.get());
        }
        // in the if block - member comes out linked by also calling LinkMember
        // in the else block - member only links to user and returns member
        User userSaved = user.get();
        return new UserInfoDto(member.getEmail(), userSaved.getAuthProvider(), userSaved.getCreatedAt(), userSaved.getId());

    }

    public Member CreateMember(UUID id, String firstName, String lastName, String email) {
        Member member = new Member();
        User user = userRepository.findById(id).orElse(null);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setEmail(email);
        member.setDateOfBirth(LocalDate.of(1980, 1, 1));
        return LinkMember(member, user);
    }

    private Member LinkMember(Member member, User user) {
        member.setUser(user);
        memberRepository.save(member);
        return member;
    }
}
