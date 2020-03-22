package com.schedule.service;

import com.schedule.dto.PaymentDTO;
import com.schedule.entity.Payment;
import com.schedule.exception.InvalidDueDateException;
import com.schedule.exception.NoCreditCardException;
import com.schedule.exception.NoPaymentException;
import com.schedule.job.JobScheduler;
import com.schedule.repository.CreditCardRepository;
import com.schedule.repository.PaymentRepository;
import org.quartz.SchedulerException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class PaymentService {

    @Inject
    private PaymentRepository repository;

    @Inject
    private CreditCardRepository creditCardRepository;

    @Transactional
    public void updatePayment(String updateCommand, Object... params) {
        repository.update(updateCommand, params);
    }

    public Payment findPayment(Integer id) throws NoPaymentException {
        return repository.findByIdOptional(id).orElseThrow(() -> new NoPaymentException(id));
    }

    @Transactional
    public Payment scheduleNewPayment(PaymentDTO paymentDTO) throws InvalidDueDateException, SchedulerException {
        Payment payment = buildPayment(paymentDTO);
        if (payment.dueDate.isBefore(LocalDateTime.now())) {
            throw new InvalidDueDateException(payment.dueDate.toString());
        }
        repository.persistAndFlush(payment);
        new JobScheduler(payment).schedule();
        return payment;
    }

    private Payment buildPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.dueDate = LocalDateTime.parse(paymentDTO.getDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        payment.status = paymentDTO.getStatus();
        payment.currency = paymentDTO.getCurrency();
        payment.productId = paymentDTO.getProductId();
        payment.amount = paymentDTO.getAmount();
        payment.description = paymentDTO.getDescription();
        payment.customerMail = paymentDTO.getCustomerMail();
        payment.creditCard = creditCardRepository.findCreditCardByCardNumber(paymentDTO.getCreditCard())
                .orElseThrow(() -> new NoCreditCardException(paymentDTO.getCreditCard()));
        return payment;
    }

}
