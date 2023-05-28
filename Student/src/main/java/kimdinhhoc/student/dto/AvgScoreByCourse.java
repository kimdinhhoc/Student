package kimdinhhoc.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgScoreByCourse {
	private int courseId;
	private String courseName;
	private double avg;
}
