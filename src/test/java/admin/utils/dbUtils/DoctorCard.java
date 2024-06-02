package admin.utils.dbUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCard {
    private String employee_id;
    private String first_name;
    private String second_name;
    private String middle_name;
    private String photo_uri;
    private String qualification;
    private String job;
    private String department;
    private String created_at;
    private String updated_at;
}
