package com.song.flow.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.song.flow.boot.common.entity.FlowInstance;

public interface FlowInstanceRepository extends JpaRepository<FlowInstance, String> {

}