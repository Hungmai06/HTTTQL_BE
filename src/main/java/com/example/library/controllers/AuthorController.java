package com.example.library.controllers;

import com.example.library.dtos.request.AuthorRequestDTO;
import com.example.library.dtos.response.AuthorResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Author;
import com.example.library.services.AuthorService;
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
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@Valid @PathVariable Long id) throws DataNotFoundException {
        Author author = authorService.getAuthor(id);
        return ResponseEntity.ok(AuthorResponse.builder()
                        .name(author.getName())
                        .id(author.getId())
                .build());
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllAuthor(){
        List<AuthorResponse> list = new ArrayList<>();
        for(Author x : authorService.getAllAuthor()){
            list.add(AuthorResponse.builder()
                            .id(x.getId())
                            .name(x.getName())
                    .build()
            );
        }
        return ResponseEntity.ok(list);
    }
    @PostMapping("/")
    public ResponseEntity<?> createAuthor(@RequestBody @Valid AuthorRequestDTO authorRequestDTO) throws DataNotFoundException {
        Author author = authorService.createAuthor(authorRequestDTO);
        return ResponseEntity.ok(AuthorResponse.builder()
                        .id(author.getId())
                        .name(author.getName())
                .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@Valid @PathVariable Long id, @Valid @RequestBody AuthorRequestDTO authorRequestDTO) throws DataNotFoundException {
        Author author = authorService.updateAuthor(id,authorRequestDTO);
        return ResponseEntity.ok(AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@Valid @PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.ok("Delete author with id = "+id);
    }

}
