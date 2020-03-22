package com.schedule.job;

import com.schedule.exception.InvalidChargeException;
import com.schedule.service.MailService;
import com.schedule.service.PaymentService;
import io.quarkus.arc.Arc;
import io.quarkus.arc.ManagedContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Optional;
import java.util.Random;

public class PaymentJob implements Job {

    private static final String SUCCESS = "Your transaction has been successfully completed! Enjoy your life!\nTransacation id: %d";
    private static final String FAIL = "Oh, no! Something went wrong with your transation :( Now you'll have to spend time of your life in the bank!\nTransacation id: %d";

    private final MailService mailer = new MailService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        ManagedContext requestContext = Arc.container().requestContext();
        if (!requestContext.isActive()) {
            requestContext.activate();
        }
        PaymentService paymentService = Arc.container().instance(PaymentService.class).get();
        int paymentId = jobExecutionContext.getMergedJobDataMap().getInt("id");
        Optional.of(paymentService.findPayment(paymentId)).ifPresent(e -> new InvalidChargeException(paymentId));
        String mailContent;
        if (new Random().nextInt(100) % 2 == 0) {
            mailContent = SUCCESS;
            paymentService.updatePayment("status = 'SUCCESS' where id=?1", paymentId);
        } else {
            mailContent = FAIL;
            paymentService.updatePayment("status = 'FAIL' where id=?1", paymentId);
        }
        mailer.sendMail(
                jobExecutionContext.getMergedJobDataMap().getString("email"),
                String.format(mailContent, paymentId)
        );
    }

}
