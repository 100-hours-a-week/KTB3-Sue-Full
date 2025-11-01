package com.example.spring_restapi.service;

public interface UpdateUserService<T, R> {
    T update(Long id, R req);
}

