package hu.webuni.spring.jupiterwebuni.jms;

import hu.webuni.spring.jupiterwebuni.dtoandfinance.PaymentDto;
import hu.webuni.spring.jupiterwebuni.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final StudentService studentService;

//    jmslistenerrel tudunk feliratkozni
    @JmsListener(destination = "payments")
    public void onPaymentMessage(PaymentDto paymentDto) {
        studentService.updateBalance(paymentDto.getStudentId(), paymentDto.getAmount());
    }
}
