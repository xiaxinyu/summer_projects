package com.summer.project.email;

/**
 * Created by xiaxinyu3@crc.com.hk on 2019.4.2
 * Build sonar report with template
 */
@Component
public class MailContentBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("sonar-report", context);
    }
}
