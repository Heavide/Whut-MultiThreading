package console;

import java.io.Serializable;
import java.sql.Timestamp;

public class Doc implements Serializable {
    private String id;
    private String creator;
    private Timestamp timestamp;
    private String description;
    private String filename;

    public Doc(String id, String creator, Timestamp timestamp, String description, String filename){
        this.id = id;
        this.creator = creator;
        this.timestamp = timestamp;
        this.description = description;
        this.filename = filename;
    }

    String getId() {return id;}
    void setId(String id) {this.id = id;}
    String getCreator() {return creator;}
    void setCreator(String creator) {this.creator = creator;}
    Timestamp getTimestamp() {return timestamp;}
    void setTimestamp(Timestamp timestamp) {this.timestamp = timestamp;}
    String getDescription() {return description;}
    void setDescription(String description) {this.description = description;}
    String getFilename() {return filename;}
    void setFilename(String filename){this.filename = filename;}
}
