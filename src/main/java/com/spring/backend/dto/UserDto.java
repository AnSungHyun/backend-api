package com.spring.backend.dto;

import com.spring.backend.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

//    @NotNull
//    @Size(min = 3, max = 50)
//    private String userId;

    @NotNull
    @Size(min = 1, max = 100)
    private String username;

    @Size(min = 1, max = 12)
    private String telNo;

    @Size(min = 1, max = 10)
    private String lang;
    @Size(min = 1, max = 10)
    private String role;

    @Size(min = 1, max = 10)
    private String roleId;

    private String[] permissions;

    @Size(min = 1, max = 10)
    private String authority;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

//   private Set<AuthorityDto> authorityDtoSet;

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .username(user.getUsername())
                .telNo(user.getTelNo())
                .lang("ko")
                .authority("admin")
                .role("admin")
                .roleId("1")
                .permissions(new String[]{"*.*.*"})
                .build();
    }
}

