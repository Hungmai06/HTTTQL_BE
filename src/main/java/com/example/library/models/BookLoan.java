package com.example.library.models;

import com.example.library.constants.BookLoanEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_loans")
public class BookLoan extends DateBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private BookLoanEnum bookLoanEnum;
    @Column
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowDate;
    @Column
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @Column
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @OneToMany(mappedBy = "bookLoan")
    private List<Fine> fines;

}
