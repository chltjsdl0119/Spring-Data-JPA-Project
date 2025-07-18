package com.example.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
// NamedEntityGraph 사용 방법
@NamedEntityGraph(
        name = "Member.all", attributeNodes = @NamedAttributeNode("team")
)
// NamedQuery 사용 방법
@NamedQuery(
        name="Member.findByUsername",
        query="select m from Member m where m.username = :username"
)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team teamA) {
        this.username = username;
        this.age = age;

        changeTeam(team);
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
