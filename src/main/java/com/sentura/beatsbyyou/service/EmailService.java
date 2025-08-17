package com.sentura.beatsbyyou.service;

import com.sentura.beatsbyyou.entity.EmailHistory;
import com.sentura.beatsbyyou.entity.EmailTemplate;
import com.sentura.beatsbyyou.entity.Order;
import com.sentura.beatsbyyou.repository.EmailHistoryRepository;
import com.sentura.beatsbyyou.repository.EmailTemplateRepository;
import com.sentura.beatsbyyou.util.SendMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional(value = Transactional.TxType.SUPPORTS)
public class EmailService {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    @Autowired
    private SendMail sendMail;

    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    public boolean sendEmail(Map<String,String> keywordMap, String emailType, String to, String cc, Order order) throws Exception {

        EmailTemplate emailTemplate = emailTemplateRepository.findFirstByEmailTypeAndActiveTrue(emailType);

        if (emailType != null) {
            String template = emailTemplate.getTemplate();
            for(String key: keywordMap.keySet()) {
                template = template.replace(key,keywordMap.get(key));
            }

            sendMail.sendEmail(to, cc, emailTemplate.getSubject(), template);

            EmailHistory emailHistory = new EmailHistory();
            emailHistory.setEmailCc(cc);
            emailHistory.setEmailTo(to);
            emailHistory.setSubject(emailTemplate.getSubject());
            emailHistory.setMessage(template);
            emailHistory.setFkOrder(order);

            emailHistoryRepository.save(emailHistory);

            return true;
        } else {
            logger.error("Email Type Not Found");
            throw new Exception("Email Type Not Found");
        }
    }
}
