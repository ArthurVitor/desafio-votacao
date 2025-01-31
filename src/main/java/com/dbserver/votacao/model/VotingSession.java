package com.dbserver.votacao.model;

import com.dbserver.votacao.enums.VotingSessionResultEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity()
@Table(name = "tb_voting_session")
@AllArgsConstructor()
@NoArgsConstructor()
@Getter()
@Setter()
public class VotingSession {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime endDate = this.creationDate.plusMinutes(1);

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VotingSessionResultEnum result = VotingSessionResultEnum.INDEFINIDO;

    @OneToMany(mappedBy = "votingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;
}
