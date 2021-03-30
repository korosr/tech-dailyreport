package models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
			name = "getOneReport",
			query = "SELECT r FROM Report AS r WHERE r.id = :id"
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
})
@NamedNativeQueries ({
	@NamedNativeQuery(
			name="getFollowedReports",
			query="SELECT r.content, r.report_date, r.title, r.id, e.name  FROM reports r JOIN relationships s on r.employee_id = s.followed_id JOIN employees e ON r.employee_id = e.id WHERE s.follower_id = ?",
			resultClass = Report.class
		),
	@NamedNativeQuery(
			name="getCountFollowedReports",
			query="SELECT COUNT(r.id)  FROM reports r JOIN relationships s on r.employee_id = s.followed_id JOIN employees e ON r.employee_id = e.id WHERE s.follower_id = ?"
		),
})

@Entity
public class Report {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

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

	@ManyToOne
	@JoinColumn(name = "timecard_id", nullable = true)
	private TimeCard timecard;

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

	public TimeCard getTimecard() {
		return timecard;
	}

	public void setTimecard(TimeCard timecard) {
		this.timecard = timecard;
	}

}