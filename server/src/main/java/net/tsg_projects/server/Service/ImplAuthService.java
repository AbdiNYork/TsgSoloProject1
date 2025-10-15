package net.tsg_projects.server.Service;

import lombok.Data;
import net.tsg_projects.server.Dto.UserInfoDto;
import net.tsg_projects.server.Entity.Member.Member;
import net.tsg_projects.server.Entity.User.User;
import net.tsg_projects.server.Repository.MemberRepository;
import net.tsg_projects.server.Repository.UserRepository;
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


    public UserInfoDto initUserIfNeeded(Jwt jwt) {
        String provider = "google.com";
        String sub = jwt.getSubject();
        String email = jwt.getClaim("email");
        String name = jwt.getClaim("name");
        String lastName = jwt.getClaim("family_name");

        // Create / find user
        Optional<User> user = userRepository.findByAuthProviderAndAuthSub(provider, sub);

        User userSaved = new User();
        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setAuthProvider(provider);
            newUser.setAuthSub(sub);
            newUser.setEmail(email);
            newUser.setCreatedAt(OffsetDateTime.now());
            newUser.setUpdatedAt(OffsetDateTime.now());
            userSaved = userRepository.save(newUser);
        }

        Member member = memberRepository.findByEmail(email);
        if(member == null) {
            member = CreateMember(userSaved.getId(), name, lastName, email);
        } else {
            member = LinkMember(member, userSaved);
        }
        // in the if block - member comes out linked by also calling LinkMember
        // in the else block - member only links to user and returns member
        return new UserInfoDto(userSaved.getEmail(), userSaved.getAuthProvider(), userSaved.getCreatedAt(), userSaved.getId());

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
