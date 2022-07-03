package org.zerock.mreview.mreview.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public abstract class BaseEntity {
    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
//
//org.springframework.validation.BindException: org.springframework.validation.BeanPropertyBindingResult: 2 errors
//Field error in object 'guestbookDTO' on field 'modDate': rejected value [2022/07/02 16:33:40];
//codes [typeMismatch.guestbookDTO.modDate,typeMismatch.modDate,typeMismatch.java.time.LocalDateTime,typeMismatch];
//arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [guestbookDTO.modDate,modDate]; arguments [];
//default message [modDate]];
//default message [Failed to convert property value of type 'java.lang.String' to required type 'java.time.LocalDateTime' for property 'modDate';
//nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [java.time.LocalDateTime]
//        for value '2022/07/02 16:33:40'; nested exception is java.lang.IllegalArgumentException: Parse attempt failed for value [2022/07/02 16:33:40]]
//
//
//
//        Field error in object 'guestbookDTO' on field 'regDate': rejected value [2022/07/02 16:33:40]; codes [typeMismatch.guestbookDTO.regDate,typeMismatch.regDate,typeMismatch.java.time.LocalDateTime,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [guestbookDTO.regDate,regDate]; arguments []; default message [regDate]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'java.time.LocalDateTime' for property 'regDate'; nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [java.time.LocalDateTime] for value '2022/07/02 16:33:40'; nested exception is java.lang.IllegalArgumentException: Parse attempt failed for value [2022/07/02 16:33:40]]

//에러2개뜨는데 하나는 modDate, 또 하나는 regDate임