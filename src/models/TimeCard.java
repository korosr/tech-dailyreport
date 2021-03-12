package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "timecards")
@NamedQueries({
    @NamedQuery(
        name = "getInTimecard",
        query = "SELECT COUNT(t) FROM TimeCard AS t WHERE t.in_time > :in_time and t.employee_id = :employee_id"
    )
})

@Entity
public class TimeCard {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(name = "in_time", nullable = true)
	private Date in_time;

	@Column(name = "out_time", nullable = true)
	private Date out_time;

	@Column(name = "employee_id", nullable = false)
	private int employee_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getIn_time() {
		return in_time;
	}

	public void setIn_time(Date up_time) {
		this.in_time = up_time;
	}

	public Date getOut_time() {
		return out_time;
	}

	public void setOut_time(Date out_time) {
		this.out_time = out_time;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
}