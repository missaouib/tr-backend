package com.main.app.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long id;

    private Long user_id;

    private Long variation_id;

    private String variation_name;

    private String commentDescription;

    private Boolean verified;

    private Boolean deleted;

    private String dateCreated;

    private String primaryImageUrl;

    private String productName;

    private String userName;

    private String userEmail;

}
