package org.acme.service;

import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

@ApplicationScoped
public class SchedulerService {
    Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Inject
    MailService mailService;

    @Scheduled(every = "60s")
    public void generateItemFile(){
        logger.info("test generate file_{}", LocalDateTime.now());
    }

    // every second, minute 35, hours 15, dom 12, every month, day of month?, every years
    @Scheduled(cron = "* 35 15 12 * ? *")
    public void scheduleSendEmailListItem() throws IOException{
        mailService.sendExcelToEmail("fauziafifn@gmail.com");
        logger.info("send email success");
    }
}