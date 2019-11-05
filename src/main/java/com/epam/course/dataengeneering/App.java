package com.epam.course.dataengeneering;

import com.epam.course.dataengeneering.config.PersistenceJPAConfig;
import com.epam.course.dataengeneering.service.FeaturesPresentationService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Slf4j
public class App {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-dd-MM");

    @SneakyThrows
    public static void main(String[] args) {

        if (args.length == 0) {
            log.info("No arguments provided.\nEnter either date range in format yyyy-dd-MM or company name.");
            return;
        }

        if (args.length > 2) {
            throw new IllegalArgumentException("Too many arguments.\nEnter either date range in format yyyy-dd-MM or company name.");
        }

        final ApplicationContext ctx = new AnnotationConfigApplicationContext(PersistenceJPAConfig.class);
        final FeaturesPresentationService presentationService = ctx.getBean(FeaturesPresentationService.class);

        if (args.length == 2 &&
                isValidDate(args[0]) &&
                isValidDate(args[1])) {

            final String outputFormat = "|%1$-40s|%2$-18s|%3$-25s|%4$-25s|\n";
            final Date dateBefore = DATE_FORMAT.parse(args[0]);
            final Date dateAfter = DATE_FORMAT.parse(args[1]);

            presentationService.saveFromDataProvider();
            final List<Object[]> issues = presentationService.findIssuesAmountOfStateByCompany(dateBefore, dateAfter);

            System.out.printf(outputFormat, "Product name", "Amount of issues", "Timely responded count", "Consumer disputed count");
            for (Object[] issue : issues) {
                final String productName = issue[0].toString();
                final String issuesAmount = issue[1].toString();
                final String timelyRespondedCount = issue[2].toString();
                final String consumerDisputedCount = issue[3].toString();

                System.out.printf(outputFormat, productName, issuesAmount, timelyRespondedCount, consumerDisputedCount);
            }
        }

        if (args.length == 1) {
            final String outputFormat = "|%1$-40s|%2$-25s|%3$-25s|\n";
            final String company = args[0];

            presentationService.saveFromDataProvider();
            final List<Object[]> result = presentationService.findIssuesAmountOfStateByCompany(company);

            if (result.size() == 0) {
                return;
            }

            final String companyName = result.get(0)[0].toString();
            final String stateName = result.get(0)[1].toString();
            final String issuesCount = result.get(0)[2].toString();

            System.out.printf(outputFormat, "Product name", "Timely responded count", "Consumer disputed count");
            System.out.printf(outputFormat, companyName, stateName, issuesCount);
        }
    }

    private static boolean isValidDate(String inDate) {
        try {
            DATE_FORMAT.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}
