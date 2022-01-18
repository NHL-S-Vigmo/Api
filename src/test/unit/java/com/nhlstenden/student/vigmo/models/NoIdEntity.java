package unit.java.com.nhlstenden.student.vigmo.models;

import com.nhlstenden.student.vigmo.models.EntityId;

public class NoIdEntity implements EntityId {
    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }
}
