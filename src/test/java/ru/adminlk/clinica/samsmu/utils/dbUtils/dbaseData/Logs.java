package ru.adminlk.clinica.samsmu.utils.dbUtils.dbaseData;

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
