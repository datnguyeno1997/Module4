package com.example.booking.service.room.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class RoomListResponse {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String categories;

    private String type;
}
