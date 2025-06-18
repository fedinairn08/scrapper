package scrapper.scrapper.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
    @SequenceGenerator(name = "chat_seq", sequenceName = "chat_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Link> links;
}
