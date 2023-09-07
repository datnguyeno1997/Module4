package com.example.booking.service.room.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class RoomDetailResponse {
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private Long typeId;

    private List<Long> categoryIds;
}
