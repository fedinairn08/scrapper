package scrapper.scrapper.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stackOverflowInfo")
public class StackOverflowInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stackOverflowInfo_seq")
    @SequenceGenerator(name = "stackOverflowInfo_seq", sequenceName = "stackOverflowInfo_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "lastAnswerCount")
    private Integer lastAnswerCount;

    @Column(name = "lastCommentCount")
    private Integer lastCommentCount;

    @OneToOne
    @JoinColumn(name = "link_id")
    private Link link;
}
