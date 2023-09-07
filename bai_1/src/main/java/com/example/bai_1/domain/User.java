package com.example.bai_1.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Set;

@Entity
@Data
@Table(name = "users")
@Where(clause = "deleted = 0")
@SQLDelete(sql= "UPDATE users SET `deleted` = 1 WHERE (`id` = ?); ")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private boolean deleted = false;
    //             fetch Lazy khi select User(JPQL) thi no se khong lay thang Profile no chi lay khi run user.getProfile() run cau len sql de lay profile cua user
// cascade = CascadeType.ALL gia su them moi User va field Profile co gia tri khi them moi User thi no se them moi luon Profile
    //    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;



}