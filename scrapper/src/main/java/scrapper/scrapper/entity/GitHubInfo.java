package scrapper.scrapper.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gitHubInfo")
public class GitHubInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gitHubInfo_seq")
    @SequenceGenerator(name = "gitHubInfo_seq", sequenceName = "gitHubInfo_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "lastCommitCount")
    private Integer lastCommitCount = 0;

    @Column(name = "lastBranchCount")
    private Integer lastBranchCount = 0;

    @OneToOne
    @JoinColumn(name = "link_id")
    private Link link;
}
