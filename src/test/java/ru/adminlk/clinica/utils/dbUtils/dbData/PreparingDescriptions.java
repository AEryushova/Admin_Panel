package ru.adminlk.clinica.utils.dbUtils.dbData;

import ru.adminlk.clinica.utils.testsUtils.TestHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreparingDescriptions {
    private UUID id;
    private String service_code;
    private String description;

    public String getTitle() {
        return TestHelper.getValueFromJson(description, "Title");
    }

    public String getDescriptions() {
        return TestHelper.getValueFromJson(description, "Description");
    }

}
