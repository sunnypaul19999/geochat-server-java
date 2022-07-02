package helios.server.geochat.model;

import javax.persistence.*;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @Column(name = "topic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int topicId;

    @Column(name = "topic_title", length = 4000)
    private String topicTitle;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plus_code")
    private GeoPoint geoPoint;

    Topic() {
    }

    Topic(int topicId, String topicTitle) {
        this.topicId = topicId;
        this.topicTitle = topicTitle;
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
}
