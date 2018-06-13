package com.song.flow.boot.repository.flow;

import org.springframework.data.jpa.repository.JpaRepository;

import com.song.flow.boot.model.entity.flow.FlowProcess;

public interface FlowProcessRepository extends JpaRepository<FlowProcess, String> {

}