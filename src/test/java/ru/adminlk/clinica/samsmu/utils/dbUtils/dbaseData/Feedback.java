package ru.adminlk.clinica.samsmu.utils.dbUtils.dbaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private UUID id;
    private UUID employees_id;
    private String author;
    private String content;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Boolean is_published;
}
