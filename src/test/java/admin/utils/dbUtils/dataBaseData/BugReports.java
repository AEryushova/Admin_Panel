package admin.utils.dbUtils.dataBaseData;
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
}
