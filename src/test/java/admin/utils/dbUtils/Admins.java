package admin.utils.dbUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admins {
    private String id;
    private String username;
    private String is_blocked;
    private String is_deleted;
    private String user_id;
    private String role_id;
}