package org.flow.boot.ticket.repository;

import org.flow.boot.ticket.entity.SysTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysTicketRepository extends JpaRepository<SysTicket, String> {

}