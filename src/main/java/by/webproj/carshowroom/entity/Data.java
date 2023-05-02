package by.webproj.carshowroom.entity;

import by.webproj.carshowroom.entity.Course;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
@Getter
public class Data {
    private Long id;
    private Integer course;
    private Course to;
    private Course from;
}
