package com.main.app.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 * The service used for building email body with email templates.
 *
 * @author simona
 *
 */
@Service
public class MailContentBuilder {

    private static final String TLT_TEMPLATE_NAME = "textLinkTextTemplate";

    private static final String TLT_TEMPLATE_MESSAGE_BEFORE_PARAM = "messageBefore";

    private static final String TLT_TEMPLATE_MESSAGE_AFTER_PARAM = "messageAfter";

    private static final String TLT_LINK_PARAM = "link";

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildContentWithLink(String link, String messageBefore, String messageAfter) {
        Context context = new Context();
        context.setVariable(TLT_TEMPLATE_MESSAGE_BEFORE_PARAM, messageBefore);
        context.setVariable(TLT_LINK_PARAM, link);
        context.setVariable(TLT_TEMPLATE_MESSAGE_AFTER_PARAM, messageAfter);
        return templateEngine.process(TLT_TEMPLATE_NAME, context);
    }

}
