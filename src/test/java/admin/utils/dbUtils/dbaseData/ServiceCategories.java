package admin.utils.dbUtils.dbaseData;

import admin.utils.otherUtils.DataHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCategories {
    private UUID id;
    private UUID parent_id;
    private String name;
    private String preparing_description;
    private Boolean is_bars_filtering;
    private Boolean is_protected;
    private int sequence;

    public String getTitle() {return DataHelper.getValueFromJson(preparing_description, "Title");
    }

    public String getDescription() {
        return DataHelper.getValueFromJson(preparing_description, "Description");
    }

}
