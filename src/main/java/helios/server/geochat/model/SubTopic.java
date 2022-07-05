package helios.server.geochat.model;

import javax.persistence.*;

import helios.server.geochat.dto.request.SubTopicDTO;

@Entity
@Table(name = "subtopic")
public class SubTopic {

    @Id
    @Column(name = "sub_topic_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_topic_id_seqgenerator")
    @SequenceGenerator(name = "sub_topic_id_seqgenerator", allocationSize = 1)
    private int id;

    @Column(name = "sub_topic_title", length = 250, nullable = false)
    private String title;

    @Column(name = "sub_topic_description", length = 4000, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    SubTopic() {
    }

    public SubTopic(SubTopicDTO subTopicDTO) {
        this.title = subTopicDTO.getTitle();
        this.description = subTopicDTO.getDescription();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
