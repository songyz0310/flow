package com.song.flow.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.song.flow.boot.common.entity.FlowProcess;

public interface ProcessRepository extends JpaRepository<FlowProcess, String> {

}