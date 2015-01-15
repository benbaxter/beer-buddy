package com.beerbuddy.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beerbuddy.core.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
