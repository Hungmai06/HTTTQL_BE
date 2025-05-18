package com.example.library.services.Impl;

import com.example.library.dtos.request.AuthorRequestDTO;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Author;
import com.example.library.repositories.AuthorRepository;
import com.example.library.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    @Override
    public Author getAuthor(Long id) throws DataNotFoundException {
        return authorRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("Cannot find author with id= "+id)
        );
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    public Author createAuthor(AuthorRequestDTO authorRequestDTO) throws DataNotFoundException {
        if(authorRepository.existsByName(authorRequestDTO.getName())){
            throw new DataNotFoundException("author existed");
        }
        Author author = Author.builder()
                .name(authorRequestDTO.getName())
                .build();
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long id, AuthorRequestDTO authorRequestDTO) throws DataNotFoundException {
        Author author = authorRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("Cannot find author with id= "+id)
        );
        author.setName(authorRequestDTO.getName());
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
      authorRepository.deleteById(id);
    }
}
