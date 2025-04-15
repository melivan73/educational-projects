package com.skillbox.fibonacci;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FibonacciRepositoryTest {

    @Autowired
    FibonacciRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EntityManager entityManager;

    @Test
    public void givenFibNumber_whenSave_thenCheckDb() {
        FibonacciNumber number = new FibonacciNumber(7, 13);
        repository.save(number);
        entityManager.flush();

        List<FibonacciNumber> result = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number WHERE index = 7",
                (rs, rowNum) -> new FibonacciNumber(rs.getInt("index"), rs.getInt("value"))
        );

        assertEquals(1, result.size());
        assertEquals(13, result.get(0).getValue());
    }


    @Test
    public void givenNumber_whenExistInDb_thenReturn() {
        repository.save(new FibonacciNumber(6, 11));
        Optional<FibonacciNumber> dbValue = repository.findByIndex(6);
        assertTrue(dbValue.isPresent());
        assertEquals(11, dbValue.get().getValue());
    }

    @Test
    public void givenNumber_whenExistAndSave_thenNoConflicts() {
        FibonacciNumber number = new FibonacciNumber(11, 89);
        repository.save(number);
        entityManager.flush();
        repository.save(number);
        entityManager.flush();
        entityManager.detach(number);

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM fibonacci_number WHERE index = 11",
                Integer.class
        );
        assertEquals(1, count);
    }

}
