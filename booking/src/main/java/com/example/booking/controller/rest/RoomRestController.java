package com.example.booking.controller.rest;

import com.example.booking.service.room.RoomService;
import com.example.booking.service.room.request.RoomSaveRequest;
import com.example.booking.service.room.response.RoomDetailResponse;
import com.example.booking.service.room.response.RoomListResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rooms")
@AllArgsConstructor
public class RoomRestController {

    private final RoomService roomService;

    @PostMapping
    public void create(@RequestBody RoomSaveRequest request){
        roomService.create(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDetailResponse> findById(@PathVariable Long id){
        return new ResponseEntity<>(roomService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<RoomListResponse>> getRooms(@PageableDefault(size = 5) Pageable pageable,
                                            @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(roomService.getRooms(pageable, search), HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateRoom(@RequestBody RoomSaveRequest request, @PathVariable Long id){
        roomService.update(request,id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        Boolean isDeleted = roomService.delete(id);
        if (isDeleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
