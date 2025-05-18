package com.example.library.services;

import com.example.library.dtos.request.PublisherRequestDTO;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Publisher;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PublisherService {
    Publisher getPublisherById(Long id) throws DataNotFoundException;
    List<Publisher> getAllPublisher();
    Publisher createPublisher(PublisherRequestDTO publisherRequestDTO) throws DataNotFoundException;
    Publisher updatePublisher(Long id,PublisherRequestDTO publisherRequestDTO) throws DataNotFoundException;
    void deletePublisher(Long id);

}
