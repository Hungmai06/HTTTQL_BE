package com.example.library.services.Impl;

import com.example.library.dtos.request.PublisherRequestDTO;
import com.example.library.dtos.response.PublisherResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Publisher;
import com.example.library.repositories.PublisherRepository;
import com.example.library.services.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;
    @Override
    public Publisher getPublisherById(Long id) throws DataNotFoundException {
       if(publisherRepository.findById(id).isEmpty()){
           throw new DataNotFoundException("Publisher not found");
       }
        return publisherRepository.findPublisherById(id);
    }

    @Override
    public List<Publisher> getAllPublisher() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher createPublisher(PublisherRequestDTO publisherRequestDTO) throws DataNotFoundException {
        if(publisherRepository.existsByName(publisherRequestDTO.getName())){
            throw new DataNotFoundException("Can not register by name exist");
        }
        Publisher publisher = Publisher.builder()
                .name(publisherRequestDTO.getName())
                .build();
        publisherRepository.save(publisher);
        return publisher;
    }

    @Override
    public Publisher updatePublisher(Long id,PublisherRequestDTO publisherRequestDTO) throws DataNotFoundException {
        Publisher publisher = publisherRepository.findPublisherById(id);
        if(publisher == null){
            throw new DataNotFoundException("Can not found publisher");
        }
        publisher.setName(publisherRequestDTO.getName());
        publisherRepository.save(publisher);
        return publisher ;
    }

    @Override
    public void deletePublisher(Long id) {
         publisherRepository.deleteById(id);
    }
}
