package tk.tcomad.testsystem.model.persistence;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
public class TestSessionDb implements Serializable {

    private final static long serialVersionUID = 13254083253534L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testId;

    private String userId;

    private Instant startTime;

    private Integer elapsedTime;

    private Float score;

    @ManyToMany
    private List<QuestionDb> questions;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<Long, Set<String>> userAnswersByQuestionId;
}
