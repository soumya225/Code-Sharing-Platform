package platform.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Code implements Comparable<Code>{
    @Id
    @JsonIgnore
    private final String id = UUID.randomUUID().toString();

    private String code;

    private String date = LocalDateTime.now().toString();

    private Integer time;

    private Integer views;

    @JsonIgnore
    private boolean hasViewsRestriction = false;

    @JsonIgnore
    private boolean hasTimeRestriction = false;

    public String getCode() {
        return code;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean hasViewsRestriction() {
        return hasViewsRestriction;
    }

    public void setHasViewsRestriction(boolean hasViewsRestriction) {
        this.hasViewsRestriction = hasViewsRestriction;
    }

    public boolean hasTimeRestriction() {
        return hasTimeRestriction;
    }

    public void setHasTimeRestriction(boolean hasTimeRestriction) {
        this.hasTimeRestriction = hasTimeRestriction;
    }

    @Override
    public int compareTo(Code o) {
        return LocalDateTime.parse(this.getDate()).compareTo(LocalDateTime.parse(o.getDate()));
    }

    @Override
    public String toString() {
        return "Code{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", views=" + views +
                ", hasViewsRestriction=" + hasViewsRestriction +
                ", hasTimeRestriction=" + hasTimeRestriction +
                '}';
    }
}
