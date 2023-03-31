package com.simpletripbe.moduledomain.community.repository;

import com.simpletripbe.moduledomain.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
