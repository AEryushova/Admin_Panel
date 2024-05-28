package admin.utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private String id;
    private String username;
    private String is_blocked;
    private String is_deleted;
    private String user_id;
    private String role_id;
}