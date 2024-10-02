package ru.adminlk.clinica.utils.dbUtils.dbData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCard {
    private UUID employee_id;
    private String first_name;
    private String second_name;
    private String middle_name;
    private String photo_uri;
    private String qualification;
    private String job;
    private String department;
    private Timestamp created_at;
    private Timestamp updated_at;
}
