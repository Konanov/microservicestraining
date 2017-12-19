package com.awesomeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    private UUID uuid;

    @Size(min = 5, message = "Minimum 5 characters")
    private String name;

    @Past(message = "Birthday should be in the past")
    private Date birthDay;
}
