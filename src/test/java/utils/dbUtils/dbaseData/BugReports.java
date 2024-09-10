package utils.dbUtils.dbaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugReports {
    private UUID id;
    private String message;
    private String email;
    private String author;
    private Timestamp created_at;
    private String phone;
    private String agent_id;
}
