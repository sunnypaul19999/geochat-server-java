package helios.server.geochat.model;

import javax.persistence.*;

@Entity
@Table(name = "subtopicmetadiscuss")
public class SubTopicMetaDiscussion {

    @Id
    @Column(name = "meta_discuss_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_topic_meta_discuss_id_seq_generator")
    @SequenceGenerator(name = "sub_topic_meta_discuss_id_seq_generator", allocationSize = 1)
    private int id;

    @Column(name = "message", length = 250, updatable = false, nullable = false)
    private String message;

    @ManyToOne(targetEntity = SubTopic.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_topic_id", nullable = false)
    private SubTopic subTopic;

    @ManyToOne(targetEntity = GeoUser.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private GeoUser geoUser;

    public SubTopicMetaDiscussion() {
    }

    public SubTopicMetaDiscussion(String message, SubTopic subTopic, GeoUser geoUser) {
        this.message = message;
        this.subTopic = subTopic;
        this.geoUser = geoUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SubTopic getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(SubTopic subTopic) {
        this.subTopic = subTopic;
    }

    public GeoUser getGeoUser() {
        return geoUser;
    }

    public void setGeoUser(GeoUser geoUser) {
        this.geoUser = geoUser;
    }
}
