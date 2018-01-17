package com.awesomeservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ApiModel("Traceable subject to observe.")
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(generator = "subject-uuid")
    @GenericGenerator(name = "subject-uuid", strategy = "uuid")
    private String uuid;

    @ApiModelProperty(notes = "Minimum of 5 characters")
    @Size(min = 5, message = "Minimum 5 characters")
    private String name;

    @ApiModelProperty(notes = "Birthday should be in the past")
    @Past(message = "Birthday should be in the past")
    private Date birthDay;

    @OneToOne(fetch = FetchType.EAGER)
    @ApiModelProperty(notes = "Users credentials")
    private Credentials credentials;

    @ApiModelProperty(notes = "Version of model")
    private int version;

    @OneToMany(mappedBy = "subject")
    private Collection<Post> posts;

    @Data
    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Credentials {

        @Id
        @GeneratedValue(generator = "credentials-uuid")
        @GenericGenerator(name = "credentials-uuid", strategy = "uuid")
        private String uuid;

        @ApiModelProperty(notes = "Minimum of 5 characters")
        @Size(min = 5, message = "Minimum 5 characters")
        private String name;

        @ApiModelProperty(notes = "Minimum of 5 characters")
        @Size(min = 5, message = "Minimum 5 characters")
        private String surname;

        public Credentials(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }
    }
}
