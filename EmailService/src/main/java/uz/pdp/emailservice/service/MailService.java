package uz.pdp.emailservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import uz.pdp.emailservice.dto.PurchaseDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;


    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void sendEmailAboutBookReview(PurchaseDto purchaseDto)
            throws MessagingException {

        String subject ="Laptops successfully purchased from Nout.uz via Stripe";
        Map<String, Object> templateModel = new HashMap<>();

        templateModel.put("laptops", purchaseDto.getPurchaseItems());
        templateModel.put("localDate", purchaseDto.getPurchaseDate());
        templateModel.put("totalAmount", "$" + purchaseDto.getTotalAmount());

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = "";
        try {
            htmlBody = thymeleafTemplateEngine.process("purchaseEmail.html", thymeleafContext);
        } catch (Exception e){
            e.printStackTrace();
        }

        sendHtmlMessage(purchaseDto.getReceiverEmail(), subject, htmlBody);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }

}
