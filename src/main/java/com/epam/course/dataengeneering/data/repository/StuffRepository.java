package com.epam.course.dataengeneering.data.repository;

import com.epam.course.dataengeneering.model.Stuff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StuffRepository extends JpaRepository<Stuff, Long> {

    @Query(value = "SELECT s.productName, " +
            "count(s.issue) issues, " +
            "count(CASE WHEN s.timelyResponse THEN 1 END) timelyresponded, " +
            "count(CASE WHEN s.consumerDisputed THEN 1 END) consumerdisputed " +
            "from Stuff s " +
            "WHERE s.dateReceived BETWEEN :startDate AND :endDate " +
            "GROUP BY s.productName " +
            "ORDER BY issues DESC", nativeQuery = true)
    List<Object[]> findProductsAndIssuesByDateRange(@Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate);


    @Query(value = "SELECT s.company, s.statename, " +
            "count(s.issue) issues " +
            "from Stuff s " +
            "WHERE s.company = :company and s.statename <> ''" +
            "GROUP BY s.company, s.stateName " +
            "ORDER BY issues DESC " +
            "LIMIT 1", nativeQuery = true)
    List<Object[]> findIssuesAmountOfStateByCompany(@Param("company") String company);
}
