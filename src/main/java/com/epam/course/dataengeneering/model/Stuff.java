package com.epam.course.dataengeneering.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stuff {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //@Column(nullable = false, unique = true)
    private Long complaintID;
    private Date dateReceived;
    private Date dateSentToCompany;
    private String productName;
    private String subProduct;
    private String issue;
    private String subIssue;
    @Lob
    private String consumerComplaintNarrative;
    @Lob
    private String companyPublicResponse;
    private String company;
    private String stateName;
    private String zipCode;
    private String tags;
    private String consumerConsentProvided;
    private String submittedVia;

    private String companyResponseToConsumer;
    private Boolean timelyResponse;
    private Boolean consumerDisputed;


}
