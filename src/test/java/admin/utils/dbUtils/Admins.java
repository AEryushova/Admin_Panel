package admin.utils.dbUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admins {
    private int id;
    private String username;
    private Boolean is_blocked;
    private Boolean is_deleted;
    private int user_id;
    private int role_id;
}