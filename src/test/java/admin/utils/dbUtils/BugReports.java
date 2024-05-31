package admin.utils.dbUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugReports {
    private String id;
    private String message;
    private String email;
    private String author;
    private String created_at;
}
