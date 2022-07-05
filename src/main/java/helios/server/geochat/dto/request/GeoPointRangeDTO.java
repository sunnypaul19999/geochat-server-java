package helios.server.geochat.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class GeoPointRangeDTO {

    @NotNull
    @Max(value = 10000)
    @Min(value = 100)
    private int radius;

    public GeoPointRangeDTO(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
