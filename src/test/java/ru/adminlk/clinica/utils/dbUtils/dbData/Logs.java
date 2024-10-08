package ru.adminlk.clinica.utils.dbUtils.dbData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Logs {
    private Timestamp log_time;
    private String code;
    private String user_name;
    private String payload;
    private String hash;
}
