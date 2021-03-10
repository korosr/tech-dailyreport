package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "relationships")
@NamedQueries({
    @NamedQuery(
        name = "followCheck",
        query = "SELECT COUNT(r) FROM Relationship AS r WHERE r.follower_id = :follower_id and r.followed_id = :followed_id"
    ),
    @NamedQuery(
        name = "deleteRelationship",
        query = "DELETE FROM Relationship AS r WHERE r.followed_id = :followed_id and r.follower_id = :follower_id"
    ),
})

@Entity
public class Relationship {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	//フォローされている人のID
	@Column(name = "followed_id", nullable = false)
	private Integer followed_id;

	//フォローしている人のID
	@Column(name = "follower_id", nullable = false)
	private Integer follower_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getFollowed_id() {
		return followed_id;
	}

	public void setFollowed_id(int followed_id) {
		this.followed_id = followed_id;
	}

	public int getFollower_id() {
		return follower_id;
	}

	public void setFollower_id(int follower_id) {
		this.follower_id = follower_id;
	}
}