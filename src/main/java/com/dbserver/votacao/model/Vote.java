package com.dbserver.votacao.model;

import com.dbserver.votacao.enums.VoteOptionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity()
@Table(name = "tb_vote")
@AllArgsConstructor()
@NoArgsConstructor()
@Getter()
@Setter()
public class Vote {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "associate_id", nullable = false)
    private Associate associate;

    @ManyToOne()
    @JoinColumn(name = "votingSession_id", nullable = false)
    private VotingSession votingSession;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VoteOptionEnum voteOption = VoteOptionEnum.NAO;
}
