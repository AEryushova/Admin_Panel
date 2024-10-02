package ru.adminlk.clinica.utils.dbUtils.dbData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Description {
    private UUID employee_expertises_id;
    private String title;
    private UUID employee_details_id;
    private int sequence;
    private Timestamp created_at;
    private Timestamp updated_at;

}
