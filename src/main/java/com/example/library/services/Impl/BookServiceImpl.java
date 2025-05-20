package com.example.library.services.Impl;

import com.example.library.dtos.request.BookRequestDTO;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.exceptions.InvalidValueException;
import com.example.library.models.Author;
import com.example.library.models.Book;
import com.example.library.models.Category;
import com.example.library.models.Publisher;
import com.example.library.repositories.AuthorRepository;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.CategoryRepository;
import com.example.library.repositories.PublisherRepository;
import com.example.library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public Book getBook(Long id) throws DataNotFoundException {
        return bookRepository.findById(id).orElseThrow(
                ()->  new DataNotFoundException(("Cannot book with id = "+id))
        );
    }

    @Override
    public List<Book> getAllBook() {

        return bookRepository.findAll();
    }

    @Override
    public Book createBook(BookRequestDTO bookRequestDTO) throws InvalidValueException {
        Book book = bookRepository.findByName(bookRequestDTO.getName());
        if(book != null){
            throw new InvalidValueException("Book existed!");
        }
        Author author = authorRepository.findById(bookRequestDTO.getAuthorId()).orElseThrow(
                ()-> new  InvalidValueException("Cannot find author")
        );
        Publisher publisher = publisherRepository.findById(bookRequestDTO.getPublisherId()).orElseThrow(
                ()-> new InvalidValueException("Cannot find publisher")
        );
        Category category = categoryRepository.findById(bookRequestDTO.getCategoryId()).orElseThrow(
                ()-> new InvalidValueException("Cannot find category")
        );
        Book book1 = Book.builder()
                .name(bookRequestDTO.getName())
                .priceBook(bookRequestDTO.getPriceBook())
                .description(bookRequestDTO.getDescription())
                .build();
        book1.setCategory(category);
        book1.setAuthor(author);
        book1.setPublisher(publisher);
        return bookRepository.save(book1);
    }

    @Override
    public Book updateBook(Long id, BookRequestDTO bookRequestDTO) throws DataNotFoundException, InvalidValueException {
        Book book = bookRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("Cannot find book with id ="+id)
        );
        Author author = authorRepository.findById(bookRequestDTO.getAuthorId()).orElseThrow(
                ()-> new  InvalidValueException("Cannot find author")
        );
        Publisher publisher = publisherRepository.findById(bookRequestDTO.getPublisherId()).orElseThrow(
                ()-> new InvalidValueException("Cannot find publisher")
        );
        Category category = categoryRepository.findById(bookRequestDTO.getCategoryId()).orElseThrow(
                ()-> new InvalidValueException("Cannot find category")
        );
        book.setPublisher(publisher);
        book.setCategory(category);
        book.setAuthor(author);
        book.setName(bookRequestDTO.getName());
        book.setPriceBook(bookRequestDTO.getPriceBook());
        book.setDescription(bookRequestDTO.getDescription());
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
      bookRepository.deleteById(id);
    }
}
