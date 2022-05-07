package ru.itis.cinema.util;

import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class EmailUtil {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private Configuration freemarkerConfiguration;


    public void sendMail(String to, String subject, String templateName, Map<String, String> data) {


        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                String mailText = FreeMarkerTemplateUtils
                        .processTemplateIntoString(freemarkerConfiguration
                                .getTemplate(templateName,"UTF-8"), data);
                messageHelper.setSubject(subject);
                messageHelper.setText(mailText, true);
                messageHelper.setTo(to);
                messageHelper.setFrom(from);
            }
        };
        mailSender.send(preparator);
    }

}
