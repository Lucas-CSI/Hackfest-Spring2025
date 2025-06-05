package fest.hack.baylortype.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.*;

@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word_value", nullable = false)
    private String word;

    @Column(name = "word_index", nullable = false)
    private Integer wordIndex;
    
    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Word() {
    }

    public Word(String word, Integer wordIndex, User user) {
        this.word = word;
        this.wordIndex = wordIndex;
	this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getWordIndex() {
        return wordIndex;
    }

    public void setWordIndex(int wordIndex) {
        this.wordIndex = wordIndex;
    }
}
