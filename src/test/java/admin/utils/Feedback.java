package admin.utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private String id;
    private String employees_id;
    private String author;
    private String content;
    private String created_at;
    private String updated_at;
    private Boolean is_published;
}
