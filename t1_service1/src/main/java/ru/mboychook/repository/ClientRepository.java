package ru.mboychook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mboychook.model.Client;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Client findByClientUuid(UUID clientUuid);

    @Override
    <S extends Client> List<S> saveAllAndFlush(Iterable<S> clients);
}