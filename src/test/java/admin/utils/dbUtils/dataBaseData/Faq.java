package admin.utils.dbUtils.dataBaseData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faq {
        private UUID id;
        private String question;
        private String answer;
        private Timestamp created_at;
        private Timestamp updated_at;
        private UUID group_id;
        private int sequence;
}
