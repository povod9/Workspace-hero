package com.workspace.hero.booking_service.Schedul;

import com.workspace.hero.booking_service.Entity.BookingEntity;
import com.workspace.hero.booking_service.Entity.WorkspaceEntity;
import com.workspace.hero.booking_service.Entity.enums.BookingStatus;
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
public class WorkspaceReleaser {

    @Autowired
    private BookingRepository bookingRepository;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void cleanupExpiredBooking(){
        LocalDateTime now = LocalDateTime.now();

        List<BookingEntity> expiredBookings = bookingRepository.findAllByEndTimeBeforeAndStatus(
                now, BookingStatus.ACTIVE
        );

        for (BookingEntity booking : expiredBookings) {
            booking.setStatus(BookingStatus.COMPLETED);
            bookingRepository.save(booking);

        }
    }
}
