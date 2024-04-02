package com.ssafy.cnnect.user.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendEmail(String toEmail,
                          String title,
                          String text) {
        SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            log.debug("MailService.sendEmail exception occur toEmail: {}, " +
                    "title: {}, text: {}", toEmail, title, text);
            e.printStackTrace();
        }
    }

    // 발신할 이메일 데이터 세팅
    private SimpleMailMessage createEmailForm(String toEmail,
                                              String title,
                                              String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        String msgg="";
        msgg+= "<div align='center' style='margin:30px; color:black;'>";
        msgg+= "<h2 align='left' style='margin-left:10%;'>인증메일</h2>";
        msgg+= "<hr style='background-color:#000000; height:2px; width:80%;'/>";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "CNNect 회원 가입을 위한 인증번호 발송 메일입니다.\n";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "아래의 인증번호를 사용하여 이메일 주소 인증을 완료하면 다음 단계로 진행이 가능합니다.\n";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:none; height: 50px;\n" +
                "   line-height: 50px; background-color:lightgrey; width:80%'>";
        msgg+= "<div style='font-size:130%;'>";
        msgg+= "<strong>"+text+"</strong><div><br/></div>";

        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(msgg);

        return message;
    }
}
