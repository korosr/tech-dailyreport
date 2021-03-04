package models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "reports")
@NamedQueries({
	@NamedQuery(
			name = "getAllReports",
			query = "SELECT r FROM Report AS r ORDER BY r.id DESC"
			),
	@NamedQuery(
			name = "getReportsCount",
			query = "SELECT COUNT(r) FROM Report AS r"
			),
	@NamedQuery(
			name = "getMyAllReports",
			query = "SELECT r FROM Report AS r WHERE r.employee = :employee ORDER BY r.id DESC"
			),
	@NamedQuery(
			name = "getMyReportsCount",
			query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee"
			),
	@NamedQuery(
			name = "getFollowedReports",
			query = "SELECT r FROM Report r INNER JOIN r.relationship p WHERE p.follower_id = :followed_id"
			)
})

@Entity
public class Report {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne//多対1
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

	@ManyToMany
	@JoinColumn(name = "followed_id", referencedColumnName = "employee_id")
	private List<Relationship> relationship;
	//SELECT reports.content FROM Reports INNER JOIN Relationships ON reports.employee_id = relationships.followed_id WHERE relationships.follower_id = 1

	@Column(name = "report_date", nullable = false)
	private Date report_date;

	@Column(name = "title", length = 255, nullable = false)
	private String title;

	@Lob//テキストエリアの指定(改行も含めてDBに保存できる)
	@Column(name = "content", nullable = false)
	private String content;


	@Column(name = "created_at", nullable = false)
	private Timestamp created_at;

	@Column(name = "updated_at", nullable = false)
	private Timestamp updated_at;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getReport_date() {
		return report_date;
	}

	public void setReport_date(Date report_date) {
		this.report_date = report_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
}