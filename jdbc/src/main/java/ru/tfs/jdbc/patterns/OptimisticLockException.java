package ru.tfs.jdbc.patterns;

public class OptimisticLockException extends RuntimeException {

    public OptimisticLockException(String vo, Long id) {
        super("Entity " + vo + " with id is updated in another transaction");
    }
}
