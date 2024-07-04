package admin.utils.dbUtils.dbaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllServices {
    private UUID id;
    private String code;
    private String name;
    private String source;
    private int sequence;
}
