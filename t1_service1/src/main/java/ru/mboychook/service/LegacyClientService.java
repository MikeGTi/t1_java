package ru.mboychook.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mboychook.model.Client;
import ru.mboychook.repository.ClientRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LegacyClientService {
    private final ClientRepository repository;
    private final Map<Long, Client> cache;

    public LegacyClientService(ClientRepository repository) {
        this.repository = repository;
        this.cache = new HashMap<>();
    }

    /*@PostConstruct
    void init() {
        getClient(1L);
    }

    public ClientDto getClient(Long id) {
        log.debug("Call method getClient with uuid {}", id);
        ClientDto clientDto = null;

        if (cache.containsKey(id)) {
            return ClientMapper.toDto(cache.get(id));
        }

        try {
            Client entity = repository.findById(id).get();
            clientDto = ClientMapper.toDto(entity);
            cache.put(id, entity);
        } catch (Exception e) {
            log.error("LegacyClientService getClient: ", e);
            throw new ClientException();
        }
        log.debug("Client info: {}", clientDto.toString());
        return clientDto;
    }*/

}