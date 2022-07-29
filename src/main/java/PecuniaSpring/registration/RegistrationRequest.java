package PecuniaSpring.registration;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

  @NotNull
  @Size(min=3, max=30)
  private String firstName;
  private String lastName;
  private String password;
  @NotNull
  @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
          flags = Pattern.Flag.UNICODE_CASE,
          message = "Adres mailwy jest niepoprawny")
  private String email;
}
