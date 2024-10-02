package ru.adminlk.clinica.utils.dbUtils.dbData;

import ru.adminlk.clinica.utils.testsUtils.TestHelper;
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

    public String getTitle() {return TestHelper.getValueFromJson(preparing_description, "Title");
    }

    public String getDescription() {
        return TestHelper.getValueFromJson(preparing_description, "Description");
    }

}
