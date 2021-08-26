package ru.tfs.diploma.entities.grey;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.diploma.entities.BaseRecord;

import javax.persistence.*;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client extends BaseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id_seq")
    @SequenceGenerator(name = "client_id_seq", sequenceName = "client_id_seq", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "REGION")
    private String region;

//    @OneToMany(mappedBy = "ru.tfs.diploma.client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Order> orders;


}
