package helios.server.geochat.model;

import java.util.List;

import javax.persistence.*;

import helios.server.geochat.dto.request.TopicDTO;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @Column(name = "topic_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_id_seqgenerator")
    @SequenceGenerator(name = "topic_id_seqgenerator", allocationSize = 1)
    private int topicId;

    @Column(name = "topic_title", length = 4000)
    private String topicTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plus_code", nullable = false)
    private GeoPoint geoPoint;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SubTopic> subTopic;

    Topic() {
    }

    public Topic(TopicDTO topicDTO) {
        this.topicTitle = topicDTO.getTopicTitle();
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public List<SubTopic> getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(List<SubTopic> subTopic) {
        this.subTopic = subTopic;
    }
}
