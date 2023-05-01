package by.webproj.carshowroom.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
public class ImageData {
    private String imageUrl;
    private List<String> colors;

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getColors() {
        return colors;
    }
}
