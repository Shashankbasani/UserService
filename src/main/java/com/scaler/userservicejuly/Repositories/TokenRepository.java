package com.scaler.userservicejuly.Repositories;

import com.scaler.userservicejuly.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token save(Token token);

    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String tokenValue,
                                                                boolean deleted,
                                                                Date currentTime);
}
