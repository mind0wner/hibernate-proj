package com.bank.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "Account")
@Setter
@Getter
@NoArgsConstructor
@ToString(of = {"accountId", "bankName", "accountType", "initialBalance", "currentBalance"})
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long accountId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    List<Transaction> transactions = new ArrayList<Transaction>();

    @Column(name = "BANK_NAME")
    private String bankName;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_CURRENCY")
    private AccountCurrency accountCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE")
    private AccountType accountType;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ACCOUNT", joinColumns = @JoinColumn(name = "ACCOUNT_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> users = new HashSet<User>();

    @Column(name = "INITIAL_BALANCE")
    private BigDecimal initialBalance;

    @Column(name = "CURRENT_BALANCE")
    private BigDecimal currentBalance;

    @Temporal(TemporalType.DATE)
    @Column(name = "OPEN_DATE")
    private Date openDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "CLOSE_DATE")
    private Date closeDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;
}
