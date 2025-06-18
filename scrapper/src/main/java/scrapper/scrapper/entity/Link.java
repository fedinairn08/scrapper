package scrapper.scrapper.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.net.URI;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_seq")
    @SequenceGenerator(name = "link_seq", sequenceName = "link_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url")
    private URI url;

    @ManyToOne()
    @JoinColumn(name = "chat")
    private Chat chat;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
}
