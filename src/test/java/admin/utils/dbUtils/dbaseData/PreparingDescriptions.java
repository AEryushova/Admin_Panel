package admin.utils.dbUtils.dbaseData;

import admin.utils.otherUtils.DataHelper;
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

        public String getTitle() {return DataHelper.getValueFromJson(description, "Title");
        }

        public String getDescriptions() {
            return DataHelper.getValueFromJson(description, "Description");
        }

}
