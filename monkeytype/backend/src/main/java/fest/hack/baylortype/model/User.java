package fest.hack.baylortype.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Score> scores;

    private String username;

    private String password;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer attempts;

    private Long startTime;

    @Column(name = "words")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Word> words;

    @Column(nullable = false, columnDefinition = "boolean default 0")
    private Boolean inGame;

    @Column(nullable = false, columnDefinition = "boolean default 0")
    private Boolean canPlantFlag;
}
