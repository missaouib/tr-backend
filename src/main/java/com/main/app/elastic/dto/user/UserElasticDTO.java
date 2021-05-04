package com.main.app.elastic.dto.user;

import com.main.app.domain.model.user.User;
import com.main.app.elastic.dto.EntityElasticDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "user")
public class UserElasticDTO extends EntityElasticDTO {

    @Field(type = FieldType.Text, fielddata = true)
    private String name;

    @Field(type = FieldType.Text, fielddata = true)
    private String email;

    @Field(type = FieldType.Text, fielddata = true)
    private String surname;

    private Date dateCreated;

    @Field(type = FieldType.Boolean)
    private boolean registrationConfirmed;

    private String role;

    public UserElasticDTO(User user) {
        super(user.getId());
        this.name = user.getName();
        this.email = user.getEmail();
        this.surname = user.getSurname();
        this.role = user.getRole().name();
        this.dateCreated = Date.from(user.getDateCreated());
        this.registrationConfirmed = user.isRegistrationConfirmed();
    }


}
