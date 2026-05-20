package cl.duoc.ms_characters.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private LocalDateTime registerDate;
    private int accountLevel;
}