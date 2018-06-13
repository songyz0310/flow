package com.song.flow.boot.repository.ticket;

import org.springframework.data.jpa.repository.JpaRepository;

import com.song.flow.boot.model.entity.ticket.SysTicket;

public interface SysTicketRepository extends JpaRepository<SysTicket, String> {

}