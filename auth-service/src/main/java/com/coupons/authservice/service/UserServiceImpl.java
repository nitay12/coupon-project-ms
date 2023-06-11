package com.coupons.authservice.service;

import com.coupons.authservice.exceptions.EntityNotFoundException;
import com.coupons.authservice.messaging.producer.UserEventProducer;
import com.coupons.authservice.model.User;
import com.coupons.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.getById(userId);
    }

    @Override
    public User updateUser(User user) throws EntityNotFoundException {
        if (!userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException("No user with id: " + user.getId());
        }
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Long userId) throws EntityNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }
        final User user = userRepository.findById(userId).get();
        userRepository.deleteById(userId);
        userEventProducer.sendUserDeletedEvent(user);
    }
}
