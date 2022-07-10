package org.zerock.mreview.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.mreview.entity.Member;
import org.zerock.mreview.mreview.entity.MemberRole;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void insertMembers(){

        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder().email("user"+i+"@gmail.com")
                    .pw(passwordEncoder.encode("1111")).nickname("reviewer"+i).build();
            member.addMemberRole(MemberRole.USER);

            memberRepository.save(member);
        });
    }


    @Test
    public void findMember(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Optional<Member> member = memberRepository.findById(Long.valueOf(i));
            System.out.println("member.get().toString() = " + member.get().toString());
        });
        
        

    }

    @Test
    @Transactional
    @Commit
    public void testDeleteMember(){
        Long mid = 1L;

        Member member = Member.builder().mid(mid).build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }
}
