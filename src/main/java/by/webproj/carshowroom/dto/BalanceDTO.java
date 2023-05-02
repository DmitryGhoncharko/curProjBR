package by.webproj.carshowroom.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class BalanceDTO {
    private Long id;
    private Long userId;
    private Double count;
    private String name;
}
