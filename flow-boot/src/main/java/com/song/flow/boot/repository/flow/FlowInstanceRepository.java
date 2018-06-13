package com.song.flow.boot.repository.flow;

import org.springframework.data.jpa.repository.JpaRepository;

import com.song.flow.boot.model.entity.flow.FlowInstance;

public interface FlowInstanceRepository extends JpaRepository<FlowInstance, String> {

}