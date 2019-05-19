package ava.config;

import ava.db.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class ScheduleConfig {

    FileRepository fileRepository;

    @Scheduled(cron = "* 0 * * * *")
    @Transactional
    public void cronTask() {
        fileRepository.eraseOldFiles();
    }
}
