package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "reactions")
@NamedQueries({
    @NamedQuery(
        name = "getReactionsCount",
        query = "SELECT COUNT(r) FROM Reaction AS r"
    ),
    @NamedQuery(
        name = "reactionCheck",
        query = "DELETE FROM Relationship AS r WHERE r.followed_id = :followed_id and r.follower_id = :follower_id"
    ),
})
@Entity
public class Reaction {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(name = "employee_id", nullable = false)
	private Integer employee_id;

	@Column(name = "report_id", nullable = false)
	private Integer report_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}

	public Integer getReport_id() {
		return report_id;
	}

	public void setReport_id(Integer report_id) {
		this.report_id = report_id;
	}
}