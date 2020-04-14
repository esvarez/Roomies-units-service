package dev.ericksuarez.roomies.units.service.model.dto;

import dev.ericksuarez.roomies.units.service.model.responses.Credentials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RegisterUserDto {
    private String email;

    private boolean enabled;

    private String username;

    private String firstName;

    private Credentials credentials;
}
