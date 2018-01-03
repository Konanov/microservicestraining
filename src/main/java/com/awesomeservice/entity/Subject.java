package com.awesomeservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@ApiModel("Traceable subject to observe.")
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    private UUID uuid;

    @ApiModelProperty(notes = "Minimum of 5 characters")
    @Size(min = 5, message = "Minimum 5 characters")
    private String name;

    @ApiModelProperty(notes = "Birthday should be in the past")
    @Past(message = "Birthday should be in the past")
    private Date birthDay;
}
