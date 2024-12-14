package ru.mboychook.util;

import org.springframework.stereotype.Component;
import ru.mboychook.exception.ClientException;
import ru.mboychook.model.dto.ClientDto;
import ru.mboychook.model.Client;

@Component
public class ClientMapper {

    public static Client toEntity(ClientDto dto) {
        if (dto.getFirstName() == null ||
                dto.getLastName() == null) {
            throw new ClientException("Client first/last name is required");
        }
        return Client.builder()
                .clientUuid(dto.getClientUuid())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .build();
    }

    public static ClientDto toDto(Client entity) {
        return ClientDto.builder()
                .clientUuid(entity.getClientUuid())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .build();
    }

}
