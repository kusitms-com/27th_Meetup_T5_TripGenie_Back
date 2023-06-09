package com.simpletripbe.moduledomain.login.entity;

import com.simpletripbe.moduledomain.basetime.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id
    private String email;

    private String name;
    private String nickname;
    private String picture;
    private String gender;
    private LocalDate birth;
    private Integer cash;

    private String roles; // ROLE_USER, ROLE_ADMIN
    private String deleteYn;

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public User(String email) {
        this.email = email;
        this.roles = "ROLE_USER";
        this.deleteYn = "N";
    }

}
