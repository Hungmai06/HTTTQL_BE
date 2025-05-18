package com.example.library.controllers;

import com.example.library.dtos.request.PublisherRequestDTO;
import com.example.library.dtos.response.PublisherResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Publisher;
import com.example.library.services.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;
    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponse> getPublisherById(@Valid @PathVariable Long id) throws DataNotFoundException {
        return ResponseEntity.ok(PublisherResponse.builder()
                        .name(publisherService.getPublisherById(id).getName())
                        .id(publisherService.getPublisherById(id).getId())
                .build());
    }
    @GetMapping("/")
    public ResponseEntity<List<PublisherResponse>> getAllPublisher(){
        List<PublisherResponse> publisherResponses = new ArrayList<>();
        for(Publisher x: publisherService.getAllPublisher()){
            publisherResponses.add(PublisherResponse.builder()
                            .name(x.getName())
                    .id(x.getId())
                    .build());
        }
        return ResponseEntity.ok(publisherResponses);
    }
    @PostMapping("/")
    public ResponseEntity<?> createPublisher(@Valid @RequestBody PublisherRequestDTO publisherRequestDTO) throws DataNotFoundException {
        Publisher publisher = publisherService.createPublisher(publisherRequestDTO);
        return ResponseEntity.ok(PublisherResponse.builder()
                        .name(publisher.getName())
                .id(publisher.getId())
                .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublisher(@Valid @PathVariable Long id, @Valid @RequestBody PublisherRequestDTO publisherRequestDTO) throws DataNotFoundException {
        Publisher publisher = publisherService.updatePublisher(id,publisherRequestDTO);
        return ResponseEntity.ok(PublisherResponse.builder()
                .name(publisher.getName())
                .id(publisher.getId())
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@Valid @PathVariable Long id){
        publisherService.deletePublisher(id);
        return  ResponseEntity.ok("delete with id ="+id+" publisher successfully");
    }
}
