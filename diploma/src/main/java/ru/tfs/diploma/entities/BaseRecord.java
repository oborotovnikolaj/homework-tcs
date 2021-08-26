package ru.tfs.diploma.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseRecord<U> implements Serializable {

//    @Id
//    @GeneratedValue(strategy = AUTO)
////    @SequenceGenerator(name = "position_id_seq", sequenceName = "position_id_seq", allocationSize = 10)
////    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
////    @GenericGenerator(name = "native", strategy = "native")
//    @Column(name = "ID")
//    private Long id;

    @CreatedDate
    @Column( name = "created_date", nullable = false, insertable = true, updatable = false)
    @Basic(fetch = FetchType.LAZY)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private Instant lastModifiedDate;

//    @CreatedBy
//    @Column(name = "CREATED_BY")
//    private String createdBy;

//    @LastModifiedBy
//    @Column(name = "LAST_MODIFIED_BY")
//    private String lastModifiedBy;
}
