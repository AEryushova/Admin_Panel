package admin.utils.dbUtils.dbaseData;

import admin.utils.otherUtils.DataHelper;
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

    public String getTimeDate() {
        return DataHelper.extractDateTimeFromTimestamp(log_time);
    }

}
