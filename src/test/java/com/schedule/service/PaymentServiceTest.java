package com.schedule.service;

import com.schedule.dto.PaymentDTO;
import com.schedule.entity.CreditCard;
import com.schedule.exception.InvalidDueDateException;
import com.schedule.exception.NoCreditCardException;
import com.schedule.exception.NoPaymentException;
import com.schedule.repository.CreditCardRepository;
import com.schedule.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaymentServiceTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeAll
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    // @Test
    public void testInvalidDateInPastException() {
        Mockito.when(creditCardRepository.findCreditCardByCardNumber(Mockito.anyInt())).thenReturn(Optional.of(new CreditCard()));
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1l);
        paymentDTO.setDueDate("2030-12-12 10:10:10");
        paymentDTO.setDueDate("2010-12-12 10:10:10");
        assertThrows(InvalidDueDateException.class, () -> paymentService.scheduleNewPayment(paymentDTO));
    }

    // @Test
    public void testNoCreditCardException() {
        Mockito.when(creditCardRepository.findCreditCardByCardNumber(Mockito.anyInt())).thenReturn(Optional.empty());
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1l);
        paymentDTO.setDueDate("2030-12-12 10:10:10");
        paymentDTO.setDueDate("2010-12-12 10:10:10");
        assertThrows(NoCreditCardException.class, () -> paymentService.scheduleNewPayment(paymentDTO));
    }

    // @Test
    public void testNoPaymentException() {
        Mockito.when(paymentRepository.findByIdOptional(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(NoPaymentException.class, () -> paymentService.findPayment(1));
    }

}
