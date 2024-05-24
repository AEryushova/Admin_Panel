package admin.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faq {
        private String id;
        private String question;
        private String answer;
        private String created_at;
        private String updated_at;
        private Boolean group_id;
        private Boolean sequence;
}
