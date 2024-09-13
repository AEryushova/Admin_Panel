package ru.adminlk.clinica.samsmu.utils.dbUtils.dbaseData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section {
    private UUID employee_details_id;
    private String title;
    private UUID employee_card_employee_id;
    private int sequence;
    private Timestamp created_at;
    private Timestamp updated_at;

}
