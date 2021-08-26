package ru.tfs.collections_generics.task3;

import java.util.*;
import java.util.stream.Collectors;

public class ClientStreamHelper {

    public static int getAgeSumByName(List<ClientTask3> clients, String name) {
        return clients.stream()
                .filter(client -> client.getName().equals(name))
                .map(ClientTask3::getAge)
                .reduce(Integer::sum).orElse(0);
    }

    public static Set<String> getNameSetOrdered(List<ClientTask3> clients) {
        return clients.stream()
                .map(ClientTask3::getName)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static boolean isClientOlderThan(List<ClientTask3> clients, int oldest) {
        return clients.stream()
                .map(ClientTask3::getAge)
                .anyMatch(age -> age > oldest);
    }

    public static Map<Integer, String> convertListToIdToNameMap(List<ClientTask3> clients) {
        return clients.stream()
                .collect(Collectors.toMap(
                        ClientTask3::getId,
                        ClientTask3::getName,
                        (a, b) -> String.join(", ", a, b),
                        LinkedHashMap::new)
                );
    }

    public static Map<Integer, List<ClientTask3>> convertListToAgeToNamesMap(List<ClientTask3> clients) {
        return clients.stream()
                .collect(Collectors.groupingBy(
                        ClientTask3::getAge,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    public static String getAllTelephones(List<ClientTask3> clients) {
        return clients.stream()
                .filter(client -> client.getPhones() != null)
                .flatMap(client -> client.getPhones().parallelStream())
                .filter(Objects::nonNull)
                .map(PhoneTask3::getNumber)
                .distinct()
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }

    public static ClientTask3 getTheOldestOwnerPhone(List<ClientTask3> clients) {
        return clients.stream()
                .filter(client -> client.getPhones() != null &&
                        client.getPhones().stream().anyMatch(phone -> phone.getType() == TelephoneTypeTask3.HOME_PHONE))
                .max(Comparator.comparingInt(ClientTask3::getAge))
                .orElse(null);
    }




}
