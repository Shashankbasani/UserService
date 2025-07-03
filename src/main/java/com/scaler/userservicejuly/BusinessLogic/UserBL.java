package com.scaler.userservicejuly.BusinessLogic;

import com.scaler.userservicejuly.Models.Token;
import com.scaler.userservicejuly.Models.User;
import com.scaler.userservicejuly.Repositories.TokenRepository;
import com.scaler.userservicejuly.Repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserBL {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    public UserBL(UserRepository userRepository, BCryptPasswordEncoder encoder, TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = encoder;
        this.tokenRepository = tokenRepository;
    }
    public Token login(String email, String password){

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            throw new RuntimeException("User with this Email is not in DB");
        }

        User user = optionalUser.get();

        if(bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            //Generate Token
            Token token = createToken(user);
            Token  savedToken = tokenRepository.save(token);
            return savedToken;
        }
        return null;
    }

    public User signup(String email, String password, String name){
        User user = new User();
        user.setEmail(email);
        user.setName(name);

        // First encrypt the password using BCrypt Algo before storing into DB

        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    public void logout(String TokenValue){
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(TokenValue,
                false,
                new Date());
        if(optionalToken.isEmpty()){
            //throw an exception
            return;
        }
        Token token = optionalToken.get();
        token.setDeleted(true);
        tokenRepository.save(token);
    }

    public User validateToken(String tokenValue){
        // firs check in db that tokenValue is present or not
        //expiry of token > current time and deleted should be false

        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(tokenValue,
                false,
                new Date());

        if(optionalToken.isEmpty()){
            //token is invalid
            return null;
        }

        return optionalToken.get().getUser();
    }
    private Token createToken(User user){
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphabetic(128));

        // Expiry time of the token is lets say 30 days from now

        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysAfterCurrentTime = today.plusDays(30);
        Date expiry = Date.from(thirtyDaysAfterCurrentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

        token.setExpiryAt(expiry);

        return token;
    }
}
