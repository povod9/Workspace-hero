package com.workspace.hero.booking_service.Schedul;

import com.workspace.hero.booking_service.Entity.BookingEntity;
import com.workspace.hero.booking_service.Entity.WorkspaceEntity;
import com.workspace.hero.booking_service.Entity.enums.WorkSpaceStatus;
import com.workspace.hero.booking_service.Repository.BookingRepository;
import com.workspace.hero.booking_service.Repository.WorkspaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class BookingCleaner {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void cleanupExpiredBooking(){
        LocalDateTime now = LocalDateTime.now();

        List<BookingEntity> expiredBooking = bookingRepository.findAllByEndTimeBefore(now);

        for(BookingEntity booking : expiredBooking){
            WorkspaceEntity ws = booking.getWorkspaceEntity();
            ws.setStatus(WorkSpaceStatus.FREE);
            workspaceRepository.save(ws);
        }
    }
}
