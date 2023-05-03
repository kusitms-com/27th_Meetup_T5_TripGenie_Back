package com.simpletripbe.moduledomain.batch.repository;

import com.simpletripbe.moduledomain.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Community, Long> {
}
