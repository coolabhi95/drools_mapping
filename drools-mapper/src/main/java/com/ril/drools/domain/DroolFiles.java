package com.ril.drools.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(
        name = "drools_table")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@DynamicUpdate
public class DroolFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "precedence_id", nullable = false)
    private Long precedenceId;

    @Column(name = "file",nullable = false)
    @Lob
    private byte[] droolsFile;

    @Column(name = "file_name",nullable = false)
    private String fileName;

}
