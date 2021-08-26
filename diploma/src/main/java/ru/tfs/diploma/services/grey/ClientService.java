package ru.tfs.diploma.services.grey;

import ru.tfs.diploma.entities.grey.Client;

import java.util.List;

/**
 * Сервис для администрирования клиентов
 */
public interface ClientService {

    List<Client> findAll();

    Client findById(Long id);

    Client save(Client client);

    void delete(Long clientId);

}
